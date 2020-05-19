package edu.ktu.ds.lab3.utils;

import java.util.Arrays;

/**
 * Porų ("maping'ų") raktas-reikšmė objektų kolekcijos - atvaizdžio realizacija
 * maišos lentele, kolizijas sprendžiant atskirų grandinėlių (angl. separate
 * chaining) metodu. Neužmirškite, jei poros raktas - nuosavos klasės objektas,
 * pvz. klasės Car objektas, klasėje būtina perdengti metodus equals(Object o)
 * ir hashCode().
 *
 * @param <K> atvaizdžio raktas
 * @param <V> atvaizdžio reikšmė
 * @author darius.matulis@ktu.lt
 * @Užduotis Peržiūrėkite ir išsiaiškinkite pateiktus metodus.
 */
public class HashMap<K, V> implements EvaluableMap<K, V> {

    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;
    public static final HashType DEFAULT_HASH_TYPE = HashType.DIVISION;

    // Maišos lentelė
    protected Node<K, V>[] table;
    // Lentelėje esančių raktas-reikšmė porų kiekis
    protected int size = 0;
    // Apkrovimo faktorius
    protected float loadFactor;
    // Maišos metodas
    protected HashType ht;
    //--------------------------------------------------------------------------
    //  Maišos lentelės įvertinimo parametrai
    //--------------------------------------------------------------------------
    // Maksimalus suformuotos maišos lentelės grandinėlės ilgis
    protected int maxChainSize = 0;
    // Permaišymų kiekis
    protected int rehashesCounter = 0;
    // Paskutinės patalpintos poros grandinėlės indeksas maišos lentelėje
    protected int lastUpdatedChain = 0;
    // Lentelės grandinėlių skaičius
    protected int chainsCounter = 0;
    // Einamas poros indeksas maišos lentelėje
    protected int index = 0;

    /* Klasėje sukurti 4 perkloti konstruktoriai, nustatantys atskirus maišos
     * lentelės parametrus. Jei kuris nors parametras nėra nustatomas -
     * priskiriama standartinė reikšmė.
     */
    public HashMap() {
        this(DEFAULT_HASH_TYPE);
    }

    public HashMap(HashType ht) {
        this(DEFAULT_INITIAL_CAPACITY, ht);
    }

