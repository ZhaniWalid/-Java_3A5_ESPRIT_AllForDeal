/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDC.esprit.ventePrivee.GUI;

import static TDC.esprit.ventePrivee.GUI.authAdminController.idUser;
import TDC.esprit.ventePrivee.classes.ProduitDAO;
import TDC.esprit.ventePrivee.entities.Produit;
import TDC.esprit.ventePrivee.interfaces.IProduitDAO;
import TDC.esprit.ventePrivee.technique.DataBase;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import static java.lang.System.out;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialogs;

import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javax.imageio.ImageIO;



/**
 *
 * @author Edzio
 */
public class AfficherProduitController  implements Initializable  {
    
    
    
    @FXML
    private Button btnSupp;
    @FXML
    private TextField labelSupp;
    /*@FXML
    private TableView<Produit_Two> TableAffiche;*/
    @FXML
    private TableView<Produit> TableAffiche;
    @FXML
    private Label labelRouge;
    @FXML
    private Label labelVert;
    @FXML
    private Button btnMenuPrincipale;
    /*@FXML
    private TableColumn<Produit_Two,String> prix_Col; */
    @FXML
    private TableColumn<Produit,Integer> Id_Col;
    @FXML
    private TableColumn<Produit,Double> prix_Col; 
    @FXML
    private TableColumn<Produit,String> Lib_Col;
    @FXML
    private TableColumn<Produit,String> Desc_Col;
    @FXML
    private TableColumn<Produit,String> Date_Col;
    @FXML
    private TableColumn<Produit,String> Categ_Col;

    
    private ObservableList<Produit> data=FXCollections.observableArrayList();
    Produit dataSelected=new Produit();
    Connection c=DataBase.getInstance().getConnection();
    PreparedStatement pst;
    ResultSet res;
    final ObservableList options=FXCollections.observableArrayList();
    final ObservableList optionsCatgorie=FXCollections.observableArrayList();
    
