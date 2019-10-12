package org.openjfx.mavenproject3;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class MainApp extends Application {
    
    

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/fxml.css");
        
        stage.setTitle("Prijava na sustav");
        stage.setScene(scene);
        
        stage.hide();
        stage.show();
    }

   public static void showInformationAlertBox(String message)
   {
      Alert alert = new Alert (AlertType.INFORMATION);
      alert.setTitle("Administracija autoskole");
      alert.setHeaderText(null);
      alert.setContentText(message);
      alert.showAndWait();
   
   
   
   }
    public static void main(String[] args) {
        launch(args);
    }

}
