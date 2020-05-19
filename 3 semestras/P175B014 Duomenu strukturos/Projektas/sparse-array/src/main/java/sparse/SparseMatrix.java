package sparse;

import com.sun.org.apache.xerces.internal.impl.dv.xs.BaseDVFactory;
import java.util.Arrays;
import java.util.Iterator;
import javax.management.openmbean.OpenMBeanInfoSupport;
import sparse.SparseMatrix.Cell;

public class SparseMatrix<T> implements SparseMatrixInterface<T>, Iterable<Cell<T>> {

  private Cell<T>[] array;
  private T emptyValue;
  private int size = 0;
  private int initialSize = 10;

  public SparseMatrix(T[][] matrix, T emptyValue) {
    this(emptyValue);

    int matrixLength = matrix[0].length;
    int matrixHeight = matrix.length;

    for (int i = 0; i < matrixLength; i++) {
      for (int j = 0; j < matrixHeight; j++) {
        add(i, j, matrix[i][j]);
      }
    }
  }

  public SparseMatrix(T emptyValue) {
    this.emptyValue = emptyValue;
    array = new Cell[initialSize];
  }

  public SparseMatrix() {
    emptyValue = null;
    array = new Cell[initialSize];
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void add(int x, int y, T value) {
    if (!value.equals(emptyValue)) {
      array[size++] = new Cell<>(x, y, value);

      if (size == initialSize) {
        expand();
      }
    }
  }

  @Override
  public T get(int x, int y) {
    for (int i = 0; i < size; i++) {
      if (array[i].x == x && array[i].y == y) {
        return array[i].value;
      }
    }

    return emptyValue;
  }

  @Override
  public void remove(int x, int y) {
    for (int i = 0; i < size; i++) {
      if (array[i].x == x && array[i].y == y) {
        shift(i);
        break;
      }
    }
  }

  @Override
  public void clear() {
    Arrays.fill(array, null);
    size = 0;
  }

  @Override
  public T[][] toMatrix() {
    int maxX = 0;
    int maxY = 0;

    for (Cell<T> i : array) {
      if (maxX < i.x) {
        maxX = i.x;
      }

      if (maxY < i.y) {
        maxY = i.y;
      }
    }

    @SuppressWarnings("unchecked")
    T[][] matrix = (T[][]) new Object[maxY][maxX];

    for (Cell<T> i : array) {
      matrix[i.y][i.x] = i.value;
    }

    return matrix;
  }

  private void shift(int index) {
    for (int i = index; i < size - 1; i++) {
      array[i] = array[i + 1];
    }

    array[size] = null;
    size--;
  }

  private void expand() {
    initialSize *= 2;
    Cell<T>[] newArray = new Cell[initialSize];
    System.arraycopy(array, 0, newArray, 0, size);

    array = newArray;
  }

  @Override
  public Iterator<Cell<T>> iterator() {
    return new Iterator<Cell<T>>() {
      int currentIndex = 0;

      @Override
      public boolean hasNext() {
        return currentIndex < size;
      }

      @Override
      public Cell<T> next() {
        return array[currentIndex++];
      }
    };
  }

  public byte[] toBytes() {
    StringBuilder sb = new StringBuilder();
    System.out.println("xd");

    for (int i = 0; i < size; i++) {
      if (array[i] != emptyValue) {
        sb.append(array[i]).append("\n");
      }
    }

    return sb.toString().getBytes();
  }

  public static Cell<?> parseString(String line) {
    String[] vars = line.split("\\|");
    return new Cell<>(Integer.parseInt(vars[0]), Integer.parseInt(vars[1]), null);
  }

  public static class Cell<T> {

    public int x;
    public int y;
    public T value;

    public Cell(int x, int y, T value) {
      this.x = x;
      this.y = y;
      this.value = value;
    }

    @Override
    public String toString() {
      return x + "|" + y + "|" + value;
    }

    public boolean equals(Cell<T> other) {
      return x == other.x && y == other.y && value.equals(other.value);
    }
  }
}
