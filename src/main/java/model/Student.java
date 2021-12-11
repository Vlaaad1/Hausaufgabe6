package model;

import java.util.Comparator;
import java.util.Objects;

/**
 * Date: 5.12.2021
 * Class Student is a subclass of class Person
 */
public class Student extends Person{

    private int studentID;
    private int totalCredits;

    /**
     * Class Constructor
     * @param firstName is the firstname of a student
     * @param lastName is the lastname of a student
     * @param studentID is the ID of a student
     * @param totalCredits is the number of a credits that the student currently has
     */
    public Student(String firstName, String lastName, int studentID, int totalCredits) {
        super(firstName, lastName);
        this.studentID = studentID;
        this.totalCredits = totalCredits;
    }

    public int getStudentID() { return studentID; }
    public void setStudentID(int studentID) { this.studentID = studentID; }

    public int getTotalCredits() { return totalCredits; }
    public void setTotalCredits(int totalCredits) { this.totalCredits = totalCredits;}

    /**
     * method used to display a student
     * @return a string containing all attributes of a student
     */
    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", studentID=" + studentID +
                ", totalCredits=" + totalCredits +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        if (!super.equals(o)) return false;
        return getStudentID() == student.getStudentID()
                && getTotalCredits() == student.getTotalCredits();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getStudentID(), getTotalCredits());
    }

    public static class NameSorter implements Comparator<Student> {
        @Override
        public int compare(Student s1, Student s2){
            return s1.getLastName().compareToIgnoreCase(s2.getLastName());
        }
    }
}