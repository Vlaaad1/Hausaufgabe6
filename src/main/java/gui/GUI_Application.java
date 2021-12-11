package gui;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import repository.CourseJDBC_Repository;
import repository.EnrollmentJDBC_Repository;
import repository.StudentJDBC_Repository;
import repository.TeacherJDBC_Repository;

import java.io.IOException;

public class GUI_Application extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(GUI_Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load() , 500 , 500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void openMenu(String file,String menuName) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(GUI_Application.class.getResource(file));
        Stage stage = new Stage();
        stage.setTitle(menuName);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}