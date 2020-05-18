/** @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 09 23
 *
 * Tai yra darbo su sąrašais greitaveikos tyrimo klasė.
 * Pavyzdyje pateiktas rikiavimo metodų tyrimas.
 * Tyrimo metu pateiktais metodais naudojamasi kaip šablonais,
 * išbandant įvairius rūšiavimo aspektus.
 *  IŠSIAIŠKINKITE metodų sudarymą, jų paskirtį.
 *  SUDARYKITE sąrašo peržiūros antišablono efektyvumo tyrimą.
 *  PASIRINKITE savo objektų klasę ir sudarykite jų generavimo metodą.
 *************************************************************************** */
package edu.ktu.ds.lab1b.demo;

import edu.ktu.ds.lab1b.util.Ks;
import edu.ktu.ds.lab1b.util.LinkedList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public class SimpleBenchmark {

    Car[] cars;
    LinkedList<Car> carSeries = new LinkedList<>();
    Random rg = new Random();  // atsitiktinių generatorius
    int[] counts = {2_000, 4_000, 8_000, 16_000};
//    pabandykite, gal Jūsų kompiuteris įveiks šiuos eksperimentus
//    paieškokite ekstremalaus apkrovimo be burbuliuko metodo
//    static int[] counts = {10_000, 20_000, 40_000, 80_000};

    void generateCars(int count) {
        String[][] makesAndModels = { // galimų automobilių markių ir jų modelių masyvas
            {"Mazda", "121", "323", "626", "MX6"},
            {"Ford", "Fiesta", "Escort", "Focus", "Sierra", "Mondeo"},
            {"Saab", "92", "96"},
            {"Honda", "Accord", "Civic", "Jazz"},
            {"Renault", "Laguna", "Megane", "Twingo", "Scenic"},
            {"Peugeot", "206", "207", "307"}
        };
        cars = new Car[count];
        rg.setSeed(2017);
        for (int i = 0; i < count; i++) {
            int makeIndex = rg.nextInt(makesAndModels.length);        // markės indeksas  0..
            int modelIndex = rg.nextInt(makesAndModels[makeIndex].length - 1) + 1;// modelio indeksas 1..
            cars[i] = new Car(makesAndModels[makeIndex][0], makesAndModels[makeIndex][modelIndex],
                    1994 + rg.nextInt(20), // metai tarp 1994 ir 2013
                    6000 + rg.nextInt(222_000), // rida tarp 6000 ir 228000
                    1000 + rg.nextDouble() * 350_000); // kaina tarp 1000 ir 351_000
        }
        Collections.shuffle(Arrays.asList(cars));
        carSeries.clear();
        for (Car c : cars) {
            carSeries.add(c);
        }
    }

    void generateAndExecute(int elementCount) {
// Paruošiamoji tyrimo dalis
        long t0 = System.nanoTime();
        generateCars(elementCount);
        LinkedList<Car> carSeries2 = carSeries.clone();
        LinkedList<Car> carSeries3 = carSeries.clone();
        LinkedList<Car> carSeries4 = carSeries.clone();
        long t1 = System.nanoTime();
        System.gc();
        System.gc();
        System.gc();
        long t2 = System.nanoTime();
//  Greitaveikos bandymai ir laiko matavimai
        carSeries.sortSystem();
        long t3 = System.nanoTime();
        carSeries2.sortSystem(Car.byPrice);
        long t4 = System.nanoTime();
        carSeries3.sortBuble();
        long t5 = System.nanoTime();
        carSeries4.sortBuble(Car.byPrice);
        long t6 = System.nanoTime();
        Ks.ouf("%7d %7.4f %7.4f %7.4f %7.4f %7.4f %7.4f \n", elementCount,
                (t1 - t0) / 1e9, (t2 - t1) / 1e9, (t3 - t2) / 1e9,
                (t4 - t3) / 1e9, (t5 - t4) / 1e9, (t6 - t5) / 1e9);
    }

    void execute() {
        long memTotal = Runtime.getRuntime().totalMemory();
        Ks.oun("memTotal= " + memTotal);
        // Pasižiūrime kaip generuoja automobilius (20) vienetų)
        generateCars(20);
        for (Car c : carSeries) {
            Ks.oun(c);
        }
        Ks.oun("1 - Pasiruošimas tyrimui - duomenų generavimas");
        Ks.oun("2 - Pasiruošimas tyrimui - šiukšlių surinkimas");
        Ks.oun("3 - Rūšiavimas sisteminiu greitu būdu be Comparator");
        Ks.oun("4 - Rūšiavimas sisteminiu greitu būdu su Comparator");
        Ks.oun("5 - Rūšiavimas List burbuliuku be Comparator");
        Ks.oun("6 - Rūšiavimas List burbuliuku su Comparator");
        Ks.ouf("%6d %7d %7d %7d %7d %7d %7d \n", 0, 1, 2, 3, 4, 5, 6);
        for (int n : counts) {
            generateAndExecute(n);
        }
    }

    public static void main(String[] args) {
        // suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)
        Locale.setDefault(new Locale("LT"));
        new SimpleBenchmark().execute();
    }
}
