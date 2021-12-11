package model;

import java.util.Objects;

/**
 * Date: 5.12.2021
 * Classname: Course
 */
public class Course {

    private int courseID;
    private String name;
    private int teacherID;
    private int credits;
    private int maxEnrollment;

    /**
     * Class constructor
     * @param courseID is the ID of the course
     * @param name is the name of the course
     * @param teacherID is the ID of the teacher assigned to the course
     * @param credits is the number of credits for the course
     * @param maxEnrollment is the maximal number of students enrolled to the course
     */
    public Course(int courseID, String name, int teacherID, int credits, int maxEnrollment) {
        this.courseID = courseID;
        this.name = name;
        this.teacherID = teacherID;
        this.credits = credits;
        this.maxEnrollment = maxEnrollment;
    }

    /**
     * getters und setters for attributes
     */

    public int getCourseID() { return courseID; }
    public void setCourseID(int courseID) { this.courseID = courseID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getTeacherID() { return teacherID; }
    public void setTeacherId(int teacherID) { this.teacherID = teacherID; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public int getMaxEnrollment() { return maxEnrollment; }
    public void setMaxEnrollment(int maxEnrollment) { this.maxEnrollment = maxEnrollment;}

    /**
     * method used to display a course
     * @return a string containing all attributes of a course
     */
    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + courseID +
                ", name='" + name + '\'' +
                ", teacherID=" + teacherID +
                ", credits=" + credits +
                ", maxEnrollment=" + maxEnrollment +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return getCourseID() == course.getCourseID() && getCredits() == course.getCredits()
                && getMaxEnrollment() == course.getMaxEnrollment()
                && Objects.equals(getName(), course.getName())
                && Objects.equals(getTeacherID(), course.getTeacherID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourseID(), getName(), getTeacherID(),
                getCredits(), getMaxEnrollment());
    }

}