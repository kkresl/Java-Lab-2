package org.example;
import java.util.List;
import java.util.ArrayList;

public class Library {

    private List<Book> books;
    private List<BookCopy> bookCopies;
    private List<Reader> readers;
    private List<Loan> loans;

    public Library() {
        books = new ArrayList<>();
        bookCopies = new ArrayList<>();
        readers = new ArrayList<>();
        loans = new ArrayList<>();
    }

    public void addBook( Book book ) {
        if (book == null){
            throw new IllegalArgumentException("Book cannot be null");
        }
        // проверка на дубликат
        // books - весь список книг, existingBoo-текущая книга
        // equals-сравнение(проверка на дубликат)
        for (Book existingBook : books) {
            if(existingBook.getName().equals(book.getName()) &&
                    existingBook.getAuthor().equals(book.getAuthor())) {
                throw new IllegalArgumentException("Book already exists");
            }
        }

        books.add(book);
    }

    public void addBookCopy(BookCopy bookCopy) {
        bookCopies.add(bookCopy);
    }

    public void addReader( Reader reader ) {
        readers.add(reader);
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

}