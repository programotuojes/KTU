#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
    pid_t parent, child;

    parent = getpid();
    child = fork();

    if (child == 0) {
        printf("\nPrevious parent: %ld\n", (long) parent);
        sleep(1);
        printf("Current parent: %ld\n", (long) getppid());
    } else if (child == -1) {
        perror("fork() failed");
        abort();
    }

    return 0;
}
