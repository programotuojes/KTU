/** @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2018 09 23
 *
 * Tai yra demonstracinė Prekės klasė (jos objektai dedami į List)
 ****************************************************************************/
package demos.shop;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Item {
    String name;
    String producent;
    int quantity;
    double unitPrice;
    // prekės konstruktorius - parametrų reikšmės perkeliamos į laukus
    public Item(String name, String producent, int quantity, double unitPrice){
        this.name = name;           // žodelis this nurodo esamo objekto laukus
        this.producent = producent; // t.y. išsprendžia konfliktą tarp vardų
        this.quantity  = quantity;
        this.unitPrice = unitPrice;
    }
    // metodas, kuris skaičiuoja išvestinę charakteristiką pagal laukų reikšmes
    public double total(){
        return unitPrice * quantity;
    }
    // suformuoja objektą iš duomenų eilutės, ją skaidydamas į atskiras dalis
    public static Item parse(String input) {
        Scanner scan = new Scanner(input);
        Pattern delim = scan.delimiter();
        scan.useDelimiter(";");    // prekės pavadinimas turi baigtis ";"
        String Name = scan.next();
        scan.skip(";");            // praleidžiame patį skyriklį
        scan.useDelimiter(delim);  // atstatome standartinius skyriklius
        String Producent =  scan.next();
        int Quantity     =  scan.nextInt();
        double UnitPrice = scan.nextDouble();
        return new Item(Name, Producent, Quantity, UnitPrice);
    }
    // objektą atvaizduoja tekstine eilute, tinkama lentelės formavimui
    @Override
    public String toString() {
        return String.format("| %-20s | %-10s | %3d | %8.2f |",
                        name, producent, quantity, unitPrice);
    }
}