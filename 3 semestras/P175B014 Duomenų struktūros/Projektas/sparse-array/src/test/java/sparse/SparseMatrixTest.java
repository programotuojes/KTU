package sparse;

import com.sun.tools.javac.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SparseMatrixTest {

  /**
   * Size of the matrix
   */
  public static final int SIZE = 3;
  public static Integer[][] matrix;
  /**
   * Pairs of integers, that coordinates of not empty cells
   */
  public static List<Pair<Integer, Integer>> coordinates;

  @BeforeClass
  public static void generateMatrix() {
    matrix = new Integer[SIZE][SIZE];
    coordinates = new ArrayList<>();

    for (int i = 0; i < SIZE / 3; i++) {
      int x = new Random().nextInt(SIZE);
      int y = new Random().nextInt(SIZE);
      coordinates.add(new Pair<>(x, y));
    }

    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        if (coordinates.contains(new Pair<>(i, j))) {
          matrix[i][j] = 69;
        } else {
          matrix[i][j] = 0;
        }
      }
    }
  }

  @Test
  public void testMatrixConstructorAndGet() {
    SparseMatrix<Integer> sparseMatrix = new SparseMatrix<>(matrix, 0);

    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        if (coordinates.contains(new Pair<>(i, j))) {
          Assert.assertEquals(Integer.valueOf(69), sparseMatrix.get(i, j));
        } else {
          Assert.assertEquals(Integer.valueOf(0), sparseMatrix.get(i, j));
        }
      }
    }
  }

  @Test
  public void testRemove() {
    SparseMatrix<Integer> sparseMatrix = new SparseMatrix<>(matrix, 0);

    for (Pair<Integer, Integer> i : coordinates) {
      sparseMatrix.remove(i.fst, i.snd);
    }

    Assert.assertEquals(sparseMatrix.size(), 0);
  }
}
