/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDC.esprit.ventePrivee.GUI;

import static TDC.esprit.ventePrivee.GUI.authAdminController.idUser;
import TDC.esprit.ventePrivee.classes.PanierDAO;
import TDC.esprit.ventePrivee.dao.interfaces.IPanierDAO;
import TDC.esprit.ventePrivee.entities.Panier;
import TDC.esprit.ventePrivee.entities.Produit;
import TDC.esprit.ventePrivee.technique.DataBase;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author majd ben khalifa
 */
public class interfacePanierController implements Initializable {
   
    private ObservableList<Panier> data=FXCollections.observableArrayList();
    
    
    Panier dataSelected=new Panier();
    Connection c=DataBase.getInstance().getConnection();
    PreparedStatement pst;
    ResultSet res;
    
    
    
    
    @FXML
    private TableView<Panier> table_panier;
    @FXML
    private TableColumn<Panier,String> libelle_prd_pnr;
    @FXML
    private TableColumn<Panier,Integer> prix_prd_pnr;
    @FXML
    private Button retourner_id;
    @FXML
    private Button vider_id;
    @FXML
    private Button supprimer_id;
    @FXML
    private TextField id_produit_invisible;
    @FXML
    private TableColumn<Panier,Integer> idProduit;
    @FXML
    private TextField id_user_invisible;

    @FXML
    private void RetournerPrecedant(ActionEvent event) throws IOException {
        
        Stage stage = null ;
        Parent root = null ;
        Alert alert;
        alert=new Alert(Alert.AlertType.INFORMATION);
        
        if (event.getSource()==retourner_id){
             //get reference to the button's stage
            stage=(Stage) retourner_id.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("MenuUser.fxml"));
        alert.setTitle("Etat Retour");
        alert.setHeaderText("Retour à l'Espace Utilisateur");
        alert.setContentText("Merci d'etre un client fidèle du All For Deal,Nous vous Souhaite la Bienvenu");
        alert.showAndWait();
            
        }
        //create a new scene with root and set the stage// Creer un nouveau scène 
         Scene scene=new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Interface Personnelle");
         stage.show();
    }
    
    

    @FXML
    private void ViderPanier(ActionEvent event) {
        
        Alert alertOk,alertErr;
        alertOk=new Alert(Alert.AlertType.INFORMATION);
        alertErr=new Alert(Alert.AlertType.WARNING);
        
        Stage stage = null;
        Produit produit=new Produit();
        
        IPanierDAO pr=new PanierDAO();
        if (pr.removeAll()){
      //  Dialogs.showInformationDialog(stage,"Suppression a éffectué avec succés","Information Dialog", "Etat Suppression");
        alertOk.setTitle("Etat Suppression");
        alertOk.setHeaderText("Suppresion Offre Produit");
        alertOk.setContentText("Suppression du produit a été éffectué avec succés");
        alertOk.showAndWait();    
        
       
        table_panier.refresh();
     
        ShowObservableArrayFxListPanier();
       
        }
        else if (!pr.removeAll()){
        //Dialogs.showWarningDialog(stage,"Suppression a été échoué","Information Dialog", "Etat Suppression");
        alertErr.setTitle("Etat Suppression");
        alertErr.setHeaderText("Suppression Offre Produit");
        alertErr.setContentText("Suppression a été échouée");
        alertErr.showAndWait();
       
        
        table_panier.refresh();
     
        ShowObservableArrayFxListPanier();
       
        }
        
        
    }

    @FXML
    private void SupprimerProduit(ActionEvent event) {
        
        Alert alertOk,alertErr;
        alertOk=new Alert(Alert.AlertType.INFORMATION);
        alertErr=new Alert(Alert.AlertType.WARNING);
        
        Stage stage = null;
        Produit produit=new Produit();
        int id=Integer.parseInt(id_produit_invisible.getText());
        IPanierDAO pr=new PanierDAO();
        if (pr.removePanierById(id)){
      //  Dialogs.showInformationDialog(stage,"Suppression a éffectué avec succés","Information Dialog", "Etat Suppression");
        alertOk.setTitle("Etat Suppression");
        alertOk.setHeaderText("Suppresion Offre Produit");
        alertOk.setContentText("Suppression du produit ,Numéro :"+id+" ,a été éffectué avec succés");
        alertOk.showAndWait();    
        
       
        table_panier.refresh();
     
        ShowObservableArrayFxListPanier();
       
        }
        else if (!pr.removePanierById(id)){
        //Dialogs.showWarningDialog(stage,"Suppression a été échoué","Information Dialog", "Etat Suppression");
        alertErr.setTitle("Etat Suppression");
        alertErr.setHeaderText("Suppression Offre Produit");
        alertErr.setContentText("Suppression a été échouée");
        alertErr.showAndWait();
       
        
        table_panier.refresh();
     
        ShowObservableArrayFxListPanier();
       
        }
        
       
        
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
        ShowObservableArrayFxListPanier();
        table_panier.setOnMouseClicked(e -> {
        
           String req="select prix,libelle,id_produit from panier where id_produit=?";
            try {
                Panier panier=(Panier)table_panier.getSelectionModel().getSelectedItem();
                pst=c.prepareStatement(req);
                pst.setInt(1,panier.getId_prd_pnr());
                res=pst.executeQuery();
                
                while (res.next()){ 
                
               // remplissage du textField invisible qui stocke l'id du produit désiré à supprimé
                id_produit_invisible.setText(res.getString("id_produit"));
                
              
         }
            } catch (SQLException ex) {
                Logger.getLogger(interfacePanierController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        

    });
    
}
    public void ShowObservableArrayFxListPanier(){
        
        try {
            
            data=FXCollections.observableArrayList();
            String sql="select * from panier where id_user="+idUser;
            res=c.createStatement().executeQuery(sql);
            while (res.next()){
                // lahné f res.getX("Attribut eli mwjoud fl base de données selon type eli 7atetou enty")
                
                data.add(new Panier(res.getInt("id_produit"),res.getString("libelle"),res.getInt("prix")));
            } 
            // lahné (" ") attribut eli mwjoud fl classe mte3k
            // Id_Col : hia fx:id ta3 colonne eli mssaméha fl sceneBuilder
            // PropertyValueFactory<EsmCLASSE,TypeAttribut fl classe>
            // w tanséch déclarehm l fou9 ba3d m t3ml "make controller" fl fichier.fxml
            //exmple :  @FXML private TableColumn<Produit,String> Categ_Col;
            // w aussi ta3ti type ta3 tableView l fou9 
            // exmple : @FXML private TableView<EsmClasseMte3k> TableAffiche;
            
            idProduit.setCellValueFactory(new PropertyValueFactory<Panier,Integer>("id_prd_pnr"));
            //prix_Col.setCellValueFactory(new PropertyValueFactory<Produit,Double>("prix_prd"));
           libelle_prd_pnr.setCellValueFactory(new PropertyValueFactory<Panier,String>("libelle_prd_pnr"));
            //Desc_Col.setCellValueFactory(new PropertyValueFactory<Produit,String>("despcription_prd"));
            prix_prd_pnr.setCellValueFactory(new PropertyValueFactory<Panier,Integer>("prix_prd_pnr"));
           ;
            
            table_panier.setItems(null);
            table_panier.layout();
           table_panier.setItems(data);
           table_panier.setVisible(true);
        } catch (SQLException ex) {
            System.out.println("Error on building Data");
            Logger.getLogger(OffreProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}