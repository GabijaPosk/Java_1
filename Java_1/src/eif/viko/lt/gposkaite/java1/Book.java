package eif.viko.lt.gposkaite.java1;
public class Book {
    private String id;
    private String title;
    private String author;
    private boolean borrowed;
    private Reader borrower;
    private int borrowedCount;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.borrowed = false;
        this.borrower = null;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public boolean isBorrowed() {
        return borrowed;
    }
    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }
    public Reader getBorrower() {
        return borrower;
    }
    public void setBorrower(Reader borrower) {
        this.borrower = borrower;
    }
    public int getBorrowedCount() {
        return borrowedCount;
    }

    public void setBorrowedCount(int borrowedCount) {
        this.borrowedCount = borrowedCount;
    }
}