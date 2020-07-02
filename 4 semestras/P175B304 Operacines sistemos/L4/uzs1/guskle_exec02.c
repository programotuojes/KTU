#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdlib.h>

#define SCRIPT_PATH "./test.sh"

int main(int argc, char *argv[]) {
    int file = creat(SCRIPT_PATH, S_IRWXU | S_IRWXG | S_IRWXO);

    if (file == -1) {
        perror("creat() failed");
        abort();
    }


    write(file, "#!/bin/sh\n", 10);
    write(file, "echo Working?\n", 15);
    int close_value = close(file);
    if (close_value == -1) {
        perror("close() failed");
        abort();
    }

    char *args[] = { SCRIPT_PATH, '\0' };
    execv(SCRIPT_PATH, args);

    return 0;
}
