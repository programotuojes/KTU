package edu.ktu.ds.lab3.demo;

import edu.ktu.ds.lab3.utils.HashMap;
import edu.ktu.ds.lab3.utils.HashType;
import edu.ktu.ds.lab3.utils.Ks;
import edu.ktu.ds.lab3.utils.ParsableHashMap;
import edu.ktu.ds.lab3.utils.ParsableMap;

import java.util.Locale;

public class ManualTest {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // suvienodiname skaičių formatus
        executeTest();
    }

    public static void executeTest() {
        Car c1 = new Car("Renault", "Laguna", 1997, 50000, 1700);
        Car c2 = new Car("Renault", "Megane", 2001, 20000, 3500);
        Car c3 = new Car("Toyota", "Corolla", 2001, 20000, 8500.8);
        Car c4 = new Car("Renault Laguna 2001 115900 7500");
        Car c5 = new Car.Builder().buildRandom();
        Car c6 = new Car("Honda   Civic  2007  36400 8500.3");
        Car c7 = new Car("Renault Laguna 2001 115900 7500");

        // Raktų masyvas
        String[] carsIds = {"TA156", "TA102", "TA178", "TA171", "TA105", "TA106", "TA107", "TA108"};
        int id = 0;
        ParsableHashMap<String, Car> carsMap
                = new ParsableHashMap<>(String::new, Car::new, HashType.DIVISION);

        carsMap.put(carsIds[id++], c1);
        carsMap.put(carsIds[id++], c1);
        carsMap.put(carsIds[id++], c1);


        // Reikšmių masyvas
        Car[] cars = { c4, c5, c6, c7};
        for (Car c : cars) {
            carsMap.put(carsIds[id++], c);
        }
        carsMap.println("Porų išsidėstymas atvaizdyje pagal raktus");
        Ks.oun("Ar egzistuoja pora atvaizdyje?");
        Ks.oun(carsMap.contains(carsIds[6]));
        Ks.oun(carsMap.contains(carsIds[7]));
        Ks.oun("Pašalinamos poros iš atvaizdžio:");
        Ks.oun(carsMap.remove(carsIds[1]));
        Ks.oun(carsMap.remove(carsIds[7]));
        carsMap.println("Porų išsidėstymas atvaizdyje pagal raktus");
        Ks.oun("Atliekame porų paiešką atvaizdyje:");
        Ks.oun(carsMap.get(carsIds[2]));
        Ks.oun(carsMap.get(carsIds[7]));
        Ks.oun("Išspausdiname atvaizdžio poras String eilute:");
        Ks.ounn(carsMap);

        Ks.oun(carsMap.toString());
        carsMap.replaceAll(c1, new Car("Toyota", "Supra", 1999, 200, 69000d));
        Ks.oun(carsMap.toString());

        Ks.oun(carsMap.get(carsIds[0]));
        Ks.oun(carsMap.replace(carsIds[0], c1, new Car("Datsun", "S30", 1970, 2, 690d)));
        Ks.oun(carsMap.get(carsIds[0]));

        Ks.oun(carsMap.containsValue(c1));
        Ks.oun(carsMap.containsValue(c2));
        Ks.oun(carsMap.containsValue(c3));
        Ks.oun(carsMap.containsValue(c4));
        Ks.oun(carsMap.containsValue(c5));
        Ks.oun(carsMap.containsValue(c6));
        Ks.oun(carsMap.containsValue(c7));
        Ks.oun(carsMap.containsValue(new Car()));


        Ks.oun(carsMap.numberOfEmpties());
    }
}
