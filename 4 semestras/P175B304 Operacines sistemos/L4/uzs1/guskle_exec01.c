#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char *argv[]) {
    if (argc != 2) {
        perror("One argument required");
        abort();
    }

    int num = atoi(argv[1]) - 1;

    if (num % 1 != 0) {
        perror("Argument must be a whole number");
        abort();
    }

    printf("PID: %9ld, PPID: %9ld, num: %4s\n", (long) getpid(), (long) getppid(), argv[1]);

    sprintf(argv[1], "%i", num);

    if (num > 0)
        execv(argv[0], argv);

    return 0;
}
