package edu.ktu.ds.lab2.klevinskas;

import edu.ktu.ds.lab2.utils.Ks;
import edu.ktu.ds.lab2.utils.Parsable;

import java.util.*;

public class Book implements Parsable<Book> {

    private String author;
    private String title;
    private int publishingYear;
    private float rating;
    private String id;

    public Book(String author, String title, int publishingYear) {
        this.author = author;
        this.title = title;
        this.publishingYear = publishingYear;
        this.rating = 0;
        id = UUID.randomUUID().toString();
    }

    public Book(String data) {
        parse(data);
    }

    @Override
    public void parse(String data) {
        try {
            Scanner scanner = new Scanner(data);
            scanner.useDelimiter("-");
            author = scanner.next();
            title = scanner.next();
            publishingYear = scanner.nextInt();
            rating = scanner.nextFloat();
        } catch (InputMismatchException e) {
            Ks.ern("Bad data format -> " + data);
        } catch (NoSuchElementException e) {
            Ks.ern("Missing data -> " + data);
        }
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%d) ☆%.1f", author, title, publishingYear, rating);
    }

    /**
     * Create a string that could later be parsed.
     *
     * @return String with the format "%s %s %d %.1f"
     */
    public String createString() {
        return String.format("%s-%s-%d-%.1f", author, title, publishingYear, rating);
    }

    @Override
    public int compareTo(Book book) {
        String thisBook = String.format("%s %s %d %.1f", author, title, publishingYear, rating);
        String otherBook = String.format("%s %s %d %.1f", book.author, book.title, book.publishingYear, book.rating);
        return thisBook.compareTo(otherBook);
    }

    public static Comparator<Book> byAuthor = Comparator.comparing((Book b) -> b.author);

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public int getPublishingYear() {
        return publishingYear;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }
}
