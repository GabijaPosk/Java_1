package eif.viko.lt.gposkaite.java1;
import eif.viko.lt.gposkaite.java1.Anotations.Author;
import eif.viko.lt.gposkaite.java1.Anotations.DeprecatedFeature;
import eif.viko.lt.gposkaite.java1.Anotations.LastModified;
import eif.viko.lt.gposkaite.java1.Exceptions.BookLimitExceededException;
import eif.viko.lt.gposkaite.java1.Exceptions.BookNotFoundException;
import eif.viko.lt.gposkaite.java1.Interfaces.LibraryAction;

import java.util.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@LastModified(date="2024-03-16")
public class Library {
    private Map<String, Book> books;
    private Set<Reader> readers;
    private Map<String, Book> borrowedBooks;

    public Library() {
        this.books = new HashMap<>();
        this.readers = new HashSet<>();
        this.borrowedBooks = new HashMap<>();
    }
    public <R> R executeLibraryAction(LibraryAction<Book, R> action, String bookId) {
        Book book = books.get(bookId);
        if (book == null) {
            return null;
        }
        return action.execute(book);
    }
    public void viewBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("Šiuo metu nėra pasiskolintų knygų.");
        } else {
            for (Book book : borrowedBooks.values()) {
                System.out.println("Knyga: " + book.getTitle() + ", Pasiskolino: " + book.getBorrower().getEmail());
            }
        }
    }
    @Author(name = "Developer A")
    public void addBook(Book book) throws IllegalArgumentException {
        if (books.containsKey(book.getId())) {
            throw new IllegalArgumentException("Knyga su tokiu ID jau egzistuoja.");
        }
        books.put(book.getId(), book);
    }

    public void registerReader(String email, String password) {
        readers.add(new Reader(email, password));
    }

    public Reader loginReader(String email, String password) {
        for (Reader reader : readers) {
            if (reader.getEmail().equals(email) && reader.getPassword().equals(password)) {
                return reader;
            }
        }
        return null;
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books.values());
    }

    public void borrowBook(String bookId, Reader reader) throws BookNotFoundException, BookLimitExceededException {
        Book book = books.get(bookId);
        if (book == null) {
            throw new BookNotFoundException("Knyga nerasta.");
        }
        if (!book.isBorrowed()) {
            book.setBorrowed(true);
            book.setBorrower(reader);
            borrowedBooks.put(bookId, book);
            reader.borrowBook(book);
            book.setBorrowedCount(book.getBorrowedCount() + 1); // Padidinkite pasiskolinimų skaičių
            System.out.println("Knyga sėkmingai pasiskolinta.");
        } else {
            throw new BookLimitExceededException("Knyga jau pasiskolinta.");
        }
    }

    public void returnBook(String bookId, Reader reader) throws BookNotFoundException {
        Book book = books.get(bookId);
        if (book == null) {
            throw new BookNotFoundException("Knyga nerasta.");
        }
        if (book.isBorrowed() && book.getBorrower().equals(reader)) {
            book.setBorrowed(false);
            borrowedBooks.remove(bookId);
            reader.returnBook(book);
            System.out.println("Knyga sėkmingai grąžinta.");
        } else {
            System.out.println("Ši knyga jums nepriklauso arba nebuvo pasiskolinta.");
        }
    }

    public void viewReaderBooks(String email) {
        boolean found = false;
        for (Reader reader : readers) {
            if (reader.getEmail().equals(email)) {
                found = true;
                List<Book> borrowedBooks = reader.getBorrowedBooks();
                if (borrowedBooks.isEmpty()) {
                    System.out.println("Jūsų sąrašas tuščias.");
                } else {
                    System.out.println("Jūsų pasiskolintos knygos:");
                    for (Book book : borrowedBooks) {
                        System.out.println("ID: " + book.getId() + ", Pavadinimas: " + book.getTitle() + ", Autorius: " + book.getAuthor());
                    }
                }
                break;
            }
        }
        if (!found) {
            System.out.println("Skaitytojas su tokiu el. paštu nerastas.");
        }
    }

    public List<Book> searchBooksByAuthor(String author) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    public Map<String, Integer> generatePopularBooksReport() {
        Map<String, Integer> bookCounts = new HashMap<>();
        for (Book book : books.values()) {
            String title = book.getTitle();
            int count = book.getBorrowedCount();
            if (count > 0) {
                bookCounts.put(title, count);
            }
        }
        return bookCounts;
    }

    public List<Pair<String, String>> getSortedBooksByTitle() {
        return books.values().stream()
                .map(book -> new Pair<>(book.getId(), book.getTitle()))
                .sorted(Comparator.comparing(Pair::getSecond))
                .collect(Collectors.toList());
    }
    @DeprecatedFeature(message = "This method will be removed in the next major release.")
    public void removeBook(String bookId) {

    }
    public void demonstrateReflection() {
        if (Book.class.isAnnotationPresent(LastModified.class)) {
            LastModified annotation = Book.class.getAnnotation(LastModified.class);
            System.out.println("Book class was last modified on: " + annotation.date());
        }
    }
}