package org.example;
import java.time.LocalDate;
//BookCopy+Reader
public class Loan {

    private int idLoan;
    private Reader reader;
    private BookCopy bookCopy;
    private LocalDate borrowDate;

    public Loan(int idLoan, Reader reader, BookCopy bookCopy, LocalDate borrowDate) {

        if (reader == null) {
            throw new IllegalArgumentException("Reader cannot be null");
        }

        if (bookCopy == null) {
            throw new IllegalArgumentException("BookCopy cannot be null");
        }

        if (borrowDate == null) {
            throw new IllegalArgumentException("Borrow date cannot be null");
        }

        this.idLoan = idLoan;
        this.reader = reader;
        this.bookCopy = bookCopy;
        this.borrowDate = borrowDate;
    }

    public int getIdLoan() {return idLoan;}

    public Reader getReader() {return reader;}

    public BookCopy getBookCopy() {return bookCopy;}

    public LocalDate getBorrowDate() {return borrowDate;}
    public void setBorrowDate(LocalDate borrowDate) {
        if (borrowDate == null) {
            throw new IllegalArgumentException("Borrow date cannot be null");
        }
        this.borrowDate = borrowDate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "idLoan=" + idLoan +
                ", reader=" + reader.getFirstName() + " " + reader.getLastName() +
                ", bookCopy=" + bookCopy.getBook().getName() +
                ", borrowDate=" + borrowDate +
                '}';
    }
}
