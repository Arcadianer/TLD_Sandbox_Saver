package core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main_screen.fxml"));
        primaryStage.setTitle("Saver");
        Scene mainscreen=new Scene(root);
        primaryStage.setScene(mainscreen);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
