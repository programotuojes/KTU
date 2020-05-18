package edu.ktu.ds.lab3.demo;

import java.util.Locale;

import edu.ktu.ds.lab3.gui.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

/*
 * Darbo atlikimo tvarka - čia yra pradinė klasė.
 */
public class DemoExecution extends Application {

    public static void main(String[] args) {
        DemoExecution.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus 
        ManualTest.executeTest();
        setUserAgentStylesheet(STYLESHEET_MODENA);    //Nauja FX išvaizda
//        setUserAgentStylesheet(STYLESHEET_CASPIAN); //Sena FX išvaizda
        MainWindow.createAndShowGui(primaryStage);
    }
}
