/****************************************************************
 * Šioje klasėje tęsiamas įvadas į JavaFX grafiką - pateikiami sprendimai,
 * demonstruojantys kartu vykdomų transformacijų efektą, Vykdant paprastų
 * rinkinių rotation ir translate kombinacijas gauname įdomius piešinius.
 * Išbandykite rekursinius piešimo metodus.
 * 
 * Pradžioje vykdykite kodą ir stebėkite atliekamus veiksmus. 
 * Suteikite sukimo kampui tokias reikšmes: 90, 60, 45, 30, 15, 6 ...
 * Didinkite kartojimų skaičių. Išbandykite su savo baziniais rinkiniais.
 * Gautus bėžinius sudėkite į laboratorinio ataskaitą.
 * 
 * @author Eimutis Karčiauskas, KTU programų inžinerijos katedra 2019 08 05
 **************************************************************************/
package demos.graphics;

import extendsFX.BaseGraphics;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Demo3_AnimTurn extends BaseGraphics {
    private VisualParameters vip = null;
    final private EventHandler ehSuite1 = e -> {
        baseSuite1(); 
        gc.rotate(vip.turnAngle());
        gc.translate(5, 3);
        gc.scale(0.975, 0.975);
    };
    final private EventHandler ehSuite2 = e -> {
        baseSuite2(); 
        gc.rotate(vip.turnAngle());
//        gc.translate(5, 3);
//        gc.scale(0.975, 0.975);
    };
    private EventHandler eh = ehSuite1;  // toliau mygtukų pagalba perjungiama
    final Timeline anim = new Timeline(new KeyFrame(Duration.millis(80), 
            e -> eh.handle(e)));
    
    private void baseSuite1() {
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(1.0);
        gc.strokeLine(0, 0, 0, canvasH/2);
        gc.setFill(randomColor());
        gc.fillOval(10, -canvasH/2+60, 111, 99);
    }    
    private void baseSuite2() {
        gc.setStroke(Color.YELLOW);
        gc.strokeLine(0, 0, 0, canvasH/2);

        gc.setFill(randomColor(0.9));
        gc.setStroke(Color.RED);
        gc.fillOval(10, -60, 15, 30);
        gc.strokeOval(-60, 60, 44, 33);

        gc.setFill(randomColor(0.8));
        gc.fillRoundRect(-130, -90, 30, 30, 40, 10);

        gc.setFill(randomColor());
        gc.fillPolygon( // žvaigždė penkių kampų, paskaičiuota atskirai
            new double[]{104, 116, 159, 123, 133, 105,  77,  87,  51,  93},
            new double[]{151, 185, 186, 204, 246, 222, 246, 204, 186, 186}, 10);
    }
    @Override
    public void createControls() {
        vip = new VisualParameters();
        addButton("clear", e -> {
            gc.restore(); // atstatome pradinę gc būseną po visų pokyčių
            clearCanvas();
            initGCstate();
        });
        addButton("suite1",   e -> { baseSuite1(); eh = ehSuite1; });
        addButton("suite2",   e -> { baseSuite2(); eh = ehSuite2; });
        addButton("animPlay", e -> { 
            anim.setCycleCount(vip.turnCount());
            anim.play();
        });
//        addNewHBox();
    }
// DĖMESIO - tai speciali klasė, kurioje saugojami vizualių parametų komponentai
// Juose užfiksuotos reikšmė gražinamos tam skirtų metodų pagalba.
// Kadangi žinomas gražinamos reikšmės tipas, tai atliekama būtina konversija.
    private class VisualParameters{
        final private TextField tfTurnAngle = addTextField("TurnAngle:", "45", 35);
        final private TextField tfTurnCount = addTextField("TurnCount:", "6", 35);
        double turnAngle(){ 
            return Double.parseDouble(tfTurnAngle.getText());
        }
        int turnCount() {
            return Integer.parseInt(tfTurnCount.getText());
        }
    }        
    private void initGCstate() {
        baseGrid();
        gc.save();    // atsimename švarią gc būseną
        gc.translate(canvasW/2, canvasH/2); // koordinąčių pradžią į centrą
    }       
    @Override
    public void start(Stage stage) throws Exception {
        setCanvas(Color.CYAN.brighter(), 600, 600);
        stage.setTitle("Eksperimentai su Canvas grafika (KTU IF VirP");
        super.start(stage);
        initGCstate();
    }       
    public static void main(String[] args) {
        launch(args);
    }
}
