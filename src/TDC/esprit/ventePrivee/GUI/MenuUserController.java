/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDC.esprit.ventePrivee.GUI;

import static TDC.esprit.ventePrivee.GUI.authAdminController.idUser;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Edzio
 */
public class MenuUserController implements Initializable {
    @FXML
    private Text nom_user_connect;
    @FXML
    private Text prnm_user_connect;
    @FXML
    private Button btn_mon_compte;
    @FXML
    private Button btn_offres;
    @FXML
    private Button btn_se_deconnect_espace;
    @FXML
    private ImageView btn_Consult_Panier;
    @FXML
    private TextField id_user_EspaceClient;
    @FXML
    private Button btn_offres_services;
    
    // variable cnx
    Connection c=DataBase.getInstance().getConnection();
    PreparedStatement pst;
    ResultSet res;
    @FXML
    private Button btn_consult_dem_serv;
   

    @FXML
    private void ConsulterCompte(ActionEvent event) throws IOException {
        
        Stage stage=null;
        Parent root=null;
        
        
        if (event.getSource()==btn_mon_compte){
             //get reference to the button's stage
            stage=(Stage) btn_mon_compte.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("CompteUserPerso.fxml"));
         //   pr.DisplayobservableArrayList();  
        }
        //create a new scene with root and set the stage// Creer un nouveau scène 
         Scene scene=new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Espace Compte Personelle");
         stage.show();
    }

    @FXML
    private void ConsulterOffres(ActionEvent event) throws IOException {
        
        Stage stage=null;
        Parent root=null;
        
        
        if (event.getSource()==btn_offres){
             //get reference to the button's stage
            stage=(Stage) btn_offres.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("OffreProduitInterface.fxml"));
         //   pr.DisplayobservableArrayList();  
        }
        //create a new scene with root and set the stage// Creer un nouveau scène 
         Scene scene=new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Espace Offres Produits");
         stage.show();
    }

    @FXML
    private void DeconnecterEspace(ActionEvent event) throws IOException {
        
        Stage stage=null;
        Parent root=null;
        Alert alert;
        alert=new Alert(Alert.AlertType.INFORMATION);
        
        if (event.getSource()==btn_se_deconnect_espace){
             //get reference to the button's stage
            stage=(Stage) btn_se_deconnect_espace.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("authAdmin.fxml"));
         //   pr.DisplayobservableArrayList();
               
        alert.setTitle("Etat Deconnexion");
        alert.setHeaderText("Deconnecter de l'Espace Utilisateur");
        alert.setContentText("Merci d'etre un client fidèle du All For Deal,Nous vous Souhaite la Bienvenu");
        alert.showAndWait();
        
            
        }
        //create a new scene with root and set the stage// Creer un nouveau scène 
         Scene scene=new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Interface Authentification All For Deal");
         stage.show();
    }

    @FXML
    private void ConsulterPanierAction(MouseEvent event) throws IOException {
        
        Stage stage=null;
        Parent root = null;
        if (event.getSource()==btn_Consult_Panier){
             //get reference to the button's stage
            stage=(Stage) btn_Consult_Panier.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("InterfacePanier.fxml"));
         //   pr.DisplayobservableArrayList();   
        }
        //create a new scene with root and set the stage// Creer un nouveau scène 
         Scene scene=new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Interface Panier Personelle All For Deal");
         stage.show();
        
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
            
        String sql="select * from utilisateur where id_user="+idUser;
        try {
            pst=c.prepareStatement(sql);
         //   pst.setInt(1,offreServ.getId_Serv());
          //  pst.setInt(2,id_user_static_get); 
            res=pst.executeQuery();
            if (res.next()){
                // champs du profile
                id_user_EspaceClient.setText(res.getString("id_user"));
                nom_user_connect.setText(res.getString("nom_user"));
                prnm_user_connect.setText(res.getString("prenom_user"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AjouterProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void versOffreServices(ActionEvent event) throws IOException {
        
        
        
        Stage stage=null;
        Parent root = null;
        if (event.getSource()==btn_offres_services){
             //get reference to the button's stage
            stage=(Stage) btn_offres_services.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("AfficherListeService.fxml"));
         //   pr.DisplayobservableArrayList();   
        }
        //create a new scene with root and set the stage// Creer un nouveau scène 
         Scene scene=new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Interface Offres Services All For Deal");
         stage.show();
        
    }

    @FXML
    private void ConsultDemServiceAction(ActionEvent event) throws IOException {
        
        Stage stage=null;
        Parent root=null;
        
        
        if (event.getSource()==btn_consult_dem_serv){
             //get reference to the button's stage
            stage=(Stage) btn_consult_dem_serv.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("InterfaceDemandes.fxml"));
         //   pr.DisplayobservableArrayList();  
        }
        //create a new scene with root and set the stage// Creer un nouveau scène 
         Scene scene=new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Espace Demandes Services");
         stage.show();
    }
    
}
