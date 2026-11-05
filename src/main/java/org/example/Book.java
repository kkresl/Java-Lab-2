package org.example;

public class Book {

    private final int idBook;
    private String name;
    private String author;


    public Book(int idBook, String name, String author) {

        // isBlank() - ялвяется ли строка пустой и есть ли пробелы
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Book name cannot be empty");
        }

        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author cannot be empty");
        }

        this.name = name;
        this.idBook = idBook;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Book name cannot be empty");
        }
        this.name = name;
    }
    //zmienia nazwe produktu po stworzeniu

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getIdBook() {
        return idBook;
    }
    //public void setIdBook(int idBook) {this.idBook = idBook; }

    @Override
    public String toString() {
        return "Book{" +
                "idBook=" + idBook +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
