/****************************************************************
 * Šioje klasėje pateikami judančių objektų formavimo pagrindai su JavaFX.
 * 
 * Pradžioje vykdykite kodą ir stebėkite atliekamus veiksmus
 * Sukurta klasė MovingBall kurios objektai juda valdomi Timeline.
 * Naujai įdedamiems kamuoliams galima keisti greitį
 *
 * @author Eimutis Karčiauskas, KTU programų inžinerijos katedra 2019 08 05
 **************************************************************************/
package demos.graphics;

import extendsFX.BaseGraphics;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Demo4_Moving extends BaseGraphics {

final Timeline anim = new Timeline(new KeyFrame(Duration.millis(40), e -> {
    nodes.forEach(node -> ((MovingBall)node).moveStep());
}));   // kadro trukmė = 40ms; ir veiksmai, kurie atliekami viename kadre
static double speed = 1.0; // bazinis greitis, kuris perduodamas objektui

// klasė, kurioje formuojamas vienas judesio žingsnis
class MovingBall extends Circle{
    double  stepX, stepY;                    // judesio vieno žingsnio pokytis
    final double left, right, top, bottom;   // judėjimo zonos ribos
    
    MovingBall(double radius){
        super(radius);                   // klasės Circle konstruktorius
        stepX = 2.4; stepY = 0.7;        // bandomoji reikšmė - galima keisti
        stepX *= speed; stepY *= speed; // įvertinamas greičio koeficientas
        left = radius + stepX; right = canvasW - radius - stepX;
        top = radius + stepY; bottom = canvasH - radius - stepY;  
        setFill(randomColor());
        relocate(radius, radius);
    }
    void moveStep(){
        double newX = getLayoutX() + stepX;
        double newY = getLayoutY() + stepY;
        setLayoutX(newX);               // perkeliame į naują vietą
        setLayoutY(newY);
        if(newX < left || newX > right) // jei yra už šoninių ribų
            stepX = -stepX;             // tai keičiame x kryptį
        if(newY < top || newY > bottom) // jei yra už vertikalių ribų
            stepY = -stepY;             // tai keičiame y kryptį
    }
    } // ************ klasės MovingBall pabaiga
    @Override
    public void createControls() {
        addButton("clear", e -> nodes.clear());
        addButton("clearFirst",e -> {if(nodes.size()>0) nodes.remove(0); });
        addButton("clearLast", e -> {if(nodes.size()>0)
                                        nodes.remove(nodes.size()-1);});
        addButton("addBall",   e -> nodes.add(new MovingBall(10)));
        addNewHBox();
        addButton("start", e -> {   // šis mygtukas dirba start-stop režimu
            Button btn = (Button)e.getSource();
            switch(btn.getText()){
                case "start":                      
                    prepareAnimation();        
                    btn.setText("pause"); break;
                case "pause":   // pristabdome animaciją
                    anim.pause();
                    btn.setText("play"); break;
                case "play":   // atnaujiname animaciją
                    anim.play();
                    btn.setText("pause"); break;
            }
        });
        addButton("speed\u2191", e -> speed *= 1.5 ); // didiname greitį
        addButton("speed\u2193", e -> speed /= 1.5 ); // mažiname greitį
    }

    private void prepareAnimation() {
        // paleidžiame animaciją su vienu kamuoliu
        nodes.add(new MovingBall(10));
        anim.setCycleCount(Timeline.INDEFINITE);
        anim.play();
    }
    @Override
    public void start(Stage stage) throws Exception {
        setCanvas(Color.CYAN.brighter(), 400, 500);
        stage.setTitle("Tiesinis judėjimas (KTU IF VirP");
        super.start(stage);
    }       
    public static void main(String[] args) {
        launch(args);
    }
}
