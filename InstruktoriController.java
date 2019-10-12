/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.mavenproject3;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import static org.openjfx.mavenproject3.KandidatiController.tempIme;

/**
 *
 * @author mario
 */
public class InstruktoriController implements Initializable {
    
     @FXML
     private TableView<InstruktoriModel> tableinstr;
     @FXML
     private TableColumn<InstruktoriModel,String> col_id ;
      @FXML
     private TableColumn<InstruktoriModel,String> col_ime;
       @FXML
     private TableColumn<InstruktoriModel,String> col_prezime ;
      @FXML
     private TableColumn<InstruktoriModel,String> col_kategorija ;
        @FXML
     private TableColumn<InstruktoriModel,String> col_vozilo ;

          
          Connection connection=Connect.conDB();
     PreparedStatement preparedStatement = null;
     ResultSet rs = null;
     ObservableList<InstruktoriModel> oblist =FXCollections.observableArrayList();
          
          
          
          @Override
            public void initialize(URL location, ResourceBundle resource) {
       
        
             col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
             col_ime.setCellValueFactory(new PropertyValueFactory<>("ime"));
             col_prezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));
             col_kategorija.setCellValueFactory(new PropertyValueFactory<>("kategorija"));
             col_vozilo.setCellValueFactory(new PropertyValueFactory<>("vozilo_naziv"));
             
             tableinstr.setItems(oblist);
             //editableCols();
             ucitajbazu();
             ComboBox();
             
    }
            
            
            public void ucitajbazu(){
        
        String query = "select  instruktor.ID, instruktor.Ime, instruktor.Prezime, instruktor.Kategorija, instruktor.Vozilo_ID, vozilo.ID, vozilo.Naziv"
                + "  FROM instruktor,vozilo "
                + "WHERE instruktor.Vozilo_ID = vozilo.ID";
        
        try{
            txtIme.clear();
           txtPrezime.clear();
            
            txtKategorija.clear();
      
            oblist.clear();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            
            while(rs.next())
            {
                 oblist.add(new InstruktoriModel(rs.getString("ID"), 
                         rs.getString("Ime"),
                         rs.getString ("Prezime"), 
                         rs.getString("Kategorija"),
                         rs.getString("vozilo.Naziv")));
                 
                 tableinstr.setItems(oblist);
             }
            
            preparedStatement.close();
            rs.close();
        }
        catch(Exception e){
             System.err.println(e);
        }
    }
            
            
    @FXML 
    private JFXTextField txtIme;
    @FXML 
    private JFXTextField txtPrezime;
   
    @FXML
    private JFXTextField txtKategorija;
    
    @FXML
    public void dodajKandidata() throws SQLException{
    
      String ime = txtIme.getText();
      String prezime = txtPrezime.getText();
      VozilaModel o = (VozilaModel) comboBox.getSelectionModel().getSelectedItem();
      String kategorija = txtKategorija.getText();
      
      
      
      String query = "INSERT INTO instruktor (Ime, Prezime, Kategorija, Vozilo_ID) VALUES (?,?,?,?)";
      
      preparedStatement = null;
      
      try{
           
          preparedStatement=connection.prepareStatement(query);
          
          preparedStatement.setString(1,ime);
          preparedStatement.setString(2,prezime);
          preparedStatement.setString(3,kategorija);
        
         preparedStatement.setInt(4, Integer.valueOf(o.getId()));
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
      
      MainApp.showInformationAlertBox("Novi instruktor " + ime +" je uspješno dodan!");
    }
    
    
    static String tempIme;
    @FXML
    public void showOnClick()
    {
       try {
          
          InstruktoriModel instruktorimodel = (InstruktoriModel) tableinstr.getSelectionModel().getSelectedItem();
          
          String query = "select * from instruktor";
          preparedStatement = connection.prepareStatement(query);
          tempIme = instruktorimodel.getIme();
          txtIme.setText(instruktorimodel.getIme());
          txtPrezime.setText(instruktorimodel.getPrezime());
          
          txtKategorija.setText(instruktorimodel.getKategorija());
          
          
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
          
          InstruktoriModel instruktorimodel = (InstruktoriModel) tableinstr.getSelectionModel().getSelectedItem();;
          
          String query = "delete from instruktor where Ime =? and Prezime = ? ";
       
          preparedStatement = connection.prepareStatement(query);
          
          preparedStatement.setString(1, instruktorimodel.getIme());
          preparedStatement.setString(2, instruktorimodel.getPrezime());
          
          name = instruktorimodel.getIme();
          lastname=instruktorimodel.getPrezime();
          preparedStatement.executeUpdate();
          
          
           preparedStatement.close();
           rs.close();
       
       
       }catch(SQLException e){
           
           System.out.println(e);
             
       
       }
    
      ucitajbazu();
      
      MainApp.showInformationAlertBox("Instruktor " + name +" je uspješno izbrisan!");
    
    
    }
    
     @FXML
    public void azurirajKandidata(){
    
        VozilaModel o = (VozilaModel) comboBox.getSelectionModel().getSelectedItem();
        
        String query = "update instruktor set Ime =?, Prezime=?, Kategorija=?, Vozilo_ID =?  where Ime = '" + tempIme + "'";
        
        try{
        
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,txtIme.getText());
            preparedStatement.setString(2,txtPrezime.getText());
            preparedStatement.setString(3,txtKategorija.getText());
            preparedStatement.setInt(4, Integer.valueOf(o.getId()));
            preparedStatement.execute();
            preparedStatement.close();
            ucitajbazu();
      
      MainApp.showInformationAlertBox("Instruktor " + txtIme.getText() +" je uspješno azuriran!");
            
        }catch (SQLException e ){
        
           System.out.println(e);
         
        }
    
    
    }
    
    @FXML
       private JFXComboBox  comboBox;
        
        ObservableList<ComboBoxModelInstruktori> list  =FXCollections.observableArrayList();
        
        
        public void ComboBox(){
        
        try{
           
        String query = "select * from vozilo";
            
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
           while(rs.next())
            {
                 list.add(new ComboBoxModelInstruktori( 
                         rs.getString("ID"),
                         rs.getString("Naziv"),
                         rs.getString ("Godina"),
                         rs.getString("Servis") 
                         
                 ));
                 
                 comboBox.setItems(list);
                 
             }
            
            preparedStatement.close();
            rs.close();
        }
        catch(Exception e){
             System.err.println(e);
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
