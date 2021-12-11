package controller;

import model.Course;
import model.Student;
import model.Teacher;
import repository.CourseJDBC_Repository;
import repository.EnrollmentJDBC_Repository;
import repository.StudentJDBC_Repository;
import repository.TeacherJDBC_Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Date: 5.12.2021
 * Classname: Controller
 * In this class are defined the main functionalities used by the UI
 */
public record Controller(CourseJDBC_Repository courseRepo, StudentJDBC_Repository studentRepo,
                         TeacherJDBC_Repository teacherRepo, EnrollmentJDBC_Repository enrolledRepo) {

    /**
     * method used to add a course in the database
     * @param courseID is the ID of the course we want to add
     * @param name is the name of the course we want to add
     * @param teacherID is the ID of the teacher assigned to the course we want to add
     * @param credits is the number of credits of the course we want to add
     * @param maxEnrollment is the maximal number of students that can join the course we want to add
     * @return true if we successfully added the course in the database
     */
    public boolean addCourse(int courseID , String name , int teacherID , int credits , int maxEnrollment) throws IOException {
        Course course = new Course(courseID , name , teacherID , credits , maxEnrollment);
        this.courseRepo.save(course);
        return true;
    }

    /**
     * method used to add a student in the database
     * @param firstName is the firstname of the student we want to add in the database
     * @param lastName is the lastname of the student we want to add in the database
     * @param studentID is the ID of the student we want to add in the database
     * @param totalCredits is the number of credits that the student has
     * @return true if we successfully added the student in the database
     */
    public boolean addStudent(String firstName , String lastName , int studentID , int totalCredits) throws IOException {
        Student student = new Student(firstName , lastName , studentID , totalCredits);
        this.studentRepo.save(student);
        return true;
    }

    /**
     * method used to add a teacher in the database
     * @param firstName is the firstname of the teacher we want to add in the database
     * @param lastName is the lastname of the teacher we want to add in the database
     * @param teacherID is the ID of the teacher we want to add in the database
     * @return true if we successfully added the student in the database
     */
    public boolean addTeacher(String firstName , String lastName , int teacherID) throws IOException {
        Teacher teacher = new Teacher(firstName , lastName , teacherID);
        this.teacherRepo.save(teacher);
        return true;
    }

    /**
     * using this method the user can enroll a specified student to a certain course
     * @param student is an object of type Student
     * @param course is an object of type Course
     * @return true if we successfully added a row in the enrollment table from the database
     */
    public boolean register(Student student , Course course) throws IOException {
        Student foundStudent = this.studentRepo.findOne(student);
        Course foundCourse = this.courseRepo.findOne(course);
        this.enrolledRepo.save(foundStudent.getStudentID() , foundCourse.getCourseID());
        return true;
    }

    /**
     * using this method the user can enroll a specified student to a certain course using their IDs
     */
    public void registerWithIDs(int studentID , int courseID) throws IOException {
        this.enrolledRepo.save(studentID , courseID);
    }
    /**
     * @return the list of all courses from the database
     */
    public ArrayList<Course> retrieveCourses() throws IOException {
        return (ArrayList<Course>) courseRepo.findAll();
    }

    /**
     * @return the list of all students from the database
     */
    public ArrayList<Student> retrieveStudents() throws IOException {
        return (ArrayList<Student>) studentRepo.findAll();
    }

    /**
     * @return the list of all teacher from the database
     */
    public ArrayList<Teacher> retrieveTeachers() throws IOException {
        return (ArrayList<Teacher>) teacherRepo.findAll();
    }

    /**
     * @param course is the course we want to delete from the database
     * @return true if we successfully deleted the course from the database
     */
    public boolean deleteCourse(Course course) throws IOException {
        Course foundCourse = this.courseRepo.findOne(course);
        this.enrolledRepo.deleteStudentsByCourseID(foundCourse.getCourseID());
        this.courseRepo.delete(foundCourse);
        return true;
    }

    /**
     * @param student is the student we want to delete from the database
     * @return true if we successfully deleted the student from the database
     */
    public boolean deleteStudent(Student student) throws IOException {
        Student foundStudent = this.studentRepo.delete(student);
        this.enrolledRepo.deleteCoursesByStudentID(foundStudent.getStudentID());
        this.studentRepo.delete(foundStudent);
        return true;
    }

    /**
     * @param teacher is the teacher we want to delete from the database
     * @return true if we successfully deleted the teacher from the database
     */
    public boolean deleteTeacher(Teacher teacher) throws IOException {
        Teacher foundTeacher = this.teacherRepo.findOne(teacher);
        ArrayList<Course> courses = (ArrayList<Course>) this.courseRepo.findAll();
        for (Course c : courses) {
            if (c.getTeacherID() == foundTeacher.getTeacherID()) {
                this.deleteCourse(c);
            }
        }
        this.teacherRepo.delete(foundTeacher);
        return true;
    }

    /**
     * this method is used to sort the courses depending on their credits
     * @return a list of courses sorted as we wanted
     */
    public ArrayList<Course> sortCoursesByCredits() throws IOException {
        ArrayList<Course> coursesList = (ArrayList<Course>) courseRepo.findAll();
        return (ArrayList<Course>) coursesList.stream()
                .sorted(Comparator.comparingInt(Course::getCredits))
                .collect(Collectors.toList());
    }

    /**
     * this method is used to find courses with a number of credits lower than a specified value
     * @param credits is an integer value corresponding to a valid number of credits
     * @return a list of courses filtered as we wanted
     */
    public ArrayList<Course> filterCoursesByCredit(int credits) throws IOException {
        Predicate<Course> byCredits = course -> course.getCredits() < credits;
        ArrayList<Course> coursesList = (ArrayList<Course>) courseRepo.findAll();
        return (ArrayList<Course>) coursesList.stream().filter(byCredits).collect(Collectors.toList());
    }

    /**
     * this method is used to sort the students depending on their credits
     * @return a list of students sorted as we wanted
     */
    public ArrayList<Student> sortStudentsByCredits() throws IOException {
        ArrayList<Student> studentsList = (ArrayList<Student>) studentRepo.findAll();
        return (ArrayList<Student>) studentsList.stream()
                .sorted(Comparator.comparingInt(Student::getTotalCredits))
                .collect(Collectors.toList());
    }

    /**
     * this method is used to find students with a number of credits lower than 25
     * @return a list of courses filtered as we wanted
     */
    public ArrayList<Student> filterStudentsByCredits() throws IOException {
        Predicate<Student> byCredits = student -> student.getTotalCredits() < 25;
        ArrayList<Student> studentsList = (ArrayList<Student>) studentRepo.findAll();
        return (ArrayList<Student>) studentsList.stream().filter(byCredits).collect(Collectors.toList());
    }

    public ArrayList<Student> findStudentsByTeacherID(int teacherID) throws IOException{
        ArrayList<Course> courses = (ArrayList<Course>) this.courseRepo.findAll();
        ArrayList<Course> foundWithId = new ArrayList<>();
        ArrayList<Integer> studentsIds = new ArrayList<>();
        ArrayList<Student> students = retrieveStudents();
        for (Course course: courses)
        {
            if(course.getTeacherID() == teacherID)
                foundWithId.add(course);
        }
        for (Course course: foundWithId){
            studentsIds.addAll(this.enrolledRepo.findStudentsByCourseID(course.getCourseID()));
        }
        students.removeIf(student -> !studentsIds.contains(student.getStudentID()));
        return students;
    }

    public ArrayList<Student> findStudentsByCourseID(int courseID) throws IOException{
        ArrayList<Integer> studentIDs = this.enrolledRepo.findStudentsByCourseID(courseID);
        ArrayList<Student> studentList = (ArrayList<Student>) this.studentRepo.findAll();
        ArrayList<Student> finalList = new ArrayList<>();
        for(Student student: studentList){
            if(studentIDs.contains(student.getStudentID())){
                finalList.add(student);
            }
        }
        return finalList;
    }
}
