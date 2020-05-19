/****************************************************************
 * Šioje klasėje pateikamas bendravimo su pelės manipuliatoriumi pagrindai
 *
 * Pradžioje vykdykite kodą ir stebėkite atliekamus veiksmus
 * Užduotis atlikite sekdami nurodymus programinio kodo komentaruose
 * Sukurti skirtingi įvykių apdorojimo metodai, kurie parodo pagrindines
 * įvykių apdorojimo savybe. Studentams siūloma toliau vystyti temą.
 *
 * @author Eimutis Karčiauskas, KTU programų inžinerijos katedra 2019 08 05
 **************************************************************************/
package demos.graphics;

import extendsFX.BaseGraphics;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Demo5_MouseShapes extends BaseGraphics {
    Polygon polygon = null;
    List<Ball> balls = new ArrayList<>(); // saugomi kilnojami Ball objektai
    final static double R = 6.0;
    int clickNum;
    //*****************************************
    private EventHandler<MouseEvent> clickInfo = e -> {
        double x = e.getX();
        double y = e.getY();
        MouseButton mouseBtn = e.getButton();
        Shape shape = (mouseBtn == MouseButton.PRIMARY) ?
                new Circle(x, y, R) :
                new Rectangle(x, y, 3 * R, 3 * R);
        shape.setFill(Color.RED);
        nodes.add(shape);
        String info = "" + ++clickNum + ": " + mouseBtn + " " + x + " " + y;
        nodes.add(new Text(x, y, info));
        // išbandykite pakartotinius figūrų paspaudimus, kokia reakcija?
//        shape.setOnMouseClicked(ev -> { // atidenkite šį kodą naujam bandymui
//                Shape sh = ((Shape)ev.getSource());
//                sh.setFill(randomColor());
//                if(sh instanceof Circle)
//                    ((Circle)sh).setRadius(((Circle)sh).getRadius() + 2);
//        });
    };
    //*****************************************
    private final EventHandler<MouseEvent> createPolygon = e -> {
        double x = e.getX();
        double y = e.getY();
        Circle circle = new Circle(x, y, R);
        circle.setFill(Color.GREEN);
        nodes.add(circle);   // įdedame rodymui - daugiau daryti nieko nereikia
        polygon.getPoints().addAll(x, y);   // polinomą papildome taško x ir y
        if (polygon.getPoints().size() == 2) // tai bus uždarantis taškas
            circle.setOnMouseClicked(event -> closePolygon(event));
    };

    // kviečiama kai spragtelime ant uždarančio pirmo poligono taško
    private void closePolygon(MouseEvent e) {
        ((Circle) e.getSource()).setOnMouseClicked(null); // išjungiame reagavimą
        polygon.setFill(randomColor(0.5));
        polygon.setStroke(Color.BLACK);
        polygon.setOnMouseClicked(ev ->
                ((Polygon) ev.getSource()).setFill(randomColor(0.5)));
        polygon = createInitPolygon();    // toliau dirbsime su nauju ppoligonu
    }

    private Polygon createInitPolygon() {
        Polygon pol = new Polygon();
        pol.setFill(Color.rgb(200, 160, 120, 0.5)); // 0.5 - tai opacity
        nodes.add(pol);
        return pol;
    }

    //*****************************************
    private final EventHandler<MouseEvent> dragableLines = e -> {
        double x = e.getX();
        double y = e.getY();
        Ball ball = new Ball(x, y, R);
        ball.setFill(Color.BLUE);
        nodes.add(ball);
        balls.add(ball);  // kanmuolius sudedame, bet juos dar galime judinti
        if (balls.size() == 1) // jei suformuotas pirmas taškas
            ball.setOnMouseClicked(event -> closeBallPolygon(event));
        if (balls.size() > 1) // sujungimas formuojamas pagal paskutinius 2 kamuolius
            nodes.add(new Connection(balls.get(balls.size() - 2),
                    balls.get(balls.size() - 1)));
    };

    // kviečiama kai spragtelime ant uždarančio pirmo poligono taško
    private void closeBallPolygon(MouseEvent e) {
        ((Circle) e.getSource()).setOnMouseClicked(null); // išjungiame reagavimą
        nodes.add(new Connection(balls.get(balls.size() - 1), balls.get(0)));
        Polygon pol = new Polygon();
        for (Ball b : balls)
            pol.getPoints().addAll(b.getCenterX(), b.getCenterY());
        pol.setFill(randomColor(0.5));
        nodes.add(pol);
        pol.setOnMouseClicked(ev ->
                ((Polygon) ev.getSource()).setFill(randomColor(0.5)));
        balls.clear();    // pasiruošiame naujo poligono formavimui
    }

    //*****************************************
    @Override
    public void createControls() {
        addButton("clear", e -> {
            nodes.clear();
            balls.clear();
        });
        addButton("clearFirst", e -> {
            if (nodes.size() > 0) nodes.remove(0);
        });
        addButton("clearLast", e -> {
            if (nodes.size() > 0)
                nodes.remove(nodes.size() - 1);
        });
        addButton("clickInfo", e -> canvas.setOnMouseClicked(clickInfo));
        addButton("createPolygon", e -> {
            polygon = createInitPolygon();
            canvas.setOnMouseClicked(createPolygon);
        });
        addButton("dragableLines", e -> {
            balls.clear();  // paruošiame sąrašą naujų sujungimų pildymui
            canvas.setOnMouseClicked(dragableLines);
        });
//        addNewHBox();
    }

    @Override
    public void start(Stage stage) throws Exception {
        setCanvas(Color.CYAN.brighter(), 600, 600);
        super.start(stage);
        baseGrid();
        canvas.setOnMouseClicked(clickInfo);
        gc.setStroke(Color.BLACK);
        gc.setFont(Font.font(20));
        gc.strokeText("Spragsėkite įvairiais pelės mygtukais", 20, 20);
        gc.strokeText("Stenkitės pataikyti į grido susikirtimus", 20, 40);
        gc.setFont(Font.font(14));
        gc.strokeText("Kurdami poligonus išbandykite susikirtimus ir uždengimus", 20, 55);
        gc.strokeText("Gaukite figūras kaip žvaigždės, eglutės, pilys, ... ", 20, 70);
    }

    public static void main(String[] args) {
        launch(args);
    }
} // *********************** Demo klasės pabaiga

// papildomos klasės, skirtos taškų ir figūrų pernešimui
class Ball extends Circle {
    private double dragBaseX;
    private double dragBaseY;

    public Ball(double centerX, double centerY, double radius) {
        super(centerX, centerY, radius);
        setOnMousePressed(e -> {
            dragBaseX = e.getSceneX() - getCenterX();
            dragBaseY = e.getSceneY() - getCenterY();
        });
        setOnMouseDragged(e -> {
            setCenterX(e.getSceneX() - dragBaseX);
            setCenterY(e.getSceneY() - dragBaseY);
        });
    }
}

class Connection extends Line {
    public Connection(Ball first, Ball second) {
        // susiejami linijos galiniai taškai su kamuolių first ir second centrais
        startXProperty().bind(first.centerXProperty());
        startYProperty().bind(first.centerYProperty());
        endXProperty().bind(second.centerXProperty());
        endYProperty().bind(second.centerYProperty());
    }
}

