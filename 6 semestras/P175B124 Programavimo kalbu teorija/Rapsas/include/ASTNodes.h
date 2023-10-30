
#ifndef ASTNODES_H
#define ASTNODES_H

#include <llvm/IR/Value.h>
#include <iostream>
#include <vector>
#include <memory>
#include <string>

using std::cout;
using std::endl;
using std::string;
using std::shared_ptr;
using std::unique_ptr;
using std::make_shared;

class CodeGenContext;
class NBlock;
class NStatement;
class NExpression;
class NVariableDeclaration;
class NMethodCall;

typedef std::vector<shared_ptr<NStatement>> StatementList;
typedef std::vector<shared_ptr<NExpression>> ExpressionList;
typedef std::vector<shared_ptr<NVariableDeclaration>> VariableList;
typedef std::vector<shared_ptr<NMethodCall>> DeferList;

class Node {
protected:
    const char m_DELIM = ':';
    const char* m_PREFIX = "--";

public:
    Node() {}
    virtual ~Node() {}
    virtual string getTypeName() const = 0;
    virtual llvm::Value* codeGen(CodeGenContext& context) {
        return (llvm::Value*)0;
    }
};

class NExpression : public Node {
public:
    NExpression() {}

    string getTypeName() const override {
        return "NExpression";
    }
};

class NStatement : public Node {
public:
    NStatement() {}

    string getTypeName() const override {
        return "NStatement";
    }
};

class NInt : public NExpression {
public:
    uint64_t value;
    NInt() {}
    NInt(uint64_t value) : value(value) {}

    string getTypeName() const override {
        return "NInt";
    }

    virtual llvm::Value* codeGen(CodeGenContext& context) override;
};

class NIdentifier : public NExpression {
public:
    string name;
    bool isType = false;

    NIdentifier() {}
    NIdentifier(const string& name) : name(name) {}

    string getTypeName() const override {
        return "NIdentifier";
    }

    virtual llvm::Value* codeGen(CodeGenContext& context) override;
};

class NMethodCall : public NExpression {
public:
    const shared_ptr<NIdentifier> id;
    shared_ptr<ExpressionList> arguments = make_shared<ExpressionList>();

    NMethodCall() {}
    NMethodCall(const shared_ptr<NIdentifier> id, shared_ptr<ExpressionList> arguments)
    : id(id), arguments(arguments) {}

    NMethodCall(const shared_ptr<NIdentifier> id)
    : id(id) {}

    string getTypeName() const override {
        return "NMethodCall";
    }

    virtual llvm::Value* codeGen(CodeGenContext& context) override;
};

class NDefer : public NExpression {
public:
    shared_ptr<NMethodCall> funcCall = make_shared<NMethodCall>();

    NDefer() {}
    NDefer(shared_ptr<NMethodCall> funcCall)
    : funcCall(funcCall) {}

    string getTypeName() const override {
        return "NDefer";
    }

    virtual llvm::Value* codeGen(CodeGenContext& context) override;
};

class NBinaryOperator : public NExpression {
public:
    int op;
    shared_ptr<NExpression> lhs;
    shared_ptr<NExpression> rhs;

    NBinaryOperator() {}
    NBinaryOperator(shared_ptr<NExpression> lhs, int op, shared_ptr<NExpression> rhs)
    : lhs(lhs), rhs(rhs), op(op) {}

    string getTypeName() const override {
        return "NBinaryOperator";
    }

    virtual llvm::Value* codeGen(CodeGenContext& context) override;
};

class NAssignment : public NExpression {
public:
    shared_ptr<NIdentifier> lhs;
    shared_ptr<NExpression> rhs;

    NAssignment() {}
    NAssignment(shared_ptr<NIdentifier> lhs, shared_ptr<NExpression> rhs)
    : lhs(lhs), rhs(rhs) {}

    string getTypeName() const override {
        return "NAssignment";
    }

    virtual llvm::Value* codeGen(CodeGenContext& context) override;
};

class NBlock : public NExpression {
public:
    shared_ptr<StatementList> statements = make_shared<StatementList>();

