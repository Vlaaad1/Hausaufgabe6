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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    private ListView<String> myListView;

    @FXML
    private TextField studentFirstName;
    @FXML
    private TextField studentLastName;
    @FXML
    private TextField studentID;
    @FXML
    private TextField studentTotalCredits;
    @FXML
    private TextField studentCourseID;
    @FXML
    private TextField teacherFirstName;
    @FXML
    private TextField teacherLastName;
    @FXML
    private TextField teacherID;
    @FXML
    private TextField teacherCourseID;

    @FXML
    protected void onStudentMenuButtonClick() throws IOException {
        GUI_Application.openMenu("studentMenu.fxml", "Student Menu");
    }

    @FXML
    protected void onTeacherMenuButtonClick() throws IOException {
        GUI_Application.openMenu("teacherMenu.fxml", "Teacher Menu");
    }

    @FXML
    protected void saveForMenuStudent() throws IOException {
        controller.addStudent(studentFirstName.getText(), studentLastName.getText(),
                Integer.parseInt(studentID.getText()), Integer.parseInt(studentTotalCredits.getText()));
    }

    @FXML
    protected void registerForMenuStudent() throws IOException {
        controller.registerWithIDs(Integer.parseInt(studentID.getText()),
                Integer.parseInt(studentCourseID.getText()));
    }

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

    @FXML
    protected void saveForMenuTeacher() throws IOException {
        controller.addTeacher(teacherFirstName.getText(), teacherLastName.getText(),
                Integer.parseInt(teacherID.getText()));
    }

    @FXML
    public void showStudents() throws IOException {
        if(teacherID.getText() != null) {
            ArrayList<Student> students = this.controller.findStudentsByTeacherID(Integer.parseInt(teacherID.getText()));
            List<String> strings = students.stream().map(Student::toString).collect(Collectors.toList());
            myListView.getItems().addAll(strings);
            myListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }

    }

    @FXML
    public void refresh() throws IOException{
        if(teacherCourseID.getText() != null && teacherID.getText() != null){
            myListView.getItems().clear();
            ArrayList<Student> students = this.controller.findStudentsByCourseID(Integer.parseInt(teacherCourseID.getText()));
            List<String> strings = students.stream().map(Student::toString).collect(Collectors.toList());
            myListView.getItems().addAll(strings);
            myListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
    }

    @FXML
    public void clearForMenuStudent(){
        studentFirstName.clear();
        studentLastName.clear();
        studentID.clear();
        studentTotalCredits.clear();
        studentCourseID.clear();
    }
    @FXML
    public void clearForMenuTeacher(){
        teacherFirstName.clear();
        teacherLastName.clear();
        teacherID.clear();
        teacherCourseID.clear();
        myListView.getItems().clear();
    }
}