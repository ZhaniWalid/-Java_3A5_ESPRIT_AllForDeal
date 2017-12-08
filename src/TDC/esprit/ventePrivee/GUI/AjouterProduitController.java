/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDC.esprit.ventePrivee.GUI;

import static TDC.esprit.ventePrivee.GUI.EspaceAdminController.id_user_static_get;
import static TDC.esprit.ventePrivee.GUI.authAdminController.idUser;
import TDC.esprit.ventePrivee.classes.ProduitDAO;
import TDC.esprit.ventePrivee.Poubelle.DechetsJava.ProduitDAO_Two;
import TDC.esprit.ventePrivee.entities.Produit;
import TDC.esprit.ventePrivee.entities.Produit.TypesProduits;
import TDC.esprit.ventePrivee.Poubelle.DechetsJava.Produit_Two;
import TDC.esprit.ventePrivee.interfaces.IProduitDAO;
import TDC.esprit.ventePrivee.technique.DataBase;
import com.mysql.jdbc.Blob;
import java.awt.Color;
import static java.awt.Color.red;

import javafx.scene.layout.*;


import java.awt.LayoutManager;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialogs;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.imageio.ImageIO;

/**
 *
 * @author Edzio
 */
public class AjouterProduitController extends Application implements Initializable {
  
    
   

    @FXML
    private Button btnAjoutPrdOffre;
    @FXML
    private Button btnBack;
    @FXML
    private TextField prix_prd;
    @FXML
    private TextField libelle_prd;
    @FXML
    private TextArea description_prd;
    /*@FXML
    private TextField dateAjout_prd;*/
    @FXML
    private ComboBox<String> categorie_prd;
    @FXML
    private Label labelError;
    @FXML
    private Label labelOk;
    @FXML
    private DatePicker date_Ajout;
    @FXML
    private Button btnBrowseFiles;
    @FXML
    private ImageView imageView_Field;
    @FXML
    private TextArea TextField_Path;      
    private ImageView imageView;
    
    private FileChooser fileChooser;
    private File file; 
    private Desktop desktop = Desktop.getDesktop();
    private Image image;
    private FileInputStream fis;
    
    
    Connection c=DataBase.getInstance().getConnection();
    PreparedStatement pst;
    ResultSet res;
    
 //   private Layout layout = null;
    // içi on va appller la méthode d'ajout dans ProduitDAO pour faire l'ajout selon les champs insités dans 
    // le fichier FXML
    @FXML
    private TextField idUser_Invisibleeee;
    
