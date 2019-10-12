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
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import static javax.management.remote.JMXConnectorFactory.connect;

/**
 *
 * @author mario
 */
public class KandidatiController implements Initializable {
    
     @FXML
     private TableView<KandidatiModel> table;
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
          @FXML 
    private TextField filterField;
          
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
             
             table.setItems(oblist);
             //editableCols();
             ucitajbazu();
             SearchBox();
             ComboBox();
             
             
    }
    public void ucitajbazu(){
        
        String query = "select kandidat.ID, kandidat.Ime, kandidat.Prezime, kandidat.JMBG, kandidat.Instruktor_ID, instruktor.ID, instruktor.Ime, instruktor.Prezime "
                + "FROM kandidat, instruktor "
                + "WHERE  kandidat.Instruktor_ID=instruktor.ID";
        
        try{
            txtIme.clear();
            txtPrezime.clear();
            txtJmbg.clear();
      
            oblist.clear();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            
            while(rs.next())
            {
                 oblist.add(new KandidatiModel(rs.getInt("ID"), 
                         rs.getString("Ime"),
                         rs.getString ("Prezime"), 
                         rs.getString("JMBG"),
                         rs.getString ("instruktor.Ime")+ " " + rs.getString ("instruktor.Prezime")));
                 
                 table.setItems(oblist);
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
    private JFXTextField txtJmbg;
    
    
    @FXML
    public void dodajKandidata() throws SQLException{
    
      String ime = txtIme.getText();
      String prezime = txtPrezime.getText();
      int jmbg = Integer.parseInt(txtJmbg.getText());
    
     
     InstruktoriModel o = (InstruktoriModel) comboBox.getSelectionModel().getSelectedItem();
       
      String query = "INSERT INTO kandidat (Ime, Prezime, JMBG, Instruktor_ID ) VALUES (?,?,?,?)";
      
      preparedStatement = null;
      
      try{
           
          preparedStatement=connection.prepareStatement(query);
          
          preparedStatement.setString(1,ime);
          preparedStatement.setString(2,prezime);
          preparedStatement.setInt(3,jmbg);
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
      SearchBox();
      
      MainApp.showInformationAlertBox("Novi kandidat " + ime +" je uspješno dodan!");
    }
    
    static String tempIme;
    @FXML
    public void showOnClick()
    {
       try {
          
          KandidatiModel kandidatimodel = (KandidatiModel) table.getSelectionModel().getSelectedItem();
          
          String query = "select * from kandidat";
          preparedStatement = connection.prepareStatement(query);
          tempIme = kandidatimodel.getIme();
          txtIme.setText(kandidatimodel.getIme());
          txtPrezime.setText(kandidatimodel.getPrezime());
          txtJmbg.setText(kandidatimodel.getJmbg());
         InstruktoriModel o = (InstruktoriModel) comboBox.getSelectionModel().getSelectedItem();
          
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
          
          KandidatiModel kandidatimodel = (KandidatiModel) table.getSelectionModel().getSelectedItem();
          
          String query = "delete from kandidat where Ime =? and Prezime = ? ";
       
          preparedStatement = connection.prepareStatement(query);
          
          preparedStatement.setString(1, kandidatimodel.getIme());
           preparedStatement.setString(2, kandidatimodel.getPrezime());
          name = kandidatimodel.getIme();
          lastname = kandidatimodel.getPrezime();
          preparedStatement.executeUpdate();
          
          
           preparedStatement.close();
           rs.close();
       
       
       }catch(SQLException e){
           
           System.out.println(e);
             
       
       }
    
      ucitajbazu();
      SearchBox(); 
      MainApp.showInformationAlertBox("Kandidat " + name +" je uspješno izbrisan!");
    
    
    }
    
    
    @FXML
    public void azurirajKandidata(){
    
        InstruktoriModel o = (InstruktoriModel) comboBox.getSelectionModel().getSelectedItem();
        
        String query = "update kandidat set Ime =?, Prezime=?, JMBG=? ,Instruktor_ID = ? where Ime = '" + tempIme + "'";
        
        try{
        
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,txtIme.getText());
            preparedStatement.setString(2,txtPrezime.getText());
            preparedStatement.setString(3,txtJmbg.getText());
            preparedStatement.setInt(4, Integer.valueOf(o.getId()));
            preparedStatement.execute();
            preparedStatement.close();
            ucitajbazu();
            SearchBox();
      MainApp.showInformationAlertBox("Kandidat " + txtIme.getText() +" je uspješno azuriran!");
            
        }catch (SQLException e ){
        
           System.out.println(e);
         
        }
        
    } 
        
        // SearchBox
 
        public void SearchBox(){
            
            
            
               // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<KandidatiModel> filteredData = new FilteredList<>(oblist, p -> true);
        
        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(KandidatiModel -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                
                if (KandidatiModel.getIme().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (KandidatiModel.getPrezime().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        
         // 3. Wrap the FilteredList in a SortedList. 
        SortedList<KandidatiModel> sortedData = new SortedList<>(filteredData);
         // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        table.setItems(sortedData);
        
       
            
        }
        
        @FXML
       private JFXComboBox  comboBox;
        
        ObservableList<ComboBoxModel> list  =FXCollections.observableArrayList();
        
        
        public void ComboBox(){
        
        try{
           
        String query = "select  instruktor.ID, instruktor.Ime, instruktor.Prezime, instruktor.Kategorija, instruktor.Vozilo_ID, vozilo.ID, vozilo.Naziv"
                + "  FROM instruktor,vozilo "
                + "WHERE instruktor.Vozilo_ID = vozilo.ID";
            
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
           while(rs.next())
            {
                 list.add(new ComboBoxModel( 
                         rs.getString("ID"),
                         rs.getString("Ime"),
                         rs.getString ("Prezime"),
                         rs.getString("Kategorija"), 
                         rs.getString("vozilo.Naziv")
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
    public void prijaviKandidata() throws SQLException{
         
         KandidatiModel kandidat = (KandidatiModel) table.getSelectionModel().getSelectedItem();
        
        String query = "INSERT INTO prijavljeni_kandidati (Kandidat_ID ) VALUES (?)";
      
      preparedStatement = null;
      
      try{
           
          preparedStatement=connection.prepareStatement(query);
          
          
          preparedStatement.setInt(1, Integer.valueOf(kandidat.getId()));
          
         
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
      SearchBox();
      
      MainApp.showInformationAlertBox("Kandidat " + kandidat.ime +" je uspješno prijavljen!");
        
        
        
        
        
        
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
                   // app_stage.setMaximized(true);
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