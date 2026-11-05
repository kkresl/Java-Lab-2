package org.example;

public class BookCopy {

    private final int idBookCopy;
    private final Book book;
    private boolean available;

    public BookCopy(int idBookCopy, Book book, boolean available) {

        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        this.idBookCopy = idBookCopy;
        this.book = book;
        this.available = available;
    }

    public int getIdBookCopy() {
        return idBookCopy;
    }

    public Book getBook() {
        return book;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "BookCopy{" +
                "idBookCopy=" + idBookCopy +
                ", book=" + book.getName() +
                ", available=" + available +
                '}';
    }
}
