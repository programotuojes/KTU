%{
#include <stdio.h>
#include <string>
#include "ASTNodes.h"
#include "grammar.hpp"
#define SAVE_TOKEN yylval.string = new string(yytext)
#define TOKEN(t) ( yylval.token = t)

static FILE* yyparse_file_ptr;

%}

%option noyywrap

%%

[ \t\r\n]               ;

"int"                   SAVE_TOKEN; return TINT;
"string"                SAVE_TOKEN; return TSTRING;
[0-9]+                  SAVE_TOKEN; return TINTLITERAL;
\"(\\.|[^"])*\"         SAVE_TOKEN; return TLITERAL;

"if"                    return TOKEN(TIF);
"else"                  return TOKEN(TELSE);
"return"                return TOKEN(TRETURN);
"defer"                 return TOKEN(TDEFER);
"for"                   return TOKEN(TFOR);
"while"                 return TOKEN(TWHILE);
"extern"                SAVE_TOKEN; return TEXTERN;
[a-zA-Z_][a-zA-Z0-9_]*  SAVE_TOKEN; return TIDENTIFIER;

"="                     return TOKEN(TEQUAL);
"=="                    return TOKEN(TCEQ);
"!="                    return TOKEN(TCNE);
"<"                     return TOKEN(TCLT);
"<="                    return TOKEN(TCLE);
">"                     return TOKEN(TCGT);
">="                    return TOKEN(TCGE);

"("                     return TOKEN(TLPAREN);
")"                     return TOKEN(TRPAREN);
"{"                     return TOKEN(TLBRACE);
"}"                     return TOKEN(TRBRACE);

","                     return TOKEN(TCOMMA);
";"                     return TOKEN(TSEMICOLON);

"+"                     return TOKEN(TPLUS);
"-"                     return TOKEN(TMINUS);
"*"                     return TOKEN(TMUL);
"/"                     return TOKEN(TDIV);
"&&"                    return TOKEN(TAND);
"||"                    return TOKEN(TOR);
"%"                     return TOKEN(TMOD);

.                       printf("Unknown token:%s\n", yytext); yyterminate();

%%
