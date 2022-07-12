package ua.edu.sumdu.j2se.krutko.tasks;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static final String FXML_PATH = "/ua/edu/sumdu/j2se/krutko/tasks/view/";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(FXML_PATH+"MainDialogWindow.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 700, 600));
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(700);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
