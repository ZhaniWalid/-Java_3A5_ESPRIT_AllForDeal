/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDC.esprit.ventePrivee.GUI;

import static TDC.esprit.ventePrivee.GUI.authAdminController.idUser;
import TDC.esprit.ventePrivee.classes.serviceDao;
import TDC.esprit.ventePrivee.entities.demandeService;
import TDC.esprit.ventePrivee.technique.DataBase;
import java.io.File;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Edzio
 */
public class ComptePersUserController implements Initializable{
    
     // variable cnx
    Connection c=DataBase.getInstance().getConnection();
    PreparedStatement pst;
    ResultSet res;
    @FXML
    private TextField id_trans_static_cmpte_user;
    @FXML
    private TextField Nom_User;
    @FXML
    private TextField prnm_User;
    @FXML
    private TextField add_user;
    @FXML
    private TextField email_user;
    @FXML
    private TextField tel_user;
    @FXML
    private TextField statu_user;
    @FXML
    private TextField login_user;
    @FXML
    private TextField pwd_user;
    @FXML
    private TextField sexe_user;
    @FXML
    private Button btn_retour_vers_espace_client;
    @FXML
    private Button btnIntAffich;
    @FXML
    private Button btnIntAjout;
    @FXML
    private AnchorPane map;
    @FXML
    private TextField objet;
    @FXML
    private TextField description;
    @FXML
    private ComboBox<String> categorie;
    @FXML
    private ComboBox<String> lieu;
    @FXML
    private DatePicker date;
    @FXML
    private Button ajout;
    @FXML
    private ImageView add;
    @FXML
    private ImageView confi;
    @FXML
    private Button maping;
    @FXML
    private ImageView gmap;
    @FXML
    private AnchorPane poub;
    @FXML
    private TableView<demandeService> tableAffiche;
    @FXML
    private TableColumn<demandeService,Integer> col_id;
    @FXML
    private TableColumn<demandeService,String> col_obj;
    @FXML
    private TableColumn<demandeService,String> col_description;
    @FXML
    private TableColumn<demandeService,String> col_categorie;
    @FXML
    private TableColumn<demandeService,String> col_lieu;
    @FXML
    private TableColumn<demandeService,String> col_date;
    @FXML
    private TableColumn<demandeService, Integer> col_validite ;
    @FXML
    private TextArea desc_modif;
    @FXML
    private TextField obj_mod;
    @FXML
    private ComboBox<Integer> id_box;
    @FXML
    private ComboBox<String> categorie_modif;
    @FXML
    private ComboBox<String> lieu_modif;
    @FXML
    private TextField date_mdoif;
    @FXML
    private Button btnModif;
    @FXML
    private Button btnSuppDemande;
    @FXML
    private TextField text_Supp;
    @FXML
    private TextField labelSearchField;
    @FXML
    private ImageView cherch;
    @FXML
    private ImageView pou;
    @FXML
    private ImageView cahier;

    // dem service
    private ObservableList<demandeService> data=FXCollections.observableArrayList();
    private ImageView iv ;
    private Image im ;
    private ImageView ivv ;
    private Image imm ;
    
    private ImageView iv1 ;
    private Image im1 ;
    
    private ImageView iv2 ;
    private Image im2 ;
    
    private ImageView iv3 ;
    private Image im3 ;
    
    
    
    private ImageView iv4 ;
    private Image im4 ;
    
