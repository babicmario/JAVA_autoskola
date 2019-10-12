package org.openjfx.mavenproject3;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import org.openjfx.mavenproject3.Connect;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnSignin;

    @FXML
    private Label lblErrors;
    @FXML
    private Label lbl_close;
     @FXML
    private Button btnKandidati;
      @FXML
    private Button btnVozila;
       @FXML
    private Button btnInstruktori;
        @FXML
    private Button btnPrijavljeni;
        @FXML
    private JFXButton btnLogout;

    @FXML
    /* private void handleButtonAction(ActionEvent event) throws IOException {

        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));

        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setTitle("Admin panel");
        app_stage.setScene(home_page_scene);
        app_stage.show();

    }

     */
    private void handleButtonAction(ActionEvent event) throws IOException {
        if (event.getSource() == lbl_close) {
            System.exit(0);
        }
        if (event.getSource() == btnSignin) {
            if (LogIn().equals("Success")) {
                try {
                    Node node = (Node) event.getSource();
                    
                    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                   
                    app_stage.close();

                    Parent home_page_parent = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
                    Scene home_page_scene = new Scene(home_page_parent);
                    app_stage.setTitle("Admin panel");
                    app_stage.setScene(home_page_scene);
                    app_stage.show();
                } catch (IOException e) {
                    System.err.print(e.getMessage());
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public LoginController() {

        con = Connect.conDB();
    }
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private String LogIn() {

        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        String sql = "SELECT * FROM uloga Where korisnickoime = ? and lozinka = ?";

        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            
            if (!resultSet.next()) {
                lblErrors.setTextFill(Color.TOMATO);
                lblErrors.setText("Enter Correct Username/Passsword");
                System.err.println("Wrong Logins --///");
                return "Error";

            } else {
                lblErrors.setTextFill(Color.GREEN);
                lblErrors.setText("Login Successful ... Redirecting");
                System.out.println("Successful Login");
                return "Success";
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return "Exception";
        }

    }
    @FXML
     JFXTextField txtUsername1;
    @FXML
     JFXPasswordField txtPassword1;
    @FXML
       JFXTextField txtEmail;
    
    @FXML
    public void dodajKorisnika() throws SQLException{
    
      String kime= txtUsername1.getText();
      String loz = txtPassword1.getText();
      String email= txtEmail.getText();
      
      Connection connection=Connect.conDB();
    
     ResultSet rs = null;
      
      
      String query = "INSERT INTO uloga (korisnickoime, lozinka, email) VALUES (?,?,?)";
      
      preparedStatement = null;
      
      try{
           
          preparedStatement=connection.prepareStatement(query);
          
          preparedStatement.setString(1,kime);
          preparedStatement.setString(2,loz);
          preparedStatement.setString(3,email);
         
      }
      catch(SQLException e)
               {
                 System.out.println(e);
      }
      finally{
      
        preparedStatement.execute();
        preparedStatement.close();
      }
         MainApp.showInformationAlertBox("Registracija je uspjesna!");
    }
    
    
    
    
  
       public void changeScreenButtonPushed(ActionEvent event)throws IOException{
           
           if (event.getSource() == btnKandidati) {
           
        
           Parent home_page_parent = FXMLLoader.load(getClass().getResource("/fxml/Kandidati.fxml"));
                    Scene home_page_scene = new Scene(home_page_parent);
                    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    
                    app_stage.close();
                    app_stage.setScene(home_page_scene);
                    app_stage.show();
           }
           
            if (event.getSource() == btnInstruktori) {
           
        
           Parent home_page_parent = FXMLLoader.load(getClass().getResource("/fxml/Instruktori.fxml"));
                    Scene home_page_scene = new Scene(home_page_parent);
                    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    
                    app_stage.close();
                    app_stage.setScene(home_page_scene);
                    app_stage.show();
           }
            
            
            
            if (event.getSource() == btnVozila) {
           
        
           Parent home_page_parent = FXMLLoader.load(getClass().getResource("/fxml/Vozila.fxml"));
                    Scene home_page_scene = new Scene(home_page_parent);
                    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    
                    app_stage.close();
                    app_stage.setScene(home_page_scene);
                    app_stage.show();
           }
            
            if (event.getSource() == btnPrijavljeni) {
           
        
           Parent home_page_parent = FXMLLoader.load(getClass().getResource("/fxml/PrijavljeniKandidati.fxml"));
                    Scene home_page_scene = new Scene(home_page_parent);
                    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    
                    app_stage.close();
                    app_stage.setScene(home_page_scene);
                    app_stage.show();
           }
            
            if (event.getSource() == btnLogout) {
           
        
           Parent home_page_parent = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
                    Scene home_page_scene = new Scene(home_page_parent);
                    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    
                    app_stage.close();
                    app_stage.setScene(home_page_scene);
                    app_stage.show();
           }
       }
        
}
