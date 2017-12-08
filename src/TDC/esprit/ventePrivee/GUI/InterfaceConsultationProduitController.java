/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDC.esprit.ventePrivee.GUI;

import static TDC.esprit.ventePrivee.GUI.OffreProduitController.idOffreStatic;
import static TDC.esprit.ventePrivee.GUI.OffreProduitController.idUserStatic;
import static TDC.esprit.ventePrivee.GUI.authAdminController.idUser;
import TDC.esprit.ventePrivee.classes.ProduitDAO;
import TDC.esprit.ventePrivee.interfaces.IProduitDAO;
import TDC.esprit.ventePrivee.entities.Commentaire;
import TDC.esprit.ventePrivee.entities.Panier;
import TDC.esprit.ventePrivee.entities.Produit;
import TDC.esprit.ventePrivee.technique.DataBase;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author majd ben khalifa
 */
public class InterfaceConsultationProduitController implements Initializable{
    @FXML
    private Button commenter_id;
    @FXML
    private Button ajouter_id;
    @FXML
    private TextField libelle_id;
    @FXML
    private TextArea description_id;
    @FXML
    private TextField pri_id;
    @FXML
    private TextField categorie_id;
    @FXML
    private TextField date_ajout_id;
    @FXML
    private TextField image_id;
    public static int id_static;
    
    Connection c=DataBase.getInstance().getConnection();
    PreparedStatement pst;
    ResultSet res;
    PreparedStatement pst2;
    ResultSet res2;
   // int idInv=Produit.id_produit_static;
    @FXML
    private TextField idRecup;
    @FXML
    private TableColumn<Commentaire,String> commentaire_id;
    @FXML
    private TextField ajout_com_id;
    @FXML
    private Button retour_id;
    private ObservableList<Commentaire> data=FXCollections.observableArrayList();
    
    @FXML
    private TableView<Commentaire> table_com_id;
    @FXML
    private TextField nom_id;
    @FXML
    private TextField prenom_id;
    @FXML
    private TextField email_id;
    @FXML
    private TextField telephone_id;
    @FXML
    private TextField idRecupUser;
    
    @FXML
    private void AjouterPanier(ActionEvent event) {
        
        Alert alertOk,alertErr;
        alertOk=new Alert(Alert.AlertType.INFORMATION);
        alertErr=new Alert(Alert.AlertType.ERROR);
         int id_pars=Integer.parseInt(idRecup.getText());
         int pri_pars=Integer.parseInt(pri_id.getText());
         
         
         Panier panier=new Panier();
         /* idOffreStatic;
         idUserStatic;*/
         panier.setPrix_prd_pnr(pri_pars);
         panier.setLibelle_prd_pnr(libelle_id.getText());
         panier.setId_prd_pnr(id_pars);
         panier.setId_user_pnr(idUser);
         
         IProduitDAO iproduit=new ProduitDAO();
         
         if(iproduit.addProduitPanier(panier)) {
             
             
        alertOk.setTitle(null);
        alertOk.setHeaderText(null);
        alertOk.setContentText(null);
        alertOk.showAndWait();
         }else {
             alertErr.setTitle(null);
        alertErr.setHeaderText(null);
        alertErr.setContentText(null);
        alertErr.showAndWait();
         }
        
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
        String req="select * from produit p,utilisateur u where p.id_produit=? and u.id_user=?";
        try {
            
            
            Produit produit=new Produit();
            pst=c.prepareStatement(req);
            pst.setInt(1,idOffreStatic);
            pst.setInt(2,idUserStatic);
            //pst.setInt(1, produit.getId_Produit());
            res=pst.executeQuery();
            if(res.next()){
                
                idRecup.setText(res.getString("id_produit"));
               libelle_id.setText(res.getString("libelle_prd"));
                description_id.setText(res.getString("description_prd"));
                pri_id.setText(res.getString("prix_prd"));
                categorie_id.setText(res.getString("categorie_prd"));
                date_ajout_id.setText(res.getString("dateAjout_prd"));
                
                idRecupUser.setText(res.getString("id_user"));
                nom_id.setText(res.getString("nom_user"));
                prenom_id.setText(res.getString("prenom_user"));
                email_id.setText(res.getString("email_user"));
                telephone_id.setText(res.getString("tel_user"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(InterfaceConsultationProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       ShowObservableArrayFxListCommentaire();
       
    }
    
    public void ShowObservableArrayFxListCommentaire(){
        
        try {
            
            data=FXCollections.observableArrayList();
            String sql="select * from commentaire where id_produit="+idOffreStatic;
            res=c.createStatement().executeQuery(sql);
            while (res.next()){
                idRecup.setText(res.getString("id_produit"));
                // lahné f res.getX("Attribut eli mwjoud fl base de données selon type eli 7atetou enty")
                data.add(new Commentaire(res.getString("commentaire")));
            } 
            // lahné (" ") attribut eli mwjoud fl classe mte3k
            // Id_Col : hia fx:id ta3 colonne eli mssaméha fl sceneBuilder
            // PropertyValueFactory<EsmCLASSE,TypeAttribut fl classe>
            // w tanséch déclarehm l fou9 ba3d m t3ml "make controller" fl fichier.fxml
            //exmple :  @FXML private TableColumn<Produit,String> Categ_Col;
            // w aussi ta3ti type ta3 tableView l fou9 
            // exmple : @FXML private TableView<EsmClasseMte3k> TableAffiche;
            
            commentaire_id.setCellValueFactory(new PropertyValueFactory<Commentaire,String>("libelle_prd_com"));
           
            
            table_com_id.setItems(null);
            table_com_id.layout();
            table_com_id.setItems(data);
            table_com_id.setVisible(true);
        } catch (SQLException ex) {
            System.out.println("Error on building Data");
            Logger.getLogger(OffreProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void commenter(ActionEvent event) {
        
        Alert alertOk,alertErr;
        alertOk=new Alert(Alert.AlertType.INFORMATION);
        alertErr=new Alert(Alert.AlertType.ERROR);
        
        Commentaire com=new Commentaire();
        com.setId_prd_com(idOffreStatic);
        com.setLibelle_prd_com(ajout_com_id.getText());
        ProduitDAO prd= new ProduitDAO();
        
        
        if (prd.CommenterProduit(com)) {
            
        ajout_com_id.clear();
        alertOk.setTitle(null);
        alertOk.setHeaderText(null);
        alertOk.setContentText(null);
        alertOk.showAndWait();
        
        table_com_id.refresh();
        ShowObservableArrayFxListCommentaire();
        
         }else {
             alertErr.setTitle(null);
        alertErr.setHeaderText(null);
        alertErr.setContentText(null);
        alertErr.showAndWait();
         }
            
            
        }
        
        
    

    @FXML
    private void retourner_precedant(ActionEvent event) throws IOException {
        
        Stage stage = null ;
        Parent root = null ;
        
        if (event.getSource()==retour_id){
             //get reference to the button's stage
            stage=(Stage) retour_id.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("OffreProduitInterface.fxml"));
            
        }
        //create a new scene with root and set the stage// Creer un nouveau scène 
         Scene scene=new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Interface Utilisateur");
         stage.show();
    }
    
}
