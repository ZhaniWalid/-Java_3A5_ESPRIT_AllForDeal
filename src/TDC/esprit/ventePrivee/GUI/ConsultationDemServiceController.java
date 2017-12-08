/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDC.esprit.ventePrivee.GUI;

import static TDC.esprit.ventePrivee.GUI.DemandesPublicController.idDemServPublicStatic;
import static TDC.esprit.ventePrivee.GUI.DemandesPublicController.idUserDemServPublicStatic;
import TDC.esprit.ventePrivee.entities.demandeService;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Edzio
 */
public class ConsultationDemServiceController implements Initializable {
    @FXML
    private TextField objet_serv;
    @FXML
    private TextArea description_serv;
    @FXML
    private TextField categorie_serv;
    @FXML
    private TextField lieu_serv;
    @FXML
    private TextField date_serv;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField mail;
    @FXML
    private TextField tel;
    @FXML
    private Button btn_retour_demServ;
    @FXML
    private TextField recuDonneServ;
    @FXML
    private TextField id_recup_prop_dem_serv;
    
    Connection c=DataBase.getInstance().getConnection();
    PreparedStatement pst;
    ResultSet res;

    @FXML
    private void RetourDemService(ActionEvent event) throws IOException {
        
        
                
        Stage stage=null;
        Parent root=null;
        
        
        if (event.getSource()==btn_retour_demServ){
             //get reference to the button's stage
            stage=(Stage) btn_retour_demServ.getScene().getWindow();
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         String req="select * from demande_service d,utilisateur u where d.id=? and u.id_user=?";
        try {
            
            
            demandeService demServ=new demandeService();
            pst=c.prepareStatement(req);
            pst.setInt(1,idDemServPublicStatic);
            pst.setInt(2,idUserDemServPublicStatic);
            //pst.setInt(1, produit.getId_Produit());
            res=pst.executeQuery();
            if(res.next()){
                
                recuDonneServ.setText(String.valueOf(res.getInt("id")));
                objet_serv.setText(res.getString("objet"));
                description_serv.setText(res.getString("description"));
                categorie_serv.setText(res.getString("cathegorie"));
                lieu_serv.setText(res.getString("lieu"));
                date_serv.setText(res.getString("date"));
                
                id_recup_prop_dem_serv.setText(String.valueOf(res.getInt("id_user")));
                nom.setText(res.getString("nom_user"));
                prenom.setText(res.getString("prenom_user"));
                mail.setText(res.getString("email_user"));
                tel.setText(res.getString("tel_user"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultationDemServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }

     
    }
    
}
