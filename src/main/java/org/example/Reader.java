
package org.example;

public class Reader {

    private int idReader;
    private String firstName;
    private String lastName;

    public Reader(int idReader, String firstName, String lastName) {

        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }

        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }

        this.idReader = idReader;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getIdReader() {return idReader;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        this.firstName = firstName;
    }

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Reader{" +
                "idReader=" + idReader +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