    @FXML
    private void AjouterProduitAuxOffres(ActionEvent event) throws FileNotFoundException {
     
                
        
        Alert alertOk,alertErr;
        alertOk=new Alert(Alert.AlertType.INFORMATION);
        alertErr=new Alert(Alert.AlertType.WARNING);
        int id_Static_userAdd=Integer.parseInt(idUser_Invisibleeee.getText());
        
        Stage stage = null;
        fis=new FileInputStream(file);
        try {
            image = new Image(file.toURI().toURL().toString(),200,150,true,true);//Path,prefWidth,prefHeight,preserveRatio,Smooth
        } catch (MalformedURLException ex) {
            Logger.getLogger(AjouterProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            BufferedImage bufferedImage=ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(AjouterProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        imageView_Field.setImage(image);
        
        double prix=Integer.parseInt(prix_prd.getText());
//        int ptsBonus=Integer.parseInt(nbPts_prd.getText());
        String Item=String.valueOf(categorie_prd.getSelectionModel().getSelectedItem());
        Produit produit = new Produit();
     
        /* if (FieldIsEmpty()){
        alertErr.setTitle("Champs de Saisie Vide");
        alertErr.setHeaderText("Verifier le champs vide");
        alertErr.setContentText("Veillez le remplir");
        alertErr.showAndWait();
        }
        else{*/
        produit.setPrix_prd(prix);
        
        if (validateLibelle()){
           
          produit.setLibelle_prd(libelle_prd.getText()); 
           
        }else{
            alertErr.setTitle("Champs Saisie Chaine Caractère");
            alertErr.setHeaderText("Chaine Saisie Libelle est Invalide");
            alertErr.setContentText("Veillez Réssayer");
            alertErr.showAndWait();
            libelle_prd.clear();
        }
   
        produit.setDespcription_prd(description_prd.getText());
        produit.setDateAjout_prd(((TextField)date_Ajout.getEditor()).getText());
        produit.setImage((InputStream)fis);
        //produit.setDateAjout_prd(dateAjout_prd.getText());
       // produit.setCategorie_prd((TypesProduits)categorie_prd.getSelectionModel().getSelectedItem());
        produit.setCategorie_prd(Item);   
        produit.setIdUser(id_Static_userAdd);
        
        IProduitDAO prod = new ProduitDAO();
        if (prod.addProduitOffre(produit)){
            //Dialogs.showInformationDialog(stage,"Ajout a éffectué avec succés","Information Dialog", "Etat Ajout Offre Produit");
            alertOk.setTitle("Etat Ajout");
            alertOk.setHeaderText("Ajout Offre Produit");
            alertOk.setContentText("Ajout a été éffectué avec succés");
            alertOk.showAndWait();
            labelOk.setText("Ajout Efféctué Avec Succés :) ");
            labelError.setText(null);
            //Clear All Fields After Adding Successfuly
            ClearAllFields();
          
        }else if (!prod.addProduitOffre(produit)){
            //Dialogs.showWarningDialog(stage,"Ajout a été échoué","Information Dialog", "Etat Ajout Offre Produit");
            alertErr.setTitle("Etat Ajout");
            alertErr.setHeaderText("Ajout Offre Produit");
            alertErr.setContentText("Ajout a été échoué");
            alertErr.showAndWait();
            labelError.setText("Ajout a été échoué :) ");
            labelOk.setText(null);
        }
        
        
        
    }                             

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Stage primaryStage = null;
        
    
        fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Text Files","*txt"),
            new ExtensionFilter("Image Files","*.png","*.jpg","*.gif"),
            new ExtensionFilter("Audio Files","*wav","*.mp3","*.aac"),
            new ExtensionFilter("All Files","*.*")
        );
        
        btnBrowseFiles.setOnAction(e -> {
        file = fileChooser.showOpenDialog(primaryStage);
        // Single File Selection
        if (file!=null){
               // desktop.open(file); => c'était pour le test de "browse" files 
                TextField_Path.setText(file.getAbsolutePath());
            try {
                image = new Image(file.toURI().toURL().toString(),200,150,true,true);//Path,prefWidth,prefHeight,preserveRatio,Smooth
                BufferedImage bufferedImage=ImageIO.read(file);
                imageView_Field.setImage(image);
                
            } catch (MalformedURLException ex) {
                Logger.getLogger(AjouterProduitController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AjouterProduitController.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            
                imageView_Field = new ImageView(image);
                imageView_Field.setFitWidth(200);
                imageView_Field.setFitHeight(150);
                imageView_Field.setVisible(true);
                imageView_Field.setSmooth(true);
                imageView_Field.setPreserveRatio(true);  
                
               
                BorderPane.setAlignment(imageView_Field, Pos.TOP_LEFT);
        }
        
        // Multiple File Selection
        /* List<File> fileList=fileChooser.showOpenMultipleDialog(primaryStage);
        if (file!=null){
        fileList.stream().forEach(selectedFiles -> {
        //  desktop.open(selectedFiles); => aussi pour le test
        TextField_Path.setText(fileList.toString());
        });
        }*/
        
        
        });
        
          String sql="select * from utilisateur where id_user="+idUser;
        try {
            
            
            
            pst=c.prepareStatement(sql);
         //   pst.setInt(1,offreServ.getId_Serv());
          //  pst.setInt(2,id_user_static_get); 
            res=pst.executeQuery();
            if (res.next()){
                // champs du profile
                idUser_Invisibleeee.setText(res.getString("id_user"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AjouterProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void RetourEnHome(ActionEvent event) throws IOException {
        Stage stage = null ;
        Parent root = null ;
        
        if (event.getSource()==btnBack){
             //get reference to the button's stage
            stage=(Stage) btnBack.getScene().getWindow();
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
    private void showListe(ActionEvent event) {
            
    }

    private void ClearAllFields(){
        
        prix_prd.clear();
        libelle_prd.clear();
       // nbPts_prd.clear();
        description_prd.clear();
        date_Ajout.setValue(null);
    }

    @FXML
    private void BrowseFilesAction(ActionEvent event) {
      
       
        
       
    }

    @Override
    public void start(Stage stage) throws Exception {
           
    
    }
    
    public boolean validateLibelle(){
        Alert alertErr;
        alertErr=new Alert(Alert.AlertType.WARNING);
        Pattern p=Pattern.compile("[a-zA-Z]+");
        Matcher m=p.matcher(libelle_prd.getText());
        if (m.find() && m.group().equals(libelle_prd.getText())){
            return true;
        }else{
            
            return false;
        }
    }
    
    /*  public boolean FieldIsEmpty(){
    
    Alert alertErr;
    alertErr=new Alert(Alert.AlertType.ERROR);
    
    if (prix_prd.getText().isEmpty() ||libelle_prd.getText().isEmpty() || description_prd.getText().isEmpty() || date_Ajout.getEditor().getText().isEmpty() || categorie_prd.getTooltip().getText().isEmpty() || TextField_Path.getText().isEmpty() || imageView_Field.getTransforms().isEmpty() ){
    
    return true;
    }else{
    return false;
    }
    }*/
}
