all: compiler

SRC_DIR := src
BUILD_DIR := build

$(info $(shell mkdir -p $(BUILD_DIR)))

SRC_FILES := $(wildcard $(SRC_DIR)/*.cpp)
OBJ_FILES := $(patsubst $(SRC_DIR)/%.cpp,$(BUILD_DIR)/%.o,$(SRC_FILES))
GRMR_OBJ := $(BUILD_DIR)/grammar.o $(BUILD_DIR)/token.o

INC = -Iinclude -Ibuild
LLVMCONFIG = llvm-config
CPPFLAGS = `$(LLVMCONFIG) --cppflags` -std=c++17 -g $(INC)
LDFLAGS = `$(LLVMCONFIG) --ldflags` -lpthread -ldl -lz -lncurses -rdynamic -L/usr/local/lib
LIBS = `$(LLVMCONFIG) --libs`

clean:
	$(RM) -r $(BUILD_DIR) compiler

$(BUILD_DIR)/grammar.cpp: $(SRC_DIR)/grammar.y
	bison -d -o $@ $<

$(BUILD_DIR)/grammar.hpp: $(BUILD_DIR)/grammar.cpp

$(BUILD_DIR)/token.cpp: $(SRC_DIR)/token.l $(BUILD_DIR)/grammar.hpp
	flex -o $@ $<

$(GRMR_OBJ): $(BUILD_DIR)/%.o: $(BUILD_DIR)/%.cpp
	clang++ -c $(CPPFLAGS) -o $@ $<

$(OBJ_FILES): $(BUILD_DIR)/%.o: $(SRC_DIR)/%.cpp $(BUILD_DIR)/grammar.cpp $(BUILD_DIR)/token.cpp
	clang++ -c $(CPPFLAGS) -o $@ $<

compiler: $(OBJ_FILES) $(GRMR_OBJ)
	clang++ $(CPPFLAGS) -o $@ $^ $(LIBS) $(LDFLAGS)