    private Button btnModif;
    private TextField labelModif;
    private ComboBox<Integer> ComboBox_Id;
    @FXML
    private TextField Text_Prix;
    @FXML
    private TextField Text_Libelle;
    @FXML
    private TextArea Text_Desc;
 //   private TextField Text_Pts;
    @FXML
    private ComboBox<String> Text_Categorie;
    @FXML
    private Button btnValidModif;
    @FXML
    private TextField labelSearchField;
    private DatePicker dateAjout_Picker;
    @FXML
    private TextField DateAjout_Field;
    private ImageView ImgView_Review;
    private Image image_review;
    @FXML
    private TextField id_prod_modif_non_visible;
    @FXML
    private TableColumn<Produit,Integer> validite_col;
    @FXML
    private TextField id_user_offre_produit_invisible;
   // boutton lorsqu"on appuye elle va supprimer une ligne selon l'id que nous avons insérer dans le champs
    // et on va appeler içi la méthode "removeProduitById(id)" icité dans "ProduitDAO"
    @FXML
    private void SuppProduit(ActionEvent event) {
        
        Alert alertOk,alertErr;
        alertOk=new Alert(Alert.AlertType.INFORMATION);
        alertErr=new Alert(Alert.AlertType.WARNING);
        
        Stage stage = null;
        Produit produit=new Produit();
        int id=Integer.parseInt(labelSupp.getText());
        IProduitDAO pr=new ProduitDAO();
        if (pr.removeProduitById(id)){
      //  Dialogs.showInformationDialog(stage,"Suppression a éffectué avec succés","Information Dialog", "Etat Suppression");
        alertOk.setTitle("Etat Suppression");
        alertOk.setHeaderText("Suppresion Offre Produit");
        alertOk.setContentText("Suppression du produit ,Numéro :"+id+" ,a été éffectué avec succés");
        alertOk.showAndWait();    
        labelVert.setText("Suppression éffectué");
        labelRouge.setText(null);
       
        TableAffiche.refresh();
     
        ShowObservableArrayFxListOffreProduit();
        FiltredSearch();
        }
        else if (!pr.removeProduitById(id)){
        //Dialogs.showWarningDialog(stage,"Suppression a été échoué","Information Dialog", "Etat Suppression");
        alertErr.setTitle("Etat Suppression");
        alertErr.setHeaderText("Suppression Offre Produit");
        alertErr.setContentText("Suppression a été échouée");
        alertErr.showAndWait();
        labelRouge.setText("Suppression échoué");
        labelVert.setText(null); 
        
        TableAffiche.refresh();
     
        ShowObservableArrayFxListOffreProduit();
        FiltredSearch();
        }
        
       /* int id = 0;
        
        try {
            data=FXCollections.observableArrayList();
            String req="delete from produit where id_produit=?";
            pst=c.prepareStatement(req);
            pst.setInt(1,id);
            data = TableAffiche.getItems();
            dataSelected =TableAffiche.getSelectionModel().getSelectedItems().get(id);
          //  dataSelected.forEach(data::remove);
            IProduitDAO prdDAO=new ProduitDAO();
            //prdDAO.removeProduitById(dataSelected);
            for (Produit dataSelected:data){
               prdDAO.removeProduitById(id);
               data.remove(new Produit().getId_Produit());
            }
        } catch (SQLException ex) {
            Logger.getLogger(AfficherProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
       
         
    }

    // boutton pour faire le retour au menu principale
    @FXML
    private void RetourMenu(ActionEvent event) throws IOException {
        Stage stage = null ;
        Parent root = null ;
        
        if (event.getSource()==btnMenuPrincipale){
             //get reference to the button's stage
            stage=(Stage) btnMenuPrincipale.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("CompteUserPerso.fxml"));
            
        }
        //create a new scene with root and set the stage// Creer un nouveau scène 
         Scene scene=new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Interface Utilisateur");
         stage.show();
        
         
    }


  // dans cette méthode on va faire l'affichage des données et ainsi la récupération des données dans les champs 
    // du text fields pour faire la modification
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /* ColId.setCellValueFactory(new PropertyValueFactory<Produit,String>("Id_Produit"));
        IProduitDAO pr=new ProduitDAO();
        pr.DisplayobservableArrayList();*/
        
        /*    ObservableList<String> data = FXCollections.observableArrayList();
        ProduitDAO produitDao=new ProduitDAO();
        data=produitDao.DisplayobservableArrayList();
        
        Id_prd.setCellValueFactory(new PropertyValueFactory<Produit,String>("id_produit"));
        prix_prd.setCellValueFactory(new PropertyValueFactory<Produit,String>("prix_prd"));
        libelle_prd.setCellValueFactory(new PropertyValueFactory<Produit,String>("libelle_prd"));
        description_prd.setCellValueFactory(new PropertyValueFactory<Produit,String>("description_prd"));
        nbPts_prd.setCellValueFactory(new PropertyValueFactory<Produit,String>("nbPts_prd"));
        dateAjout_prd.setCellValueFactory(new PropertyValueFactory<Produit,String>("dateAjout_prd"));
        categorie_prd.setCellValueFactory(new PropertyValueFactory<Produit,String>("categorie_prd"));
        
        String req="select * from produit";
        try {
        res=c.createStatement().executeQuery(req);
        
        
        while (res.next()){
        for (String p:data){
        
        data.add(0,String.valueOf(res.getInt(p.toString())));
        }
        }
        
        } catch (SQLException ex) {
        Logger.getLogger(AfficherProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        TableAffiche.setItems(data);
        TableAffiche.getColumns().addAll(Id_prd,prix_prd,libelle_prd,description_prd,nbPts_prd,dateAjout_prd,categorie_prd);*/
         
        
        TableAffiche.setEditable(true);
       
         /// Affichage des données dans un observableArrayList()
          ShowObservableArrayFxListOffreProduit();
   
               
        /// prendre les valeurs de base de données depuis la BD selon Id et les mettres dans des TextField
        
        /*    try {
        String req="select id_Produit from produit";
        pst=c.prepareStatement(req);
        ResultSet res=pst.executeQuery();
        while (res.next()){
        options.add(res.getInt("id_Produit"));
        }
        ComboBox_Id.setItems(options);
        } catch (SQLException ex) {
        Logger.getLogger(ModifProduitOffertController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
           
        TableAffiche.setOnMouseClicked(e -> {
        
           String req="select * from produit where id_Produit=? ";
            try {
                Produit produit=(Produit)TableAffiche.getSelectionModel().getSelectedItem();
                pst=c.prepareStatement(req);
                pst.setInt(1,produit.getId_Produit());
               // pst.setInt(1,ComboBox_Id.getSelectionModel().getSelectedItem());
                res=pst.executeQuery();
                
                while (res.next()){ 
                 // remplissage des textField aprés selection dans le tableaux
                Text_Prix.setText(res.getString("prix_prd"));
                Text_Libelle.setText(res.getString("libelle_prd"));
                Text_Desc.setText(res.getString("description_prd"));
            //  Text_Pts.setText(res.getString("nbPts_prd"));
                DateAjout_Field.setText(res.getString("dateAjout_prd"));
                optionsCatgorie.add(res.getString("categorie_prd"));
                Text_Categorie.getSelectionModel().getSelectedItem();
                id_prod_modif_non_visible.setText(res.getString("id_Produit")); // ==> ce text field est invisble dans GUI c'est juste pour prendre valeur d'Id
              
               // remplissage du textField invisible qui stocke l'id du produit désiré à supprimé
                labelSupp.setText(res.getString("id_Produit"));
              
             //  InputStream is=res.getBlob("Image").getBinaryStream();
           //       InputStream is=res.getBinaryStream("Image");
               
                 /* Date  StringToDatePicker = Date.valueOf(res.getString("dateAjout_prd"));
                dateAjout_Picker.setValue(StringToDatePicker.toLocalDate());*/
               // Text_Categorie.setItems(optionsCatgorie);
                
                /*        try {
                ObjectInputStream ois=new ObjectInputStream(is);
                image_review=(Image)ois.readObject();
                BufferedImage bi=null;
                bi=javax.imageio.ImageIO.read(is);;
                OutputStream os=new FileOutputStream(new File("photo.jpg"));
                byte[] content=new byte[1024];
                int size=0;
                while ((size=is.read(content)) != -1){
                os.write(content,0,size);
                
                //   image_review=new Image("file:photo.jpg",200,150,true,true);
                ImgView_Review = new ImageView(image_review);
                //                        boolean bufferedImage=ImageIO.write(bi,"jpg",out);
                out.flush();
                ImgView_Review.setImage(image_review);
                ImgView_Review.setFitWidth(200);
                ImgView_Review.setFitHeight(150);
                ImgView_Review.setVisible(true);
                ImgView_Review.setSmooth(true);
                ImgView_Review.setPreserveRatio(true);
                }
                os.close();
                is.close();
                
                
                } catch (FileNotFoundException ex) {
                Logger.getLogger(AfficherProduitController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                Logger.getLogger(AfficherProduitController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                Logger.getLogger(AfficherProduitController.class.getName()).log(Level.SEVERE, null, ex);
                }*/
               
               
             }
            } catch (SQLException ex) {
                Logger.getLogger(ModifProduitOffertController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        });
        /*
        ComboBox_Id.setOnAction(e -> {
        String req="select * from produit where id_Produit=? ";
        try {
        //   Produit produit=(Produit)TableAffiche.getSelectionModel().getSelectedItem();
        pst=c.prepareStatement(req);
        // pst.setInt(1,produit.getId_Produit());
        pst.setInt(1,ComboBox_Id.getSelectionModel().getSelectedItem());
        res=pst.executeQuery();
        
        while (res.next()){
        Text_Prix.setText(res.getString("prix_prd"));
        Text_Libelle.setText(res.getString("libelle_prd"));
        Text_Desc.setText(res.getString("description_prd"));
        //    Text_Pts.setText(res.getString("nbPts_prd"));
        DateAjout_Field.setText(res.getString("dateAjout_prd"));
        optionsCatgorie.add(res.getString("categorie_prd"));
        Text_Categorie.getSelectionModel().getSelectedItem();
        
        /* Date  StringToDatePicker = Date.valueOf(res.getString("dateAjout_prd"));
        dateAjout_Picker.setValue(StringToDatePicker.toLocalDate());*/
        // Text_Categorie.setItems(optionsCatgorie);
        /*    }
        } catch (SQLException ex) {
        Logger.getLogger(ModifProduitOffertController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        });*/
        
       // recherche : Filtrer le tableau afficher
        FiltredSearch();
     
        
        
    }
    
    /* public static void main(String[] args) {
    launch(args);
    }*/

    // cette méthode est inutile à utiliser
    private void VersIntModif(ActionEvent event) throws IOException, SQLException {
        
        Stage stage = null ;
        Parent root = null ;
        
        int id=0;
        
        if (event.getSource()==btnModif){
            
            String sql="select * from produit where id_Produit=?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setInt(1, id);
            res=pst.executeQuery();
            while (res.next()){
                labelModif.setText(res.getString(1));
            }
          //  int id=Integer.parseInt(labelModif.getText());
            
             //get reference to the button's stage
            stage=(Stage) btnModif.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("ModifierProduit.fxml"));
            
        }
        //create a new scene with root and set the stage// Creer un nouveau scène 
         Scene scene=new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Interface Mofication Produit");
         stage.show();
    }

    /// içi dans cette méthode lorsqu'on a appyue sur la bouton valider modification la modification va etre éxecuté 
    // et on va appeler la méthode "update" incité dans "ProduitDAO"
    @FXML
    private void ValiderModif(ActionEvent event) {
        
        Alert alertOk,alertErr;
        alertOk=new Alert(Alert.AlertType.INFORMATION);
        alertErr=new Alert(Alert.AlertType.WARNING);
        
        Stage stage = null;
        double prix=Integer.parseInt(Text_Prix.getText());
 //       int ptsBonus=Integer.parseInt(Text_Pts.getText());
        String Item=String.valueOf(Text_Categorie.getSelectionModel().getSelectedItem());
     //   int id_modif=ComboBox_Id.getSelectionModel().getSelectedItem();
        int id_nonVisible_Modif=Integer.parseInt(id_prod_modif_non_visible.getText());
        Produit produit = new Produit();
        
        produit.setPrix_prd(prix);
        produit.setLibelle_prd(Text_Libelle.getText());
        produit.setDespcription_prd(Text_Desc.getText());
        
        //produit.setDateAjout_prd(((TextField)dateAjout_Picker.getEditor()).getText());
        produit.setDateAjout_prd(DateAjout_Field.getText());
        produit.setCategorie_prd(Item);
        produit.setId_Produit(id_nonVisible_Modif);
        
        IProduitDAO prdDAO=new ProduitDAO();
        prdDAO.updateProduit(produit);
        
        TableAffiche.refresh();
     
        ShowObservableArrayFxListOffreProduit();
        FiltredSearch();
            
        alertOk.setTitle("Etat Mise A jour");
        alertOk.setHeaderText("Modifier Offre Produit");
        alertOk.setContentText("Mise à jour du produit : "+produit.getLibelle_prd()+" ,Numéro : "+produit.getId_Produit()+" a été éffectué avec succés");
        alertOk.showAndWait();
        
        
        ViderChamps();
        
        /*Refresh(TableAffiche, data);
        
        TableAffiche.setItems(null);
        TableAffiche.layout();
        TableAffiche.setItems(data);
        TableAffiche.setVisible(true);
        TableAffiche.refresh();
        */
    }

    public void ViderChamps(){
        
        Text_Prix.clear();
        Text_Libelle.clear();
        Text_Desc.clear();
   //     Text_Pts.clear();
        DateAjout_Field.clear();
        //Text_Categorie.setValue(null);
    }

    // ma méthode 
   /// Affichage des données dans un observableArrayList()
    public void ShowObservableArrayFxListOffreProduit(){
        
        try {
            
            data=FXCollections.observableArrayList();
         //   String sql="select * from produit where valid_prd=1";
            
            String sql="select * from produit where id_user="+idUser;
            
            res=c.createStatement().executeQuery(sql);
            while (res.next()){
                // lahné f res.getX("Attribut eli mwjoud fl base de données selon type eli 7atetou enty")
                data.add(new Produit(res.getInt("id_Produit"),res.getDouble("prix_prd"),res.getString("libelle_prd"),res.getString("description_prd"),res.getString("dateAjout_prd"),res.getString("categorie_prd"),res.getInt("valid_prd"),res.getInt("id_user")));
            } 
            // lahné (" ") attribut eli mwjoud fl classe mte3k
            // Id_Col : hia fx:id ta3 colonne eli mssaméha fl sceneBuilder
            // PropertyValueFactory<EsmCLASSE,TypeAttribut fl classe>
            // w tanséch déclarehm l fou9 ba3d m t3ml "make controller" fl fichier.fxml
            //exmple :  @FXML private TableColumn<Produit,String> Categ_Col;
            // w aussi ta3ti type ta3 tableView l fou9 
            // exmple : @FXML private TableView<EsmClasseMte3k> TableAffiche;
            
            Id_Col.setCellValueFactory(new PropertyValueFactory<Produit,Integer>("Id_Produit"));
            prix_Col.setCellValueFactory(new PropertyValueFactory<Produit,Double>("prix_prd"));
            Lib_Col.setCellValueFactory(new PropertyValueFactory<Produit,String>("libelle_prd"));
            Desc_Col.setCellValueFactory(new PropertyValueFactory<Produit,String>("despcription_prd"));
            Date_Col.setCellValueFactory(new PropertyValueFactory<Produit,String>("dateAjout_prd"));
            Categ_Col.setCellValueFactory(new PropertyValueFactory<Produit,String>("categorie_prd"));
            validite_col.setCellValueFactory(new PropertyValueFactory<Produit,Integer>("validite"));
            
            TableAffiche.setItems(null);
            TableAffiche.layout();
            TableAffiche.setItems(data);
            TableAffiche.setVisible(true);
        } catch (SQLException ex) {
            System.out.println("Error on building Data");
            Logger.getLogger(AfficherProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // ma méthode
    /**
 *
 * @author Edzio
 */
    // Recherche Filtrée
    public void FiltredSearch(){
            // Filtered List
        FilteredList<Produit> filteredProduitListData = new FilteredList<>(data,e->true);
        
        labelSearchField.setOnKeyReleased(e -> {
                labelSearchField.textProperty().addListener((observableValue,oldValue,newValue) -> {
                filteredProduitListData.setPredicate((Predicate<? super Produit>) produit -> {
                if (newValue==null || newValue.isEmpty()){
                return true;
                }
                String toLowerCaseNewValue = newValue.toLowerCase();
                
                /*  if (produit.getId_Produit()==newValue){
                return true;
                }*/ 
                if (produit.getCategorie_prd().toLowerCase().contains(toLowerCaseNewValue)){
                    return true;
                }else if(produit.getLibelle_prd().toLowerCase().contains(toLowerCaseNewValue)){
                    return true;
                }
                return false;
                   });
              });
        });
        
         //Sorted List
              SortedList<Produit>  sortedProduitListData = new SortedList<>(filteredProduitListData);
              
              sortedProduitListData.comparatorProperty().bind(TableAffiche.comparatorProperty());
              TableAffiche.setItems(sortedProduitListData);
    }
    
}
    /* @FXML*/
    /*   private void FilterProduitData(KeyEvent event) {
    
    // Filtered List
    FilteredList<Produit> filteredProduitListData = new FilteredList<>(data,e->true);
    
    labelSearchField.textProperty().addListener((observableValue,oldValue,newValue) -> {
    filteredProduitListData.setPredicate((Predicate<? super Produit>) produit -> {
    if (newValue==null || newValue.isEmpty()){
    return true;
    }
    String toLowerCaseNewValue = newValue.toLowerCase();
    
    /*  if (produit.getId_Produit()==newValue){
    return true;
    }*/
    /*    if (produit.getCategorie_prd().toLowerCase().contains(toLowerCaseNewValue)){
    return true;
    }else if(produit.getLibelle_prd().toLowerCase().contains(toLowerCaseNewValue)){
    return true;
    }
    return false;
    });
    });
    //Sorted List
    SortedList<Produit>  sortedProduitListData = new SortedList<>(filteredProduitListData);
    
    sortedProduitListData.comparatorProperty().bind(TableAffiche.comparatorProperty());
    TableAffiche.setItems(sortedProduitListData);
    }*/
 
