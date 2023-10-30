#include <iostream>
#include <fstream>
#include "ASTNodes.h"
#include "CodeGen.h"
#include "ObjGen.h"

extern shared_ptr<NBlock> programBlock;

extern int yyparse(FILE* fp);
extern FILE* yyin;

int main(int argc, char** argv) {
    yyin = fopen(argv[1], "r");
    yyparse();

    CodeGenContext context;
    context.generateCode(*programBlock);
    ObjGen(context);

    return 0;
}
