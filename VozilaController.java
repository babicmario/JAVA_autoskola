/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.mavenproject3;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author mario
 */
public class VozilaController implements Initializable {
    
     @FXML
     private TableView<VozilaModel> tablevozila;
     @FXML
     private TableColumn<VozilaModel,String> col_id ;
     @FXML
     private TableColumn<VozilaModel,String> col_naziv;
     @FXML
     private TableColumn<VozilaModel,String> col_godina ;
     @FXML
     private TableColumn<VozilaModel,String> col_servis ;
         
          
          Connection connection=Connect.conDB();
     PreparedStatement preparedStatement = null;
     ResultSet rs = null;
     ObservableList<VozilaModel> oblist =FXCollections.observableArrayList();
     
      @Override
            public void initialize(URL location, ResourceBundle resource) {
       
        
             col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
             col_naziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
             col_godina.setCellValueFactory(new PropertyValueFactory<>("godina"));
             col_servis.setCellValueFactory(new PropertyValueFactory<>("servis"));
             
             
             tablevozila.setItems(oblist);
             //editableCols();
             ucitajbazu();
             
             
    }
            public void ucitajbazu(){
        
        String query = "select * from vozilo";
        
        try{
            txtNaziv.clear();
           txtGodina.clear();
            txtServis.clear();
            
           
            oblist.clear();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            
            while(rs.next())
            {
                 oblist.add(new VozilaModel(rs.getString("ID"), 
                         rs.getString("Naziv"),
                          rs.getString("Godina"),
                         rs.getString("Servis")));
                 
                 tablevozila.setItems(oblist);
             }
            
            preparedStatement.close();
            rs.close();
        }
        catch(Exception e){
             System.err.println(e);
        }
    }
            @FXML 
    private JFXTextField txtNaziv;
    @FXML 
    private JFXTextField txtGodina;
    @FXML 
    private JFXTextField txtServis;
    
    
    @FXML
    public void dodajKandidata() throws SQLException{
    
      String naziv = txtNaziv.getText();
      int godina = Integer.parseInt(txtGodina.getText());
      String servis = txtServis.getText();
      
      
      
      
      String query = "INSERT INTO vozilo (Naziv, Godina, Servis) VALUES (?,?,?)";
      
      preparedStatement = null;
      
      try{
           
          preparedStatement=connection.prepareStatement(query);
          
          preparedStatement.setString(1,naziv);
          preparedStatement.setInt(2,godina);
          preparedStatement.setString(3,servis);
         
      }
      catch(SQLException e)
               {
                 System.out.println(e);
      }
      finally{
      
        preparedStatement.execute();
        preparedStatement.close();
      }
      
      
      ucitajbazu();
      
      MainApp.showInformationAlertBox("Novo vozilo " + naziv +" je uspješno dodan!");
    }
    
    
    static String tempNaziv;
    @FXML
    public void showOnClick()
    {
       try {
          
          VozilaModel vozilamodel = (VozilaModel) tablevozila.getSelectionModel().getSelectedItem();
          
          String query = "select * from vozila";
          preparedStatement = connection.prepareStatement(query);
          tempNaziv = vozilamodel.getNaziv();
          txtNaziv.setText(vozilamodel.getNaziv());
          txtGodina.setText(vozilamodel.getGodina());
          txtServis.setText(vozilamodel.getServis());
         
          
          
           preparedStatement.close();
           rs.close();
       
       
       }catch(SQLException e){
           
           System.out.println(e);
             
       
       }
    }
    
    
    @FXML
    
    public void izbrisiKandidata(){
    String name = null;
    String lastname = null;
    
      try {
          
          VozilaModel vozilamodel = (VozilaModel) tablevozila.getSelectionModel().getSelectedItem();
          
          String query = "delete from vozilo where Naziv =? and Godina = ? ";
       
          preparedStatement = connection.prepareStatement(query);
          
          preparedStatement.setString(1, vozilamodel.getNaziv());
          preparedStatement.setString(2, vozilamodel.getGodina());
          
          name = vozilamodel.getNaziv();
          lastname=vozilamodel.getGodina();
          preparedStatement.executeUpdate();
          
          
           preparedStatement.close();
           rs.close();
       
       
       }catch(SQLException e){
           
           System.out.println(e);
             
       
       }
    
      ucitajbazu();
      
      MainApp.showInformationAlertBox("Vozilo " + name +" je uspješno izbrisano!");
    
    
    }
    
     @FXML
    public void azurirajKandidata(){
    
        
        String query = "update vozilo set Naziv =?, Godina=?, Servis=? where Naziv = '" + tempNaziv + "'";
        
        try{
        
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,txtNaziv.getText());
            preparedStatement.setString(2,txtGodina.getText());
            preparedStatement.setString(3,txtServis.getText());
            
            preparedStatement.execute();
            preparedStatement.close();
            ucitajbazu();
      
      MainApp.showInformationAlertBox("Vozilo " + txtNaziv.getText() +" je uspješno azurirano!");
            
        }catch (SQLException e ){
        
           System.out.println(e);
         
        }
    
    
    }
    
    
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
