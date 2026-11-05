package org.example;

import java.util.Scanner;

/**
 Konsolowy interfejs użytkownika

 GRASP:

 Pure Fabrication - klasa nie istnieje w dziedzinie biblioteki; powstała, aby
 oddzielić obsługę konsoli (Scanner, System.out) od logiki
 Controller - przyjmuje polecenia użytkownika i deleguje je do Library
 High Cohesion - jedyna odpowiedzialność: komunikacja z użytkownikiem
 Low Coupling - UI zna tylko Library; Library nie zna UI
 Scanner jest wstrzykiwany przez konstruktor (Dependency Injection)

 */
public class ConsoleUI {

    private final Library library;
    private final Scanner scanner;

    public ConsoleUI(Library library, Scanner scanner) {
        this.library = library;
        this.scanner = scanner;
    }

    //Główna pętla programu
    public void run() {
        boolean running = true;

        while (running) {
            printMenu();

            try {
                int choice = readInt("Choose option: ");
                running = handleChoice(choice);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    //Zwraca false, gdy użytkownik wybrał wyjście
    private boolean handleChoice(int choice) {
        switch (choice) {
            case 1:
                addBook();
                break;
            case 2:
                addReader();
                break;
            case 3:
                addBookCopy();
                break;
            case 4:
                borrowBook();
                break;
            case 5:
                returnBook();
                break;
            case 6:
                library.displayBooks();
                break;
            case 7:
                library.displayBookCopies();
                break;
            case 8:
                library.displayReaders();
                break;
            case 9:
                library.displayLoans();
                break;
            case 10:
                showReaderBooks();
                break;
            case 0:
                System.out.println("Goodbye!");
                return false;
            default:
                System.out.println("Invalid option!");
        }
        return true;
    }

    private void printMenu() {
        System.out.println("\n----- LIBRARY -----");
        System.out.println("1. Add book / Dodaj książkę");
        System.out.println("2. Add reader / Dodaj czytelnika");
        System.out.println("3. Add book copy / Dodaj egzemplarz książki");
        System.out.println("4. Borrow book / Wypożycz książkę");
        System.out.println("5. Return book / Zwróć książkę");
        System.out.println("6. Display books / Wyświetl książki");
        System.out.println("7. Display book copies / Wyświetl egzemplarze książek");
        System.out.println("8. Display readers / Wyświetl czytelników");
        System.out.println("9. Display loans / Wyświetl wypożyczenia");
        System.out.println("10. Books assigned to a user / Książki wypożyczone przez czytelnika");
        System.out.println("0. Exit / Wyjście");
    }

    private void addBook() {
        int bookId = readInt("Book ID: ");
        String name = readLine("Book name: ");
        String author = readLine("Author: ");

        library.addBook(new Book(bookId, name, author));
        System.out.println("Book added!");
    }

    private void addReader() {
        int readerId = readInt("Reader ID: ");
        String firstName = readLine("First name: ");
        String lastName = readLine("Last name: ");

        library.addReader(new Reader(readerId, firstName, lastName));
        System.out.println("Reader added!");
    }

    private void addBookCopy() {
        int copyId = readInt("Copy ID: ");
        int bookId = readInt("Book ID: ");

        library.addBookCopy(copyId, bookId);
        System.out.println("Book copy added!");
    }

    private void borrowBook() {
        int loanId = readInt("Loan ID: ");
        int readerId = readInt("Reader ID: ");
        int copyId = readInt("Book copy ID: ");

        library.borrowBook(loanId, readerId, copyId);
        System.out.println("Book borrowed!");
    }

    private void returnBook() {
        int loanId = readInt("Loan ID: ");

        library.returnBook(loanId);
        System.out.println("Book returned!");
    }

    private void showReaderBooks() {
        String lastName = readLine("Last name: ");

        library.assignedUserBooks(lastName);
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    //Pyta do skutku, aż użytkownik poda liczbę (zamiast wyjątku NumberFormatException)
    private int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt).trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
}