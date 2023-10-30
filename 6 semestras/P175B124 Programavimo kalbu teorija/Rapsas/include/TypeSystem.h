#ifndef TYPESYSTEM_H
#define TYPESYSTEM_H

#include <llvm/IR/Type.h>
#include <llvm/IR/Value.h>
#include <llvm/IR/LLVMContext.h>
#include <llvm/IR/IRBuilder.h>
#include <llvm/IR/Module.h>
#include <string>
#include <map>
#include <vector>
#include "ASTNodes.h"

using namespace llvm;
using std::string;
using std::pair;
using std::map;
using TypeNamePair = pair<string, string>;

class TypeSystem{
private:
    LLVMContext& llvmContext;
    map<Type*, map<Type*, CastInst::CastOps>> _castTable;
    void addCast(Type* from, Type* to, CastInst::CastOps op);

public:
    Type* intTy = Type::getInt32Ty(llvmContext);
    Type* stringTy = Type::getInt8PtrTy(llvmContext);

    TypeSystem(LLVMContext& context);

    Type* getVarType(const NIdentifier& type);
    Type* getVarType(string typeStr);

    Value* getDefaultValue(string typeStr, LLVMContext &context);
    Value* cast(Value* value, Type* type, BasicBlock* block);

    static string llvmTypeToStr(Value* value);
    static string llvmTypeToStr(Type* type);
};

#endif // TYPESYSTEM_H
