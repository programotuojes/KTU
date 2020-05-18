package bonus_tasks;

import extendsFX.BaseGraphics;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class TicTacToe extends BaseGraphics {

    /**
     * Indicates the current player.<br>
     * "o" for zeroes;<br>
     * "x" for crosses.
     */
    private String currentPlayer = "o";
    private String[] board = new String[9];

    public static void main(String[] args) {
        launch(args);
    }

    private EventHandler<MouseEvent> onClick = e -> {
        double x = e.getX();
        double y = e.getY();

        if (y < 100) {
            if (x < 100 && board[0] == null) {
                drawSymbol(50, 50);
                board[0] = currentPlayer;
            } else if (x > 100 && x < 200 && board[1] == null) {
                drawSymbol(150, 50);
                board[1] = currentPlayer;
            } else if (x > 200 && board[2] == null) {
                drawSymbol(250, 50);
                board[2] = currentPlayer;
            }
        } else if (y < 200) {
            if (x < 100 && board[3] == null) {
                drawSymbol(50, 150);
                board[3] = currentPlayer;
            } else if (x > 100 && x < 200 && board[4] == null) {
                drawSymbol(150, 150);
                board[4] = currentPlayer;
            } else if (x > 200 && board[5] == null) {
                drawSymbol(250, 150);
                board[5] = currentPlayer;
            }
        } else {
            if (x < 100 && board[6] == null) {
                drawSymbol(50, 250);
                board[6] = currentPlayer;
            } else if (x > 100 && x < 200 && board[7] == null) {
                drawSymbol(150, 250);
                board[7] = currentPlayer;
            } else if (x > 200 && board[8] == null) {
                drawSymbol(250, 250);
                board[8] = currentPlayer;
            }
        }

        checkIfDone();
        currentPlayer = currentPlayer.equals("o") ? "x" : "o";
    };

    private void drawGrid() {
        gc.setLineWidth(2);
        gc.setStroke(Color.BLACK);
        gc.strokeLine(100, 0, 100, 300);
        gc.strokeLine(200, 0, 200, 300);
        gc.strokeLine(0, 100, 300, 100);
        gc.strokeLine(0, 200, 300, 200);
    }

    private void drawSymbol(double x, double y) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(10);

        if (currentPlayer.equals("o")) {
            gc.strokeOval(x - 30, y - 30, 60, 60);
        } else {
            gc.strokeLine(x - 30, y - 30, x + 30, y + 30);
            gc.strokeLine(x + 30, y - 30, x - 30, y + 30);
        }
    }

    private void drawWinningLine(int i) {
        gc.setLineWidth(4);
        gc.setStroke(Color.GRAY);

        switch (i) {
            case 0:
                // Top row
                gc.strokeLine(10, 50, 290, 50);
                break;
            case 1:
                // Middle row
                gc.strokeLine(10, 150, 290, 150);
                break;
            case 2:
                // Bottom row
                gc.strokeLine(10, 250, 290, 250);
                break;
            case 3:
                // Left column
                gc.strokeLine(50, 10, 50, 290);
                break;
            case 4:
                // Middle column
                gc.strokeLine(150, 10, 150, 290);
                break;
            case 5:
                // Right column
                gc.strokeLine(250, 10, 250, 290);
                break;
            case 6:
                // Diagonal 1
                gc.strokeLine(10, 10, 290, 290);
                break;
            case 7:
                // Diagonal 2
                gc.strokeLine(290, 10, 10, 290);
                break;
        }
    }

    private void drawText(String text) {
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(new Font(70));
        gc.setLineWidth(1);

        gc.setFill(Color.IVORY);
        gc.fillText(text, 150, 150);

        gc.setStroke(Color.BLACK);
        gc.strokeText(text, 150, 150);
    }

    private void checkIfDone() {
        for (int i = 0; i < 8; i++) {
            String line = null;
            switch (i) {
                case 0:
                    // Top row
                    line = board[0] + board[1] + board[2];
                    break;
                case 1:
                    // Middle row
                    line = board[3] + board[4] + board[5];
                    break;
                case 2:
                    // Bottom row
                    line = board[6] + board[7] + board[8];
                    break;
                case 3:
                    // Left column
                    line = board[0] + board[3] + board[6];
                    break;
                case 4:
                    // Middle column
                    line = board[1] + board[4] + board[7];
                    break;
                case 5:
                    // Right column
                    line = board[2] + board[5] + board[8];
                    break;
                case 6:
                    // Diagonal 1
                    line = board[0] + board[4] + board[8];
                    break;
                case 7:
                    // Diagonal 2
                    line = board[2] + board[4] + board[6];
                    break;
            }

            if (line.equals("ooo")) {
                drawWinningLine(i);
                drawText("O wins");
                canvas.setOnMouseClicked(null);
                return;
            } else if (line.equals("xxx")) {
                drawWinningLine(i);
                drawText("X wins");
                canvas.setOnMouseClicked(null);
                return;
            }
        }

        // Check whether all cells are used
        boolean draw = true;
        for (String i : board) {
            if (i == null) {
                draw = false;
                break;
            }
        }

        if (draw) {
            drawText("Draw");
            canvas.setOnMouseClicked(null);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Kryžiukai - nuliukai");
        setCanvas(Color.WHITE, 300, 300);
        stage.setResizable(false);
        super.start(stage);

        drawGrid();
        canvas.setOnMouseClicked(onClick);
    }

    @Override
    public void createControls() {

    }
}
