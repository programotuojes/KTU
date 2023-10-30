#include <llvm/IR/Value.h>
#include <llvm/IR/LLVMContext.h>
#include <llvm/IR/IRBuilder.h>
#include <llvm/IR/Module.h>
#include <llvm/IR/LegacyPassManager.h>
#include <llvm/Support/raw_ostream.h>
#include <llvm/IR/Module.h>
#include <llvm/Bitcode/BitcodeWriterPass.h>
#include <llvm/IR/IRPrintingPasses.h>
#include <llvm/Support/raw_ostream.h>
#include <llvm/Support/FileSystem.h>
#include <llvm/Support/Host.h>
#include <llvm/Support/FormattedStream.h>
#include "CodeGen.h"
#include "ASTNodes.h"
#include "TypeSystem.h"

#include <iostream>

using legacy::PassManager;

#define ISTYPE(value, id) (value->getType()->getTypeID() == id)

static Type* TypeOf(const NIdentifier& type, CodeGenContext& context) {
    return context.typeSystem.getVarType(type);
}

static Value* CastToBoolean(CodeGenContext& context, Value* condValue) {
    if (ISTYPE(condValue, Type::IntegerTyID)) {
        condValue = context.builder.CreateIntCast(condValue, Type::getInt1Ty(context.llvmContext), true);
        return context.builder.CreateICmpNE(condValue, ConstantInt::get(Type::getInt1Ty(context.llvmContext), 0, true));
    } else {
        return condValue;
    }
}

void CodeGenContext::generateCode(NBlock& root) {
    std::vector<Type*> sysArgs;
    FunctionType* mainFuncType = FunctionType::get(Type::getVoidTy(this->llvmContext), makeArrayRef(sysArgs), false);
    Function* mainFunc = Function::Create(mainFuncType, GlobalValue::ExternalLinkage, "main");
    BasicBlock* block = BasicBlock::Create(this->llvmContext, "entry");

    pushBlock(block);
    Value* retValue = root.codeGen(*this);
    popBlock();

    llvm::legacy::PassManager passManager;
    passManager.add(createPrintFunctionPass(outs()));
    passManager.run(*(this->theModule.get()));
}

llvm::Value* NAssignment::codeGen(CodeGenContext& context) {
    Value* dst = context.getSymbolValue(this->lhs->name);
    auto dstType = context.getSymbolType(this->lhs->name);
    string dstTypeStr = dstType->name;
    if (!dst) {
        LogError("Undeclared variable");
        return nullptr;
    }

    Value* exp = exp = this->rhs->codeGen(context);
    exp = context.typeSystem.cast(exp, context.typeSystem.getVarType(dstTypeStr), context.currentBlock());
    context.builder.CreateStore(exp, dst);
    return dst;
}

llvm::Value* NBinaryOperator::codeGen(CodeGenContext& context) {
    Value* L = this->lhs->codeGen(context);
    Value* R = this->rhs->codeGen(context);

    if (!L || !R) {
        return nullptr;
    }

    switch (this->op) {
        case TPLUS:
            return context.builder.CreateAdd(L, R, "addtmp");
        case TMINUS:
            return context.builder.CreateSub(L, R, "subtmp");
        case TMUL:
            return context.builder.CreateMul(L, R, "multmp");
        case TDIV:
            return context.builder.CreateSDiv(L, R, "divtmp");
        case TMOD:
            return context.builder.CreateSRem(L, R, "remtmp");
        case TAND:
            return context.builder.CreateAnd(L, R, "andtmp");
        case TOR:
            return context.builder.CreateOr(L, R, "ortmp");

        case TCLT:
            return context.builder.CreateICmpULT(L, R, "cmptmp");
        case TCLE:
            return context.builder.CreateICmpSLE(L, R, "cmptmp");
        case TCGE:
            return context.builder.CreateICmpSGE(L, R, "cmptmp");
        case TCGT:
            return context.builder.CreateICmpSGT(L, R, "cmptmp");
        case TCEQ:
            return context.builder.CreateICmpEQ(L, R, "cmptmp");
        case TCNE:
            return context.builder.CreateICmpNE(L, R, "cmptmp");
        default:
            LogError("Unknown binary operator");
            return nullptr;
    }
}

llvm::Value* NBlock::codeGen(CodeGenContext& context) {
    Value* last = nullptr;

    for (auto it = this->statements->begin(); it != this->statements->end(); it++) {
        last = (*it)->codeGen(context);
    }

    return last;
}

llvm::Value* NInt::codeGen(CodeGenContext& context) {
    return ConstantInt::get(Type::getInt32Ty(context.llvmContext), this->value, true);
}

llvm::Value* NIdentifier::codeGen(CodeGenContext& context) {
    Value* value = context.getSymbolValue(this->name);

    if (!value) {
        LogError("Unknown variable name " + this->name);
        return nullptr;
    }

    return context.builder.CreateLoad(value, false, "");
}

llvm::Value* NExpressionStatement::codeGen(CodeGenContext& context) {
    return this->expression->codeGen(context);
}

