package edu.ktu.ds.lab3.demo;

import edu.ktu.ds.lab3.gui.ValidationException;
import edu.ktu.ds.lab3.utils.HashType;
import edu.ktu.ds.lab3.utils.Ks;
import edu.ktu.ds.lab3.utils.ParsableHashMap;
import edu.ktu.ds.lab3.utils.ParsableMap;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * @author eimutis
 */
public class SimpleBenchmark {

    public static final String FINISH_COMMAND = "                               ";
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("edu.ktu.ds.lab3.gui.messages");

    private final Timekeeper timekeeper;

    private final String[] BENCHMARK_NAMES = {"add0.75", "add0.25", "rem0.75", "rem0.25", "get0.75", "get0.25"};
    private final int[] COUNTS = {10000, 20000, 40000, 80000};

    private final ParsableMap<String, Car> carsMap
            = new ParsableHashMap<>(String::new, Car::new, 10, 0.75f, HashType.DIVISION);
    private final ParsableMap<String, Car> carsMap2
            = new ParsableHashMap<>(String::new, Car::new, 10, 0.25f, HashType.DIVISION);
    private final Queue<String> chainsSizes = new LinkedList<>();

    /**
     * For console benchmark
     */
    public SimpleBenchmark() {
        timekeeper = new Timekeeper(COUNTS);
    }

    /**
     * For Gui benchmark
     *
     * @param resultsLogger
     * @param semaphore
     */
    public SimpleBenchmark(BlockingQueue<String> resultsLogger, Semaphore semaphore) {
        semaphore.release();
        timekeeper = new Timekeeper(COUNTS, resultsLogger, semaphore);
    }

    public static void main(String[] args) {
        executeTest();
    }

    public static void executeTest() {
        // suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)
        Locale.setDefault(new Locale("LT"));
        Ks.out("Greitaveikos tyrimas:\n");
        new SimpleBenchmark().startBenchmark();
    }

    public void startBenchmark() {
        try {
            benchmark();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void benchmark() throws InterruptedException {
        try {
            chainsSizes.add(MESSAGES.getString("maxChainLength"));
            chainsSizes.add("   kiekis      " + BENCHMARK_NAMES[0] + "   " + BENCHMARK_NAMES[1]);
            for (int k : COUNTS) {
                Car[] carsArray = CarsGenerator.generateShuffleCars(k);
                String[] carsIdsArray = CarsGenerator.generateShuffleIds(k);
                carsMap.clear();
                carsMap2.clear();
                timekeeper.startAfterPause();
                timekeeper.start();

                for (int i = 0; i < k; i++) {
                    carsMap.put(carsIdsArray[i], carsArray[i]);
                }
                timekeeper.finish(BENCHMARK_NAMES[0]);

                String str = "   " + k + "          " + carsMap.getMaxChainSize();
                for (int i = 0; i < k; i++) {
                    carsMap2.put(carsIdsArray[i], carsArray[i]);
                }
                timekeeper.finish(BENCHMARK_NAMES[1]);

                str += "         " + carsMap2.getMaxChainSize();
                chainsSizes.add(str);

                Arrays.stream(carsIdsArray).forEach(carsMap::remove);
                timekeeper.finish(BENCHMARK_NAMES[2]);

                Arrays.stream(carsIdsArray).forEach(carsMap2::remove);
                timekeeper.finish(BENCHMARK_NAMES[3]);

                Arrays.stream(carsIdsArray).forEach(carsMap2::get);
                timekeeper.finish(BENCHMARK_NAMES[4]);

                Arrays.stream(carsIdsArray).forEach(carsMap2::get);
                timekeeper.finish(BENCHMARK_NAMES[5]);
                timekeeper.seriesFinish();
            }

            StringBuilder sb = new StringBuilder();
            chainsSizes.forEach(p -> sb.append(p).append(System.lineSeparator()));
            timekeeper.logResult(sb.toString());
            timekeeper.logResult(FINISH_COMMAND);
        } catch (ValidationException e) {
            timekeeper.logResult(e.getMessage());
        }
    }
}
