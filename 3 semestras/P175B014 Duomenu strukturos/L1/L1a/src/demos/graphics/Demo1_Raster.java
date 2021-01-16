/****************************************************************
 * Šioje klasėje pateikami rastrinių efektų pavyzdžiai, kurie gaunami
 * vykdant daugkartinį tiesių ir ovalų brėžimą (Muaro  efektas)
 * https://en.wikipedia.org/wiki/Moir%C3%A9_pattern
 *
 * Pradžioje vykdykite kodą ir stebėkite atliekamus veiksmus.
 * Keiskite parametrus ir stebėkite gaunamus efektus
 * UŽDUOTYS: sukurkite metodus:
 *  - kurie linijų pluoštus skleistų iš įvairių kampų;
 *  - apskritimų centrai būtų keturiuose taškuose;
 *  - rekursijos metu gaunami ovalai būtų nukreipti į apačią.
 * Gynimo metu atlikite dėstytojo nurodytas užduotis naujų metodų pagalba.
 *
 * @author Eimutis Karčiauskas, KTU programų inžinerijos katedra 2019 08 05
 **************************************************************************/
package demos.graphics;

import extendsFX.BaseGraphics;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static java.lang.Math.*;

public class Demo1_Raster extends BaseGraphics {
    TextField tfCount = null;
    TextField tfDifer = null;
    ComboBox<String> cbColor = null;
    Color color = Color.BLUE;

    void drawSetOfLines() {
        int count = Integer.parseInt(tfCount.getText());
        double dif = Double.parseDouble(tfDifer.getText());
        gc.setStroke(color);
        double x = 0;
        for (int i = 0; i <= count; i++) {
            gc.strokeLine(canvasW / 2 - dif / 2, canvasH + 50, x, 0);
            gc.strokeLine(canvasW / 2 + dif / 2, canvasH + 50, x, 0);
            x += canvasW / count;
        }
    }

    private void task1() {
        int count = Integer.parseInt(tfCount.getText());
        gc.setStroke(color);

        double angle = 360f / count;
        for (int i = 1; i <= count; i++)
            gc.strokeLine(0, 0, 1000 * cos(toRadians(angle * i)), 1000 * sin(toRadians(angle * i)));
    }

    private void task2() {
        int count = Integer.parseInt(tfCount.getText());
        double dif = Double.parseDouble(tfDifer.getText());
        gc.setStroke(color);
        double dx = canvasW / count / 2;
        double dy = canvasH / count / 2;
        double x = 0, y = 0;
        for (int i = 0; i < count; i++) {
            gc.strokeOval(x - dif / 2, y - dif / 2, canvasW - x * 2, canvasH - y * 2);
            gc.strokeOval(x + dif / 2, y - dif / 2, canvasW - x * 2, canvasH - y * 2);
            gc.strokeOval(x - dif / 2, y + dif / 2, canvasW - x * 2, canvasH - y * 2);
            gc.strokeOval(x + dif / 2, y + dif / 2, canvasW - x * 2, canvasH - y * 2);
            x += dx;
            y += dy;
        }
    }

    private void task3(double x, double y, double w, double h) {
        if (h < 4)
            return;

        gc.setStroke(randomColor());
        gc.setFill(randomColor());
        gc.setLineWidth(h < canvasH / 10 ? 0.7 : 3);
        gc.fillOval(x, canvasH - h, w, h);
        gc.strokeOval(x, canvasH - h, w, h);

        task3(x, y, w / 2, h / 2);
        task3(x + w / 2, y, w / 2, h / 2);
    }

    void drawSetOfCircles() {
        int count = Integer.parseInt(tfCount.getText());
        double dif = Double.parseDouble(tfDifer.getText());
        gc.setStroke(color);
        double dx = canvasW / count / 2;
        double dy = canvasH / count / 2;
        double x = 0, y = 0;
        for (int i = 0; i < count; i++) {
            gc.strokeOval(x - dif / 2, y, canvasW - x - x, canvasH - y - y);
            gc.strokeOval(x + dif / 2, y, canvasW - x - x, canvasH - y - y);
            x += dx;
            y += dy;
        }
    }

    // rekursinio brėžimo pavyzdys, galimos tolimesnės užduotys yra pagal
    //  https://en.wikipedia.org/wiki/Sierpi%C5%84ski_triangle
    public void drawRecursive(double x, double y, double w, double h) {
        if (h < 4) return;       // rekursijos pabaigos sąlyga
        gc.setStroke(randomColor());
        gc.setFill(randomColor());   // parenkame spalvas ir linijos plotį
        gc.setLineWidth(h < canvasH / 10 ? 0.7 : 3);
        gc.fillOval(x, y, w, h);
        gc.strokeOval(x, y, w, h);
        drawRecursive(x, y, w / 2, h / 2);
        drawRecursive(x + w / 2, y, w / 2, h / 2);
    }

    @Override
    public void createControls() {
        tfCount = addTextField("linijų kiekis", "80", 40);
        tfDifer = addTextField("atstumas tarp centrų", "150", 40);
        cbColor = addComboBox("spalvos",
                e -> color = Color.web(cbColor.getValue()),
                "blue", "green", "red", "magenta");
        addNewHBox();
        addButton("clear", e -> clearCanvas());
        addButton("Lines", e -> drawSetOfLines());
        addButton("Circles", e -> drawSetOfCircles());
        addButton("Recursive", e -> drawRecursive(0, 0, canvasW, canvasH));
        addButton("Task 1", e -> task1());
        addButton("Task 2", e -> task2());
        addButton("Task 3", e -> task3(0, 0, canvasW, canvasH));
        addNewHBox();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Eksperimentai su rastriniais (Muaro) efektais © Eimutis");
        setCanvas(Color.CYAN.brighter(), 600, 400);
        super.start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