llvm::Value* NFunctionDeclaration::codeGen(CodeGenContext& context) {
    std::vector<Type*> argTypes;

    for (auto& arg: *this->arguments) {
        argTypes.push_back(TypeOf(*arg->type, context));
    }
    
    Type* retType = TypeOf(*this->type, context);
    FunctionType* functionType = FunctionType::get(retType, argTypes, false);
    Function* function = Function::Create(functionType, GlobalValue::ExternalLinkage, this->id->name.c_str(), context.theModule.get());

    if (!this->isExternal) {
        BasicBlock* basicBlock = BasicBlock::Create(context.llvmContext, "entry", function, nullptr);

        context.builder.SetInsertPoint(basicBlock);
        context.pushBlock(basicBlock);

        auto origin_arg = this->arguments->begin();

        for (auto& ir_arg_it: function->args()) {
            ir_arg_it.setName((*origin_arg)->id->name);
            Value* argAlloc = (*origin_arg)->codeGen(context);

            context.builder.CreateStore(&ir_arg_it, argAlloc, false);
            context.setSymbolValue((*origin_arg)->id->name, argAlloc);
            context.setSymbolType((*origin_arg)->id->name, (*origin_arg)->type);
            context.setFuncArg((*origin_arg)->id->name, true);
            origin_arg++;
        }

        this->block->codeGen(context);
        context.popBlock();
        context.clearDefers();
    }

    return function;
}

llvm::Value* NMethodCall::codeGen(CodeGenContext& context) {
    Function* calleeF = context.theModule->getFunction(this->id->name);
    
    std::vector<Value*> argsv;
    for (auto it = this->arguments->begin(); it != this->arguments->end(); it++) {
        argsv.push_back((*it)->codeGen(context));

        if (!argsv.back()) {
            return nullptr;
        }
    }

    return context.builder.CreateCall(calleeF, argsv, "calltmp");
}

llvm::Value* NDefer::codeGen(CodeGenContext& context) {
    context.addDefer(this->funcCall);
    return nullptr;
}

llvm::Value* NVariableDeclaration::codeGen(CodeGenContext& context) {
    Type* type = TypeOf(*this->type, context);
    Value* initial = nullptr;
    Value* inst = nullptr;

    inst = context.builder.CreateAlloca(type);

    context.setSymbolType(this->id->name, this->type);
    context.setSymbolValue(this->id->name, inst);

    if (this->assignmentExpr != nullptr) {
        NAssignment assignment(this->id, this->assignmentExpr);
        assignment.codeGen(context);
    }

    return inst;
}

llvm::Value* NReturnStatement::codeGen(CodeGenContext& context) {
    Value* returnValue = this->expression->codeGen(context);
    context.execDefers(context);
    context.builder.CreateRet(returnValue);
    return returnValue;
}

llvm::Value* NIfStatement::codeGen(CodeGenContext& context) {
    Value* condValue = this->condition->codeGen(context);

    if (!condValue) {
        return nullptr;
    }

    condValue = CastToBoolean(context, condValue);
    Function* theFunction = context.builder.GetInsertBlock()->getParent();
    BasicBlock *thenBB = BasicBlock::Create(context.llvmContext, "then", theFunction);
    BasicBlock *falseBB = BasicBlock::Create(context.llvmContext, "else");
    BasicBlock *mergeBB = BasicBlock::Create(context.llvmContext, "ifcont");

    if (this->falseBlock) {
        context.builder.CreateCondBr(condValue, thenBB, falseBB);
    } else {
        context.builder.CreateCondBr(condValue, thenBB, mergeBB);
    }

    context.builder.SetInsertPoint(thenBB);
    context.pushBlock(thenBB);
    this->trueBlock->codeGen(context);
    context.popBlock();
    thenBB = context.builder.GetInsertBlock();

    if (thenBB->getTerminator() == nullptr) {       
        context.builder.CreateBr(mergeBB);
    }

    if (this->falseBlock) {
        theFunction->getBasicBlockList().push_back(falseBB);    
        context.builder.SetInsertPoint(falseBB);            
        context.pushBlock(thenBB);
        this->falseBlock->codeGen(context);
        context.popBlock();
        context.builder.CreateBr(mergeBB);
    }

    theFunction->getBasicBlockList().push_back(mergeBB);        
    context.builder.SetInsertPoint(mergeBB);        

    return nullptr;
}

llvm::Value* NForStatement::codeGen(CodeGenContext& context) {
    Function* theFunction = context.builder.GetInsertBlock()->getParent();

    BasicBlock *block = BasicBlock::Create(context.llvmContext, "forloop", theFunction);
    BasicBlock *after = BasicBlock::Create(context.llvmContext, "forcont");

    if (this->initial) {
        this->initial->codeGen(context);
    }

    Value* condValue = this->condition->codeGen(context);
    if (!condValue) {
        return nullptr;
    }

    condValue = CastToBoolean(context, condValue);
    context.builder.CreateCondBr(condValue, block, after);
    context.builder.SetInsertPoint(block);
    context.pushBlock(block);
    this->block->codeGen(context);
    context.popBlock();

    if (this->increment) {
        this->increment->codeGen(context);
    }

    condValue = this->condition->codeGen(context);
    condValue = CastToBoolean(context, condValue);
    context.builder.CreateCondBr(condValue, block, after);

    theFunction->getBasicBlockList().push_back(after);
    context.builder.SetInsertPoint(after);

    return nullptr;
}

llvm::Value *NLiteral::codeGen(CodeGenContext& context) {
    return context.builder.CreateGlobalString(this->value, "string");
}

void LogError(const string str) {
    fprintf(stderr, "LogError: %s\n", str.c_str());
}
