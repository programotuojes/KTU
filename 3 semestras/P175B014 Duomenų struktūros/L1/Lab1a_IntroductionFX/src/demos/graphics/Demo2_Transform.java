/****************************************************************
 * Šioje klasėje pateikiami JavaFX grafikos transformacijų pavyzdžiai
 * https://o7planning.org/en/11157/javafx-transformations-tutorial
 *
 * Pradžioje vykdykite kodą ir stebėkite atliekamus veiksmus
 * Užduotis atlikite sekdami nurodymus programinio kodo komentaruose
 * Gynimo metu atlikite dėstytojo nurodytas užduotis naujų metodų pagalba.
 *
 * @author Eimutis Karčiauskas, KTU programų inžinerijos katedra 2019 08 05
 **************************************************************************/
package demos.graphics;

import extendsFX.BaseGraphics;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static java.lang.Math.*;

public class Demo2_Transform extends BaseGraphics {
    private VisualParameters vip = null;

    // rinkinys figūrų, kurios brėžiamos atlikus transformacijas
    // UŽDUOTIS: sukurkite savo figūrų rinkinį, kurį transformuosite
    private void drawSuite1() {
        gc.setFill(randomColor());
        gc.fillRect(0, 0, 30, 40);
        gc.setFill(randomColor());
        gc.fillOval(0, 0, 30, 40);
        gc.setLineWidth(0.6);
        gc.setStroke(Color.BLACK);
        gc.strokeText("Hello", 0, 50);
    }

    private void task1() {
        double[] x = {0, 50 * cos(toRadians(60)), 100 * cos(toRadians(60))};
        double[] y = {50 * sin(toRadians(60)), 0, 50 * sin(toRadians(60))};
        gc.setFill(randomColor());
        gc.fillPolygon(x, y, 3);
    }

    @Override
    public void createControls() {
        vip = new VisualParameters();
        addNewHBox();
        addButton("clear", e -> {
            gc.restore();
            clearCanvas();
            gc.save();
        });
        addButton("grid", e -> baseGrid());
        addButton("translate", e -> gc.translate(vip.stepX(), vip.stepY()));
        addButton("scale+", e -> gc.scale(2, 2));
        addButton("scale-", e -> gc.scale(0.5, 0.5));
        addButton("rotate", e -> gc.rotate(vip.rotateAngle()));
        addButton("draw1", e -> drawSuite1());
        addButton("Task 1", e -> task1());
    }

    // DĖMESIO - tai speciali klasė, kurioje saugojami vizualių parametų komponentai
// Juose užfiksuotos reikšmė gražinamos tam skirtų metodų pagalba.
// Kadangi žinomas gražinamos reikšmės tipas, tai atliekama būtina konversija.
    private class VisualParameters {
        final private TextField tfStepX = addTextField("stepX=", "100", 35);
        final private TextField tfStepY = addTextField("stepY=", "100", 35);
        final private TextField tfRotateAngle = addTextField
                ("rotateAngle=", "-30", 40);

        double stepX() {
            return Double.parseDouble(tfStepX.getText());
        }

        double stepY() {
            return Double.parseDouble(tfStepY.getText());
        }

        double rotateAngle() {
            return Double.parseDouble(tfRotateAngle.getText());
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("JavaFX transformacijų tyrimas © Eimutis");
        setCanvas(Color.CYAN.brighter(), 600, 400);
        super.start(stage);
        gc.save();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
