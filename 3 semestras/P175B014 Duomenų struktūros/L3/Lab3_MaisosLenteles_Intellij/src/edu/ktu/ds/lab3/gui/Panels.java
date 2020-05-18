package edu.ktu.ds.lab3.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author darius
 */
public class Panels extends GridPane {

    public final static String STYLE_COMMON = "-fx-text-fill: #006464;"
            + "-fx-background-color: #F7EBC7;"
            + "-fx-background-radius: 10;"
            + "-fx-padding: 3;";
    public final static String STYLE_PINK = "-fx-text-fill: #006464;"
            + "-fx-background-color: pink;"
            + "-fx-background-radius: 10;"
            + "-fx-padding: 3;";
    public final static String STYLE_ERROR = "-fx-text-fill: #006464;"
            + "-fx-background-color: red;"
            + "-fx-background-radius: 10;"
            + "-fx-padding: 3;";
    public static final String BTN_STYLE = "-fx-text-fill: #006464;"
            + "-fx-background-color: #FFCF3B;"
            + "-fx-border-radius: 20;"
            + "-fx-background-radius: 20;"
            + "-fx-padding: 4;";
    public static final String TABLE_CELL_STYLE_EMPTY = "-fx-table-cell-border-color: transparent;"
            + "-fx-border-color: transparent;";
    public static final String TABLE_CELL_STYLE_FILLED = "-fx-text-fill: #006464;"
            + "-fx-background-color: #FFCF3B;"
            + "-fx-table-cell-border-color: #006464;"
            + "-fx-border-color: #006464;";

    private final static int SPACING = 5;

    private final List<TextField> tfs = new ArrayList<>();
    private final List<Button> btns = new ArrayList<>();

    /**
     * Sukuriama parametrų lentelė (GridPane išdėstymo dėsnis)
     * <pre>
     * |-------------------------------|
     * |                |------------| |
     * |   lblTexts[0]  | tfTexts[0] | |
     * |                |------------| |
     * |                               |
     * |                |------------| |
     * |   lblTexts[1]  | tfTexts[1] | |
     * |                |------------| |
     * |      ...             ...      |
     * |-------------------------------|
     * </pre>
     *
     * @param lblTexts
     * @param tfTexts
     * @param columnWidth
     */
    public Panels(String[] lblTexts, String[] tfTexts, int columnWidth) {
        super();

        if (lblTexts == null || tfTexts == null) {
            throw new IllegalArgumentException("Arguments for table of parameters are incorrect");
        }

        if (lblTexts.length > tfTexts.length) {
            tfTexts = Arrays.copyOf(tfTexts, lblTexts.length);
            Arrays.fill(tfTexts, "");

        }

        paneLayout();
        initTableOfParameters(columnWidth, lblTexts, tfTexts);
    }

    /**
     * Sukuriamas mygtukų tinklelis (GridPane išdėstymo dėsnis)
     * <pre>
     * |-------------------------------------|
     * | |-------------| |-------------|     |
     * | | btnNames[0] | | btnNames[1] | ... |
     * | |-------------| |-------------|     |
     * |                                     |
     * | |-------------| |-------------|     |
     * | | btnNames[2] | | btnNames[3] | ... |
     * | |-------------| |-------------|     |
     * |       ...              ...          |
     * |-------------------------------------|
     * </pre>
     *
     * @param btnNames
     * @param gridX
     * @param gridY
     */
    public Panels(String[] btnNames, int gridX, int gridY) {
        if (btnNames == null || gridX < 1 || gridY < 1) {
            throw new IllegalArgumentException("Arguments for buttons grid are incorrect");
        }
        paneLayout();
        initGridOfButtons(gridX, gridY, btnNames);
    }

    private void paneLayout() {
        setAlignment(Pos.CENTER);
        setHgap(SPACING);
        setVgap(SPACING);
        setPadding(new Insets(SPACING));
    }

    private void initTableOfParameters(int columnWidth, String[] lblTexts, String[] tfTexts) {
        for (int i = 0; i < lblTexts.length; i++) {
            add(new Label(lblTexts[i]), 0, i);
        }
        for (int i = 0; i < tfTexts.length; i++) {
            TextField tf = new TextField(tfTexts[i]);
            tf.setStyle(STYLE_COMMON);
            tf.setPrefWidth(columnWidth);
            tf.setAlignment(Pos.CENTER);
            tfs.add(tf);
            add(tf, 1, i);
        }
    }

    private void initGridOfButtons(int gridX, int gridY, String[] btnNames) {
        Queue<String> btnNamesQueue = new LinkedList<>(Arrays.asList(btnNames));
        for (int i = 0; i < gridY; i++) {
            for (int j = 0; j < gridX; j++) {
                if (btnNamesQueue.isEmpty()) {
                    break;
                }
                Button button = new Button(btnNamesQueue.poll());
                button.setStyle(BTN_STYLE);
                btns.add(button);
                GridPane.setHgrow(button, Priority.ALWAYS);
                button.setMaxWidth(Double.MAX_VALUE);
                add(button, j, i);
            }
        }
    }

    /**
     * Gražinamas lentelės parametrų sąrašas
     *
     * @return Gražinamas lentelės parametrų sąrašas
     */
    public List<String> getParametersOfTable() {
        return tfs.stream().map(TextInputControl::getText).collect(Collectors.toList());
    }

    /**
     * Gražinamas parametrų lentelės TextField objektų sąrašas
     *
     * @return Gražinamas parametrų lentelės TextField objektų sąrašas
     */
    public List<TextField> getTfOfTable() {
        return tfs;
    }

    /**
     * Gražinamas mygtukų tinklelio Button objektų sąrašas
     *
     * @return Gražinamas mygtukų tinklelio Button objektų sąrašas
     */
    public List<Button> getButtons() {
        return btns;
    }
}
