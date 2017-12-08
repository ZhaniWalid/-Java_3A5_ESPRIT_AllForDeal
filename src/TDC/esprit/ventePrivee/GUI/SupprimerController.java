/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TDC.esprit.ventePrivee.GUI;

import static TDC.esprit.ventePrivee.GUI.authAdminController.idUser;
import TDC.esprit.ventePrivee.classes.ServiceDao_1;
import TDC.esprit.ventePrivee.interfaces.IServiceDao;
import TDC.esprit.ventePrivee.entities.Service;
import TDC.esprit.ventePrivee.technique.DataBase;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Chedli
 */
public class SupprimerController implements Initializable{
    @FXML
    private Button btnSupp;
    
    @FXML
    TableView<Service> table;
    @FXML
    private Button btnRetour;
    @FXML
    private TableColumn<Service, Integer> id_col;
    @FXML
    private TableColumn<Service, String> desc_col;
    @FXML
    private TableColumn<Service, String> duree_col;
    @FXML
    private TableColumn<Service, String> lieu_col;
    @FXML
    private TableColumn<Service, String> date_col;
    @FXML
    private TableColumn<Service, String> note_col;

    
    
    private ObservableList<Service> data=FXCollections.observableArrayList();
    Service dataSelected=new Service();
    Connection c=DataBase.getInstance().getConnection();
    PreparedStatement pst;
    ResultSet res;
    final ObservableList options=FXCollections.observableArrayList();
    final ObservableList optionsNote=FXCollections.observableArrayList();
    @FXML
    private TextField textsupp;
    @FXML
    private ComboBox<String> note_val;
    @FXML
    private TextField lieu;
    @FXML
    private TextField durree;
    @FXML
    private TextArea desc;
    @FXML
    private TextField date;
    @FXML
    private Button btnmodif;
    @FXML
    private ComboBox<Integer> id_modif;
    @FXML
    private TextField labelSearchField;
    @FXML
    private Button map;
    
