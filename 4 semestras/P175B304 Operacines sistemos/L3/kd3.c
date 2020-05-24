/*
1. Darydamas uzduotis naudojuosi tik LD1, LD2, LD3 aprasais,
   sistemos pagalbos sistema (man ir pan.), savo darytomis LD uzduotimis
2. Užduoti darau savarankiškai be treciuju asmenu pagalbos
3. Uzduoti koreguoju naudodamasis vienu kompiuteriu

Gustas Klevinskas SUTINKU
*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/resource.h>
#include <sys/mman.h>

int main(int argc, char *argv[]) {
    struct stat sb1;
    struct stat sb2;
    struct rlimit rl;

    if (argc == 1 || argc > 3) {
        printf("Naudojimas:\n %s failas1 [failas2]\n", argv[0]);
        exit(255);
    }

    FILE *file = fopen(argv[1], "r+");
    if (!file) {
        file = fopen(argv[1], "w+");
        fseek(file, 4095, SEEK_SET);
        fputc('a', file);
        fclose(file);
        return 0;
    }

    lstat(argv[1], &sb1);

    int pos = sb1.st_size / 2 - 1;

    fseek(file, pos, SEEK_SET);
    fputc('S', file);

    if (argc == 3) {

        rl.rlim_cur = 5;    // TODO change to 5

        if (setrlimit(RLIMIT_CPU, &rl) == -1)
            abort();

        if (lstat(argv[1], &sb1) == -1 || lstat(argv[2], &sb2) == -1)
            abort();

        int fd1 = fileno(file);
        int fd2 = open(argv[3]);

        void *map1 = mmap(NULL, sb1.st_size, PROT_READ, MAP_SHARED, fd1, 0);
        void *map2 = mmap(NULL, sb2.st_size, PROT_READ | PROT_WRITE, MAP_SHARED, fd2, 0);

        while (1);
    }

    fclose(file);

    return 0;
}
