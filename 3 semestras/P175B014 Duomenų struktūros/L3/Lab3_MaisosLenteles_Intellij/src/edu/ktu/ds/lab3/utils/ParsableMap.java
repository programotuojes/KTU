package edu.ktu.ds.lab3.utils;

/**
 * @param <K>
 * @param <V>
 */
public interface ParsableMap<K, V> extends EvaluableMap<K, V> {

    V put(String dataString);

    void load(String filePath);

    void save(String filePath);

    void println();

    void println(String title);

    /**
     * Grąžina maišos lentelės turinį, skirtą atvaizdavimui JavaFX lentelėse
     *
     * @param delimiter Poros toString() eilutės kirtiklis
     * @return Grąžina maišos lentelės turinį dvimačiu masyvu
     */
    String[][] getModelList(String delimiter);
}
