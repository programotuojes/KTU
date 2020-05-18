package edu.ktu.ds.lab3.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ParsableHashMap<K, V extends Parsable<V>> extends HashMap<K, V> implements ParsableMap<K, V> {

    private final Function<String, K> keyCreateFunction;   // funkcija bazinio rakto objekto kūrimui
    private final Function<String, V> valueCreateFunction; // funkcija bazinio reikšmės objekto kūrimui

    /**
     * Konstruktorius su funkcija bazinių rakto ir reikšmės objektų kūrimui
     *
     * @param keyCreateFunction
     * @param valueCreateFunction
     */
    public ParsableHashMap(Function<String, K> keyCreateFunction,
            Function<String, V> valueCreateFunction) {

        this(keyCreateFunction, valueCreateFunction, DEFAULT_HASH_TYPE);
    }

    /**
     * Konstruktorius su funkcija bazinių rakto ir reikšmės objektų kūrimui
     *
     * @param keyCreateFunction
     * @param valueCreateFunction
     * @param ht
     */
    public ParsableHashMap(Function<String, K> keyCreateFunction,
            Function<String, V> valueCreateFunction,
            HashType ht) {

        this(keyCreateFunction, valueCreateFunction, DEFAULT_INITIAL_CAPACITY, ht);
    }

    /**
     * Konstruktorius su funkcija bazinių rakto ir reikšmės objektų kūrimui
     *
     * @param keyCreateFunction
     * @param valueCreateFunction
     * @param initialCapacity
     * @param ht
     */
    public ParsableHashMap(Function<String, K> keyCreateFunction,
            Function<String, V> valueCreateFunction,
            int initialCapacity,
            HashType ht) {

        this(keyCreateFunction, valueCreateFunction, initialCapacity, DEFAULT_LOAD_FACTOR, ht);
    }

    /**
     * Konstruktorius su funkcija bazinių rakto ir reikšmės objektų kūrimui
     *
     * @param keyCreateFunction
     * @param valueCreateFunction
     * @param initialCapacity
     * @param loadFactor
     * @param ht
     */
    public ParsableHashMap(Function<String, K> keyCreateFunction,
            Function<String, V> valueCreateFunction,
            int initialCapacity,
            float loadFactor,
            HashType ht) {

        super(initialCapacity, loadFactor, ht);
        this.keyCreateFunction = keyCreateFunction;
        this.valueCreateFunction = valueCreateFunction;
    }

    /**
     * Sukuria elementą iš String. Jei turime failą, kuriame saugomos
     * raktas-reikšmė poros, šį metodą reikėtų atitinkamai modifikuoti
     *
     * @param dataString
     * @return
     */
    @Override
    public V put(String dataString) {
        return super.put(
                create(keyCreateFunction, dataString, "Nenustatyta raktų kūrimo funkcija"),
                create(valueCreateFunction, dataString, "Nenustatyta reikšmių kūrimo funkcija")
        );
    }

    /**
     * Suformuoja atvaizį iš filePath failo
     *
     * @param filePath
     */
    @Override
    public void load(String filePath) {
        if (filePath == null || filePath.length() == 0) {
            return;
        }
        clear();
        try (BufferedReader fReader = Files.newBufferedReader(Paths.get(filePath), Charset.defaultCharset())) {
            fReader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .forEach(this::put);
        } catch (FileNotFoundException e) {
            Ks.ern("Tinkamas duomenų failas nerastas: " + e.getLocalizedMessage());
        } catch (IOException | UncheckedIOException e) {
            Ks.ern("Failo skaitymo klaida: " + e.getLocalizedMessage());
        }
    }

    /**
     * Išsaugoja sąrašą faile fName tekstiniu formatu tinkamu vėlesniam
     * skaitymui
     *
     * @param filePath
     */
    @Override
    public void save(String filePath) {
        throw new UnsupportedOperationException("Saugojimas.. nepalaikomas");
    }

    /**
     * Atvaizdis spausdinamas į Ks.ouf("");
     */
    @Override
    public void println() {
        if (super.isEmpty()) {
            Ks.oun("Atvaizdis yra tuščias");
        } else {
            String[][] data = getModelList("=");
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    String format = (j == 0 | j % 2 == 1) ? "%7s" : "%15s";
                    Object value = data[i][j];
                    Ks.ouf(format, (value == null ? "" : value));
                }
                Ks.oufln("");
            }
        }

        Ks.oufln("****** Bendras porų kiekis yra " + super.size());
    }

    /**
     * Spausdinant galima nurodyti antraštę
     *
     * @param title
     */
    @Override
    public void println(String title) {
        Ks.ounn("========" + title + "=======");
        println();
        Ks.ounn("======== Atvaizdžio pabaiga =======");
    }

    @Override
    public String[][] getModelList(String delimiter) {
        String[][] result = new String[table.length][];
        int count = 0;
        for (Node<K, V> n : table) {
            List<String> list = new ArrayList<>();
            list.add("[ " + count + " ]");
            while (n != null) {
                list.add("-->");
                list.add(split(n.toString(), delimiter));
                n = n.next;
            }
            result[count] = list.toArray(new String[0]);
            count++;
        }
        return result;
    }

    private String split(String s, String delimiter) {
        int k = s.indexOf(delimiter);
        if (k <= 0) {
            return s;
        }
        return s.substring(0, k);
    }

    private static <T, R> R create(Function<T, R> function, T data, String errorMessage) {
        return Optional.ofNullable(function)
                .map(f -> f.apply(data))
                .orElseThrow(() -> new IllegalStateException(errorMessage));
    }
}
