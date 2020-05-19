package benchmark;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import sparse.SparseMatrix;
import sparse.SparseMatrix.Cell;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1)
@Warmup(iterations = 1)
@Measurement(iterations = 1)
public class SpeedBenchmark {

  @Param({"500", "1000", "1500", "2000"})
  public int matrixSize = 500;

  SparseMatrix<Boolean> sparseMatrix;
  Boolean[][] matrix;

  @Setup(Level.Trial)
  public void generateBoard() {
    Random random = new Random();

    sparseMatrix = new SparseMatrix<>();
    matrix = new Boolean[matrixSize][matrixSize];

    for (int i = 0; i < matrixSize / 4; i++) {
      int x = random.nextInt(matrixSize);
      int y = random.nextInt(matrixSize);

      sparseMatrix.add(x, y, true);
      matrix[x][y] = true;
    }
  }

  @Benchmark
  public void sparseMatrixIterationTest(SpeedBenchmark sb, Blackhole blackhole) {
    for (Cell<Boolean> i : sb.sparseMatrix) {
      blackhole.consume(i);
    }
  }

  @Benchmark
  public void matrixIterationTest(SpeedBenchmark sb, Blackhole blackhole) {
    for (int i = 0; i < sb.matrixSize; i++) {
      for (int j = 0; j < sb.matrixSize; j++) {
        if (sb.matrix[i][j] != null) {
          blackhole.consume(sb.matrix[i][j]);
        }
      }
    }
  }
}
