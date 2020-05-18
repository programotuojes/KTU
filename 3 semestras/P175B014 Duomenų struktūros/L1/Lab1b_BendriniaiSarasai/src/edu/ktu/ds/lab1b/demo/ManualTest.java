/** @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 09 23
 *
 * Tai yra demonstracinė automobilių sąrašo bandymų klasė, kuri skirta tiesiog
 * veiksmų su sąrašais išbandymui
 *  IŠSIAIŠKINKITE metodų sudarymą, jų paskirtį.
 *  PASIRINKITE savo objektų klasę ir sudarykite analogiškus metodus
 *  GERESNIAM ĮSISAVINIMUI rekomenduojame pradėti nuo tuščios klasės
 *************************************************************************** */
package edu.ktu.ds.lab1b.demo;

import edu.ktu.ds.lab1b.util.Ks;
import java.util.Comparator;
import java.util.Locale;

public class ManualTest {

    CarList cars = new CarList();

    void execute() {
        createCars();
        createCarList();
//        countRenault();
//        appendCarList();
//        checkCarMarketFilters();
//        checkCarMarketSorting();
    }

    void createCars() {
        Car c1 = new Car("Renault", "Laguna", 1997, 50000, 1700);
        Car c2 = new Car("Renault", "Megane", 2001, 20000, 3500);
        Car c3 = new Car("Toyota", "Corolla", 2001, 20000, 8500.8);
        Car c4 = new Car();
        Car c5 = new Car();
        Car c6 = new Car();
        c4.parse("Renault Laguna 2001 115900 7500");
        c5.parse("Renault Megane 1946 365100 9500");
        c6.parse("Honda   Civic  2007  36400 8500,3");

        Ks.oun(c1);
        Ks.oun(c2);
        Ks.oun(c3);
        Ks.oun("Pirmų 3 auto ridos vidurkis= "
                + (c1.getMileage() + c2.getMileage() + c3.getMileage()) / 3);
        Ks.oun(c4);
        Ks.oun(c5);
        Ks.oun(c6);
        Ks.oun("Kitų 3 auto kainų suma= "
                + (c4.getPrice() + c5.getPrice() + c6.getPrice()));
    }

    void createCarList() {
        Car c1 = new Car("Renault", "Laguna", 1997, 50000, 1700);
        Car c2 = new Car("Renault", "Megane", 2001, 20000, 3500);
        Car c3 = new Car("Toyota", "Corolla", 2001, 20000, 8500.8);
        cars.add(c1);
        cars.add(c2);
        cars.add(c3);
        cars.println("Pirmi 3 auto");
        cars.add("Renault Laguna 2001 115900 7500");
        cars.add("Renault Megane 1946 365100 9500");
        cars.add("Honda   Civic  2007  36400 8500,3");

        cars.println("Visi 6 auto");
        cars.forEach(System.out::println);
        Ks.oun("Pirmų 3 auto ridos vidurkis= "
                + (cars.get(0).getMileage() + cars.get(1).getMileage()
                + cars.get(2).getMileage()) / 3);

        Ks.oun("Kitų 3 auto kainų suma= "
                + (cars.get(3).getPrice() + cars.get(4).getPrice()
                + cars.get(5).getPrice()));
        // palaipsniui atidenkite sekančias eilutes ir išbandykite
//        cars.add(0, new Car("Mazda","6",2007,50000,27000));
//        cars.add(6, new Car("Hyundai","Lantra",1998,9500,777));
//        cars.set(4, c3);
//        cars.println("Po įterpimų");
//        cars.remove(7);
//        cars.remove(0);
//        cars.println("Po išmetimų");
//        cars.remove(0); cars.remove(0); cars.remove(0);
//        cars.remove(0); cars.remove(0); cars.remove(0);
//        cars.println("Po visų išmetimų");
//        cars.remove(0);
//        cars.println("Po visų išmetimų");
    }

    void countRenault() {
        int sk = 0;
        for (Car c : cars) {
            if (c.getMake().compareTo("Renault") == 0) {
                sk++;
            }
        }
        Ks.oun("Renault automobilių yra = " + sk);
    }

    void appendCarList() {
        for (int i = 0; i < 8; i++) {
            cars.add(new Car("Ford", "Focus",
                    2009 - i, 40000 + i * 10000, 36000 - i * 2000));
        }
        cars.add("Ford Mondeo  2009 37000 36000.0");
        cars.add("Fiat Bravo   2008 27000 32500,0");
        cars.add("Ford Fiesta  2009 37000 16000,0");
        cars.add("Audi A6      2006 87000 36000,0");
        cars.println("Testuojamų automobilių sąrašas");
        cars.save("ban.txt");
    }
    
    void checkCarMarketFilters() {
        CarMarket market = new CarMarket();

        market.allCars.load("ban.txt");
        market.allCars.println("Bandomasis rinkinys");

        cars = market.getNewerCars(2001);
        cars.println("Pradedant nuo 2001");

        cars = market.getByPrice(3000, 10000);
        cars.println("Kaina tarp 3000 ir 10000");

        cars = market.getHighestMileageCars();
        cars.println("Daugiausiai nuvažiavę");

        cars = market.getByMakeAndModel("F");
        cars.println("Turi būti tik Fiatai ir Fordai");

        cars = market.getByMakeAndModel("Ford M");

        cars.println("Turi būti tik Ford Mondeo");
        int n = 0;
        for (Car c : cars) {
            n++;    // testuojame ciklo veikimą
        }
        Ks.oun("Ford Mondeo kiekis = " + n);
    }

    // išbandykite veikimą, o po to pakeiskite į Lambda stiliaus komparatorius.
    void checkCarMarketSorting() {
        CarMarket market = new CarMarket();

        market.allCars.load("ban.txt");
        Ks.oun("========" + market.allCars.get(0));
        market.allCars.println("Bandomasis rinkinys");
        market.allCars.sortBuble(Car.byMakeAndModel);
        market.allCars.println("Rūšiavimas pagal Markę ir Modelį");
        market.allCars.sortBuble(Car.byPrice);
        market.allCars.println("Rūšiavimas pagal kainą");
        market.allCars.sortBuble(Car.byYearAndPrice);
        market.allCars.println("Rūšiavimas pagal Metus ir Kainą");
        market.allCars.sortBuble(byMileage);
        market.allCars.sortBuble((a, b) -> Integer.compare(a.getMileage(), b.getMileage()));
        market.allCars.println("Rūšiavimas pagal Ridą");
        market.allCars.sortBuble();
        market.allCars.println("Rūšiavimas pagal compareTo - Kainą");
    }

    static Comparator byMileage = new Comparator() {
        @Override
        public int compare(Object obj1, Object obj2) {
            int m1 = ((Car) obj1).getMileage();
            int m2 = ((Car) obj2).getMileage();
            // rida atvirkščia mažėjančia tvarka, pradedant nuo didžiausios
            if (m1 < m2) {
                return 1;
            }
            if (m1 > m2) {
                return -1;
            }
            return 0;
        }
    };

    public static void main(String... args) {
        // suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)
        Locale.setDefault(new Locale("LT"));
        new ManualTest().execute();
    }
}
