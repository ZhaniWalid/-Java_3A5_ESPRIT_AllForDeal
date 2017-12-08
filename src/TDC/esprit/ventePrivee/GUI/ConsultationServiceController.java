/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDC.esprit.ventePrivee.GUI;

import static TDC.esprit.ventePrivee.GUI.AfficherListeServiceController.idServiceInv;
import static TDC.esprit.ventePrivee.GUI.AfficherListeServiceController.idUserInv;
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
 * @author user
 */
public class ConsultationServiceController implements Initializable {
    @FXML
    private TextField id_user_recuper;
    @FXML
    private TextField nom_user;
    @FXML
    private TextField prnm_user;
    @FXML
    private TextField address_user;
    @FXML
    private TextField email_user;
    @FXML
    private TextField tel_user;
    @FXML
    private TextArea desc_serv;
    @FXML
    private TextField date_serv;
    @FXML
    private TextField categorie_serv;
    @FXML
    private TextField id_serv_user_recup;
    
    Connection c=DataBase.getInstance().getConnection();
    PreparedStatement pst;
    ResultSet res;
    @FXML
    private Button btnRetour;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    String sql="select * from  utilisateur U,offre_service O where U.id_user=? and O.id_serv_prop=?";
        try {
            
          
            pst=c.prepareStatement(sql);
            pst.setInt(1,idUserInv);
            pst.setInt(2,idServiceInv); 
            res=pst.executeQuery();
            if (res.next()){
                // champs du profile
                id_user_recuper.setText(res.getString("id_user"));
                nom_user.setText(res.getString("nom_user"));
                prnm_user.setText(res.getString("prenom_user"));
                address_user.setText(res.getString("adresse_user"));
                email_user.setText(res.getString("email_user"));
                tel_user.setText(res.getString("tel_user"));
                // header du profile
                id_serv_user_recup.setText(res.getString("id_serv_prop"));
                desc_serv.setText(res.getString("desc_serv_prop"));
                date_serv.setText(res.getString("date_serv_prop"));
                categorie_serv.setText(res.getString("categorie_serv_prop"));

                // remplir champs @email auto et n°Tel auto
              // id_user_static.setText(String.valueOf(id_user_static_get)); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultationServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    @FXML
    private void RetourAction(ActionEvent event) throws IOException {
        
        Parent root=null;
        Stage stage=null;
        
        if (event.getSource()==btnRetour){          
            stage=(Stage)btnRetour.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("AfficherListeService.fxml"));
        }  
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
}
