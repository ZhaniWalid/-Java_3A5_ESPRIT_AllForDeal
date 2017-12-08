/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDC.esprit.ventePrivee.GUI;

import TDC.esprit.ventePrivee.entities.OffreService;
import TDC.esprit.ventePrivee.technique.DataBase;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class AfficherListeServiceController implements Initializable {
    
    @FXML
    private TableView<OffreService> table_service;
    @FXML
    private TableColumn<OffreService,Integer> col_idService;
    @FXML
    private TableColumn<OffreService,String> col_Description;
    @FXML
    private TableColumn<OffreService,String> col_DateService;
    @FXML
    private TableColumn<OffreService,String> col_Categorie_Service;
    
    Connection c=DataBase.getInstance().getConnection();
    PreparedStatement pst;
    ResultSet res;
    private ObservableList<OffreService> data;
    @FXML
    private TextField col_Recherche;
    @FXML
    private Button btnConsulter;
    @FXML
    private TextField id_user_invisble;
    @FXML
    private Button btnRetour;
    @FXML
    private TextField id_serv_invisible;
    
    public static int idServiceInv;
    public static int idUserInv;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        

        ShowListService();
        FiltredSearch();
        
        // changement de validité du Service lorsqu'on clique sur le bouton "valider Service"
        table_service.setOnMouseClicked(e -> {
               
          // String requete="select * from offre_service where id_serv_prop=? ";
     //     String requete="select S.id_serv_prop,S.desc_serv_prop,S.date_serv_prop,S.categorie_serv_prop,S.valid_serv,U.id_user,U.nom_user,U.prenom_user,U.email_user,U.tel_user from offre_service S INNER JOIN utilisateur U on S.id_user_rel=U.id_user where id_serv_prop=? and id_user=?";
          String requete="select S.id_serv_prop,S.desc_serv_prop,S.date_serv_prop,S.categorie_serv_prop,U.id_user,U.nom_user,U.prenom_user,U.adresse_user,U.email_user,U.tel_user from offre_service S INNER JOIN utilisateur U on S.id_user_rel=U.id_user where id_serv_prop=? and id_user=?";
         
            OffreService offServ=(OffreService)table_service.getSelectionModel().getSelectedItem();
             
            try {
                pst=c.prepareStatement(requete);
                pst.setInt(1,offServ.getId_Serv());
                pst.setInt(2,offServ.getIdUser());
                res=pst.executeQuery();
                
                  while (res.next()){ 
                 // remplissage des textField aprés selection dans le tableaux
                      
                       id_serv_invisible.setText(res.getString("id_serv_prop"));
                       idServiceInv=Integer.parseInt(res.getString("id_serv_prop"));
                       
                       id_user_invisble.setText(res.getString("id_user"));
                       idUserInv=Integer.parseInt(res.getString("id_user"));
//                      id_user_static_get=Integer.parseInt(id_propr_Non_Visible.toString());
                     }
           } catch (SQLException ex) {
               Logger.getLogger(AfficherListeServiceController.class.getName()).log(Level.SEVERE, null, ex);
           }
    });
}
    
    public void ShowListService(){
             
        
          /// Affichage des données dans un observableArrayList()
        
        try {
             
            data=FXCollections.observableArrayList();
            String sql="select * from offre_service where valid_serv=1";
            res=c.createStatement().executeQuery(sql);
            while (res.next()){
                // lahné f res.getX("Attribut eli mwjoud fl base de données selon type eli 7atetou enty")
                data.add(new OffreService(res.getInt("id_serv_prop"),res.getInt("id_user_rel"),res.getString("desc_serv_prop"),res.getString("date_serv_prop"),res.getString("categorie_serv_prop")));
            }  
            // lahné (" ") attribut eli mwjoud fl classe mte3k
            // Id_Col : hia fx:id ta3 colonne eli mssaméha fl sceneBuilder
            // PropertyValueFactory<EsmCLASSE,TypeAttribut fl classe>
            // w tanséch déclarehm l fou9 ba3d m t3ml "make controller" fl fichier.fxml
            //exmple :  @FXML private TableColumn<Produit,String> Categ_Col;
            // w aussi ta3ti type ta3 tableView l fou9 
            // exmple : @FXML private TableView<EsmClasseMte3k> TableAffiche;
            
            col_idService.setCellValueFactory(new PropertyValueFactory<OffreService,Integer>("id_Serv"));
            col_Description.setCellValueFactory(new PropertyValueFactory<OffreService,String>("desc_Serv"));
            col_DateService.setCellValueFactory(new PropertyValueFactory<OffreService,String>("date_Serv"));
            col_Categorie_Service.setCellValueFactory(new PropertyValueFactory<OffreService,String>("catgorie_Serv"));
       
            //table_service.setItems(null);
            
            table_service.setItems(data);
            table_service.layout();
            table_service.setVisible(true);
        } catch (SQLException ex) {
            System.out.println("Error on building Data");
            Logger.getLogger(AfficherListeServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }      
        
        // lors de la selection d'une ligne dans le tableaux
        
        table_service.setOnMouseClicked(e -> {
        
        
        
        });
    }

    @FXML
    private void ConsulterService(ActionEvent event) throws IOException {
        
        Parent root=null;
        Stage stage=null;
        
        if (event.getSource()==btnConsulter){          
            stage=(Stage)btnConsulter.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("ConsultationService.fxml"));
        }
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void seDeconnecter(ActionEvent event) throws IOException {
        
        Parent root=null;
        Stage stage=null;
        
        if (event.getSource()==btnRetour){          
            stage=(Stage)btnRetour.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("MenuUser.fxml"));
        }
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();    }
    
    // Recherche Filtrée
    public void FiltredSearch(){
            // Filtered List
        FilteredList<OffreService> filteredServiceListData = new FilteredList<>(data,e->true);
        
        col_Recherche.setOnKeyReleased(e -> {
                col_Recherche.textProperty().addListener((observableValue,oldValue,newValue) -> {
                filteredServiceListData.setPredicate((Predicate<? super OffreService>) os -> {
                if (newValue==null || newValue.isEmpty()){
                return true;
                }
                String toLowerCaseNewValue = newValue.toLowerCase();
                
                /*  if (produit.getId_Produit()==newValue){
                return true;
                }*/ 
                if (os.getCatgorie_Serv().toLowerCase().contains(toLowerCaseNewValue)){
                    return true;
                }
                return false;
                   });
              });
        });
        
         //Sorted List
              SortedList<OffreService>  sortedServiceListData = new SortedList<>(filteredServiceListData);
              
              sortedServiceListData.comparatorProperty().bind(table_service.comparatorProperty());
              table_service.setItems(sortedServiceListData);
    }
}
