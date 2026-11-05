package org.example;

import java.time.LocalDate;

//BookCopy+Reader
//Информация о выдаче книги
public class Loan {

    private final int idLoan;
    private final Reader reader;
    private final BookCopy bookCopy;
    private LocalDate borrowDate;
    private LocalDate returnDate;

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
        this.returnDate = null;
    }

    public int getIdLoan() {
        return idLoan;
    }

    public Reader getReader() {
        return reader;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        if (borrowDate == null) {
            throw new IllegalArgumentException("Borrow date cannot be null");
        }
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
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
