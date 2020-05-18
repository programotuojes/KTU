import static sparse.SparseMatrix.parseString;

import extendsFX.BaseGraphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sparse.SparseMatrix;
import sparse.SparseMatrix.Cell;

public class Main extends BaseGraphics {

  private static final int MULTIPLIER = 5;
  private static final int SIZE = 100;
  private Boolean[][] matrix;
  private SparseMatrix<Boolean> sparseMatrix;
  private Stage stage;

  public static void main(String[] args) {
    launch(args);
  }

  private void fillSquare(double x, double y) {
    x = Math.floor(x / MULTIPLIER);
    y = Math.floor(y / MULTIPLIER);

    if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
      return;
    }

    gc.setFill(Color.BLACK);
    gc.fillRect(x * MULTIPLIER, y * MULTIPLIER, MULTIPLIER, MULTIPLIER);

    if (matrix[(int) x][(int) y] == null) {
      sparseMatrix.add((int) x, (int) y, true);
    }

    matrix[(int) x][(int) y] = true;
  }

  private void drawGrid() {
    gc.setLineWidth(0.1);
    gc.setStroke(Color.BLACK);
    for (int i = 0; i < SIZE; i++) {
      gc.strokeLine(MULTIPLIER * i, 0, MULTIPLIER * i, MULTIPLIER * SIZE);
      gc.strokeLine(0, MULTIPLIER * i, MULTIPLIER * SIZE, MULTIPLIER * i);
    }
  }

  private void save() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd-HH-mm-ss");
    Date date = new Date();
    File file = new File(formatter.format(date) + ".spm");

    try (FileOutputStream stream = new FileOutputStream(file)) {
      stream.write(sparseMatrix.toBytes());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void read() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open sparse matrix file");
    File selectedFile = fileChooser.showOpenDialog(stage);

    try (Scanner scanner = new Scanner(selectedFile)) {
      while (scanner.hasNext()) {
        Cell<?> cell = parseString(scanner.nextLine());
        fillSquare(cell.x * MULTIPLIER, cell.y * MULTIPLIER);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void clear() {
    matrix = new Boolean[SIZE][SIZE];
    sparseMatrix = new SparseMatrix<>();
    clearCanvas();
    drawGrid();
  }

  @Override
  public void start(Stage stage) throws Exception {
    stage.setTitle("Sparse matrix");
    setCanvas(Color.WHITE, MULTIPLIER * SIZE, MULTIPLIER * SIZE);
    stage.setResizable(false);
    super.start(stage);
    this.stage = stage;

    clear();

    canvas.setOnMouseClicked(e -> fillSquare(e.getX(), e.getY()));
    canvas.setOnMouseDragged(e -> fillSquare(e.getX(), e.getY()));
  }

  @Override
  public void createControls() {
    addButton("Save", e -> save());
    addButton("Open", e -> read());
    addButton("Save matrix", e -> saveMatrix());
    addButton("Open matrix", e -> readMatrix());
    addButton("Clear", e -> clear());
  }


  private void saveMatrix() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd-HH-mm-ss");
    Date date = new Date();
    File file = new File(formatter.format(date) + ".mat");

    try (FileOutputStream stream = new FileOutputStream(file)) {
      for (Boolean[] i : matrix) {
        stream.write(matrixRowToBytes(i));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void readMatrix() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open matrix file");
    File selectedFile = fileChooser.showOpenDialog(stage);

    try (Scanner scanner = new Scanner(selectedFile)) {
      int index = 0;
      while (scanner.hasNext()) {
        parseFromMatrixFile(scanner.nextLine(), index++);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private byte[] matrixRowToBytes(Boolean[] row) {
    StringBuilder sb = new StringBuilder();

    for (Boolean i : row) {
      if (i == null || !i) {
        sb.append(0);
      } else {
        sb.append(1);
      }
    }

    return sb.append("\n").toString().getBytes();
  }

  private void parseFromMatrixFile(String line, int j) {
    for (int i = 0; i < line.length(); i++) {
      if (line.charAt(i) == '1') {
        fillSquare(j * MULTIPLIER, i * MULTIPLIER);
      }
    }
  }
}
