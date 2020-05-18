/** @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 09 23
 *
 * Tai išvestinė kompleksinės duomenų struktūros klasė, kuri leidžia
 * papildomai atlikti įvedimo - išvedimo veiksmus su sąrašo elementais.
 * Objektai, kurie bus dedami į sąrašą, turi tenkinti interfeisą KTUable<E>.
 *
 * Užduotis: Peržiūrėkite ir išsiaiškinkite pateiktus metodus. Metodų algoritmai
 * yra aptarti paslaitos metu
 *****************************************************************************
 */
package edu.ktu.ds.lab1b.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Function;

public class ParsableList<E extends Parsable<E>> extends LinkedList<E> {

    private Function<String, E> createFunction;

    public ParsableList() {
    }

    public ParsableList(Function<String, E> createFunction) {
        this.createFunction = createFunction;
    }
    
    public void add(String dataString) {        // sukuria elementą iš String
        add(createElement(dataString)); // ir įdeda jį į pabaigą
    }

    public void load(String fName) {//suformuoja sąrašą iš fName failo
        clear();
        if (fName.length() == 0) {
            return;
        }
        try {
            (new File(Ks.getDataFolder())).mkdir();
            String fN = Ks.getDataFolder() + File.separatorChar + fName;
            BufferedReader fReader
                    = new BufferedReader(new FileReader(new File(fN)));
            String dLine;
            while ((dLine = fReader.readLine()) != null) {
                add(dLine);
            }
            fReader.close();
        } catch (FileNotFoundException e) {
            Ks.ern("Duomenų failas " + fName + " nerastas");
//            System.exit(0);
        } catch (IOException e) {
            Ks.ern("Failo " + fName + " skaitymo klaida");
            System.exit(0);
        }
    }

    public void save(String fName) {    // išsaugoja sąrašą faile fName
        PrintWriter fWriter = null;     // tekstiniu formatu
        try {                           // tinkamu vėlesniam skaitymui
            // jei vardo nėra - failas neformuojamas
            if (fName.equals("")) {
                return;
            }

            String fN = Ks.getDataFolder() + File.separatorChar + fName;
            fWriter = new PrintWriter(new FileWriter(new File(fN)));
            for (Parsable d1 = super.get(0); d1 != null; d1 = super.getNext()) {
                fWriter.println(d1.toString());
            }
            fWriter.close();
        } catch (IOException e) {
            Ks.ern("!!! Klaida formuojant " + fName + " failą.");
            System.exit(0);
        }
    }

    public void println() {  // sąrašas spausdinamas į Ks.oun("");
        int eilNr = 0;
        if (super.isEmpty()) {
            Ks.oun("Sąrašas yra tuščias");
        } else {
            for (Parsable d1 = super.get(0); d1 != null; d1 = super.getNext()) {
                String printData = String.format("%3d: %s ", eilNr++, d1.toString());
                Ks.oun(printData);
            }
        }
        Ks.oun("****** Bendras elementų kiekis yra " + super.size());
    }

    public void println(String title) { // spausdinant galima nurodyti antraštę
        Ks.oun("========" + title + "=======");
        println();
        Ks.oun("======== Sąrašo pabaiga =======");
    }

    protected E createElement(String data) {
        if (createFunction == null) {
            throw new IllegalStateException("Nenustatyta sąrašo elementų kūrimo funkcija");
        }
        return createFunction.apply(data);
    }
}