    public HashMap(int initialCapacity, HashType ht) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR, ht);
    }

    public HashMap(float loadFactor, HashType ht) {
        this(DEFAULT_INITIAL_CAPACITY, loadFactor, ht);
    }

    public HashMap(int initialCapacity, float loadFactor, HashType ht) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }

        if ((loadFactor <= 0.0) || (loadFactor > 1.0)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }

        this.table = new Node[initialCapacity];
        this.loadFactor = loadFactor;
        this.ht = ht;
    }

    /**
     * Patikrinama ar atvaizdis yra tuščias.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Grąžinamas atvaizdyje esančių porų kiekis.
     *
     * @return Grąžinamas atvaizdyje esančių porų kiekis.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Išvalomas atvaizdis.
     */
    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
        index = 0;
        lastUpdatedChain = 0;
        maxChainSize = 0;
        rehashesCounter = 0;
        chainsCounter = 0;
    }

    /**
     * Patikrinama ar pora egzistuoja atvaizdyje.
     *
     * @param key raktas.
     * @return Patikrinama ar pora egzistuoja atvaizdyje.
     */
    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    // Method #1
    public boolean containsValue(Object value) {
        return toString().contains(value.toString());
    }

    // Method #4
    // Returns null if successfully added, value otherwise
    public V putIfAbsent(K key, V value) {
        if (get(key) == null) {
            put(key, value);
            return null;
        }

        return value;
    }

    // Method #6
    public boolean replace(K key, V oldValue, V newValue) {
        if (get(key) != null && get(key).equals(oldValue)) {
            Node<K, V> node = getInChain(key, table[hash(key, ht)]);
            node.value = newValue;
            return true;
        }

        return false;
    }

    // Method #7
    public void replaceAll(V oldValue, V newValue) {
        for (Node<K, V> i : table) {
            for (Node<K, V> n = i; n != null; n = n.next) {
                if (n.value.equals(oldValue))
                    n.value = newValue;
            }
        }
    }

    // Method #11
    public int numberOfEmpties() {
        int emptyCells = 0;

        for (Node<K, V> i : table) {
            if (i == null)
                emptyCells++;
        }

        return emptyCells;
    }


    /**
     * Atvaizdis papildomas nauja pora.
     *
     * @param key   raktas,
     * @param value reikšmė.
     * @return Atvaizdis papildomas nauja pora.
     */
    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value is null in put(Key key, Value value)");
        }
        index = hash(key, ht);
        if (table[index] == null) {
            chainsCounter++;
        }

        Node<K, V> node = getInChain(key, table[index]);
        if (node == null) {
            table[index] = new Node<>(key, value, table[index]);
            size++;

            if (size > table.length * loadFactor) {
                rehash(table[index]);
            } else {
                lastUpdatedChain = index;
            }
        } else {
            node.value = value;
            lastUpdatedChain = index;
        }

        return value;
    }

    /**
     * Grąžinama atvaizdžio poros reikšmė.
     *
     * @param key raktas.
     * @return Grąžinama atvaizdžio poros reikšmė.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null in get(Key key)");
        }

        index = hash(key, ht);
        Node<K, V> node = getInChain(key, table[index]);
        return (node != null) ? node.value : null;
    }

    /**
     * Pora pašalinama iš atvaizdžio.
     *
     * @param key Pora pašalinama iš atvaizdžio.
     * @return key raktas.
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null in remove(Key key)");
        }

        index = hash(key, ht);
        Node<K, V> previous = null;
        for (Node<K, V> n = table[index]; n != null; n = n.next) {
            if ((n.key).equals(key)) {
                if (previous == null) {
                    table[index] = n.next;
                } else {
                    previous.next = n.next;
                }
                size--;

                if (table[index] == null) {
                    chainsCounter--;
                }
                return n.value;
            }
            previous = n;
        }
        return null;
    }

    /**
     * Permaišymas
     *
     * @param node
     */
    private void rehash(Node<K, V> node) {
        HashMap<K, V> newMap = new HashMap<>(table.length * 2, loadFactor, ht);
        for (int i = 0; i < table.length; i++) {
            while (table[i] != null) {
                if (table[i].equals(node)) {
                    lastUpdatedChain = i;
                }
                newMap.put(table[i].key, table[i].value);
                table[i] = table[i].next;
            }
        }
        table = newMap.table;
        maxChainSize = newMap.maxChainSize;
        chainsCounter = newMap.chainsCounter;
        rehashesCounter++;
    }

    /**
     * Maišos funkcijos skaičiavimas: pagal rakto maišos kodą apskaičiuojamas
     * atvaizdžio poros indeksas maišos lentelės masyve
     *
     * @param key
     * @param hashType
     * @return
     */
    private int hash(K key, HashType hashType) {
        int h = key.hashCode();
        switch (hashType) {
            case DIVISION:
                return Math.abs(h) % table.length;
            case MULTIPLICATION:
                double k = (Math.sqrt(5) - 1) / 2;
                return (int) (((k * Math.abs(h)) % 1) * table.length);
            case JCF7:
                h ^= (h >>> 20) ^ (h >>> 12);
                h = h ^ (h >>> 7) ^ (h >>> 4);
                return h & (table.length - 1);
            case JCF8:
                h = h ^ (h >>> 16);
                return h & (table.length - 1);
            default:
                return Math.abs(h) % table.length;
        }
    }

    /**
     * Paieška vienoje grandinėlėje
     *
     * @param key
     * @param node
     * @return
     */
    private Node<K, V> getInChain(K key, Node<K, V> node) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null in getInChain(Key key, Node node)");
        }
        int chainSize = 0;
        for (Node<K, V> n = node; n != null; n = n.next) {
            chainSize++;
            if ((n.key).equals(key)) {
                return n;
            }
        }
        maxChainSize = Math.max(maxChainSize, chainSize + 1);
        return null;
    }

    @Override
    public String[][] toArray() {
        String[][] result = new String[table.length][];
        int count = 0;
        for (Node<K, V> n : table) {
            String[] list = new String[getMaxChainSize()];
            int countLocal = 0;
            while (n != null) {
                list[countLocal++] = n.toString();
                n = n.next;
            }
            result[count] = list;
            count++;
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Node<K, V> node : table) {
            if (node != null) {
                for (Node<K, V> n = node; n != null; n = n.next) {
                    result.append(n.toString()).append(System.lineSeparator());
                }
            }
        }
        return result.toString();
    }

    /**
     * Grąžina maksimalų grandinėlės ilgį.
     *
     * @return Maksimalus grandinėlės ilgis.
     */
    @Override
    public int getMaxChainSize() {
        return maxChainSize;
    }

    /**
     * Grąžina formuojant maišos lentelę įvykusių permaišymų kiekį.
     *
     * @return Permaišymų kiekis.
     */
    @Override
    public int getRehashesCounter() {
        return rehashesCounter;
    }

    /**
     * Grąžina maišos lentelės talpą.
     *
     * @return Maišos lentelės talpa.
     */
    @Override
    public int getTableCapacity() {
        return table.length;
    }

    /**
     * Grąžina paskutinės papildytos grandinėlės indeksą.
     *
     * @return Paskutinės papildytos grandinėlės indeksas.
     */
    @Override
    public int getLastUpdatedChain() {
        return lastUpdatedChain;
    }

    /**
     * Grąžina grandinėlių kiekį.
     *
     * @return Grandinėlių kiekis.
     */
    @Override
    public int getChainsCounter() {
        return chainsCounter;
    }

    protected static class Node<K, V> {

        // Raktas
        protected K key;
        // Reikšmė
        protected V value;
        // Rodyklė į sekantį grandinėlės mazgą
        protected Node<K, V> next;

        protected Node() {
        }

        protected Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }
}
