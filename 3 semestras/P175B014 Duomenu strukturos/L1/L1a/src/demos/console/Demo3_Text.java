/****************************************************************
 * Šioje klasėje tiriame pradinių duomenų išskyrimo iš teksto metodus
 * Tiriame lietuviškų simbolių rikiavimo ypatybes, išbandome Regex metodiką
 * 
 * Visos demonstracinės klasės turi main metodą ir vykdomos Run File (Shift+F6)
 * 
 * Pradžioje vykdykite kodą ir stebėkite atliekamus veiksmus
 * Užduotis atlikite sekdami nurodymus programinio kodo komentaruose
 * Gynimo metu atlikite dėstytojo nurodytas užduotis naujų metodų pagalba.
 *
 * @author Eimutis Karčiauskas, KTU programų inžinerijos katedra 2019 08 05
 **************************************************************************/
package demos.console;

import extendsFX.BaseConsole;
import java.text.Collator;
import java.util.*;
import java.util.regex.*;
import javafx.stage.Stage;

public class Demo3_Text extends BaseConsole{
    
    private void sumInts() { // String skaidome į skaičius ir sumuojame
        // jei pradžioje ekranas tuščias jį užpildome demo variantu
        // toliau modifikuojant tekstą bus naudojamas koreguotas variantas
        if(ta1.getText().isEmpty())
            ta1.appendText("  0   1   22  333   444  " + nL);
        // keiskite, kad skyriklis būtų ne vienas tarpas, bet tarpų seka
        String[] sa = readLastLine().split(" +"); // " +" 
        int sum = 0;
        for(String s: sa)
            if(!s.isEmpty())    //jei pradžioje tarpai atsiranda tuščia eilutė
                sum += Integer.parseInt(s);
        ta2.appendText(String.join("+", sa) + " = " + sum + nL);
    }
    // eksperimentuosime panaudojant colatorius su įvairiomis lokalėmis 
    final String[] words = {"efektas", "įvartis", "ūdra", "ąsotis", "šienas",
           "ąžuolas", "čerpė", "ėdžios", "indėlis", "yla", "avilys",
           "citata", "čempionas", "ačiū", "actas", "įdaras", "citadelė",
           "įrašykite papildomus žodžius ir vėl bandykite"
    };
    private void sortStandart(){
        if(ta1.getText().isEmpty())  // jei tuščia - pateikiame demo variantą
            ta1.appendText(String.join(nL, words) + nL);
        String[] ws = ta1.getText().split(nL);
        Arrays.sort(ws);  // Rikiavimas be Collator - visos lietuviškos gale
        ta2.appendText("***** Standartinis rikiavimas pagal raidžių kodus\n");
        ta2.appendText(String.join(nL, ws) + nL); 
    }
    private void sortLT(){
        if(ta1.getText().isEmpty())  // jei tuščia - pateikiame demo variantą
            ta1.appendText(String.join(nL, words) + nL);
        String[] ws = ta1.getText().split(nL);
        Collator collatorLT = Collator.getInstance(new Locale("LT"));
        Arrays.sort(ws, collatorLT);  // Rikiavimas lietuviškai
        ta2.appendText("***** rikiavimas pagal lietuviškas taisykles\n");
        ta2.appendText(String.join(nL, ws) + nL); 
    }
    private void listLocales(){
        int num = 0;
        for(Locale loc: Locale.getAvailableLocales())
            ta2.appendText("" + ++num + ": " + loc + nL);
        ta2.appendText("*** DEFAULT Locale: " + Locale.getDefault() + nL);
    }
    //********* Toliau apie Regex struktūras
// https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html    
// https://farenda.com/java/java-8-regex-match-group-named-capturing-groups/
// https://www.logicbig.com/tutorials/core-java-tutorial/java-regular-expressions/named-captruing-groups.html
// http://www.java2s.com/Tutorials/Java/Java_Regular_Expression/0070__Java_Regex_Groups.htm    
    /********************************************************
    * Toliau ekrane pateikiama eilutė, kur iki simbolių <> Regex Patternas
    * o toliau seka pagal šį šabloną apdorojamas tekstas
    * po pirmo bandymo pakeiskite šabloną kad būtų atpažintas minusas
    * sekančių keitimų metu atpažinkite:
    *   a - žodžius su lotyniškomis raidėmis
    *   b - žodžius su lietuviškomis raidėmis
    *   c - informaciją tarp skliaustų ()
    ***************************************************/
    void regexExamples(){
        if(ta1.getText().isEmpty())  // jei tuščia - pateikiame demo variantą
            ta1.appendText("d+<>pirmas=1;antras>-22:trečias:333viskas" + nL);
        String[] sa = readLastLine().split("<>");      // padaliname į dvi dalis
        ta2.appendText(String.join(" : ", sa)+nL); // pakartojame dešinėje
        Pattern pat = Pattern.compile(sa[0]);      // pirma dalis - tai Pattern
        Matcher m = pat.matcher(sa[1]);            // kita dalis - apdorojimui
        while (m.find())                     // kol randamas šablonas
            ta2.appendText(m.group() + nL);  // pateikiamas jo atitikmuo
    }
    @Override
    public void createControls() {
        super.createControls();
        addButton("lastLine",action -> ta2.appendText(readLastLine()+nL) );       
        addButton("sum1",    action -> sumInts());       
        addButton("sort",    action -> sortStandart());       
        addButton("sortLT",  action -> sortLT());       
        addButton("locales", action -> listLocales());       
        addButton("regex",   action -> regexExamples());         
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Eksperimentai su teksto apdorojimo metodais (VirP)");
        super.start(stage);  
        Locale.setDefault(new Locale("LT"));
    }
    public static void main(String[] args) {
        launch();
    }
}
