package org.example;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

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

    public void displayBooks() {
        for (Book book : books) {
            System.out.println(book);
            // book-временная переменная, books-список книг
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
    public void borrowBook(int loanId, int readerId, int bookCopyId) {

        Reader selectedReader = null;
        BookCopy selectedCopy = null;

        // Ищем читателя
        for (Reader reader : readers) {
            if (reader.getIdReader() == readerId) {
                selectedReader = reader;
                break;
            }
        }

        // Ищем экземпляр
        for (BookCopy copy : bookCopies) {
            if (copy.getIdBookCopy() == bookCopyId) {
                selectedCopy = copy;
                break;
            }
        }

        // Проверяем читателя
        if (selectedReader == null) {
            throw new IllegalArgumentException("Reader not found");
        }

        // Проверяем экземпляр
        if (selectedCopy == null) {
            throw new IllegalArgumentException("Book copy not found");
        }

        // Проверяем доступность
        if (!selectedCopy.isAvailable()) {
            throw new IllegalArgumentException("Book is not available");
        }

        // Создаём выдачу
        Loan loan = new Loan(
                loanId,
                selectedReader,
                selectedCopy,
                LocalDate.now()
        );

        loans.add(loan);

        // Помечаем экземпляр как занятый
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

}