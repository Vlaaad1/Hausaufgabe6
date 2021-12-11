package model;

import java.util.Objects;

/**
 * Date: 5.12.2021
 * Class Teacher is a subclass of class Person
 */
public class Teacher extends Person{

    private int teacherID;

    /**
     * Class Constructor
     * @param firstName is the firstname of a teacher
     * @param lastName is the lastname of a teacher
     * @param teacherID is the ID of a teacher
     */
    public Teacher(String firstName, String lastName, int teacherID) {
        super(firstName, lastName);
        this.teacherID = teacherID;
    }

    public int getTeacherID() { return teacherID; }
    public void setTeacherID(int teacherID) { this.teacherID = teacherID; }

    /**
     * method used to display a teacher
     * @return a string containing all attributes of a teacher
     */
    @Override
    public String toString() {
        return "Teacher{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", teacherID=" + teacherID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher teacher)) return false;
        if (!super.equals(o)) return false;
        return getTeacherID() == teacher.getTeacherID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTeacherID());
    }
}
