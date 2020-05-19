/** @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2018 09 23
 *
 * Tai yra demonstracinė Bandymų klasė - tikrinama vizualiai
 ****************************************************************************/
package demos.shop;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class TrialSystemOut {
    private Shop shop;

    TrialSystemOut(String name) {
        this.shop = new Shop(name);
    }   
    // bandomieji duomenys
    final public static String[]  input1 = {
            "Krepšinio kamuolys; Molten 7 32,99",
            "Teniso raketė;    Nike  20 49,98",
            "Futbolo kamuolys;  Nike   2 31,00",
            "Beisbolo kamuolys;  Nike  35 14,00\n"
    };
    final public static String[]  input2 = {
            "Krepšinio kamuolys; Adidas 7 30,99",
            "Futbolo kamuolys;  Adidas 2 26,00",
            "Teniso raketė;   Nike  20 66,98",
            "Beisbolo kamuolys;  Nike 35 17,00\n"
    };
    // ============= išbandomi prekių ir parduotuvių metodai
    // išbandome su vienetinėmis prekėmis
    private void trial1() { 
        System.out.println("***** vienetinių prekių patikrinimas");
        Item si1 = new Item("Krepšinio kamuolys", "Spalding", 10, 32.99);
        Item si2 = new Item("Teniso raketė", "Nike", 100, 93.99);
        System.out.println(si1);
        System.out.println(si2);
        System.out.println("Pirmos prekės bendra kaina = " + si1.total());
        System.out.println("Antros prekės bendra kaina = " + si2.total());
    }
    // papildo prekių sąrašą su konstruktoriumi suformuotomis prekėmis
    private void trial2(){
        shop.addItems(
            new Item("Krepšinio kamuolys", "Spalding", 12, 32.99),
            new Item("Teniso raketė",       "Nike",    30, 93.99),
            new Item("Futbolo kamuolys",    "Adidas",  12, 32.99),
            new Item("Teniso raketė",       "Adidas",  30, 93.99)
        );
        System.out.println("***** prekės iš konstruktoriaus");
        shop.print();
    }
    // papildo bandomąjį prekių sąrašą iš duomenų eilučių
    private void trial3(List<String> input) {
        shop.addItems(input);
        System.out.println("***** po papildymo iš eilučių");
        shop.print();
    }
    // papildo bandomąjį prekių sąrašą iš failo
    private void loadFromFile(String fileName){ 
        Path path = Paths.get(fileName);
        try{
            List<String> input = Files.readAllLines(path, 
                                      java.nio.charset.StandardCharsets.UTF_8);
            shop.addItems(input);
        } catch (IOException e) {
            System.err.println(e);
            System.err.println("!!! Klaida apdorojant failą " + fileName);
        }         
        System.out.println("***** " + shop.Name + ": po papildymo iš failo");
        shop.print();
    }
    // jau paruoštas bandomąsias duomenų eilutes išveda į failą
    // toliau galima autonomiškai pildyti ir modifikuoti
    private void saveToFile(String fileName, List<String> output){ 
        Path path = Paths.get(fileName);
        try{
        if(!Files.exists(path))   // jei nėra - tai formuojame iš sąrašo
            Files.write(path, output, java.nio.charset.StandardCharsets.UTF_8);
        else
            System.err.println("!!! Failas " + fileName + " jau egzistuoja");
        } catch (IOException e) {
            System.out.println(e);
        }         
    }
    // pagraindinis bandomasis metodas - perjungimas komentarų pagalba
    private void trial(){
//        trial1();  // vienetinės prekės
//        trial2();  // formavimas su konstruktoriumi
//        trial3(input1);  // formavimas iš pirmo eilučių sąrašo
//        trial3(input2);  // formavimas iš antro eilučių sąrašo
        // Prieš išvedant padarome jungtinį eilučių sąrašą
        List<String> output = new ArrayList<>();
//        output.addAll(input1); // išsaugojame jungtinį sąrašą
//        output.addAll(input2);
        saveToFile(shop.Name + ".csv", output);
        loadFromFile(shop.Name + ".csv");  // formavimas iš failo
        shop.fullRaport(30, 10);        
    }
    public static void main(String[] args) {
        // lietuviškoje lokalėje dešimtainė dalis atskiriama kableliu
        // kad nebūtų nesusipratimų su default kultūra - fiksuojame LT
        Locale.setDefault(new Locale("LT"));
        TrialSystemOut ts = new TrialSystemOut("Atletas");
        ts.trial();
        System.out.println("Finišas - Bandymai atlikti");        
    }
}
