# Laboratorinis darbas Nr.3 - Maišos lentelės duomenų struktūrų tyrimas

## Darbo tikslai

1.  Įsisavinti maišos lentelių sudarymo principus, galimus kolizijų
    sprendimo metodus.

2.  Prasmingai panaudoti maišos lenteles.

3.  Įtvirtinti grafinės vartotojo sąsajos programavimo žinias.

4.  Ištirti skirtingų maišos lentelių realizacijų metodų greitaveiką.

## Atsiskaitymas

1.  Pateikiama atlikta individuali darbo dalis ir ataskaitos elektroninė
    versija.

2.  Operatyviai atliekamos dėstytojo nurodytos užduotys:

    a)  Atlikti sukurto projekto demonstraciją.

    b)  Atlikti nurodytų metodų greitaveikos tyrimą.

    c)  Atsakoma į klausimus apie **edu.ktu.ds.lab3.utils** paketo
        klasių struktūrą ir metodus.

## Darbo eiga

### Duota
Projektas **Lab3\_MaisosLenteles**, kuriame yra pateiktos
toliau naudojamos sisteminės klasės, grafinės JavaFX aplinkos klasės
(paketas **edu.ktu.ds.lab3.gui)** ir demonstracinė Atvaizdžio ADT
realizacija maišos lentele, kolizijas sprendžianti atskirų grandinėlių
metodu (klasės **edu.ktu.ds.lab3.utils.HashMap ir ParsableHashMap**), o
taip pat demo variantas laboratorinio darbo vykdymui pakete
**edu.ktu.ds.lab3.demo.**

### Reikia sukurti
Sukurkite naują paketą **edu.ktu.ds.lab3.pavarde,** esant
poreikiui į jį perkelkite reikiamas klases iš ankstesnių laboratorinių
darbų, o taip pat atlikite reikalingas užduotis, susijusias su maišos
lentelių kūrimu bei panaudojimu.

### Tyrimo ir analizės dalis

1.  Išnagrinėkite klasės **edu.ktu.ds.lab3.utils.HashMap** metodus,
    ištirkite jų panaudojimą operacijų atlikimui su atvaizdžiu.

2.  Atlikite maišos lentelės greitaveikos tyrimo eksperimentus su faile
    \<lab3\_projekto\_direktorija\>/data/zodynas.txt surašytais
    žodžiais.

### Individuali programavimo dalis

1.  Pagal duotą **Car** klasės pavyzdį sukurkite individualiai
    pasirinktų elementų klases (4-5 komponentai), tenkinančias
    **Parsable** interfeisą. Programinį kodą rašykite į individualų
    paketą **edu.ktu.ds.lab3.pavarde**.

2.  Sudarykite individualiai pasirinktų elementų panaudojimo klasės
    testus su skirtingais duomenų rinkiniais.

3.  Pakete **edu.ktu.ds.lab3.pavarde** sudarykite individualių elementų
    atvaizdžių panaudojimo klasę, kurioje būtų atvaizdžio formavimas,
    poros šalinimas atvaizdyje, raktą atitinkančios reikšmės paieška ir
    pan., panaudojant klasę **edu.ktu.ds.lab3.utils.HashMap** (3
    metodai). Sukurtų metodų veikimą demonstruokite pateiktoje grafinėje
    JavaFX aplinkoje arba sukurkite nuosavą, pasinaudodami paskaitų
    medžiaga.

4.  Klasėje **edu.ktu.ds.lab3.utils.HashMap** realizuokite metodus (1,
    4, 11 metodai):

    a) ***boolean containsValue(Object value)***, grąžinantį true, jei
    atvaizdyje egzistuoja vienas ar daugiau raktų metodo argumente
    nurodytai reikšmei.

    b) ***V putIfAbsent(K key, V value)***. Jei argumente nurodytas raktas
    neturi reikšmės šiame atvaizdyje, tada argumente nurodyta raktas -
    reikšmė pora įrašoma ir grąžinama null. Kitu atveju grąžinama
    atvaizdyje jau egzistuojanti raktą atitinkanti reikšmė.

    c) ***int numberOfEmpties***, grąžinantį maišos lentelės masyvo tuščių
    elementų skaičių.

5.  Klasėje **edu.ktu.ds.lab3.utils.HashMap** sukurkite individualiai
    pagal variantą nurodytus metodus (M4 ir M5 užduotys), atlikite jų
    rankinį testavimą klasėje **ManualTest**.

6.  Pakete **edu.ktu.ds.lab3.pavarde** sukurkite interfeisą
    **edu.ktu.ds.lab3.utils.Map** maišos lentele realizuojančią klasę
    **HashMapOa**, kolizijas sprendžiant individualiai pagal variantą
    nurodytu atviros adresacijos metodu: tiesinio dėstymo, kvadratinio
    dėstymo ar dvigubos maišos (M6 užduotis).

7.  Atlikite pagal variantą nurodytą greitaveikos tyrimą (G1, G2
    užduotis), sudarykite vykdymo laikų grafikus ir atlikite rezultatų
    analizę. Tai atlikite su Jūsų individualios klasės atvaizdžių
    poromis ir su faile \<lab3\_projekto\_direktorija\>/data/zodynas.txt
    esančiais žodžiais (tyrimui užtenka, kad raktas ir reikšmė būtų tas
    pats žodis).
