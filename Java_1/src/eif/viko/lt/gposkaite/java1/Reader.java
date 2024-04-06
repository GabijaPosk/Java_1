package eif.viko.lt.gposkaite.java1;
import java.util.ArrayList;
import java.util.List;
public class Reader {
    private String email;
    private String password;
    private List<Book> borrowedBooks;
    public Reader(String email, String password) {
        this.email = email;
        this.password = password;
        this.borrowedBooks = new ArrayList<>();
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
    public void borrowBook(Book book) {
        if (!borrowedBooks.contains(book)) {
            borrowedBooks.add(book);
        }
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }
}