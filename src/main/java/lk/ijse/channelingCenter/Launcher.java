package lk.ijse.channelingCenter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Launcher extends Application {
    public static void main(String []args){
        launch(args);
    }

    public void start(Stage stage) throws Exception{
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/navigationPannelFrom.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Channeling Center");
       // stage.setFullScreen(true);
        stage.show();
    }
}
