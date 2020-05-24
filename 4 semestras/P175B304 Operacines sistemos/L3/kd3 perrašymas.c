/*
1. Darydamas uzduotis naudojuosi tik LD1, LD2, LD3 aprasais,
   sistemos pagalbos sistema (man ir pan.), savo darytomis LD uzduotimis
2. Užduoti darau savarankiškai be treciuju asmenu pagalbos
3. Uzduoti koreguoju naudodamasis vienu kompiuteriu

Gustas Klevinskas, sutinku
*/

#define _XOPEN_SOURCE 500
#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <ftw.h>
#include <fcntl.h>
#include <unistd.h>

int simple_files = 0;
int sparse_files = 0;

int func(const char *path, const struct stat *st, int flag, struct FTW *fbuf) {
    if (st->st_mode & S_IRGRP) {
        if (flag == FTW_F) {
            printf("%s - paprastas failas\n", path);
            simple_files++;
        } else if (st->st_size > st->st_blocks * st->st_blksize) {
            printf("%s - sparse failas\n", path);
            sparse_files++;
        } else {
            printf("%s\n", path);
        }
    }

    return 0;
}

int main(int argc, char *argv[]) {
    printf("(C) 2020 Gustas Klevinskas, %s\n", __FILE__);

    if (argc == 2)
        printf("Nurodytas 1 argumentas: %s\n", argv[1]);
    else if (argc == 3)
        printf("Nurodyti 2 argumentai: %s %s\n", argv[1], argv[2]);
    else {
        printf("Naudojimas:\n %s katalogas [failas]\n", argv[0]);
        exit(253);
    }

    printf("%s katalogo TURINYS:\n", argv[1]);

    int nftw_ret = nftw(argv[1], func, 20, FTW_PHYS);
    if (nftw_ret == -1) {
        printf("ntfw() klaida\n");
        abort();
    }

    printf("VISO: %i yra paprasti failai ir %i - sparse tipo failai\n", simple_files, sparse_files);

    if (argc == 3) {
        int file = open(argv[2], O_RDWR);
        if (file == -1) {
            printf("Failas %s neegzistuoja arba jo negalima atidaryti/modifikuoti\n", argv[2]);
            abort();
        }

        struct stat sb;
        int file_stat = fstat(file, &sb);
        if (file_stat == -1) {
            printf("Nepavyko gauti failo %s statistikos\n", argv[2]);
            abort();
        }

        long file_size = sb.st_size;
        printf("Failo dydis: %ld B\n", file_size);
        if (file_size % 2 != 0) {
            printf("Failo dydis nesidalija į dvi lygias dalis\n");
            exit(254);
        }

        printf("Keičiamos dalys: %ld B ir %ld B\n", file_size / 2, file_size / 2);

        // TODO swap halves

        int close_file = close(file);
        if (close_file == -1) {
            printf("Nepavyko uždaryti failo %s\n", argv[2]);
            abort();
        }

        printf("Sėkmingai pakeista\n");
    }

    return 0;
}
