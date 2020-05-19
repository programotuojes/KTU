package edu.ktu.ds.lab1b.klevinskas;

import edu.ktu.ds.lab1b.util.Ks;

import java.util.*;

public class BookTests {

    private static BookList books = new BookList();

    public static void main(String[] args) {
        generateBooks();
        printBooks("Pradiniai duomenys", books);

        testParse();    // Test the parsable implementation
        testList();     // Testing list functions

        // Filtered books where publishing year > 1950
        printBooks("Books from the year 1950:", getBooksFromYear(books, 1950));

        // Testing UŽDUOTIS 1
        books.addLast(new Book("addLast", "Užduotis1", 2019));
        printBooks("UŽDUOTIS 1 - addLast(E e)", books);

        // Testing UŽDUOTIS 2
        books.remove(books.get(3));
        printBooks("UŽDUOTIS 2 - remove(E e)", books);

        // Testing UŽDUOTIS 3
        BookList list = new BookList();
        list.add(new Book("New Book", "1", 1995));
        list.add(new Book("New Book", "2", 1997));
        list.add(new Book("New Book", "3", 1999));
        books.addAll(2, list);
        printBooks("UŽDUOTIS 3 - addAll(int index, LinkedList list)", books);


        greitaveika1();
        greitaveika2();
    }

    private static void generateBooks() {
        books.add(new Book("Mark Twain", "Moby Dick", 2019));
        books.add(new Book("Maironis", "Metai", 1894));
        books.add(new Book("Antanas Škėma", "Balta drobulė", 1958));
        books.add(new Book("Balys Sruoga", "Kupstas", 1980));

        books.get(1).setRating(9.9f);
        books.get(2).setRating(6.9f);
    }

    private static void testParse() {
        for (Object i : books.toArray()) {
            Book parsedBook = new Book(((Book) i).createString());

            Ks.ou("Before parsing: ");
            Ks.ouf("%s\n", i);
            Ks.ou("After parsing:  ");
            Ks.ouf("%s\n", parsedBook);
            Ks.oun(((Book) i).compareTo(parsedBook) == 0 ? "PASS" : "FAIL");
        }
    }

    private static void testList() {
        printBooks("Before list editing", books);

        books.add(1, new Book("add 1", "title", 2019));
        printBooks("Adding a new book", books);

        books.add(3, new Book("add 3", "title", 2019));
        printBooks("Adding a new book", books);

        books.set(1, new Book("set test", null, 0));
        printBooks("Changing 2nd book", books);

        books.remove(5);
        printBooks("Removing the 6th book", books);
    }

    private static BookList getBooksFromYear(BookList bookList, int year) {
        BookList newList = new BookList();

        for (Object i : bookList.toArray())
            if (((Book) i).getPublishingYear() >= year)
                newList.add((Book) i);

        return newList;
    }

    private static void printBooks(String title, BookList books) {
        Ks.oun("---------------");
        Ks.oun("-- " + title + " --");
        for (Book i : books)
            Ks.oun(i);
    }


    private static void greitaveika1() {
        final int size = 1000000;

        Ks.ouf("\n%s\n", "------------------------------");
        Ks.ouf("%s\n", "Greitaveika - UŽDUOTIS 1");
        Ks.ouf("Masyvo dydis - %s\n", size);

        int[] x = new int[size];
        int[] y = new int[size];
        double[] num1 = new double[size];
        double[] num2 = new double[size];

        for (int i = 0; i < size; i++) {
            x[i] = new Random(Calendar.getInstance().getTimeInMillis()).nextInt();
            y[i] = new Random(Calendar.getInstance().getTimeInMillis()).nextInt();
        }


        // Math.sqrt()
        long begin = Calendar.getInstance().getTimeInMillis();

        for (int i = 0; i < size; i++)
            num1[i] = Math.sqrt(x[i] * x[i] + y[i] * y[i]);
        long end = Calendar.getInstance().getTimeInMillis();

        Ks.ouf("%d ms su Math.sqrt().\n", end - begin);


        // Math.hypot()
        begin = Calendar.getInstance().getTimeInMillis();

        for (int i = 0; i < size; i++)
            num2[i] = Math.hypot(x[i], y[i]);
        end = Calendar.getInstance().getTimeInMillis();

        Ks.ouf("%d ms su Math.hypot().\n", end - begin);
    }

    private static void greitaveika2() {
        final int size = 100000;
        final int amountOfOperations = 1000;

        Ks.ouf("\n%s\n", "------------------------------");
        Ks.ouf("%s\n", "Greitaveika - UŽDUOTIS 2");
        Ks.ouf("LinkedList dydis - %s\n", size);

        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < size; i++)
            linkedList.add(new Random(Calendar.getInstance().getTimeInMillis()).nextInt(100000000));

        int[] indexes = new int[amountOfOperations];
        for (int i = 0; i < amountOfOperations; i++)
            indexes[i] = new Random(Calendar.getInstance().getTimeInMillis()).nextInt(size);

        long sum = 0;
        // Repeat operations to get an average
        for (int i = 0; i < amountOfOperations; i++) {
            long begin = Calendar.getInstance().getTimeInMillis();

            linkedList.indexOf(indexes[i]);

            long end = Calendar.getInstance().getTimeInMillis();
            sum += end - begin;
        }
        Ks.ouf("%d ms su indexOf(); operacijų kiekis - %d.\n", sum, amountOfOperations);


        sum = 0;
        // Repeat operations to get an average
        for (int i = 0; i < amountOfOperations; i++) {
            long begin = Calendar.getInstance().getTimeInMillis();

            linkedList.lastIndexOf(indexes[i]);

            long end = Calendar.getInstance().getTimeInMillis();
            sum += end - begin;
        }
        Ks.ouf("%d ms su lastIndexOf(); operacijų kiekis - %d.\n", sum, amountOfOperations);
    }

    private static void atmintis() {
        final int size = 100000;

        long before = memUsage();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++)
            list.add(new Random(Calendar.getInstance().getTimeInMillis()).nextInt());

        long after = memUsage();

        Ks.ouf("\n---------------\n");
        Ks.ouf("List užima %d baitus", after - before);
        Ks.ouf("\n---------------\n");

        before = memUsage();
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < size; i++)
            linkedList.add(list.get(i));

        after = memUsage();

        Ks.ouf("\n---------------\n");
        Ks.ouf("LinkedList užima %d baitus", after - before);
        Ks.ouf("\n---------------\n");
    }

    private static long memUsage() {
        System.gc();
        long memTotal = Runtime.getRuntime().totalMemory();
        long memFree = Runtime.getRuntime().freeMemory();
        return memTotal - memFree;
    }
}
