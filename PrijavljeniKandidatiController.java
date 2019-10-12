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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author mario
 */
public class PrijavljeniKandidatiController implements Initializable {
    
     @FXML
     private TableView<KandidatiModel> tableprijavljeni;
     @FXML
     private TableColumn<KandidatiModel,Integer> col_id ;
      @FXML
     private TableColumn<KandidatiModel,String> col_ime;
       @FXML
     private TableColumn<KandidatiModel,String> col_prezime ;
         @FXML
     private TableColumn<KandidatiModel,String> col_jmbg ;
          @FXML
     private TableColumn<KandidatiModel,String> col_instruktor ;
    
          
          
              
     Connection connection=Connect.conDB();
     PreparedStatement preparedStatement = null;
     ResultSet rs = null;
     ObservableList<KandidatiModel> oblist =FXCollections.observableArrayList();
     
     @Override
    public void initialize(URL location, ResourceBundle resource) {
       
        
         col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
             col_ime.setCellValueFactory(new PropertyValueFactory<>("ime"));
             col_prezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));
             col_jmbg.setCellValueFactory(new PropertyValueFactory<>("jmbg"));
             col_instruktor.setCellValueFactory(new PropertyValueFactory<>("instruktor_ime"));
             
             tableprijavljeni.setItems(oblist);
             ucitajbazu();
             
     
}
    
    
    public void ucitajbazu(){
        
        String query = "select  prijavljeni_kandidati.ID, kandidat.Ime, kandidat.Prezime, kandidat.JMBG,instruktor.Ime,instruktor.Prezime "
                + "FROM kandidat, prijavljeni_kandidati, instruktor  "
                + "WHERE   kandidat.ID = prijavljeni_kandidati.Kandidat_ID  AND  kandidat.Instruktor_ID=instruktor.ID  "
                ;
        
        try{
            
            txtIme.clear();
            txtPrezime.clear();
            txtJmbg.clear();
      
            oblist.clear();
      
            
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            
            while(rs.next())
            {
                 oblist.add(new KandidatiModel( rs.getInt("ID"), 
                         rs.getString("Ime"),
                         rs.getString ("Prezime"), 
                         rs.getString("JMBG"),
                         rs.getString ("instruktor.Ime")+ " " + rs.getString ("instruktor.Prezime")));
                 
                 tableprijavljeni.setItems(oblist);
             }
            
            preparedStatement.close();
            rs.close();
        }
        catch(Exception e){
             System.err.println(e);
        }
    }
    
     @FXML 
    private TextField txtIme;
    @FXML 
    private TextField txtPrezime;
    @FXML 
    private TextField txtJmbg;
    
    static String tempIme;
    @FXML
    public void showOnClick1()
    {
       try {
          
          KandidatiModel kandidatimodel = (KandidatiModel) tableprijavljeni.getSelectionModel().getSelectedItem();
          
          String query = "select  prijavljeni_kandidati.ID, kandidat.Ime, kandidat.Prezime, kandidat.JMBG,instruktor.Ime,instruktor.Prezime "
                + "FROM kandidat, prijavljeni_kandidati, instruktor  "
                + "WHERE   kandidat.ID = prijavljeni_kandidati.Kandidat_ID  AND  kandidat.Instruktor_ID=instruktor.ID  "
                ;
          preparedStatement = connection.prepareStatement(query);
          tempIme = kandidatimodel.getIme();
          txtIme.setText(kandidatimodel.getIme());
          txtPrezime.setText(kandidatimodel.getPrezime());
          txtJmbg.setText(kandidatimodel.getJmbg());
          
          
           preparedStatement.close();
           rs.close();
       
       
       }catch(SQLException e){
           
           System.out.println(e);
             
       
       }
    
    
    
    }
    
    
    
    @FXML
    
    public void izbrisiKandidata(){
     
    
      try {
          
          KandidatiModel kandidatimodel = (KandidatiModel) tableprijavljeni.getSelectionModel().getSelectedItem();
          
          String query = "delete from prijavljeni_kandidati where  ID = ? ";
       
          preparedStatement = connection.prepareStatement(query);
          
          preparedStatement.setInt(1, kandidatimodel.getId());
          
          preparedStatement.executeUpdate();
          
          
           preparedStatement.close();
           rs.close();
       
       
       }catch(SQLException e){
           
           System.out.println(e);
             
       
       }
    
      ucitajbazu();
       
      MainApp.showInformationAlertBox("Kandidat "  +" je uspješno izbrisan!");
    
    
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