    // jdoula
    @FXML
    private Button btnAffiche;
    @FXML
    private Button btnAjout;
    
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
                id_trans_static_cmpte_user.setText(res.getString("id_user"));
                Nom_User.setText(res.getString("nom_user"));
                prnm_User.setText(res.getString("prenom_user"));
                add_user.setText(res.getString("adresse_user"));
                email_user.setText(res.getString("email_user"));
                tel_user.setText(res.getString("tel_user"));
                statu_user.setText(res.getString("statut_user"));
                login_user.setText(res.getString("login_user"));
                pwd_user.setText(res.getString("pwd_user"));
                sexe_user.setText(res.getString("sexe_user"));
  
            }
        } catch (SQLException ex) {
            Logger.getLogger(AjouterProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // afficher demande service
           FiltredSearch();
        
          String path = new File("src/TDC/esprit/ventePrivee/GUI/images/ajouter.png").getAbsolutePath();
        im = new Image(new File(path).toURI().toString());
        iv = new ImageView(im);
        add.setImage(im);
        
        
        
       String path1 = new File("src/TDC/esprit/ventePrivee/GUI/images/confirm.png").getAbsolutePath();
       
        imm = new Image(new File(path1).toURI().toString());
        ivv = new ImageView(imm);
        confi.setImage(imm);
        
        
        String path2 = new File("src/TDC/esprit/ventePrivee/GUI/images/recherche.png").getAbsolutePath();
       
        im1 = new Image(new File(path2).toURI().toString());
        iv1 = new ImageView(im1);
        cherch.setImage(im1);
        
        
        
        String path5 = new File("src/TDC/esprit/ventePrivee/GUI/images/gmap.png").getAbsolutePath();
       
        im4 = new Image(new File(path5).toURI().toString());
        iv4 = new ImageView(im4);
        gmap.setImage(im4);
        
        
        
        
        String path3 = new File("src/TDC/esprit/ventePrivee/GUI/images/deleteCategory.png").getAbsolutePath();
       
        im2 = new Image(new File(path3).toURI().toString());
        iv2 = new ImageView(im2);
        pou.setImage(im2);
        
         String path4 = new File("src/TDC/esprit/ventePrivee/GUI/images/editerCategory.png").getAbsolutePath();
       
        im3 = new Image(new File(path4).toURI().toString());
        iv3 = new ImageView(im3);
        cahier.setImage(im3);
        
        afficherDem();
        
        
   
        
    }

    
     // Recherche Filtrée
    public void FiltredSearch(){
            // Filtered List
        FilteredList<demandeService> filteredDemandeListData = new FilteredList<>(data,e->true);
        
        
        labelSearchField.setOnKeyReleased(e -> {
                labelSearchField.textProperty().addListener((observableValue,oldValue,newValue) -> {
                filteredDemandeListData.setPredicate((Predicate<? super demandeService>)( demandeService ds) -> {
                if (newValue==null || newValue.isEmpty()){
                return true;
                }
                String toLowerCaseNewValue = newValue.toLowerCase();
                
               
                if (ds.getCathegorie().toLowerCase().contains(toLowerCaseNewValue)){
                    return true;
                }else if(ds.getLieu().toLowerCase().contains(toLowerCaseNewValue)){
                    return true;
                }
                return false;
                   }); 
              });
        });
        
         //Sorted List
              SortedList<demandeService>  sortedDemandeListData = new SortedList<>(filteredDemandeListData);
              
              sortedDemandeListData.comparatorProperty().bind(tableAffiche.comparatorProperty());
              tableAffiche.setItems(sortedDemandeListData);
    }
    
    @FXML
    private void VersEspaceClient(ActionEvent event) throws IOException {
        
        Stage stage=null;
        Parent root=null;
        Alert alert;
        alert=new Alert(Alert.AlertType.INFORMATION);
        
        if (event.getSource()==btn_retour_vers_espace_client){
             //get reference to the button's stage
            stage=(Stage) btn_retour_vers_espace_client.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("MenuUser.fxml"));
         //   pr.DisplayobservableArrayList();
               
        alert.setTitle("Etat Deconnexion");
        alert.setHeaderText("Deconnecter de l'Espace Personelle");
        alert.setContentText("Merci d'etre un client fidèle du All For Deal,Nous vous Souhaite la Bienvenu");
        alert.showAndWait();
        
            
        }
        //create a new scene with root and set the stage// Creer un nouveau scène 
         Scene scene=new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Interface Utilisateur");
         stage.show();
    }

    @FXML
    private void VersIntAffich(ActionEvent event) throws IOException {
        
        Stage stage = null ;
        Parent root = null ;
        /*TableView table=new TableView();
        ProduitDAO produit=new ProduitDAO();
        produit.DisplayobservableArrayList();*/
        
       /* IProduitDAO pr=new ProduitDAO();
        TableView table=new TableView();*/
        
        if (event.getSource()==btnIntAffich){
             //get reference to the button's stage
            stage=(Stage) btnIntAffich.getScene().getWindow();
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

    @FXML
    private void VersInterfaceAjout(ActionEvent event) throws IOException {
        
          
        Stage stage = null ;
        Parent root = null ;
        
        if (event.getSource()==btnIntAjout){
             //get reference to the button's stage
            stage=(Stage) btnIntAjout.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("AjouterProduit.fxml"));
            
        }
        //create a new scene with root and set the stage// Creer un nouveau scène 
         Scene scene=new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Interface Ajout Offres Produits");
         stage.show();
    }

    @FXML
    private void ajout(ActionEvent event) throws IOException {
            
        Alert alertOk = new Alert(Alert.AlertType.INFORMATION);
        String itemCategorie=String.valueOf(categorie.getSelectionModel().getSelectedItem());
        String itemLieu=String.valueOf(lieu.getSelectionModel().getSelectedItem());
        String itemDate=((TextField)date.getEditor()).getText();
        
        demandeService demServ=new demandeService();
        
        demServ.setObjet(objet.getText());
        demServ.setDescription(description.getText());
        demServ.setCathegorie(itemCategorie);
        demServ.setLieu(itemLieu);
        demServ.setDate(itemDate); 
        demServ.setIdUserDemServ(idUser);
        
        serviceDao servDao = new serviceDao();
        servDao.ajouter(demServ);
        
        alertOk.setTitle("etat ajout");
        alertOk.setHeaderText("demande service");
        alertOk.setContentText("Votre demande a été éffectué avec succées");
        tableAffiche.refresh();
        afficherDem();
    }

    @FXML
    private void afficher_map(ActionEvent event) {
        Googlemap g=new Googlemap();
        g.start(new Stage());
    }

    @FXML
    private void remplir(MouseEvent event) {
         
        demandeService d=(demandeService)tableAffiche.getSelectionModel().getSelectedItem();
        String s = String.valueOf(d.getId());
        id_box.getItems().clear();
        id_box.getItems().add(d.getId());
        id_box.getSelectionModel().selectFirst();
        obj_mod.setText(d.getObjet());
       desc_modif.setText(d.getDescription());
        date_mdoif.setText(d.getDate());
        text_Supp.setText(String.valueOf(d.getId()));
        System.out.println(d.getCathegorie());
        categorie_modif.setValue(d.getCathegorie());
        System.out.println(d.getLieu());
       lieu_modif.setValue(d.getLieu());
    }


    @FXML
    private void Modifier(ActionEvent event) throws IOException {
        
          Alert alertOk,alertErr;
        alertOk=new Alert(Alert.AlertType.INFORMATION);
        alertErr=new Alert(Alert.AlertType.WARNING);
        
       
        String ItemCategorie=String.valueOf(categorie_modif.getSelectionModel().getSelectedItem());
        String ItemLieu=String.valueOf(lieu_modif.getSelectionModel().getSelectedItem());

        //int id_modif=id_box.getSelectionModel().getSelectedItem();
        demandeService demServ = new demandeService();
        
        demServ.setObjet(obj_mod.getText());
        demServ.setDescription(desc_modif.getText());
        demServ.setCathegorie(ItemCategorie);
        demServ.setLieu(ItemLieu);
        demServ.setDate(date_mdoif.getText());
        demServ.setId(id_box.getValue());
        
       serviceDao servDAO=new serviceDao();
        servDAO.updateService(demServ);  ;
        
        alertOk.setTitle("Etat Mise A jour");
        alertOk.setHeaderText("Modifier Offre Produit");
        alertOk.setContentText("Mise à jour a été éffectué avec succés");
        alertOk.showAndWait();
        tableAffiche.refresh();
        afficherDem();
        
    }

    @FXML
    private void SuppDemande(ActionEvent event) throws IOException {
          Alert alertOk,alertErr;
        alertOk=new Alert(Alert.AlertType.INFORMATION);
        alertErr=new Alert(Alert.AlertType.WARNING);
        
        int id=Integer.parseInt(text_Supp.getText());
        demandeService produit=new demandeService();
        serviceDao pr=new serviceDao();
        
        pr.removeServiceById(id);
        alertOk.setTitle("Etat Mise A jour");
        alertOk.setHeaderText("Demande service supprimé");
        alertOk.setContentText("la suppression  a été éffectué avec succés");
        alertOk.showAndWait();
        tableAffiche.refresh();
        afficherDem();
    }

    @FXML
    private void SuppDemande(MouseEvent event) {
    }

    @FXML
    private void remplir(DragEvent event) {
    }
    
    public void afficherDem(){
        
             try {
            
            data=FXCollections.observableArrayList();
            String req="select * from demande_service where id_user_demande="+idUser;
            res=c.createStatement().executeQuery(req);
            while (res.next()){
                data.add(new demandeService(res.getInt("id"),res.getString("objet"),res.getString("description"),res.getString("cathegorie"),res.getString("lieu"),res.getString("date"),res.getInt("valid_serv_dem"),res.getInt("id_user_demande")));
            } 
          
            
            col_id.setCellValueFactory(new PropertyValueFactory<demandeService,Integer>("id"));
            col_obj.setCellValueFactory(new PropertyValueFactory<demandeService,String>("objet"));
            col_description.setCellValueFactory(new PropertyValueFactory<demandeService,String>("description"));
            col_categorie.setCellValueFactory(new PropertyValueFactory<demandeService,String>("cathegorie"));
            col_lieu.setCellValueFactory(new PropertyValueFactory<demandeService,String>("lieu"));
            col_date.setCellValueFactory(new PropertyValueFactory<demandeService,String>("date"));
             col_validite.setCellValueFactory(new PropertyValueFactory<demandeService,Integer>("valid_serv_dem"));

            
            //TableAffiche.setItems(null);
            tableAffiche.setItems(data);
            tableAffiche.setVisible(true);
        } catch (SQLException ex) {
            System.out.println("Error on building Data");
            Logger.getLogger(demandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    // partie jdoula 

    @FXML
    private void afficherService(ActionEvent event) throws IOException {
          
        Stage stage = null ;
        Parent root = null ;
        
        if (event.getSource()==btnAffiche){
             //get reference to the button's stage
            stage=(Stage) btnAffiche.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("InterfaceSupp.fxml"));
            
        }
        //create a new scene with root and set the stage// Creer un nouveau scène 
         Scene scene=new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Interface Affichage Mes Services");
         stage.show();
    }
        
        
    

    @FXML
    private void ajoutService(ActionEvent event) throws IOException {
        
        Stage stage = null ;
        Parent root = null ;
        
        if (event.getSource()==btnAjout){
             //get reference to the button's stage
            stage=(Stage) btnAjout.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            
        }
        //create a new scene with root and set the stage// Creer un nouveau scène 
         Scene scene=new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Interface Ajout Service");
         stage.show();
    }
}
