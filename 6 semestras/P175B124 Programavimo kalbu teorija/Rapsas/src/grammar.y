%{
#include "ASTNodes.h"
#include <stdio.h>
NBlock* programBlock;
extern int yylex();
void yyerror(const char* s) {
    printf("Error: %s\n", s);
    exit(1);
}

%}

%union {
    NBlock* block;
    NExpression* expr;
    NStatement* stmt;
    NIdentifier* ident;
    NMethodCall* fcall;
    NVariableDeclaration* var_decl;
    std::vector<shared_ptr<NVariableDeclaration>>* varvec;
    std::vector<shared_ptr<NExpression>>* exprvec;
    std::string* string;
    int token;
}

%token <string> TIDENTIFIER TINT TSTRING TINTLITERAL TLITERAL TEXTERN TDEFER
%token <token> TCEQ TCNE TCLT TCLE TCGT TCGE TEQUAL
%token <token> TLPAREN TRPAREN TLBRACE TRBRACE TCOMMA TSEMICOLON
%token <token> TPLUS TMINUS TMUL TDIV TAND TOR TMOD
%token <token> TIF TELSE TFOR TWHILE TRETURN

%type <ident> ident typename
%type <fcall> func_call
%type <expr> expr assign
%type <varvec> func_decl_args
%type <exprvec> call_args
%type <block> program stmts block
%type <stmt> stmt var_decl func_par_decl func_decl if_stmt for_stmt while_stmt
%type <token> comparison

%left TPLUS TMINUS
%left TMUL TDIV TMOD

%start program

%%

program
    : stmts { programBlock = $1; }
    ;

stmts
    : stmt { $$ = new NBlock(); $$->statements->push_back(shared_ptr<NStatement>($1)); }
    | stmts stmt { $1->statements->push_back(shared_ptr<NStatement>($2)); }
    ;

stmt
    : var_decl
    | func_decl
    | expr { $$ = new NExpressionStatement(shared_ptr<NExpression>($1)); }
    | TRETURN expr { $$ = new NReturnStatement(shared_ptr<NExpression>($2)); }
    | if_stmt
    | for_stmt
    | while_stmt
    ;

block
    : TLBRACE stmts TRBRACE { $$ = $2; }
    | TLBRACE TRBRACE { $$ = new NBlock(); }
    ;

typename
    : TINT { $$ = new NIdentifier(*$1); $$->isType = true; delete $1; }
    | TSTRING { $$ = new NIdentifier(*$1); $$->isType = true; delete $1; }
    ;

var_decl
    : typename ident
        { $$ = new NVariableDeclaration(shared_ptr<NIdentifier>($1), shared_ptr<NIdentifier>($2), nullptr); }
    | typename ident TEQUAL expr
        { $$ = new NVariableDeclaration(shared_ptr<NIdentifier>($1), shared_ptr<NIdentifier>($2), shared_ptr<NExpression>($4)); }
    ;

func_decl
    : typename ident TLPAREN func_decl_args TRPAREN block
        { $$ = new NFunctionDeclaration(shared_ptr<NIdentifier>($1), shared_ptr<NIdentifier>($2), shared_ptr<VariableList>($4), shared_ptr<NBlock>($6)); }
    | TEXTERN typename ident TLPAREN func_decl_args TRPAREN
        { $$ = new NFunctionDeclaration(shared_ptr<NIdentifier>($2), shared_ptr<NIdentifier>($3), shared_ptr<VariableList>($5), nullptr, true); }
    ;

func_par_decl
    : typename ident { $$ = new NVariableDeclaration(shared_ptr<NIdentifier>($1), shared_ptr<NIdentifier>($2), nullptr);}
    ;

func_decl_args
    : { $$ = new VariableList(); }
    | func_par_decl { $$ = new VariableList(); $$->push_back(shared_ptr<NVariableDeclaration>($<var_decl>1)); }
    | func_decl_args TCOMMA func_par_decl { $1->push_back(shared_ptr<NVariableDeclaration>($<var_decl>3)); }
    ;

ident
    : TIDENTIFIER { $$ = new NIdentifier(*$1); delete $1; }
    ;

func_call
    : ident TLPAREN call_args TRPAREN { $$ = new NMethodCall(shared_ptr<NIdentifier>($1), shared_ptr<ExpressionList>($3)); }
    ;

expr
    : assign { $$ = $1; }
    | TDEFER func_call { $$ = new NDefer(shared_ptr<NMethodCall>($2)); }
    | func_call { $$ = $1; }
    | ident { $<ident>$ = $1; }
    | TINTLITERAL { $$ = new NInt(atol($1->c_str())); }
    | TLITERAL { $$ = new NLiteral(*$1); delete $1; }
    | expr comparison expr { $$ = new NBinaryOperator(shared_ptr<NExpression>($1), $2, shared_ptr<NExpression>($3)); }
    | expr TMOD expr { $$ = new NBinaryOperator(shared_ptr<NExpression>($1), $2, shared_ptr<NExpression>($3)); }
    | expr TMUL expr { $$ = new NBinaryOperator(shared_ptr<NExpression>($1), $2, shared_ptr<NExpression>($3)); }
    | expr TDIV expr { $$ = new NBinaryOperator(shared_ptr<NExpression>($1), $2, shared_ptr<NExpression>($3)); }
    | expr TPLUS expr { $$ = new NBinaryOperator(shared_ptr<NExpression>($1), $2, shared_ptr<NExpression>($3)); }
    | expr TMINUS expr { $$ = new NBinaryOperator(shared_ptr<NExpression>($1), $2, shared_ptr<NExpression>($3)); }
    | TLPAREN expr TRPAREN { $$ = $2; }
    ;

assign
    : ident TEQUAL expr { $$ = new NAssignment(shared_ptr<NIdentifier>($1), shared_ptr<NExpression>($3)); }
    ;

call_args
    : { $$ = new ExpressionList(); }
    | expr { $$ = new ExpressionList(); $$->push_back(shared_ptr<NExpression>($1)); }
    | call_args TCOMMA expr { $1->push_back(shared_ptr<NExpression>($3)); }
    ;

comparison
    : TCEQ
    | TCNE
    | TCLT
    | TCLE
    | TCGT
    | TCGE
    | TAND
    | TOR
    ;

if_stmt
    : TIF expr block { $$ = new NIfStatement(shared_ptr<NExpression>($2), shared_ptr<NBlock>($3)); }
    | TIF expr block TELSE block { $$ = new NIfStatement(shared_ptr<NExpression>($2), shared_ptr<NBlock>($3), shared_ptr<NBlock>($5)); }
    | TIF expr block TELSE if_stmt { 
        auto blk = new NBlock(); 
        blk->statements->push_back(shared_ptr<NStatement>($5)); 
        $$ = new NIfStatement(shared_ptr<NExpression>($2), shared_ptr<NBlock>($3), shared_ptr<NBlock>(blk)); 
    }
    ;

for_stmt
    : TFOR TLPAREN expr TSEMICOLON expr TSEMICOLON expr TRPAREN block
        { $$ = new NForStatement(shared_ptr<NBlock>($9), shared_ptr<NExpression>($3), shared_ptr<NExpression>($5), shared_ptr<NExpression>($7)); }
    ;

while_stmt
    : TWHILE TLPAREN expr TRPAREN block
        { $$ = new NForStatement(shared_ptr<NBlock>($5), nullptr, shared_ptr<NExpression>($3), nullptr); }
    ;

%%
