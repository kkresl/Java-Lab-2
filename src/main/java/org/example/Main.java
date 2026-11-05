package org.example;

import java.util.Scanner;

/*
 Application entry point. Creates the objects and starts the interface;
 contains no logic (Low Coupling, High Cohesion)
 */
public class Main {

    public static void main(String[] args) {
        Library library = new Library();

        try (Scanner scanner = new Scanner(System.in)) {
            new ConsoleUI(library, scanner).run();
        }
    }
}