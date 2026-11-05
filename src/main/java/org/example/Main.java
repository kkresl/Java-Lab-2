package org.example;

import java.util.Scanner;

public class Main {

    static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        boolean running = true;

        while (running) {

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

            System.out.print("Choose option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            try {

                switch (choice) {

                    case 1:
                        System.out.print("Book ID: ");
                        int bookId = Integer.parseInt(scanner.nextLine());

                        System.out.print("Book name: ");
                        String name = scanner.nextLine();

                        System.out.print("Author: ");
                        String author = scanner.nextLine();

                        library.addBook(new Book(bookId, name, author));
                        System.out.println("Book added!");
                        break;

                    case 2:
                        System.out.print("Reader ID: ");
                        int readerId = Integer.parseInt(scanner.nextLine());

                        System.out.print("First name: ");
                        String firstName = scanner.nextLine();

                        System.out.print("Last name: ");
                        String lastName = scanner.nextLine();

                        library.addReader(
                                new Reader(readerId, firstName, lastName)
                        );

                        System.out.println("Reader added!");
                        break;

                    case 3:
                        System.out.print("Copy ID: ");
                        int copyId = Integer.parseInt(scanner.nextLine());

                        System.out.print("Book ID: ");
                        int parentBookId = Integer.parseInt(scanner.nextLine());

                        library.addBookCopy(copyId, parentBookId);
                        System.out.println("Book copy added!");
                        break;

                    case 4:
                        System.out.print("Loan ID: ");
                        int loanId = Integer.parseInt(scanner.nextLine());

                        System.out.print("Reader ID: ");
                        int borrowReaderId = Integer.parseInt(scanner.nextLine());

                        System.out.print("Book copy ID: ");
                        int borrowCopyId = Integer.parseInt(scanner.nextLine());

                        library.borrowBook(
                                loanId,
                                borrowReaderId,
                                borrowCopyId
                        );

                        System.out.println("Book borrowed!");
                        break;

                    case 5:
                        System.out.print("Loan ID: ");
                        int returnLoanId = Integer.parseInt(scanner.nextLine());

                        library.returnBook(returnLoanId);

                        System.out.println("Book returned!");
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
                        System.out.print("Last name: ");
                        String lastname = scanner.nextLine();

                        library.assignedUserBooks(lastname);
                        break;

                    case 0:
                        running = false;
                        System.out.println("Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid option!");
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}