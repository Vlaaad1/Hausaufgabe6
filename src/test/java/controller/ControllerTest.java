package controller;

import model.Course;
import model.Student;
import model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.CourseJDBC_Repository;
import repository.EnrollmentJDBC_Repository;
import repository.StudentJDBC_Repository;
import repository.TeacherJDBC_Repository;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    CourseJDBC_Repository courseJDBCRepository = new CourseJDBC_Repository();
    StudentJDBC_Repository studentJDBCRepository = new StudentJDBC_Repository();
    TeacherJDBC_Repository teacherJDBCRepository = new TeacherJDBC_Repository();
    EnrollmentJDBC_Repository enrolledJDBCRepository = new EnrollmentJDBC_Repository();

    Controller controller = new Controller(courseJDBCRepository,studentJDBCRepository,
            teacherJDBCRepository,enrolledJDBCRepository);

    Course course1 = new Course(1,"MAP",99,6,70);
    Course course2 = new Course(2,"BD",67,3,60);
    Course course3 = new Course(3,"PS",67,4,75);
    Course course4 = new Course(4,"FP",99,5,50);

    Student student1 = new Student("Ana","Brando",1,20);
    Student student2 = new Student("George","Pratt",2,25);
    Student student3 = new Student("Chris","Sandler",3,35);
    Student student4 = new Student("Keanu","Reeves",4,30);
    Student student5 = new Student("Tom","Hardy",5,30);
    Student student6 = new Student("John","Long",6,15);

    Teacher teacher1 = new Teacher("Robert","DeNiro", 99);
    Teacher teacher2 = new Teacher("Joe", "Pesci", 67);

    @BeforeEach
    void setUp() throws IOException {
        studentJDBCRepository.delete(student1);
        studentJDBCRepository.delete(student2);
        studentJDBCRepository.delete(student3);
        studentJDBCRepository.delete(student4);
        studentJDBCRepository.delete(student5);
        studentJDBCRepository.delete(student6);

        teacherJDBCRepository.delete(teacher1);
        teacherJDBCRepository.delete(teacher2);

        courseJDBCRepository.delete(course1);
        courseJDBCRepository.delete(course2);
        courseJDBCRepository.delete(course3);
        courseJDBCRepository.delete(course4);

        enrolledJDBCRepository.delete(student1.getStudentID(), course2.getCourseID());
        enrolledJDBCRepository.delete(student1.getStudentID(), course3.getCourseID());
        enrolledJDBCRepository.delete(student4.getStudentID(), course1.getCourseID());
        enrolledJDBCRepository.delete(student5.getStudentID(), course2.getCourseID());
        enrolledJDBCRepository.delete(student2.getStudentID(), course2.getCourseID());
    }

    //teste curs
    @Test
    void testFindAllCourses() throws IOException {
        assertEquals(controller.retrieveCourses().size(),0);
        controller.addCourse(1,"MAP",99,6,70);
        controller.addCourse(2,"BD",67,3,60);
        controller.addCourse(3,"PS",67,4,75);
        assertEquals(controller.retrieveCourses().size(),3);
    }
    @Test
    void testAddCourse() throws IOException {
        assertEquals(controller.retrieveCourses().size(),0);
        controller.addCourse(3,"PS",67,4,75);
        controller.addCourse(4,"FP",99,5,50);
        assertEquals(controller.retrieveCourses().size(),2);
    }

    @Test
    void testDeleteCourse() throws IOException {
        assertEquals(controller.retrieveCourses().size(),0);
        controller.addCourse(1,"MAP",99,6,70);
        controller.addCourse(2,"BD",67,3,60);
        controller.addCourse(3,"PS",67,4,75);
        controller.addCourse(4,"FP",99,5,50);
        assertEquals(controller.retrieveCourses().size(),4);
        controller.deleteCourse(course2);
        controller.deleteCourse(course3);
        controller.deleteCourse(course4);
        assertEquals(controller.retrieveCourses().size(),1);
    }

    @Test
    void testfilterCoursesByCredit()  throws IOException {
        assertEquals(controller.retrieveCourses().size(),0);
        controller.addCourse(1,"MAP",99,6,70);
        controller.addCourse(2,"BD",67,3,60);
        controller.addCourse(3,"PS",67,4,75);
        controller.addCourse(4,"FP",99,5,50);
        assertEquals(controller.retrieveCourses().size(),4);
        assertEquals(controller.filterCoursesByCredit(6).size(),3);
        assertEquals(controller.filterCoursesByCredit(6).get(1),course3);
    }

    @Test
    void testSortCoursesByCredits() throws IOException {
        assertEquals(controller.retrieveCourses().size(),0);
        controller.addCourse(1,"MAP",99,6,70);
        controller.addCourse(2,"BD",67,3,60);
        controller.addCourse(3,"PS",67,4,75);
        controller.addCourse(4,"FP",99,5,50);
        assertEquals(controller.retrieveCourses().size(),4);
        assertEquals(controller.sortCoursesByCredits().get(0),course2);
    }



    //teste student
    @Test
    void testFindAllStudents() throws IOException {
        assertEquals(controller.retrieveStudents().size(),0);
        controller.addStudent("Ana","Brando",1,20);
        controller.addStudent("George","Pratt",2,25);
        controller.addStudent("Chris","Sandler",3,35);
        assertEquals(controller.retrieveStudents().size(),3);
    }

    @Test
    void testAddStudent() throws IOException {
        assertEquals(controller.retrieveStudents().size(),0);
        controller.addStudent("Ana","Brando",1,20);
        controller.addStudent("George","Pratt",2,25);
        controller.addStudent("Chris","Sandler",3,35);
        controller.addStudent("Keanu","Reeves",4,30);
        controller.addStudent("Tom","Hardy",5,30);
        controller.addStudent("John","Long",6,15);
        assertEquals(controller.retrieveStudents().size(),6);
    }

    @Test
    void testDeleteStudent() throws IOException {
        assertEquals(controller.retrieveStudents().size(),0);
        controller.addStudent("Ana","Brando",1,20);
        controller.addStudent("George","Pratt",2,25);
        controller.addStudent("Chris","Sandler",3,35);
        controller.addStudent("Keanu","Reeves",4,30);
        controller.addStudent("Tom","Hardy",5,30);
        controller.addStudent("John","Long",6,15);
        assertEquals(controller.retrieveStudents().size(),6);
        controller.deleteStudent(student2);
        controller.deleteStudent(student3);
        controller.deleteStudent(student1);
        controller.deleteStudent(student5);
        assertEquals(controller.retrieveStudents().size(),2);
    }

    @Test
    void testFilterStudentsByCredits() throws IOException {
        assertEquals(controller.retrieveStudents().size(),0);
        controller.addStudent("Ana","Brando",1,20);
        controller.addStudent("Keanu","Reeves",4,30);
        controller.addStudent("Tom","Hardy",5,30);
        controller.addStudent("John","Long",6,15);
        assertEquals(controller.retrieveStudents().size(),4);
        assertEquals(controller.filterStudentsByCredits().size(),2);
        assertEquals(controller.filterStudentsByCredits().get(1),student6);
        assertEquals(controller.filterStudentsByCredits().get(0),student1);
        assertEquals(controller.retrieveStudents().size(),4);
    }

    @Test
    void testSortStudentsByCredits() throws IOException {
        assertEquals(controller.retrieveStudents().size(),0);
        controller.addStudent("Ana","Brando",1,20);
        controller.addStudent("George","Pratt",2,25);
        controller.addStudent("Chris","Sandler",3,35);
        controller.addStudent("Keanu","Reeves",4,30);
        controller.addStudent("Tom","Hardy",5,30);
        assertEquals(controller.retrieveStudents().size(),5);
        assertEquals(controller.sortStudentsByCredits().size(),5);
        assertEquals(controller.sortStudentsByCredits().get(0),student1);
        assertEquals(controller.sortStudentsByCredits().get(4),student3);
    }

    //teste teacher
    @Test
    void testFindAllTeachers() throws IOException {
        assertEquals(controller.retrieveTeachers().size(),0);
        controller.addTeacher("Robert","DeNiro", 99);
        assertEquals(controller.retrieveTeachers().size(),1);
    }

    @Test
    void testAddTeacher() throws IOException {
        assertEquals(controller.retrieveTeachers().size(),0);
        controller.addTeacher("Robert","DeNiro", 99);
        controller.addTeacher("Joe","Pesci", 67);
        assertEquals(controller.retrieveTeachers().size(),2);
    }

    @Test
    void testDeleteTeacher() throws IOException {
        assertEquals(controller.retrieveTeachers().size(),0);
        controller.addTeacher("Robert","DeNiro", 99);
        controller.addTeacher("Joe","Pesci", 67);
        assertEquals(controller.retrieveTeachers().size(),2);
        controller.deleteTeacher(teacher2);
        assertEquals(controller.retrieveTeachers().size(),1);
        controller.deleteTeacher(teacher1);
        assertEquals(controller.retrieveTeachers().size(),0);
    }

    //test register
    @Test
    void testRegister() throws IOException {
        assertEquals(controller.retrieveCourses().size(),0);
        assertEquals(controller.retrieveStudents().size(),0);

        controller.addStudent("Ana","Brando",1,20);
        controller.addStudent("George","Pratt",2,25);
        controller.addStudent("Chris","Sandler",3,35);
        controller.addStudent("Keanu","Reeves",4,30);
        controller.addStudent("Tom","Hardy",5,30);
        assertEquals(controller.retrieveStudents().size(),5);

        controller.addCourse(1,"MAP",99,6,70);
        controller.addCourse(2,"BD",67,3,60);
        controller.addCourse(3,"PS",67,4,75);
        assertEquals(controller.retrieveCourses().size(),3);

        assertTrue(controller.register(student1, course2));
        assertTrue(controller.register(student1, course3));
        assertTrue(controller.register(student4, course1));
        assertTrue(controller.register(student5, course2));
        assertTrue(controller.register(student2, course2));
    }

}