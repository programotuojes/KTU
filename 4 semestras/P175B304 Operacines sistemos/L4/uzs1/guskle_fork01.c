#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>

int main(int argc, char *argv[]) {
    pid_t pid1, pid2, pid3;

    pid1 = getpid();
    printf(" %ld\n", (long) pid1);

    pid2 = fork();
    pid3 = fork();

    if (pid2 == 0)
        printf(" |_ %ld\n", (long) getpid());
    else if (pid3 == 0)
        printf("    |_ %ld\n", (long) getpid());

    sleep(1);
    return 0;
}
