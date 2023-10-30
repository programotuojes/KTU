#ifndef CODEGEN_H
#define CODEGEN_H

#include <llvm/IR/Value.h>
#include <llvm/IR/LLVMContext.h>
#include <llvm/IR/IRBuilder.h>
#include <llvm/IR/Module.h>

#include <stack>
#include <vector>
#include <memory>
#include <string>
#include <map>
#include "ASTNodes.h"
#include "grammar.hpp"
#include "TypeSystem.h"

using namespace llvm;
using std::unique_ptr;
using std::string;
using std::vector;
using std::map;

using SymTable = map<string, Value*>;

class CodeGenBlock {
public:
    BasicBlock* block;
    int numOfBlockDefers = 0;
    Value* returnValue;
    map<string, Value*> locals;
    map<string, shared_ptr<NIdentifier>> types;
    map<string, bool> isFuncArg;
};

class CodeGenContext {
private:
    vector<CodeGenBlock*> blockStack;

public:
    LLVMContext llvmContext;
    IRBuilder<> builder;
    unique_ptr<Module> theModule;
    SymTable globalVars;
    TypeSystem typeSystem;
    shared_ptr<DeferList> defers = make_shared<DeferList>();

    CodeGenContext() : builder(llvmContext), typeSystem(llvmContext) {
        theModule = unique_ptr<Module>(new Module("main", this->llvmContext));
    }

    Value* getSymbolValue(string name) const {
        for(auto it = blockStack.rbegin(); it != blockStack.rend(); it++) {
            if ((*it)->locals.find(name) != (*it)->locals.end()) {
                return (*it)->locals[name];
            }
        }

        return nullptr;
    }

    shared_ptr<NIdentifier> getSymbolType(string name) const{
        for (auto it = blockStack.rbegin(); it != blockStack.rend(); it++) {
            if ((*it)->types.find(name) != (*it)->types.end()) {
                return (*it)->types[name];
            }
        }

        return nullptr;
    }

    bool isFuncArg(string name) const {
        for (auto it = blockStack.rbegin(); it != blockStack.rend(); it++) {
            if ((*it)->isFuncArg.find(name) != (*it)->isFuncArg.end()) {
                return (*it)->isFuncArg[name];
            }
        }

        return false;
    }

    void setSymbolValue(string name, Value* value) {
        blockStack.back()->locals[name] = value;
    }

    void setSymbolType(string name, shared_ptr<NIdentifier> value) {
        blockStack.back()->types[name] = value;
    }

    void setFuncArg(string name, bool value) {
        blockStack.back()->isFuncArg[name] = value;
    }

    BasicBlock* currentBlock() const {
        return blockStack.back()->block;
    }

    void addDefer(shared_ptr<NMethodCall> funcCall) {
        defers->push_back(funcCall);
        blockStack.back()->numOfBlockDefers++;
    }

    void execDefers(CodeGenContext& context) {
        for (auto it = defers->rbegin(); it != defers->rend(); it++) {
            (*it)->codeGen(context);
        }

        for (auto i = 0; i < blockStack.back()->numOfBlockDefers; i++) {
            defers->pop_back();
        }

        blockStack.back()->numOfBlockDefers = 0;
    }

    void clearDefers() {
        defers->clear();
    }

    void pushBlock(BasicBlock* block) {
        CodeGenBlock* codeGenBlock = new CodeGenBlock();
        codeGenBlock->block = block;
        codeGenBlock->returnValue = nullptr;
        blockStack.push_back(codeGenBlock);
    }

    void popBlock() {
        CodeGenBlock* codeGenBlock = blockStack.back();
        blockStack.pop_back();
        delete codeGenBlock;
    }

    void generateCode(NBlock&);
};

#endif // CODEGEN_H
