package edu.ktu.ds.lab2.klevinskas;

import edu.ktu.ds.lab2.utils.AvlSet;
import edu.ktu.ds.lab2.utils.BstSet;
import edu.ktu.ds.lab2.utils.Ks;

import java.util.*;

public class BookTest {

    private static Book[] books;
    private static BstSet<Book> booksBstSet = new BstSet<>();

    private static long bstTime;
    private static long avlTime;

    public static void main(String[] args) {
        executeTest();
        bstBenchmark(10000);
        bstAvlAdd(10000);
        bstAvlContains(10000);
        greitaveika2(1000000);
        greitaveika3(1000000);
        testAvlRemove();
    }

    private static void generateBooks() {
        Book b1 = new Book("Mark Twain", "Moby Dick", 2019);
        Book b2 = new Book("Maironis", "Metai", 1894);
        Book b3 = new Book("Antanas Škėma", "Balta drobulė", 1958);
        Book b4 = new Book("Balys Sruoga", "Kupstas", 1980);

        b2.setRating(9.9f);
        b3.setRating(6.9f);

        books = new Book[]{b1, b2, b3, b4};

        booksBstSet.add(b1);
        booksBstSet.add(b2);
        booksBstSet.add(b3);
        booksBstSet.add(b4);
        booksBstSet.add(new Book("ZZZ", "Test", 1999));
        booksBstSet.add(new Book("AAA", "Test", 1999));
        booksBstSet.add(new Book("Kkk", "Test", 1999));
    }

    private static void executeTest() {
        generateBooks();

        // Initial set
        Ks.oun("Pradinis sąrašas");
        Ks.oun("\n" + booksBstSet.toVisualizedString(""));

        // Check if contains
        Ks.oun("Elemento priklausomumo aibei tyrimas");
        for (Book i : books)
            Ks.oun(i + " " + booksBstSet.contains(i));
        Book b1 = new Book("Pranas", "Gera knyga", 1553);
        Book b2 = new Book("Antanas", "Nu liuks", 1993);
        Ks.oun(b1 + " " + booksBstSet.contains(b1));
        Ks.oun(b2 + " " + booksBstSet.contains(b2));
        booksBstSet.add(b1);

        // Delete from set
        Ks.oun("Elemento šalinimas");
        Ks.oun("Pradinis sąrašas");
        Ks.oun("\n" + booksBstSet.toVisualizedString(""));
        booksBstSet.remove(b1);
        booksBstSet.remove(new Book("Maironis", "Metai", 1894));
        booksBstSet.remove(new Book("ZZZ", "Test", 1999));
        booksBstSet.remove(new Book("AAA", "Test", 1999));
        booksBstSet.remove(new Book("Kkk", "Test", 1999));
        Ks.oun("Sąrašas su pašalinimais");
        Ks.oun("\n" + booksBstSet.toVisualizedString(""));
    }

    private static void bstBenchmark(int n) {
        BstSet<Integer> set1 = new BstSet<>();
        BstSet<Integer> set2 = new BstSet<>();

        for (int i = 0; i < n; i++) {
            set1.add(i);
            if (i % 2 == 0)
                set2.add(i);
        }

        Ks.oun("-----");
        Ks.oun("Tikrinamas containsAll() (dydis " + n + ")");

        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();

        long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long startTime = System.currentTimeMillis();

        Ks.oun(set1.containsAll(set2));

        long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long endTime = System.currentTimeMillis();

        Ks.oun("Atminties sąnaudos: " + (endMemory - startMemory));
        Ks.oun("Laikas: " + (endTime - startTime) + " ms");

        // -----

        Ks.oun("-----");
        Ks.oun("Tikrinamas containsAll() su skirtingu sąrašu (dydis " + n + ")");

        set2.add(n + 1);

        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();

        startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        startTime = System.currentTimeMillis();

        Ks.oun(set1.containsAll(set2));

        endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        endTime = System.currentTimeMillis();

        Ks.oun("Atminties sąnaudos: " + (endMemory - startMemory));
        Ks.oun("Laikas: " + (endTime - startTime) + " ms");

        // -----

        Ks.oun("-----");
        Ks.oun("Tikrinamas remvoveAll() (dydis " + n + ")");

        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();

        startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        startTime = System.currentTimeMillis();

        set1.removeAll(set2);

        endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        endTime = System.currentTimeMillis();

        Ks.oun("Atminties sąnaudos: " + (endMemory - startMemory));
        Ks.oun("Laikas: " + (endTime - startTime) + " ms");

        // -----

        Ks.oun("-----");
        Ks.oun("Tikrinamas pollFirst() (dydis " + n + ")");

        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();

        startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        startTime = System.currentTimeMillis();

        Ks.oun(set1.pollFirst());

        endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        endTime = System.currentTimeMillis();

        Ks.oun("Atminties sąnaudos: " + (endMemory - startMemory));
        Ks.oun("Laikas: " + (endTime - startTime) + " ms");

        Ks.oun("-----");
        Ks.oun("BstSet aukštis: " + set1.getHeight());
    }

