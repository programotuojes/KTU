package edu.ktu.ds.lab3.klevinskas;

import edu.ktu.ds.lab3.utils.HashType;
import edu.ktu.ds.lab3.utils.Map;

import java.util.Arrays;

public class HashMapOa<K, V> implements Map<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final HashType DEFAULT_HASH_TYPE = HashType.DIVISION;

    private Entry<K, V>[] table;
    private int size = 0;
    private int deletedEntries = 0;
    private float loadFactor;
    private HashType ht;

    public HashMapOa() {
        this(DEFAULT_HASH_TYPE);
    }

    public HashMapOa(HashType ht) {
        this(DEFAULT_INITIAL_CAPACITY, ht);
    }

    public HashMapOa(int initialCapacity, HashType ht) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR, ht);
    }

    public HashMapOa(float loadFactor, HashType ht) {
        this(DEFAULT_INITIAL_CAPACITY, loadFactor, ht);
    }

    public HashMapOa(int initialCapacity, float loadFactor, HashType ht) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }

        if ((loadFactor <= 0.0) || (loadFactor > 1.0)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }

        this.table = new Entry[initialCapacity];
        this.loadFactor = loadFactor;
        this.ht = ht;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    @Override
    public String[][] toArray() {
        String[][] result = new String[table.length][];

        for (int i = 0; i < table.length; i++) {
            result[i] = new String[]{table[i].toString()};
        }

        return result;
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null)
            throw new IllegalArgumentException("Key or value is null in put(K key, V value)");

        table[getUnusedIndex(key)] = new Entry<>(key, value);
        size++;

        if (size + deletedEntries > table.length * loadFactor)
            rehash();

        return value;
    }

    @Override
    public V get(K key) {
        if (key == null)
            throw new IllegalArgumentException("Key is null in get(K key)");

        if (getIndex(key) != -1)
            return table[getIndex(key)].value;
        else
            return null;
    }

    @Override
    public V remove(K key) {
        V removedValue = get(key);

        if (removedValue != null) {
            table[getIndex(key)] = new Entry<>(null, null);
            size--;
            deletedEntries++;
        }

        return removedValue;
    }

    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    private int getIndex(K key) {
        int index = hash(key, ht);

        for (int i = 0; i < table.length; i++) {
            int tempIndex = (index + i) % table.length;
            Entry<K, V> tempEntry = table[tempIndex];

            if (tempEntry != null && tempEntry.key != null && tempEntry.key.equals(key)) {
                return tempIndex;
            }
        }

        return -1;
    }

    private int getUnusedIndex(K key) {
        int index = hash(key, ht);

        for (int i = 0; i < table.length; i++) {
            int tempIndex = (index + i) % table.length;
            Entry<K, V> tempEntry = table[tempIndex];

            if (tempEntry == null || tempEntry.key == null)
                return tempIndex;
        }

        return -1;
    }

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

    private void rehash() {
        HashMapOa<K, V> newMap = new HashMapOa<>(table.length * 2, loadFactor, ht);

        for (Entry<K, V> i : table)
            if (i != null && i.key != null)
                newMap.put(i.key, i.value);

        table = newMap.table;
        deletedEntries = 0;.
    }

    private static class Entry<K, V> {

        protected K key;
        protected V value;

        protected Entry() {

        }

        protected Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
}
