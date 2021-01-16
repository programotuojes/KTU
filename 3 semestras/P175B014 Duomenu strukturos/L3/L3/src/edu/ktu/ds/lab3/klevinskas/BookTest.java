package edu.ktu.ds.lab3.klevinskas;

import edu.ktu.ds.lab3.utils.HashMap;
import edu.ktu.ds.lab3.utils.ParsableHashMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BookTest {

    private static String[] words = new String[80369];
    private static Book[] books;
    private static ParsableHashMap<String, Book> bookMap = new ParsableHashMap<>(String::new, Book::new);

    private static File file = new File("data/zodynas.txt");
    private static Scanner scanner;
    private static HashMapOa<String, String> hashMapOa = new HashMapOa<>();
    private static HashMap<String, String> hashMap = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        generateBooks();
        executeTest();

        scanner = new Scanner(file);
        String line = scanner.nextLine();
        int index = 0;
        while (scanner.hasNext()) {
            words[index++] = line;
            line = scanner.nextLine();
        }

        warmup(10000);

        for (int i = 1; i <= 8; i++) {
            hashMapOa = new HashMapOa<>();
            hashMap = new HashMap<>();
            greitaveika1(i * 10000);
            greitaveika2(i * 10000);
        }
    }

    private static void generateBooks() {
        bookMap.clear();

        Book b1 = new Book("Mark Twain", "Moby Dick", 2019);
        Book b2 = new Book("Maironis", "Metai", 1894);
        Book b3 = new Book("Antanas Škėma", "Balta drobulė", 1958);
        Book b4 = new Book("Balys Sruoga", "Kupstas", 1980);

        b2.setRating(9.9f);
        b3.setRating(6.9f);

        books = new Book[]{b1, b2, b3, b4};

        bookMap.put(b1.generateKey(), b1);
        bookMap.put(b2.generateKey(), b2);
        bookMap.put(b3.generateKey(), b3);
        bookMap.put(b4.generateKey(), b4);
    }

    private static void executeTest() {
        containsValueTest();
        System.out.println("\n\n\n");

        putIfAbsentTest();
        System.out.println("\n\n\n");

        replaceTest();
        System.out.println("\n\n\n");

        replaceAllTest();
        System.out.println("\n\n\n");

        generateBooks();
        System.out.println("======== numberOfEmpties testas ========");
        bookMap.println("Pradinis sąrašas");
        System.out.println("Tuščių celių - " + bookMap.numberOfEmpties());
    }

    private static void containsValueTest() {
        System.out.println("======== containsValue testas ========");

        generateBooks();
        bookMap.println("Pradinis sąrašas");

        Book newBook = new Book("Nauja knyga", "Labai nauja", 2019);

        System.out.printf("%s - %s\n", books[0], bookMap.contains(books[0].generateKey()));
        System.out.printf("%s - %s\n", newBook, bookMap.contains(newBook.generateKey()));
    }

    private static void putIfAbsentTest() {
        System.out.println("======== putIfAbsent testas ========");

        bookMap.println("Pradinis sąrašas");

        Book newBook = new Book("Šefas", "Kepimo knyga", 2000);

        System.out.printf("%s - %s\n", books[0], bookMap.putIfAbsent(books[0].generateKey(), books[0]));
        System.out.printf("%s - %s\n", newBook, bookMap.putIfAbsent(newBook.generateKey(), newBook));

        bookMap.println("Sąrašas po pridėjimo");
    }

    private static void replaceTest() {
        System.out.println("======== replace testas ========");

        generateBooks();
        bookMap.println("Pradinis sąrašas");

        Book newBook = new Book("Atnaujintas pav", "Kepimo knyga", 2000);

        System.out.printf("%s keičiamas į %s\n%b\n", newBook, books[1], bookMap.replace(newBook.generateKey(), newBook, books[1]));
        System.out.printf("%s keičiamas į %s\n%b\n", books[1], newBook, bookMap.replace(books[1].generateKey(), books[1], newBook));

        bookMap.println("Sąrašas po pakeitimo");
    }

    private static void replaceAllTest() {
        System.out.println("======== replaceAll testas ========");

        generateBooks();

        bookMap.put("keksas", books[0]);
        bookMap.println("Pradinis sąrašas");
        Book newBook = new Book("Smagumynas", "Duomenu strk.", 1800);

        bookMap.replaceAll(books[0], newBook);
        bookMap.println("Sąrašas po pakeitimo");
    }


    private static void greitaveika1(int size) throws FileNotFoundException {
        System.out.printf("Dydis - %d\n", size);

        long begin;
        long end;

        begin = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            hashMapOa.put(words[i], words[i]);
        }
        end = System.currentTimeMillis();

        System.out.printf("HashMapOa put() %4d ms\n", end - begin);


        begin = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            hashMap.put(words[i], words[i]);
        }
        end = System.currentTimeMillis();

        System.out.printf("HashMap   put() %4d ms\n\n", end - begin);
    }

    private static void greitaveika2(int size) throws FileNotFoundException {
        System.out.printf("Dydis - %d\n", size);

        long begin;
        long end;

        begin = System.currentTimeMillis();
        for (int i = 0; i < size; i++)
            hashMapOa.remove(words[i]);
        end = System.currentTimeMillis();

        System.out.printf("HashMapOa remove() %4d ms\n", end - begin);


        begin = System.currentTimeMillis();
        for (int i = 0; i < size; i++)
            hashMap.remove(words[i]);
        end = System.currentTimeMillis();

        System.out.printf("HashMap   remove() %4d ms\n\n", end - begin);
    }

    private static void warmup(int size) throws FileNotFoundException {
        scanner = new Scanner(file);
        for (int i = 0; i < size; i++) {
            String line = scanner.nextLine();
            hashMapOa.put(line, line);
        }

        scanner = new Scanner(file);
        for (int i = 0; i < size; i++) {
            String line = scanner.nextLine();
            hashMap.put(line, line);
        }

        scanner = new Scanner(file);
        for (int i = 0; i < size; i++)
            hashMapOa.remove(scanner.nextLine());

        scanner = new Scanner(file);
        for (int i = 0; i < size; i++)
            hashMap.remove(scanner.nextLine());
    }
}
