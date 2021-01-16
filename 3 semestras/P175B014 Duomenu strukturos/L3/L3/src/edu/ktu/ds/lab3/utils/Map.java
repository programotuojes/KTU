package edu.ktu.ds.lab3.utils;

/**
 * Interfeisu aprašomas Atvaizdžio ADT.
 *
 * @param <K> Atvaizdžio poros raktas
 * @param <V> Atvaizdžio poros reikšmė
 */
public interface Map<K, V> {

    /**
     * Patikrinama ar atvaizdis yra tuščias.
     *
     * @return true, jei tuščias
     */
    boolean isEmpty();

    /**
     * Grąžinamas atvaizdyje esančių porų kiekis.
     *
     * @return Grąžinamas atvaizdyje esančių porų kiekis.
     */
    int size();

    /**
     * Išvalomas atvaizdis.
     *
     */
    void clear();

    /**
     * Grąžinamas porų masyvas.
     *
     * @return Grąžinamas porų masyvas.
     */
    String[][] toArray();

    /**
     * Atvaizdis papildomas nauja pora.
     *
     * @param key raktas,
     * @param value reikšmė.
     * @return Grąžinama atvaizdžio poros reikšmė.
     */
    V put(K key, V value);

    /**
     * Grąžinama atvaizdžio poros reikšmė.
     *
     * @param key raktas.
     * @return Grąžinama atvaizdžio poros reikšmė.
     */
    V get(K key);

    /**
     * Iš atvaizdžio pašalinama pora.
     *
     * @param key raktas.
     * @return Grąžinama pašalinta atvaizdžio poros reikšmė.
     */
    V remove(K key);

    /**
     * Patikrinama ar atvaizdyje egzistuoja pora su raktu key.
     *
     * @param key raktas.
     * @return true, jei atvaizdyje egzistuoja pora su raktu key, kitu atveju -
     * false
     */
    boolean contains(K key);
}
