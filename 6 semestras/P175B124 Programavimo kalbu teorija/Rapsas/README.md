## Reikalingi įrankiai
 - clang
 - llvm
 - bison
 - flex

## Paleidimas
1. Kompiliatorių sukompiliuoti su make.
2. Sukompiliuoti programą (pavyzdžiai pateikti examples kataloge).
3. Su'link'inti gautą `out.o` failą (gali tekti nurodyti `-no-pie` pasirinkimą).

```
make
./compiler examples/defer
gcc -no-pie out.o -o defer
```
