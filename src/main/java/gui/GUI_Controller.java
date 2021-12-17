package gui;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import model.Student;
import repository.CourseJDBC_Repository;
import repository.EnrollmentJDBC_Repository;
import repository.StudentJDBC_Repository;
import repository.TeacherJDBC_Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classname: GUI_Controller
 * Date: 12.12.2021
 * Class that contains the main functionalities for the graphic interface
 */
public class GUI_Controller {
    CourseJDBC_Repository courseJDBCRepository = new CourseJDBC_Repository();
    StudentJDBC_Repository studentJDBCRepository = new StudentJDBC_Repository();
    TeacherJDBC_Repository teacherJDBCRepository = new TeacherJDBC_Repository();
    EnrollmentJDBC_Repository enrolledJDBCRepository = new EnrollmentJDBC_Repository();

    Controller controller = new Controller(courseJDBCRepository,studentJDBCRepository,
            teacherJDBCRepository,enrolledJDBCRepository);

    @FXML
    private Label totalCreditsLabel;

    @FXML
    private TextField loginStudentID;
    @FXML
    private Label invalidLabelStudent;

    @FXML
    private TextField loginTeacherID;
    @FXML
    private Label invalidLabelTeacher;

    @FXML
    private ListView<String> myListView;

    @FXML
    private TextField studentID;
    @FXML
    private TextField studentCourseID;
    @FXML
    private Label doneLabel;

    @FXML
    private TextField teacherID;
    @FXML
    private TextField teacherCourseID;


    /**
     *This function is used to open the login window for student
     */
    @FXML
    protected void openStudentLogin() throws IOException {
        GUI_Application.openMenu("studentLogin.fxml", "Student Login");
    }

    /**
     *This function is used to open the menu for student
     */
    @FXML
    protected void onStudentMenuButtonClick() throws IOException {
        int id = Integer.parseInt(loginStudentID.getText());
        if(controller.findStudentByID(id)){
            GUI_Application.openMenu("studentMenu.fxml", "Student Menu");
        }
        else {
            invalidLabelStudent.setText("Invalid ID. Please try again.");
        }
    }

    /**
     *This function is used to open the login window for student
     */
    @FXML
    protected void openTeacherLogin() throws IOException {
        GUI_Application.openMenu("teacherLogin.fxml", "Teacher Login");
    }

    /**
     * This function is used to open the menu for teacher if the id is valid
     * or set a default message for an invalid value
     */
    @FXML
    protected void onTeacherMenuButtonClick() throws IOException {
        int id = Integer.parseInt(loginTeacherID.getText());
        if(controller.findTeacherByID(id)){
            GUI_Application.openMenu("teacherMenu.fxml", "Teacher Menu");
        }
        else {
            invalidLabelTeacher.setText("Invalid ID. Please try again.");
        }
    }

    /**
     *This function is used to register a student to a course in the database using the gui
     */
    @FXML
    protected void registerForMenuStudent() throws IOException {
        controller.registerWithIDs(Integer.parseInt(studentID.getText()),
                Integer.parseInt(studentCourseID.getText()));
        doneLabel.setText("Done!");
    }

    /**
     * This function is used to display the credits of a specified student from the database
     */
    @FXML
    protected void showStudentCredits() throws IOException {
        ArrayList<Student> studentList = controller.retrieveStudents();
        boolean found = false;
        for (Student s:studentList) {
            if(s.getStudentID() == Integer.parseInt(studentID.getText())){
                totalCreditsLabel.setText("Credits:" + s.getTotalCredits());
                found =  true;
            }
        }
        if (found=false){
            totalCreditsLabel.setText("Student not found");
        }

    }

    /**
     * This function is used to display the students who are registered to all of
     * teacher's courses depending on teacher's ID
     */
    @FXML
    public void showStudents() throws IOException {
        if(teacherID.getText() != null) {
            ArrayList<Student> students = this.controller.findStudentsByTeacherID(
                    Integer.parseInt(teacherID.getText()));
            List<String> strings = students.stream().map(Student::toString).collect(Collectors.toList());
            myListView.getItems().addAll(strings);
            myListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }

    }

    /**
     * This function is used to display the students who are registered to course depending on
     * course's ID and teacher's ID, clearing the previous list and updating all tables
     * from the database
     */
    @FXML
    public void refresh() throws IOException{
        if(teacherCourseID.getText() != null && teacherID.getText() != null){
            myListView.getItems().clear();
            ArrayList<Student> students = this.controller.findStudentsByCourseAndTeacherID(
                    Integer.parseInt(teacherCourseID.getText()), Integer.parseInt(teacherID.getText()));
            List<String> strings = students.stream().map(Student::toString).collect(Collectors.toList());
            myListView.getItems().addAll(strings);
            myListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
    }

    /**
     * Function to clear the text fields in the Student Menu
     */
    @FXML
    public void clearForMenuStudent(){
        studentID.clear();
        studentCourseID.clear();
        doneLabel.setText("");
    }

    /**
     * Function to clear the text fields in the Teacher Menu
     */
    @FXML
    public void clearForMenuTeacher(){
        teacherID.clear();
        teacherCourseID.clear();
        myListView.getItems().clear();
    }

    /**
     * Function to clear the text fields in the Student Login Menu
     */
    @FXML
    public void clearIdLogin(){
        loginStudentID.clear();
        invalidLabelStudent.setText("");
    }

    /**
     * Function to clear the text fields in the Teacher Login Menu
     */
    @FXML
    public void clearTeacherIdLogin(){
        loginTeacherID.clear();
        invalidLabelTeacher.setText("");
    }
}