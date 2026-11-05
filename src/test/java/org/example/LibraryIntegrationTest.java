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
        Library library = new Library();

        // Добавляем две книги с разными ID
        Book harryPotter = new Book(1, "Harry Potter", "J.K. Rowling");
        Book hobbit = new Book(2, "The Hobbit", "J.R.R. Tolkien");

        library.addBook(harryPotter);
        library.addBook(hobbit);

        // Создаем двух читателей и добавляем их в библиотеку
        Reader reader1 = new Reader(1, "Anna", "Smith");
        Reader reader2 = new Reader(2, "John", "Brown");

        library.addReader(reader1);
        library.addReader(reader2);

        // Добавляем три физических экземпляра книг.
        // У Harry Potter будет два экземпляра, у The Hobbit один
        library.addBookCopy(101, 1);
        library.addBookCopy(102, 1);
        library.addBookCopy(201, 2);

        // Проверяем работу сеттеров и геттеров
        harryPotter.setName("Harry Potter and the Philosopher's Stone");
        reader1.setFirstName("Anne");
        reader1.setLastName("Smyth");

        assertEquals("Harry Potter and the Philosopher's Stone",
                harryPotter.getName());

        assertEquals("Anne", reader1.getFirstName());
        assertEquals("Smyth", reader1.getLastName());

        // Проверяем выдачу книг двум разным читателям
        library.borrowBook(1001, 1, 101);
        library.borrowBook(1002, 2, 201);

        // Попытка выдать уже занятый экземпляр должна закончиться ошибкой
        assertThrows(IllegalArgumentException.class,
                () -> library.borrowBook(1003, 2, 101));

        // Проверяем, что нельзя добавить книгу с уже существующим ID
        assertThrows(IllegalArgumentException.class,
                () -> library.addBook(
                        new Book(1, "Another title", "Another author")));

        // Проверяем, что нельзя добавить книгу с уже существующим названием
        assertThrows(IllegalArgumentException.class,
                () -> library.addBook(
                        new Book(3, "Harry Potter and the Philosopher's Stone",
                                "J.K. Rowling")));

        // Проверяем, что нельзя добавить читателя с повторяющимся ID
        assertThrows(IllegalArgumentException.class,
                () -> library.addReader(
                        new Reader(1, "Other", "Reader")));

        // Нельзя добавить экземпляр книги, если самой книги не существует
        assertThrows(IllegalArgumentException.class,
                () -> library.addBookCopy(999, 999));

        // Проверяем, что ID выдачи должен быть уникальным
        assertThrows(IllegalArgumentException.class,
                () -> library.borrowBook(1001, 1, 102));

        // Нельзя выдать книгу читателю, которого нет в библиотеке
        assertThrows(IllegalArgumentException.class,
                () -> library.borrowBook(1004, 999, 102));

        // Проверяем попытку выдать несуществующий экземпляр книги
        assertThrows(IllegalArgumentException.class,
                () -> library.borrowBook(1004, 1, 999));

        // Проверяем поиск книг по фамилии читателя
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            // Получаем список книг, которые взял читатель с фамилией Smyth
            library.assignedUserBooks("Smyth");

        } finally {
            // Возвращаем обычный вывод в консоль
            System.setOut(originalOut);
        }

        // Проверяем, что в результате присутствует нужный экземпляр
        assertTrue(output.toString().contains("Copy ID: 101"));

        // Проверяем название выданной книги
        assertTrue(output.toString().contains("Harry Potter and the Philosopher's Stone"));

        // Проверяем, что поиск не зависит от регистра букв
        assertDoesNotThrow(() -> library.assignedUserBooks("sMYTH"));

        // Возвращаем книгу с ID выдачи 1001
        library.returnBook(1001);

        // После возврата экземпляр снова должен быть доступен для выдачи
        library.borrowBook(1003, 1, 101);

        // Проверяем, что нельзя вернуть одну и ту же книгу повторно
        assertThrows(IllegalArgumentException.class,
                () -> library.returnBook(1001));

        // Нельзя вернуть книгу по ID выдачи, которого не существует
        assertThrows(IllegalArgumentException.class,
                () -> library.returnBook(9999));

        // Проверяем запрос книг по фамилии несуществующего читателя
        assertThrows(IllegalArgumentException.class,
                () -> library.assignedUserBooks("Unknown"));

        // Проверяем конструкторы классов на некорректные данные

        // Название книги не должно быть пустым
        assertThrows(IllegalArgumentException.class,
                () -> new Book(10, " ", "Author"));

        // Автор книги также не может быть пустым
        assertThrows(IllegalArgumentException.class,
                () -> new Book(10, "Title", ""));

        // Проверяем, что у читателя должно быть имя и фамилия
        assertThrows(IllegalArgumentException.class,
                () -> new Reader(10, "", "Last"));

        assertThrows(IllegalArgumentException.class,
                () -> new Reader(10, "First", " "));

        // Экземпляр книги не может существовать без самой книги
        assertThrows(IllegalArgumentException.class,
                () -> new BookCopy(1, null, true));

        // Проверяем, что выдачу нельзя создать без читателя
        assertThrows(IllegalArgumentException.class,
                () -> new Loan(1, null,
                        new BookCopy(1, harryPotter, true),
                        LocalDate.now()));

        // Также обязательно должен быть указан экземпляр книги
        assertThrows(IllegalArgumentException.class,
                () -> new Loan(1, reader1, null, LocalDate.now()));

        // Проверяем, что дата выдачи не может отсутствовать
        assertThrows(IllegalArgumentException.class,
                () -> new Loan(1, reader1,
                        new BookCopy(2, harryPotter, true), null));

        // Создаем отдельную выдачу для проверки работы сеттеров класса Loan
        Loan loan = new Loan(2000, reader1,
                new BookCopy(3000, harryPotter, true),
                LocalDate.now());

        // Меняем дату выдачи и дату возврата
        loan.setBorrowDate(LocalDate.now().minusDays(1));
        loan.setReturnDate(LocalDate.now());

        // Проверяем, что даты действительно изменились
        assertEquals(LocalDate.now().minusDays(1),
                loan.getBorrowDate());

        assertEquals(LocalDate.now(), loan.getReturnDate());

        // Проверяем, что нельзя установить пустую дату выдачи
        assertThrows(IllegalArgumentException.class,
                () -> loan.setBorrowDate(null));

        // Проверяем методы отображения информации
        assertDoesNotThrow(library::displayBooks);
        assertDoesNotThrow(library::displayBookCopies);
        assertDoesNotThrow(library::displayReaders);
        assertDoesNotThrow(library::displayLoans);
    }
}