    @FXML
    private void Supprimer(ActionEvent event) {
          Alert alertOk,alertErr;
        alertOk=new Alert(Alert.AlertType.INFORMATION);
        alertErr=new Alert(Alert.AlertType.WARNING);
        
        Stage stage = null;
        int id=Integer.parseInt(textsupp.getText());
        Service service=new Service();
        IServiceDao pr=new ServiceDao_1();
        if (pr.removeServiceById(id)){
      //  Dialogs.showInformationDialog(stage,"Suppression a éffectué avec succés","Information Dialog", "Etat Suppression");
        alertOk.setTitle("Etat Suppression");
        alertOk.setHeaderText("Suppresion Offre Service");
        alertOk.setContentText("Suppression a été éffectué avec succés");
        alertOk.showAndWait();   
        table.refresh();
        afficherMesServices();
        }
        else if (!pr.removeServiceById(id)){
        //Dialogs.showWarningDialog(stage,"Suppression a été échoué","Information Dialog", "Etat Suppression");
        alertErr.setTitle("Etat Suppression");
        alertErr.setHeaderText("Suppression Offre Service");
        alertErr.setContentText("Suppression a été échouée");
        alertErr.showAndWait();     
        table.refresh();
        afficherMesServices();
      }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            
            data=FXCollections.observableArrayList();
            String sql="select * from service where id_user_service="+idUser;
            res=c.createStatement().executeQuery(sql);
            while (res.next()){
                data.add(new Service(res.getInt("id"),res.getString("description"),res.getString("duree"),res.getString("lieu"),res.getString("dateajout"),res.getString("noteevaluation"),res.getInt("id_user_service")));
            } 
          
            id_col.setCellValueFactory(new PropertyValueFactory<Service,Integer>("id"));
            desc_col.setCellValueFactory(new PropertyValueFactory<Service,String>("description"));
            duree_col.setCellValueFactory(new PropertyValueFactory<Service,String>("duree"));
            lieu_col.setCellValueFactory(new PropertyValueFactory<Service,String>("lieu"));
            date_col.setCellValueFactory(new PropertyValueFactory<Service,String>("date_ajout"));
            note_col.setCellValueFactory(new PropertyValueFactory<Service,String>("note_evaluation"));
    
            //TableAffiche.setItems(null);
            table.setItems(data);
            table.setVisible(true);
        } catch (SQLException ex) {
            System.out.println("Error on building Data");
            Logger.getLogger(SupprimerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //////////////////
         try {
            String req="select id from service";
            pst=c.prepareStatement(req);
            ResultSet res=pst.executeQuery();
            while (res.next()){
                options.add(res.getInt("id"));
            }
            id_modif.setItems(options);
        } catch (SQLException ex) {
            Logger.getLogger(SupprimerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        id_modif.setOnAction(e -> {
        
            String req="select * from service where id=? ";
            try {
                pst=c.prepareStatement(req);
                pst.setInt(1,id_modif.getSelectionModel().getSelectedItem());
                res=pst.executeQuery();
                
                while (res.next()){
                desc.setText(res.getString("description"));
                durree.setText(res.getString("duree"));
                lieu.setText(res.getString("lieu"));
                date.setText(res.getString("dateajout"));
                optionsNote.add(res.getString("noteevaluation"));
                note_val.getSelectionModel().getSelectedItem();
               // Text_Categorie.setItems(optionsCatgorie);
            }
            } catch (SQLException ex) {
                Logger.getLogger(SupprimerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
    });
    }

    @FXML
    private void Retour(ActionEvent event) throws IOException {
         Stage stage = null ;
        Parent root = null ;
        
        if (event.getSource()==btnRetour){
             //get reference to the button's stage
            stage=(Stage) btnRetour.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("CompteUserPerso.fxml"));
            
        }
        //create a new scene with root and set the stage// Creer un nouveau scène 
         Scene scene=new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Interface Utilisateur");
         stage.show();
    }

    @FXML
    private void modifier(ActionEvent event) {
       Alert alertOk,alertErr;
        alertOk=new Alert(Alert.AlertType.INFORMATION);
        alertErr=new Alert(Alert.AlertType.WARNING);
        
        Stage stage = null;

        String Item=String.valueOf(note_val.getSelectionModel().getSelectedItem());
        int id_mo=id_modif.getSelectionModel().getSelectedItem();
        Service service = new Service();
        
        service.setDescription(desc.getText());
        service.setDuree(durree.getText());
        service.setLieu(lieu.getText());
        service.setDate_ajout(date.getText());
        service.setNote_evaluation(Item);
        service.setId(id_mo);
        
        IServiceDao servDAO=new ServiceDao_1();
        servDAO.updateService(service);
        
        alertOk.setTitle("Etat Mise A jour");
        alertOk.setHeaderText("Modifier Offre Service");
        alertOk.setContentText("Mise à jour a été éffectué avec succés");
        alertOk.showAndWait();
        
        ViderChamps();
        table.refresh();
        afficherMesServices();
        
    }

    public void ViderChamps(){
        
        desc.clear();
        durree.clear();
        lieu.clear();
        date.clear();
        note_val.setValue(null);
    }
    
    public void afficherMesServices(){
         try {
            
            data=FXCollections.observableArrayList();
            String sql="select * from service where id_user_service="+idUser;
            res=c.createStatement().executeQuery(sql);
            while (res.next()){
                data.add(new Service(res.getInt("id"),res.getString("description"),res.getString("duree"),res.getString("lieu"),res.getString("dateajout"),res.getString("noteevaluation"),res.getInt("id_user_service")));
            } 
          
            id_col.setCellValueFactory(new PropertyValueFactory<Service,Integer>("id"));
            desc_col.setCellValueFactory(new PropertyValueFactory<Service,String>("description"));
            duree_col.setCellValueFactory(new PropertyValueFactory<Service,String>("duree"));
            lieu_col.setCellValueFactory(new PropertyValueFactory<Service,String>("lieu"));
            date_col.setCellValueFactory(new PropertyValueFactory<Service,String>("date_ajout"));
            note_col.setCellValueFactory(new PropertyValueFactory<Service,String>("note_evaluation"));
    
            //TableAffiche.setItems(null);
            table.setItems(data);
            table.setVisible(true);
        } catch (SQLException ex) {
            System.out.println("Error on building Data");
            Logger.getLogger(SupprimerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Recherche Filtrée
    @FXML
    public void FiltredSearch(){
            // Filtered List
        FilteredList<Service> filteredServiceListData = new FilteredList<>(data,e->true);
        
        labelSearchField.setOnKeyReleased(e -> {
                labelSearchField.textProperty().addListener((observableValue,oldValue,newValue) -> {
                filteredServiceListData.setPredicate((Predicate<? super Service>) service -> {
                if (newValue==null || newValue.isEmpty()){
                return true;
                }
                String toLowerCaseNewValue = newValue.toLowerCase();
                
              
                if (service.getLieu().toLowerCase().contains(toLowerCaseNewValue)){
                    return true;
                }else if(service.getDuree().toLowerCase().contains(toLowerCaseNewValue)){
                    return true;
                }
                return false;
                   });
              });
        });
        
         //Sorted List
              SortedList<Service>  sortedServiceListData = new SortedList<>(filteredServiceListData);
              
              sortedServiceListData.comparatorProperty().bind(table.comparatorProperty());
              table.setItems(sortedServiceListData);
    }

    @FXML
    private void afficher_map(ActionEvent event) {
          Googlemap g=new Googlemap();
        g.start(new Stage());
    }
    
}

    
