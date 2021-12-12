package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;

/**
 * Classname: GUI_Application
 * Date: 12.12.2021
 * Class is used for creating the stages from the gui
 */
public class GUI_Application extends Application {
    private static Stage primaryStage;

    /**
     * Opens the main menu that contains two buttons
     * One for Student Menu
     * The other one for Teacher Menu
     */
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(GUI_Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load() , 500 , 500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Function that creates a new stage
     * @param file is a string who specifies the file where is the code for a menu
     * @param menuName is the name of the new menu
     */
    public static void openMenu(String file,String menuName) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(GUI_Application.class.getResource(file));
        Stage stage = new Stage();
        stage.setTitle(menuName);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}