package demos.shop;

import extendsFX.BaseConsole;
import java.util.Locale;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TrialWithGuiFX extends BaseConsole{
    private VisualParameters vip = null;
    private Shop shop = null;

    @Override
    public void createControls() {
        super.createControls();
        addButton("var1", action -> {
            ta1.appendText(String.join(nL, TrialSystemOut.input1)); 
        });
        addButton("var2", action -> {
            ta1.appendText(String.join(nL, TrialSystemOut.input2)); 
        });
        vip = new VisualParameters();
        addNewHBox();
        addButton("brangiausia", action -> {
            shop.addItems(ta1.getText(), false);
            ta2.appendText("Brangiausia prekė:\n" + shop.maxItemPrice()+"\n"); 
        });
        addButton("price>=", action -> {
            shop.addItems(ta1.getText(), false);
            double lim = vip.limGE();
            ta2.appendText("Kaina >= " + lim + nL);            
            ta2.appendText(shop.itemsPriceGE(lim)); 
        });
        addButton("ataskaita", action -> {
            shop.addItems(ta1.getText(), false);
            String answer = shop.fullRaport(vip.limGE(), vip.limLE());
            ta2.appendText(answer); 
        });
        addButton("gamintojai", action -> {
            shop.addItems(ta1.getText(), false);
            String answer = shop.differentProducentsCount();
            ta2.appendText(answer); 
        });
    }
    private class VisualParameters{
        final private TextField tfGE = addTextField("riba>=", "40", 30);
        final private TextField tfLE = addTextField("riba<=", "10", 30);
        double limGE(){ 
            return Double.parseDouble(tfGE.getText());
        }
        double limLE() {
            return Double.parseDouble(tfLE.getText());
        }
    }
    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage); //To change body of generated methods, choose Tools | Templates.
        // lietuviškoje lokalėje dešimtainė dalis atskiriama kableliu
        // kad nebūtų nesusipratimų su default kultūra - fiksuojame LT
        Locale.setDefault(new Locale("LT"));
        ta1.appendText(String.join(nL, TrialSystemOut.input1)); 
        shop = new Shop("Atletas");
    }
    public static void main(String[] args) {
        launch();
    }
}
