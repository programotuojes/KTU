# Laboratorinis darbas Nr. 1b - Sąrašinių duomenų struktūrų kūrimas

## Darbo tikslai

1. Išmokti kurti klases, tenkinančias nurodytą sąsają;
2. Išmokti atskirų objektų apdorojimo bendriniuose sąrašuose būdus (sukūrimas,
įdėjimas, peržiūra, atranka), pasinaudojant duotos sąsajos metodais;
3. Išmokti testavimo klasių kūrimo pradmenis;

## Atsiskaitymas

1. Pateikiama atlikta individuali darbo dalis ir ataskaitos elektroninė versija;
2. Operatyviai atliekamos dėstytojo nurodytos užduotys:
   * modifikuoti individualią klasę;
   * sukurti ar modifikuoti apdorojimo metodus;
3. Atsakoma į klausimus apie `edu.ktu.ds.lab1b.util` paketo klasių struktūrą ir
metodus.

## Darbo eiga

### Duota

Du paketai (`edu.ktu.ds.lab1b.demo` ir `edu.ktu.ds.lab1b.util`), kuriuose yra
pateiktos toliau naudojamos sisteminės klasės ir demo variantas laboratorinio
darbo vykdymui.

### Reikia sukurti

Naują paketą `edu.ktu.ds.lab1b.pavarde` su klasėmis individualioms
užduotims spręsti.

### Tyrimo ir analizės dalis

1. Išnagrinėti elementarios klasės `Car` struktūrą, išbandyti jos metodus;
2. Išnagrinėti apjungiančios klasės `CarMarket` struktūrą, išbandyti jos metodus;
3. Naudajantis klasės `ManualTest` pavyzdžiu, išbandyti klasių `List` ir
`ParsableList` metodus, ištirti sąrašo metodų sąveiką su `Parsable` tipo elementais;
4. Išbandyti `SimpleBenchmark` klasėje realizuotą greitaveikos tyrimą.

### Individuali klasių konstravimo dalis

1. Pagal duotą `Car` klasės pavyzdį sukurti individualiai pasirinktas elemento
klases (4-5 komponentai), tenkinančias `Parsable` interfeisą; programinį
kodą rašyti į individualų paketą `edu.ktu.ds.lab1b.pavarde`;
2. Patikrinti individualios klasės veikimą testo klasės pagalba;
3. Sudaryti individualių elementų apskaitos klasę, kurioje būtų elementų
peržiūra ir jų atranka pagal įvairius kriterijus;
4. Sudaryti elementų apskaitos klasės demonstracinius metodus;
5. Realizuoti `LinkedList` metodus `add(int k, E e)`, `set(int k, E e)` ir
`remove(int k)`; 
6. Realizuoti individualiai nurodytus metodus;
7. Atlikti individualiai nurodytų metodų greitaveikos tyrimą;
8. Įvertinti sunaudojamos atminties kiekį.

Individualiai pasirenkamų duomenų tipai yra suderinami su laboratorinių darbų
dėstytoju, galimi pavyzdžiai:

* prekės iš didmeninės ir mažmeninės prekybos asortimento;
* elektronikos komponentai;
* kompiuteriai;
* knygos;
* multimedijos kūriniai;
* kelionės;
* sporto varžybų ir dalyvių duomenys;
* kitokie elementai, turintys po 4-5 juos apibūdinančias charakteristikas.
 