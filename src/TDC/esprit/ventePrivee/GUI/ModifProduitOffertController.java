/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDC.esprit.ventePrivee.GUI;

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Edzio
 */
public class ModifProduitOffertController implements Initializable{
    @FXML
    private TextField Text_Id;
    @FXML
    private TextField Text_Prix;
    @FXML
    private TextField Text_Libelle;
    @FXML
    private TextField Text_Pts;
    @FXML
    private TextField Text_Date;
    @FXML
    private Button btnValidModif;
    @FXML
    private Button btnRetour;
    @FXML
    private TextArea Text_Desc;
    @FXML
    private ComboBox<String> Text_Categorie;
    @FXML
    private ComboBox<Integer> ComboBox_Id;
    
    
    
    final ObservableList options=FXCollections.observableArrayList();
    private ObservableList<Produit> data;
    Produit dataSelected=new Produit();
    Connection c=DataBase.getInstance().getConnection();
    PreparedStatement pst;
    ResultSet res;
   

    @FXML
    private void ValiderModif(ActionEvent event) throws SQLException {
        
        Stage stage = null;
        int id=Integer.parseInt(Text_Id.getText());
        double prix=Integer.parseInt(Text_Prix.getText());
        int ptsBonus=Integer.parseInt(Text_Pts.getText());
        String Item=String.valueOf(Text_Categorie.getSelectionModel().getSelectedItem());
        
        Produit produit = new Produit();
        
           try{
        String sql="select * from produit where id_Produit=id";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setInt(1, id);
            res=pst.executeQuery();
            while (res.next()){
                Text_Id.setText(String.valueOf(id));
                Text_Prix.setText(res.getString(2));     
            }
            
            //TableAffiche.setItems(null);
            /*TableAffiche.setItems(data);
            TableAffiche.setVisible(true);*/
         } catch (SQLException ex) {
            System.out.println("Error on building Data");
            Logger.getLogger(AfficherProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        
    

    @FXML
    private void versIntAff(ActionEvent event) throws IOException {
        
        Stage stage = null ;
        Parent root = null ;
        
        
        if (event.getSource()==btnRetour){
             //get reference to the button's stage
            stage=(Stage) btnRetour.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("AfficheListeProduitOffert.fxml"));
         //   pr.DisplayobservableArrayList();
            
        }
        //create a new scene with root and set the stage// Creer un nouveau scène 
         Scene scene=new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Affichage liste des offres produits");
         stage.show();
         
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
        try {
            String req="select id_Produit from produit";
            pst=c.prepareStatement(req);
            ResultSet res=pst.executeQuery();
            while (res.next()){
                options.add(res.getInt("id_Produit"));
            }
            ComboBox_Id.setItems(options);
        } catch (SQLException ex) {
            Logger.getLogger(ModifProduitOffertController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ComboBox_Id.setOnAction(e -> {
        
            String req="select * from produit where id_Produit=? ";
            try {
                pst=c.prepareStatement(req);
                pst.setInt(1,ComboBox_Id.getSelectionModel().getSelectedItem());
                res=pst.executeQuery();
                
                while (res.next()){
                Text_Prix.setText(res.getString("prix_prd"));
            }
            } catch (SQLException ex) {
                Logger.getLogger(ModifProduitOffertController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            
         
        });
    }
    
}

