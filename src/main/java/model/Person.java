package model;

import java.util.Objects;

/**
 * Date: 5.12.2021
 * Classname: Person
 */
public class Person {

    protected String firstName;
    protected String lastName;

    /**
     * Class Constructor
     * @param firstName is the firstname of a person
     * @param lastName is the lastname of a person
     */
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return  Objects.equals(getFirstName(), person.getFirstName())
                && Objects.equals(getLastName(), person.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }
}
