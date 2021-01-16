package edu.ktu.ds.lab3.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 * Klasė, skirta duomenų išvedimui į Java GUI
 *
 * @author darius
 */
public class KsGui {

    private static int lineNr;
    private static boolean formatStartOfLine = true;

    public static void setFormatStartOfLine(boolean formatStartOfLine) {
        KsGui.formatStartOfLine = formatStartOfLine;
    }

    private static String getStartOfLine() {
        return (formatStartOfLine) ? ++lineNr + "| " : "";
    }

    public static void ou(TextArea ta, Object o) {
        StringBuilder sb = new StringBuilder();
        if (o instanceof Iterable) {
            ((Iterable) o).forEach(p -> sb.append(p).append(System.lineSeparator()));
        } else {
            sb.append(o.toString());
        }
        Platform.runLater(() -> ta.appendText(sb.toString()));
    }

    public static void oun(TextArea ta, Object o) {
        StringBuilder sb = new StringBuilder();
        if (o instanceof Iterable) {
            ((Iterable) o).forEach(p -> sb.append(p).append(System.lineSeparator()));
            sb.append(System.lineSeparator());
        } else {
            sb.append(o.toString()).append(System.lineSeparator());
        }
        Platform.runLater(() -> ta.appendText(sb.toString()));
    }

    public static void ou(TextArea ta, Object o, String msg) {
        String startOfLine = getStartOfLine();
        Platform.runLater(() -> ta.appendText(startOfLine + msg + ": "));
        oun(ta, o);
    }

    public static void oun(TextArea ta, Object o, String msg) {
        String startOfLine = getStartOfLine();
        Platform.runLater(() -> ta.appendText(startOfLine + msg + ": " + System.lineSeparator()));
        oun(ta, o);
    }

    public static void ounArgs(TextArea ta, String format, Object... args) {
        String startOfLine = getStartOfLine();
        Platform.runLater(() -> ta.appendText(startOfLine + String.format(format, args) + System.lineSeparator()));
    }

    public static void ounerr(TextArea ta, Exception e) {
        Region region = (Region) ta.lookup(".content");
        region.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
        String startOfLine = getStartOfLine();
        Platform.runLater(() -> ta.appendText(startOfLine + e.getLocalizedMessage() + System.lineSeparator()));
    }

    public static void ounerr(TextArea ta, String msg) {
        Region region = (Region) ta.lookup(".content");
        region.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
        String startOfLine = getStartOfLine();
        Platform.runLater(() -> ta.appendText(startOfLine + msg + System.lineSeparator()));
    }

    public static void ounerr(TextArea ta, String msg, String parameter) {
        Region region = (Region) ta.lookup(".content");
        region.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
        String startOfLine = getStartOfLine();
        Platform.runLater(() -> ta.appendText(startOfLine + msg + ((parameter == null || parameter.isEmpty())
                ? "" : ": " + parameter) + System.lineSeparator()));
    }
}
