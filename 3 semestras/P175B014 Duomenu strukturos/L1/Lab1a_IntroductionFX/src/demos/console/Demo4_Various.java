/****************************************************************
 * Šioje klasėje tiriame įvarių Javos paketų naudingas klases
 * Rekomenduojame plačiau patyrinėti metodiką su Enumemeration,
 * klasės Calendar metodus, jų ryšį su LT lokale.
 * operacijas su failais pakete java.nio
 * tinklinį programavimą su paketo java.net klasėmis
 * 
 * ... Taigi čia galime pasireikšti visi: tiek kolegos, tiek studentai 
 *
 * @author visi kurie pateiks medžiagą
 **************************************************************************/
package demos.console;

import extendsFX.BaseConsole;
import java.util.Enumeration;
import java.util.Properties;
import javafx.stage.Stage;

public class Demo4_Various extends BaseConsole{

    private void infoAboutEnviroment() {
        ta1.appendText("*** Informacija apie aplinkos savybes:" + nL);
        Properties p = System.getProperties();
        Enumeration keys = p.keys();
        while (keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            String value = (String)p.get(key);
            ta1.appendText(key + ": " + value + nL);
        }
    }
    @Override
    public void createControls() {
        super.createControls();    // sukuriame bazinius mygtukus
        addButton("info", e -> infoAboutEnviroment());
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Įvairūs demonstraciniai metodai (KTU IF)");
        super.start(stage);  
    }
    public static void main(String[] args) {
        launch();
    }
}
