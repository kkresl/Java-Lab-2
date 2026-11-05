package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LibraryIntegrationTest {

    @Test
    void completeLibraryWorkflow() {

        // Создаем новую библиотеку, с которой будем работать во время теста
        // Creating a new library to work with during the test
        Library library = new Library();

        // Добавляем две книги с разными ID
        // Adding two books with different IDs
        Book harryPotter = new Book(1, "Harry Potter", "J.K. Rowling");
        Book hobbit = new Book(2, "The Hobbit", "J.R.R. Tolkien");

        library.addBook(harryPotter);
        library.addBook(hobbit);

        // Создаем двух читателей и добавляем их в библиотеку
        // Creating two readers and adding them to the library
        Reader reader1 = new Reader(1, "Anna", "Smith");
        Reader reader2 = new Reader(2, "John", "Brown");

        library.addReader(reader1);
        library.addReader(reader2);

        // Добавляем три физических экземпляра книг.
        // У Harry Potter будет два экземпляра, у The Hobbit один
        // Adding three physical book copies.
        // Harry Potter will have two copies, The Hobbit one
        library.addBookCopy(101, 1);
        library.addBookCopy(102, 1);
        library.addBookCopy(201, 2);

        // Проверяем работу сеттеров и геттеров
        // Testing setters and getters
        harryPotter.setName("Harry Potter and the Philosopher's Stone");
        reader1.setFirstName("Anne");
        reader1.setLastName("Smyth");

        assertEquals("Harry Potter and the Philosopher's Stone",
                harryPotter.getName());

        assertEquals("Anne", reader1.getFirstName());
        assertEquals("Smyth", reader1.getLastName());

        // Проверяем выдачу книг двум разным читателям
        // Testing book lending to two different readers
        library.borrowBook(1001, 1, 101);
        library.borrowBook(1002, 2, 201);

        // Попытка выдать уже занятый экземпляр должна закончиться ошибкой
        // Attempting to lend an already borrowed copy should result in an error
        assertThrows(IllegalArgumentException.class,
                () -> library.borrowBook(1003, 2, 101));

        // Проверяем, что нельзя добавить книгу с уже существующим ID
        // Verifying that a book with an existing ID cannot be added
        assertThrows(IllegalArgumentException.class,
                () -> library.addBook(
                        new Book(1, "Another title", "Another author")));

        // Проверяем, что нельзя добавить книгу с уже существующим названием
        // Verifying that a book with an existing title cannot be added
        assertThrows(IllegalArgumentException.class,
                () -> library.addBook(
                        new Book(3, "Harry Potter and the Philosopher's Stone",
                                "J.K. Rowling")));

        // Проверяем, что нельзя добавить читателя с повторяющимся ID
        // Verifying that a reader with a duplicate ID cannot be added
        assertThrows(IllegalArgumentException.class,
                () -> library.addReader(
                        new Reader(1, "Other", "Reader")));

        // Нельзя добавить экземпляр книги, если самой книги не существует
        // Cannot add a book copy if the book itself does not exist
        assertThrows(IllegalArgumentException.class,
                () -> library.addBookCopy(999, 999));

        // Проверяем, что ID выдачи должен быть уникальным
        // Verifying that the loan ID must be unique
        assertThrows(IllegalArgumentException.class,
                () -> library.borrowBook(1001, 1, 102));

        // Нельзя выдать книгу читателю, которого нет в библиотеке
        // Cannot lend a book to a reader who does not exist in the library
        assertThrows(IllegalArgumentException.class,
                () -> library.borrowBook(1004, 999, 102));

        // Проверяем попытку выдать несуществующий экземпляр книги
        // Testing an attempt to lend a non-existent book copy
        assertThrows(IllegalArgumentException.class,
                () -> library.borrowBook(1004, 1, 999));

        // Проверяем поиск книг по фамилии читателя
        // Testing book search by reader's last name
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            // Получаем список книг, которые взял читатель с фамилией Smyth
            // Getting the list of books borrowed by the reader with the last name Smyth
            library.assignedUserBooks("Smyth");

        } finally {
            // Возвращаем обычный вывод в консоль
            // Restoring the original console output
            System.setOut(originalOut);
        }

        // Проверяем, что в результате присутствует нужный экземпляр
        // Verifying that the required copy is present in the result
        assertTrue(output.toString().contains("Copy ID: 101"));

        // Проверяем название выданной книги
        // Verifying the title of the lent book
        assertTrue(output.toString().contains("Harry Potter and the Philosopher's Stone"));

        // Проверяем, что поиск не зависит от регистра букв
        // Verifying that the search is case-insensitive
        assertDoesNotThrow(() -> library.assignedUserBooks("sMYTH"));

        // Возвращаем книгу с ID выдачи 1001
        // Returning the book with loan ID 1001
        library.returnBook(1001);

        // После возврата экземпляр снова должен быть доступен для выдачи
        // After return, the copy should be available for lending again
        library.borrowBook(1003, 1, 101);

        // Проверяем, что нельзя вернуть одну и ту же книгу повторно
        // Verifying that the same book cannot be returned twice
        assertThrows(IllegalArgumentException.class,
                () -> library.returnBook(1001));

        // Нельзя вернуть книгу по ID выдачи, которого не существует
        // Cannot return a book by a non-existent loan ID
        assertThrows(IllegalArgumentException.class,
                () -> library.returnBook(9999));

        // Проверяем запрос книг по фамилии несуществующего читателя
        // Testing book request by a non-existent reader's last name
        assertThrows(IllegalArgumentException.class,
                () -> library.assignedUserBooks("Unknown"));

        // Проверяем конструкторы классов на некорректные данные
        // Testing class constructors for invalid data

        // Название книги не должно быть пустым
        // Book title cannot be empty
        assertThrows(IllegalArgumentException.class,
                () -> new Book(10, " ", "Author"));

        // Автор книги также не может быть пустым
        // Book author also cannot be empty
        assertThrows(IllegalArgumentException.class,
                () -> new Book(10, "Title", ""));

        // Проверяем, что у читателя должно быть имя и фамилия
        // Verifying that a reader must have a first and last name
        assertThrows(IllegalArgumentException.class,
                () -> new Reader(10, "", "Last"));

        assertThrows(IllegalArgumentException.class,
                () -> new Reader(10, "First", " "));

        // Экземпляр книги не может существовать без самой книги
        // A book copy cannot exist without the book itself
        assertThrows(IllegalArgumentException.class,
                () -> new BookCopy(1, null, true));

        // Проверяем, что выдачу нельзя создать без читателя
        // Verifying that a loan cannot be created without a reader
        assertThrows(IllegalArgumentException.class,
                () -> new Loan(1, null,
                        new BookCopy(1, harryPotter, true),
                        LocalDate.now()));

        // Также обязательно должен быть указан экземпляр книги
        // A book copy must also be specified
        assertThrows(IllegalArgumentException.class,
                () -> new Loan(1, reader1, null, LocalDate.now()));

        // Проверяем, что дата выдачи не может отсутствовать
        // Verifying that the borrow date cannot be missing
        assertThrows(IllegalArgumentException.class,
                () -> new Loan(1, reader1,
                        new BookCopy(2, harryPotter, true), null));

        // Создаем отдельную выдачу для проверки работы сеттеров класса Loan
        // Creating a separate loan to test the setters of the Loan class
        Loan loan = new Loan(2000, reader1,
                new BookCopy(3000, harryPotter, true),
                LocalDate.now());

        // Меняем дату выдачи и дату возврата
        // Changing the borrow date and return date
        loan.setBorrowDate(LocalDate.now().minusDays(1));
        loan.setReturnDate(LocalDate.now());

        // Проверяем, что даты действительно изменились
        // Verifying that the dates have indeed changed
        assertEquals(LocalDate.now().minusDays(1),
                loan.getBorrowDate());

        assertEquals(LocalDate.now(), loan.getReturnDate());

        // Проверяем, что нельзя установить пустую дату выдачи
        // Verifying that an empty borrow date cannot be set
        assertThrows(IllegalArgumentException.class,
                () -> loan.setBorrowDate(null));

        // Проверяем методы отображения информации
        // Testing information display methods
        assertDoesNotThrow(library::displayBooks);
        assertDoesNotThrow(library::displayBookCopies);
        assertDoesNotThrow(library::displayReaders);
        assertDoesNotThrow(library::displayLoans);
    }
}