    private static void greitaveika2(int n) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        HashSet<Integer> hashSet = new HashSet<>();
        long startTime;
        long endTime;

        for (int i = 0; i < n; i++) {
            treeSet.add(i);
            hashSet.add(i);
        }

        int randomInt = new Random().nextInt(n);
        Ks.oun("-----");
        Ks.oun("Ieškoma skaičiaus " + randomInt);

        startTime = System.nanoTime();
        treeSet.contains(randomInt);
        endTime = System.nanoTime();
        Ks.oun("TreeSet contains() (dydis " + n + "). Laikas: " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        treeSet.contains(randomInt);
        endTime = System.nanoTime();
        Ks.oun("HashSet contains() (dydis " + n + "). Laikas: " + (endTime - startTime) + " ns");
    }

    private static void greitaveika3(int n) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        HashSet<Integer> hashSet = new HashSet<>();
        Collection<Integer> collection = new ArrayList<>();
        long startTime;
        long endTime;

        for (int i = 0; i < n; i++) {
            treeSet.add(i);
            hashSet.add(i);
            if (i % 5 == 0)
                collection.add(i);
        }

        Ks.oun("-----");

        startTime = System.currentTimeMillis();
        treeSet.containsAll(collection);
        endTime = System.currentTimeMillis();
        Ks.oun("TreeSet contains() (dydis " + n + "). Laikas: " + (endTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
        treeSet.containsAll(collection);
        endTime = System.currentTimeMillis();
        Ks.oun("HashSet contains() (dydis " + n + "). Laikas: " + (endTime - startTime) + " ms");
    }

    private static void bstAvlAdd(int n) {
        BstSet<Integer> bstSet = new BstSet<>();
        AvlSet<Integer> avlSet = new AvlSet<>();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < n; i++)
            bstSet.add(i);
        long endTime = System.currentTimeMillis();
        bstTime = endTime - startTime;
        Ks.oun("-----");
        Ks.oun("BstSet add() (" + n + " elementų). Laikas: " + bstTime + " ms");

        startTime = System.currentTimeMillis();
        for (int i = 0; i < n; i++)
            avlSet.add(i);
        endTime = System.currentTimeMillis();
        avlTime = endTime - startTime;
        Ks.oun("AvlSet add() (" + n + " elementų). Laikas: " + avlTime + " ms");
    }

    private static void bstAvlContains(int n) {
        BstSet<Integer> bstSet = new BstSet<>();
        AvlSet<Integer> avlSet = new AvlSet<>();

        for (int i = 0; i < n; i++) {
            bstSet.add(i);
            avlSet.add(i);
        }

        int randomInt = new Random().nextInt(n);
        long startTime = System.nanoTime();
        bstSet.contains(randomInt);
        long endTime = System.nanoTime();
        bstTime = endTime - startTime;
        Ks.oun("-----");
        Ks.oun("Ieškoma skaičiaus " + randomInt);
        Ks.oun("BstSet contains() (" + n + " elementų). Laikas: " + bstTime + " ns");

        startTime = System.nanoTime();
        avlSet.contains(randomInt);
        endTime = System.nanoTime();
        avlTime = endTime - startTime;
        Ks.oun("AvlSet contains() (" + n + " elementų). Laikas: " + avlTime + " ns");
    }

    private static void testAvlRemove() {
        AvlSet<Integer> avlSet = new AvlSet<>();

        for (int i = 0; i < 10; i++)
            avlSet.add(i);

        Ks.oun("Pradinis sąrašas\n" + avlSet.toVisualizedString(""));
        avlSet.remove(7);
        Ks.oun("Remove 7\n" + avlSet.toVisualizedString(""));
        avlSet.remove(3);
        Ks.oun("Remove 3\n" + avlSet.toVisualizedString(""));
        avlSet.remove(4);
        Ks.oun("Remove 4\n" + avlSet.toVisualizedString(""));
    }
}
