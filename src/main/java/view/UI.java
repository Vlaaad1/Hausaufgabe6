package view;

import controller.Controller;
import model.Course;
import model.Student;
import model.Teacher;

import java.io.IOException;
import java.util.Scanner;

/**
 * Date: 5.12.2021
 * Classname: UI
 *
 */
public class UI {
    private final Controller controller;
    private final Scanner input = new Scanner(System.in);

    /**
     * Class Constructor
     * @param controller is an object of type Controller used to take the main functionalities
     */
    public UI(Controller controller) {
        this.controller = controller;
    }

    public Course readCourse() {
        System.out.println("ID of the course:\n");
        int courseId = input.nextInt();
        System.out.println("Name of the course:\n");
        String name = input.next();
        System.out.println("ID of the teacher:\n");
        int teacherId = input.nextInt();
        System.out.println("Number of credits:\n");
        int credits = input.nextInt();
        System.out.println("Maximum number of students:\n");
        int maxEnrollment = input.nextInt();
        return new Course(courseId, name, teacherId, credits, maxEnrollment);
    }

    public Student readStudent() {
        System.out.println("First name of the student:\n");
        String firstName = input.next();
        System.out.println("Last name of the student:\n");
        String lastName = input.next();
        System.out.println("Student ID:\n");
        int sID = input.nextInt();
        System.out.println("Number of credits:\n");
        int totalCredits = input.nextInt();
        return new Student(firstName, lastName, sID, totalCredits);
    }

    public Teacher readTeacher() {
        System.out.println("First name of the teacher:\n");
        String firstName = input.next();
        System.out.println("Last name of the teacher:\n");
        String lastName = input.next();
        System.out.println("Teacher ID:\n");
        int tID = input.nextInt();
        return new Teacher(firstName, lastName, tID);
    }

    /**
     * Function display uses a switch command to decide which functionalities
     * of the controller to call;
     * User must choose a number between 1-14 and 15 exits the application
     */
    public void display() throws IOException {
        while (true) {
            System.out.println("""
                    Please choose an option:\s
                    1. Add a course\s
                    2. Add a student\s
                    3. Add a teacher\s
                    4. Register a student to a course\s
                    5. Display all courses\s
                    6. Display all students\s
                    7. Display all teachers\s
                    8. Delete a course\s
                    9. Delete a student\s
                    10. Delete a teacher\s
                    11. Sort the courses by credits\s
                    12. Display the courses with less than a given number of credits\s
                    13. Sort the students by credits\s
                    14. Display the students with less than 15 credits (filter)\s
                    15. Exit\s
                    """
            );
            int option = input.nextInt();
            int token = 0;
            switch (option) {
                case 1: //add course
                    System.out.println("ID of the course:\n");
                    int courseId = input.nextInt();
                    System.out.println("Name of the course:\n");
                    String name = input.next();
                    System.out.println("ID of the teacher:\n");
                    int teacherId = input.nextInt();
                    System.out.println("Number of credits:\n");
                    int credits = input.nextInt();
                    System.out.println("Maximum number of students:\n");
                    int maxEnrollment = input.nextInt();
                    controller.addCourse(courseId, name, teacherId, credits, maxEnrollment);
                    break;
                case 2: //add student
                    System.out.println("First name of the student:\n");
                    String firstName = input.next();
                    System.out.println("Last name of the student:\n");
                    String lastName = input.next();
                    System.out.println("Student ID:\n");
                    int sID = input.nextInt();
                    System.out.println("Number of credits:\n");
                    int totalCredits = input.nextInt();
                    controller.addStudent(firstName, lastName, sID, totalCredits);
                    break;
                case 3: //add teacher
                    System.out.println("First name of the teacher:\n");
                    firstName = input.next();
                    System.out.println("Last name of the teacher:\n");
                    lastName = input.next();
                    System.out.println("Teacher ID:\n");
                    int tID = input.nextInt();
                    controller.addTeacher(firstName, lastName, tID);
                    break;
                case 4: //register
                    this.controller.register(readStudent(), readCourse());
                    break;
                case 5: //display all courses
                    System.out.println(controller.retrieveCourses());
                    break;
                case 6: //display all students
                    System.out.println(controller.retrieveStudents());
                    break;
                case 7: //display all teachers
                    System.out.println(controller.retrieveTeachers());
                    break;
                case 8: //delete a course
                    controller.deleteCourse(this.readCourse());
                    break;
                case 9: //delete a student
                    controller.deleteStudent(this.readStudent());
                    break;
                case 10: //delete a teacher
                    controller.deleteTeacher(this.readTeacher());
                    break;
                case 11: //sort courses by credits
                    System.out.println(controller.sortCoursesByCredits());
                    break;
                case 12: //filter courses by a given no of credits
                    System.out.println("Please write the number of credits:\n");
                    credits = input.nextInt();
                    System.out.println(controller.filterCoursesByCredit(credits));
                    break;
                case 13: //sort students by credits
                    System.out.println(controller.sortStudentsByCredits());
                    break;
                case 14: //filter students
                    System.out.println(controller.filterStudentsByCredits());
                    break;
                case 15: //exit the app
                    token = 1;
                    break;
            }
            if (token == 1) break;
        }
    }
}