    NBlock() {}

    string getTypeName() const override {
        return "NBlock";
    }

    virtual llvm::Value* codeGen(CodeGenContext& context) override;
};

class NExpressionStatement : public NStatement {
public:
    shared_ptr<NExpression> expression;

    NExpressionStatement() {}

    NExpressionStatement(shared_ptr<NExpression> expression)
    : expression(expression) {}

    string getTypeName() const override {
        return "NExpressionStatement";
    }

    virtual llvm::Value* codeGen(CodeGenContext& context) override;
};

class NVariableDeclaration : public NStatement {
public:
    const shared_ptr<NIdentifier> type;
    shared_ptr<NIdentifier> id;
    shared_ptr<NExpression> assignmentExpr = nullptr;

    NVariableDeclaration() {}
    NVariableDeclaration(const shared_ptr<NIdentifier> type, shared_ptr<NIdentifier> id, shared_ptr<NExpression> assignmentExpr = nullptr)
    : type(type), id(id), assignmentExpr(assignmentExpr) {
        assert(type->isType);
    }

    string getTypeName() const override {
        return "NVariableDeclaration";
    }

    virtual llvm::Value* codeGen(CodeGenContext& context) override;
};

class NFunctionDeclaration : public NStatement {
public:
    shared_ptr<NIdentifier> type;
    shared_ptr<NIdentifier> id;
    shared_ptr<VariableList> arguments = make_shared<VariableList>();
    shared_ptr<NBlock> block;
    bool isExternal = false;

    NFunctionDeclaration() {}
    NFunctionDeclaration(
        shared_ptr<NIdentifier> type,
        shared_ptr<NIdentifier> id,
        shared_ptr<VariableList> arguments,
        shared_ptr<NBlock> block,
        bool isExt = false)
    : type(type), id(id), arguments(arguments), block(block), isExternal(isExt) {
        assert(type->isType);
    }

    string getTypeName() const override {
        return "NFunctionDeclaration";
    }

    virtual llvm::Value* codeGen(CodeGenContext& context) override;
};

class NReturnStatement : public NStatement {
public:
    shared_ptr<NExpression> expression;
    NReturnStatement() {}
    NReturnStatement(shared_ptr<NExpression> expression)
    : expression(expression) {}

    string getTypeName() const override {
        return "NReturnStatement";
    }

    virtual llvm::Value* codeGen(CodeGenContext& context) override;
};

class NIfStatement : public NStatement {
public:
    shared_ptr<NExpression> condition;
    shared_ptr<NBlock> trueBlock;
    shared_ptr<NBlock> falseBlock;

    NIfStatement() {}
    NIfStatement(shared_ptr<NExpression> cond, shared_ptr<NBlock> blk, shared_ptr<NBlock> blk2 = nullptr)
    : condition(cond), trueBlock(blk), falseBlock(blk2) {}

    string getTypeName() const override {
        return "NIfStatement";
    }

    llvm::Value* codeGen(CodeGenContext& context) override;
};

class NForStatement : public NStatement{
public:
    shared_ptr<NExpression> initial, condition, increment;
    shared_ptr<NBlock> block;

    NForStatement() {}
    NForStatement(
        shared_ptr<NBlock> b,
        shared_ptr<NExpression> init = nullptr,
        shared_ptr<NExpression> cond = nullptr,
        shared_ptr<NExpression> incre = nullptr)
    : block(b), initial(init), condition(cond), increment(incre){
        if (condition == nullptr){
            condition = make_shared<NInt>(1);
        }
    }

    string getTypeName() const override {
        return "NForStatement";
    }

    llvm::Value* codeGen(CodeGenContext& context) override;
};

class NLiteral : public NExpression {
public:
    string value;

    NLiteral() {}
    NLiteral(const string& str) {
        value = str.substr(1, str.length() - 2);
    }

    string getTypeName() const override {
        return "NLiteral";
    }

    llvm::Value* codeGen(CodeGenContext& context) override;
};

void LogError(const string str);

#endif // ASTNODES_H
