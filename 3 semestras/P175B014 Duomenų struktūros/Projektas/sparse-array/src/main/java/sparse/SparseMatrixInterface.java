package sparse;

public interface SparseMatrixInterface<T> {

  int size();

  void add(int x, int y, T value);

  T get(int x, int y);

  void remove(int x, int y);

  void clear();

  T[][] toMatrix();
}
