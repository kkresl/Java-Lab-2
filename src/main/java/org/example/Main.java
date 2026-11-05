package org.example;

import java.util.Scanner;

/*
 Punkt startowy aplikacji. Tworzy obiekty i uruchamia interfejs;
 nie zawiera żadnej logiki (Low Coupling, High Cohesion)
 */
public class Main {

    public static void main(String[] args) {
        Library library = new Library();

        try (Scanner scanner = new Scanner(System.in)) {
            new ConsoleUI(library, scanner).run();
        }
    }
}