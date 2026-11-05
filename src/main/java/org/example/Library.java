package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Library {

    private final List<Book> books;
    private final List<BookCopy> bookCopies;
    private final List<Reader> readers;
    private final List<Loan> loans;

    public Library() {
        books = new ArrayList<>();
        bookCopies = new ArrayList<>();
        readers = new ArrayList<>();
        loans = new ArrayList<>();
    }

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        // duplicate check
        // books - the whole list of books, existingBook - the current book
        // equals - comparison (duplicate check)
        for (Book existingBook : books) {

            if (existingBook.getIdBook() == book.getIdBook()) {
                throw new IllegalArgumentException("Book ID already exists");
            }

            if (existingBook.getName().equals(book.getName()) &&
                    existingBook.getAuthor().equals(book.getAuthor())) {
                throw new IllegalArgumentException("Book already exists");
            }
        }

        books.add(book);
    }

    public void addBookCopy(BookCopy bookCopy) {
        bookCopies.add(bookCopy);
    }

    public void addReader(Reader reader) {
        for (Reader existingReader : readers) {
            if (existingReader.getIdReader() == reader.getIdReader()) {
                throw new IllegalArgumentException("Reader ID already exists");
            }
        }

        readers.add(reader);
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public void displayBooks() {
        for (Book book : books) {
            System.out.println(book);
            // book-временная переменная, books-список книг
            // book - temporary variable, books - the list of books
        }
    }


    public void displayBookCopies() {
        for (BookCopy copy : bookCopies) {
            System.out.println(copy);
        }
    }

    public void displayReaders() {
        for (Reader reader : readers) {
            System.out.println(reader);
        }
    }

    public void displayLoans() {
        for (Loan loan : loans) {
            System.out.println(loan);
        }
    }

    // выдача книги
    // issuing a book
    public void borrowBook(int loanId, int readerId, int bookCopyId) {

        Reader selectedReader = null;
        BookCopy selectedCopy = null;

        // Ищем читателя
        // Searching for the reader
        for (Reader reader : readers) {
            if (reader.getIdReader() == readerId) {
                selectedReader = reader;
                break;
            }
        }

        // Ищем экземпляр
        // Searching for the book copy
        for (BookCopy copy : bookCopies) {
            if (copy.getIdBookCopy() == bookCopyId) {
                selectedCopy = copy;
                break;
            }
        }

        // Проверяем читателя
        // Checking the reader
        if (selectedReader == null) {
            throw new IllegalArgumentException("Reader not found");
        }

        // Проверяем экземпляр
        // Checking the book copy
        if (selectedCopy == null) {
            throw new IllegalArgumentException("Book copy not found");
        }

        // Проверяем доступность
        // Checking availability
        if (!selectedCopy.isAvailable()) {
            throw new IllegalArgumentException("Book is not available");
        }

        // проверяем loan id
        // checking loan id
        for (Loan existingLoan : loans) {
            if (existingLoan.getIdLoan() == loanId) {
                throw new IllegalArgumentException("Loan ID already exists");
            }
        }

        // Создаём выдачу
        // Creating the loan
        Loan loan = new Loan(
                loanId,
                selectedReader,
                selectedCopy,
                LocalDate.now()
        );

        loans.add(loan);

        // Помечаем экземпляр как занятый
        // Marking the copy as unavailable
        selectedCopy.setAvailable(false);
    }

    public void returnBook(int loanId) {

        for (Loan loan : loans) {

            if (loan.getIdLoan() == loanId) {

                if (loan.getReturnDate() != null) {
                    throw new IllegalArgumentException("Book already returned");
                }

                loan.setReturnDate(LocalDate.now());

                loan.getBookCopy().setAvailable(true);

                return;
            }
        }

        throw new IllegalArgumentException("Loan not found");
    }

    //dodawanie ksiażki za pomoca ID juz istniejącej ksiazki
    public void addBookCopy(int copyId, int bookId) {

        for (Book book : books) {
            if (book.getIdBook() == bookId) {

                BookCopy copy = new BookCopy(copyId, book, true);
                bookCopies.add(copy);

                return;
            }
        }

        throw new IllegalArgumentException("Book not found");
    }

    // Показываем книги, которые сейчас находятся у читателя
    // Displaying books currently held by the reader
    public void assignedUserBooks(String lastName) {

        // Проверяем, существует ли читатель с такой фамилией
        // Checking if a reader with this last name exists
        Reader selectedReader = null;

        for (Reader reader : readers) {
            if (reader.getLastName().equalsIgnoreCase(lastName)) {
                selectedReader = reader;
                break;
            }
        }

        if (selectedReader == null) {
            throw new IllegalArgumentException("Reader not found");
        }

        boolean found = false;

        // Перебираем все выдачи
        // Iterating through all loans
        for (Loan loan : loans) {

            // Проверяем, принадлежит ли выдача найденному читателю
            // и не была ли книга возвращена
            // Checking if the loan belongs to the found reader
            // and if the book has not been returned yet
            if (loan.getReader().getIdReader() == selectedReader.getIdReader()
                    && loan.getReturnDate() == null) {

                System.out.println(
                        "Copy ID: " + loan.getBookCopy().getIdBookCopy()
                                + " | Book: " + loan.getBookCopy().getBook()
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("This reader has no borrowed books");
        }
    }

}