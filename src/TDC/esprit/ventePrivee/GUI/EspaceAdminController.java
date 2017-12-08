/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDC.esprit.ventePrivee.GUI;

import static TDC.esprit.ventePrivee.GUI.ProfileUserController.mailServerProperties;
import static TDC.esprit.ventePrivee.GUI.authAdminController.loginAdmin;
import TDC.esprit.ventePrivee.classes.AdminDAO;
import TDC.esprit.ventePrivee.classes.CadeauxDAO;
import TDC.esprit.ventePrivee.entities.Admin;
import TDC.esprit.ventePrivee.entities.Cadeaux;
import TDC.esprit.ventePrivee.entities.OffreService;
import TDC.esprit.ventePrivee.entities.Produit;
import TDC.esprit.ventePrivee.entities.Rec_User_Service;
import TDC.esprit.ventePrivee.entities.User;
import TDC.esprit.ventePrivee.entities.User_Service;
import TDC.esprit.ventePrivee.interfaces.IAdminDAO;
import TDC.esprit.ventePrivee.technique.DataBase;
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
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SortEvent;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.C;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Edzio
 */
public class EspaceAdminController implements Initializable {
    @FXML
    private Button btnLogout;

    Stage stage = null ;
    Parent root = null ;
    Alert alert=new Alert(Alert.AlertType.INFORMATION);
    @FXML
    private Tab tabGestUsers;
    @FXML
    private Tab tabGestProd;
    @FXML
    private TableView<Produit> TableAffiche;
    @FXML
    private TableColumn<Produit,Integer> Id_Col;
    @FXML
    private TableColumn<Produit,Double> prix_Col;
    @FXML
    private TableColumn<Produit,String> Lib_Col;
    @FXML
    private TableColumn<Produit,String> Date_Col;
    @FXML
    private TableColumn<Produit,String> Categ_Col;
    @FXML
    private TableColumn<Produit,String> Desc_Col;
    @FXML
    private TableColumn<Produit,Integer> valid_col;
    @FXML
    private TextField id_admin;
    @FXML
    private TextField login_root;
    @FXML
    private TextField pwd_root;
    @FXML
    private Button btnModifRoot;
    @FXML
    private Button btn_valid_prod;
    @FXML
    private Tab donne_admin;   
    @FXML
    private TextField field_search_offre_prod;
    @FXML
    private Tab tabGestServ;
    @FXML
    private Button btnEditable;
    @FXML
    private TextField nom_root;
    @FXML
    private TextField prnm_root;
    @FXML
    private TextField age_root;
    @FXML
    private TextField email_root;
    /*@FXML
    private TextField sexe_root;*/
    @FXML
    private Text txt_nom_admin;
    @FXML
    private Text txt_prnm_admin;
    @FXML
    private ComboBox<String> sexxx_roooot;
    @FXML
    private TextField field_sex_root;
    @FXML
    private TableColumn<User_Service,String> desc_serv_col;
    @FXML
    private TableColumn<User_Service,String> Date_serv_col;
    @FXML
    private TableColumn<User_Service,String> Categ_serv_col;
    @FXML
    private TableColumn<User_Service,Integer> valid_serv_col;
    @FXML
    private TableColumn<User_Service,String> nom_user_col;
    @FXML
    private TableColumn<User_Service,String> prenom_user_col;
    @FXML
    private TableColumn<User_Service,String> email_user_col;
    @FXML
    private TableColumn<User_Service,Integer> tel_user_col;
    @FXML
    private TableColumn<User_Service,?> prop_serv;
    @FXML
    private TableView<User_Service> TableAfficheServ_User;
    @FXML
    private Button btn_valid_serv;
    @FXML
    private Button btn_profile;
    @FXML
    private TextField fieldSearch_Service;
    @FXML
    private TableColumn<User_Service,Integer> id_col_dem_serv;
    @FXML
    private TableColumn<User_Service,Integer> id_col_prop_serv;
    @FXML
    private TextField id_propr_Non_Visible;
   
 
    
    Connection c=DataBase.getInstance().getConnection();
    PreparedStatement pst;
    ResultSet res;
   
     int id=Admin.id_admin_static;
 //    public static int id_user_get=User.id_user_static_get;
     
     private ObservableList<Produit> data;
     private ObservableList data2;
     private ObservableList<User_Service> data3;
     public static int id_user_static_get;
     private ObservableList<Rec_User_Service> data4;

   //  private ObservableList<User> data3;
    /*  @FXML
    private TableColumn<?, ?> id_col_dem_serv;*/
    @FXML
    private TableView<Rec_User_Service> table_Rec_ServRec;
    @FXML
    private TableColumn<Rec_User_Service,Integer> id_Rec_user;
    @FXML
    private TableColumn<Rec_User_Service,String> nom_Rec;
    @FXML
    private TableColumn<Rec_User_Service,String> prnm_Rec;
    @FXML
    private TableColumn<Rec_User_Service,String> email_Rec;
    @FXML
    private TableColumn<Rec_User_Service,Integer> id_Serv_Rec;
    @FXML
    private TableColumn<Rec_User_Service,String> categ_Serv_Rec;
    @FXML
    private TableColumn<Rec_User_Service,Integer> id_rec_object;
    @FXML
    private TableColumn<Rec_User_Service,String> desc_rec;
    @FXML
    private TableColumn<Rec_User_Service,String> date_rec;
    @FXML
    private TableColumn<Rec_User_Service,String> rasion_rec;
    @FXML
    private TableColumn<Rec_User_Service,Integer> nb_Pts_Rec;
    @FXML
    private ProgressBar progress_bar;
    @FXML
    private Button envoieAvertissement;
    @FXML
    private TextField field_get_email_reclamant;
    
    // partie du gestion cadeaux
    @FXML
    private Tab ajouter_cadeaux;
    @FXML
    private TextField P_Bonus;
    @FXML
    private Button ajouter;
    @FXML
    private TextArea desc;
    @FXML
    private ImageView imageAjoutCadeaux;
    @FXML
    private Button btn_brows;
    @FXML
    private TextField img;
    @FXML
    private TableView<Cadeaux> tab2; // tableaux affichage lors de l'ajout dans la partie admin de gest de cadeaux
    @FXML
    private TableColumn<Cadeaux,Integer> id2;
    @FXML
    private TableColumn<Cadeaux,Integer> p2;
    @FXML
    private TableColumn<Cadeaux,String> d2;
    @FXML
    private Tab modifier_Cadeaux;
    @FXML
    private TableView<Cadeaux> tab; // tableaux affichage lors de la modification dans la partie admin de gest de cadeaux
    @FXML
    private TableColumn<Cadeaux,Integer> id1;
    @FXML
    private TableColumn<Cadeaux,Integer> p1;
    @FXML
    private TableColumn<Cadeaux,String> d1;
    @FXML
    private TextField P_Bonus2;
    @FXML
    private TextArea desc2;
    @FXML
    private Button modif;
    @FXML
    private Button supp;
    @FXML
    private TextField iddd;
     
    private ObservableList<Cadeaux> dataCadeaux;
    private FileChooser fileChooser;
    private File file; 
    private Image image;
    private FileInputStream fis;
    // fin partie du gestion cadeaux
    
    
    
   
     // API mail attributes
        static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
    
    @FXML
    private void LogoutAction(ActionEvent event) throws IOException {
        
                     alert.setTitle("Etat Deconnexion");
                     alert.setHeaderText("Deconnecter de l'Espace");
                     alert.setContentText("Merci d'etre l'Admin du All For Deal,Nous vous Souhaite la Bienvenu");
                     alert.showAndWait();
           //get reference to the button's stage
            stage=(Stage) btnLogout.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("authAdmin.fxml"));
             //create a new scene with root and set the stage// Creer un nouveau scène 
                 Scene scene=new Scene(root);
                 stage.setScene(scene);
                 stage.setTitle("Interface Adminsitrateur All For Deal");
                 stage.show();
       }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
       Alert alertUpdate;
       alertUpdate=new Alert(Alert.AlertType.INFORMATION);
       Stage primaryStage = null; 
      
       
        // récupération des données de l'admin depuis l'interface d'authentification
        
        String req="select * from admin where id_admin=?";
        
        try {
            Admin admin=new Admin();
              
             pst=c.prepareStatement(req);
             pst.setInt(1,id);
             // pst.setInt(1,ComboBox_Id.getSelectionModel().getSelectedItem());
             res=pst.executeQuery();
             
             if (res.next()){
                 
                 // champs profil admin
                 id_admin.setText(res.getString("id_admin"));
                 nom_root.setText(res.getString("nom_admin"));
                 prnm_root.setText(res.getString("prnm_admin"));
                 age_root.setText(res.getString("age_admin"));
                 field_sex_root.setText(res.getString("sexe_admin"));
                 email_root.setText(res.getString("email_admin"));
                 login_root.setText(res.getString("login_admin"));
                 pwd_root.setText(res.getString("pwd_admin"));
                 
                 // champs au dessus d'état admin
                 
                 txt_nom_admin.setText(res.getString("nom_admin"));
                 txt_prnm_admin.setText(res.getString("prnm_admin"));
             }
        } catch (SQLException ex) {
            Logger.getLogger(EspaceAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
                // affichage des demandes d'offres produits
             ShowObservableArrayFxListOffreProduit();
             // filtrage
             FiltredSearch();
        
       //// changement de validité du produit lorsqu'on clique sur le bouton "valider produit"
        TableAffiche.setOnMousePressed(e -> {
               
            String requete="select * from produit where id_Produit=? ";

            Produit produit=(Produit)TableAffiche.getSelectionModel().getSelectedItem();
            
            btn_valid_prod.setOnAction(ev-> {
                
                try {
                    String sql="update produit set valid_prd=1 where id_produit=?";
                    pst=c.prepareStatement(sql);
                 //   pst.setInt(1,produit.getValidite());
                    pst.setInt(1,produit.getId_Produit());
                    // pst.setInt(1,ComboBox_Id.getSelectionModel().getSelectedItem());
                    pst.executeUpdate();
                      TableAffiche.refresh();
                      ShowObservableArrayFxListOffreProduit();
                      FiltredSearch();
                    System.out.println("Mise à jour terminé avec succés ");
                    alertUpdate.setTitle("Validité");
                    alertUpdate.setHeaderText("Administrateur a validé le produit : "+ produit.getLibelle_prd()+" ,Numéro : "+ produit.getId_Produit());
                    alertUpdate.setContentText("Ce Produit a été validé avec succés");
                    alertUpdate.showAndWait();
                      
                } catch (SQLException ex) {
                    Logger.getLogger(EspaceAdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
               
        });
        
        
        
        
        
        // Affichage liste des offres services liées avec leurs propriétaires et avec recherche
        
        ShowObservableArrayFxListOffreService();
        FiltredSearchOffreServiceUser();
        
          //// changement de validité du Service lorsqu'on clique sur le bouton "valider Service"
        TableAfficheServ_User.setOnMouseClicked(e -> {
               
          // String requete="select * from offre_service where id_serv_prop=? ";
          String requete="select S.id_serv_prop,S.desc_serv_prop,S.date_serv_prop,S.categorie_serv_prop,S.valid_serv,U.id_user,U.nom_user,U.prenom_user,U.email_user,U.tel_user from offre_service S INNER JOIN utilisateur U on S.id_user_rel=U.id_user where id_serv_prop=? and id_user=?";
         
            User_Service offreUserService=(User_Service)TableAfficheServ_User.getSelectionModel().getSelectedItem();
             
            try {
                pst=c.prepareStatement(requete);
                pst.setInt(1,offreUserService.getId_Serv());
                pst.setInt(2,offreUserService.getIdUser());
                res=pst.executeQuery();
                
                  while (res.next()){ 
                 // remplissage des textField aprés selection dans le tableaux
                       id_propr_Non_Visible.setText(res.getString("id_user"));
                       id_user_static_get=Integer.parseInt(res.getString("id_user"));
//                      id_user_static_get=Integer.parseInt(id_propr_Non_Visible.toString());
                     }
           } catch (SQLException ex) {
               Logger.getLogger(EspaceAdminController.class.getName()).log(Level.SEVERE, null, ex);
           }
            btn_valid_serv.setOnAction(ev-> {
                
                try {
                    OffreService Offservice=new OffreService();
                    String sql="update offre_service set valid_serv=1 where id_serv_prop=?";
                    pst=c.prepareStatement(sql);
                 //   pst.setInt(1,produit.getValidite());
                    pst.setInt(1,offreUserService.getId_Serv());
                    // pst.setInt(1,ComboBox_Id.getSelectionModel().getSelectedItem());
                      
                    pst.executeUpdate();
                  
                   
                    TableAfficheServ_User.refresh();
                    ShowObservableArrayFxListOffreService();
                    FiltredSearchOffreServiceUser();
                   //   FiltredSearch();
                    System.out.println("Mise à jour terminé avec succés ");
                    alertUpdate.setTitle("Validité");
                    alertUpdate.setHeaderText("Administrateur a validé le Service du catégorie : "+ offreUserService.getCatgorie_Serv()+" ,Numéro : "+ offreUserService.getId_Serv());
                    alertUpdate.setContentText("Ce Produit a été validé avec succés");
                    alertUpdate.showAndWait();
                      
                } catch (SQLException ex) {
                    Logger.getLogger(EspaceAdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
               
        });
        
          // affichage du Tableaux Multiple : User Reclamant sur Service / Service Reclamé / la Reclamation
       showObservableArrayListServiceReclame_UserReclamant_Reclamation();
       
       // Partie du Gestion du Cadeaux dans la partie d'Administrateur
       
       ShowObservableArrayFxListCadeauxt();
       
        fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Files","*txt"),
            new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.gif"),
            new FileChooser.ExtensionFilter("Audio Files","*wav","*.mp3","*.aac"),
            new FileChooser.ExtensionFilter("All Files","*.*")
        );
        
           btn_brows.setOnAction(e -> {
        file = fileChooser.showOpenDialog(primaryStage);
        // Single File Selection
        if (file!=null){
               // desktop.open(file); => c'était pour le test de "browse" files 
			   // TextField_Path ==> TextField eli yt7at fih chamin ta3 l foto , chouf f déclaration mte3k chnwa esmou mch kifkif ena w yek fl code
			   // imageView_Field ==> hia instance ta3 imageView , kifkif chouf selon ton code chnwa esmha 
			   
                img.setText(file.getAbsolutePath());
            try {
                image = new Image(file.toURI().toURL().toString(),200,150,true,true);//Path,prefWidth,prefHeight,preserveRatio,Smooth
                BufferedImage bufferedImage=ImageIO.read(file);
               imageAjoutCadeaux.setImage(image);
                
            } catch (MalformedURLException ex) {
                Logger.getLogger(InscriUserController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(InscriUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            
                imageAjoutCadeaux = new ImageView(image);
                imageAjoutCadeaux.setFitWidth(200);
                imageAjoutCadeaux.setFitHeight(150);
                imageAjoutCadeaux.setVisible(true);
                imageAjoutCadeaux.setSmooth(true);
                imageAjoutCadeaux.setPreserveRatio(true);  
                
               
                BorderPane.setAlignment(imageAjoutCadeaux,Pos.BOTTOM_RIGHT);
         }
        });
           
         tab.setOnMouseClicked(e -> {
        
           String reqt="select * from cadeaux where id_cadeau=? ";
            
           try {
                Cadeaux cadeaux=(Cadeaux)tab.getSelectionModel().getSelectedItem();
                pst=c.prepareStatement(reqt);
                pst.setInt(1,cadeaux.getId_cad());
                res=pst.executeQuery();

                while (res.next()){
                P_Bonus2.setText(res.getString("nbr_pts_bonus_cad"));
                desc2.setText(res.getString("descrp_cad"));
                //img2.setImage((Image) res.getBlob("Image"));
                iddd.setText(res.getString("id_cadeau"));

             
             }
            } catch (SQLException ex) {
                Logger.getLogger(EspaceAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
            });  
        /// fin gestion cadeaux
       
       // getEmailReclamant();
        
        ///
          
       /*    table_Rec_ServRec.setOnMouseClicked(e -> {
       
       String requete="select u.id_user,u.nom_user,u.prenom_user,u.email_user,s.id_serv_prop,s.categorie_serv_prop,r.id_rec,r.desc_rec,r.date_rec,r.raison,r.nbRec_Serv from utilisateur u INNER JOIN reclamations r ON u.id_user=r.id_user_rec INNER JOIN offre_service s ON r.id_service_rec=s.id_serv_prop where u.id_user=? and s.id_serv_prop=? and r.id_rec=?";
       Rec_User_Service rec=new Rec_User_Service();
       rec= (Rec_User_Service) table_Rec_ServRec.getSelectionModel().getSelectedItems();
       
       try {
       
       pst=c.prepareStatement(requete);
       pst.setInt(1,rec.getIdUser());
       pst.setInt(2,rec.getId_Serv());
       pst.setInt(3,rec.getId_rec());
       
       res=pst.executeQuery();
       
       while(res.next()){
       field_get_email_reclamant.setText(res.getString("id_user"));
       }
       } catch (SQLException ex) {
       Logger.getLogger(EspaceAdminController.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       
       });
       */
    }

    
        // ma méthode
    // Recherche Filtrée
    public void FiltredSearch(){
            // Filtered List
        FilteredList<Produit> filteredProduitListData = new FilteredList<>(data,e->true);
        
        field_search_offre_prod.setOnKeyReleased(e -> {
                field_search_offre_prod.textProperty().addListener((observableValue,oldValue,newValue) -> {
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
    
    public void ShowObservableArrayFxListOffreProduit(){
             
        
          /// Affichage des données dans un observableArrayList()
        
        try {
            
            data=FXCollections.observableArrayList();
            String sql="select * from produit";
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
            valid_col.setCellValueFactory(new PropertyValueFactory<Produit,Integer>("validite"));
            //TableAffiche.setItems(null);
            TableAffiche.setItems(data);
            TableAffiche.setVisible(true);
        } catch (SQLException ex) {
            System.out.println("Error on building Data");
            Logger.getLogger(AfficherProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }      
    } 
    
    @FXML
    private void ModifRoot(ActionEvent event) {
        
        Alert alertOk,alertErr;
        alertOk=new Alert(Alert.AlertType.INFORMATION);
        
        int age_admin_root=Integer.parseInt(age_root.getText());
        int id_nonVisible_Modif_root=Integer.parseInt(id_admin.getText());
       // String typ_sex_root=String.valueOf(sex_rooot.getSelectedToggle().selectedProperty().getName().toString());
        String item_sex_root=String.valueOf(sexxx_roooot.getSelectionModel().getSelectedItem());
        Admin admin = new Admin();
        
        
        admin.setNom_Admin(nom_root.getText());
        admin.setPrnm_Admin(prnm_root.getText());
        admin.setAge_Admin(age_admin_root);
        admin.setSex_Admin(item_sex_root);
        admin.setEmail_Admin(email_root.getText());
        admin.setLogin_Admin(login_root.getText());
        admin.setPwd_Admin(pwd_root.getText());
        admin.setId_Admin(id);
        
        IAdminDAO iAdmin= new AdminDAO();
        iAdmin.updateProfileAdmin(admin);
        
        // récupération des données de l'admin depuis l'interface d'authentification
        
        String req="select * from admin where id_admin=?";
        
        try {
             pst=c.prepareStatement(req);
             pst.setInt(1,id);
             // pst.setInt(1,ComboBox_Id.getSelectionModel().getSelectedItem());
             res=pst.executeQuery();
             
             if (res.next()){
                 // champs au dessus d'état admin
                 
                 txt_nom_admin.setText(res.getString("nom_admin"));
                 txt_prnm_admin.setText(res.getString("prnm_admin"));
             }
        } catch (SQLException ex) {
            Logger.getLogger(EspaceAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        alertOk.setTitle("Etat Mise A jour");
        alertOk.setHeaderText("Modifier Compte Administrateur All For Deal");
        alertOk.setContentText("Mise à jour du Compte Administrateur a été éffectué avec succés");
        alertOk.showAndWait();
    }

    public void ShowObservableArrayFxListOffreService(){
         /// Affichage des données dans un observableArrayList()
        
        try {
            
            data3=FXCollections.observableArrayList();
        //    data3=FXCollections.observableArrayList();
           String sql="select S.id_serv_prop,S.desc_serv_prop,S.date_serv_prop,S.categorie_serv_prop,S.valid_serv,U.id_user,U.nom_user,U.prenom_user,U.email_user,U.tel_user from offre_service S INNER JOIN utilisateur U on S.id_user_rel=U.id_user";
          // String sql="select desc_serv_prop,date_serv_prop,categorie_serv_prop,valid_serv from offre_service";
            res=c.createStatement().executeQuery(sql);
            while (res.next()){
                // lahné f res.getX("Attribut eli mwjoud fl base de données selon type eli 7atetou enty")
              //  data2.addAll(new OffreService(res.getString("desc_serv_prop"),res.getString("date_serv_prop"),res.getString("categorie_serv_prop"),res.getInt("valid_serv")),new User(res.getString("nom_user"),res.getString("prenom_user"),res.getString("email_user"),res.getInt("tel_user")));
            //    data2.addAll(new OffreService(res.getString("desc_serv_prop"),res.getString("date_serv_prop"),res.getString("categorie_serv_prop"),res.getInt("valid_serv")));
             //   data3.addAll(new User(res.getString("nom_user"),res.getString("prenom_user"),res.getString("email_user"),res.getInt("tel_user")));
               data3.addAll(new User_Service(res.getInt("id_serv_prop"),res.getString("desc_serv_prop"),res.getString("date_serv_prop"),res.getString("categorie_serv_prop"),res.getInt("valid_serv"),res.getInt("id_user"),res.getString("nom_user"),res.getString("prenom_user"),res.getString("email_user"),res.getInt("tel_user")));

            } 
            // lahné (" ") attribut eli mwjoud fl classe mte3k
            // Id_Col : hia fx:id ta3 colonne eli mssaméha fl sceneBuilder
            // PropertyValueFactory<EsmCLASSE,TypeAttribut fl classe>
            // w tanséch déclarehm l fou9 ba3d m t3ml "make controller" fl fichier.fxml
            //exmple :  @FXML private TableColumn<Produit,String> Categ_Col;
            // w aussi ta3ti type ta3 tableView l fou9 
            // exmple : @FXML private TableView<EsmClasseMte3k> TableAffiche;
            
            id_col_dem_serv.setCellValueFactory(new PropertyValueFactory<User_Service,Integer>("id_Serv"));
            desc_serv_col.setCellValueFactory(new PropertyValueFactory<User_Service,String>("desc_Serv"));
            Date_serv_col.setCellValueFactory(new PropertyValueFactory<User_Service,String>("date_Serv"));
            Categ_serv_col.setCellValueFactory(new PropertyValueFactory<User_Service,String>("catgorie_Serv"));
            valid_serv_col.setCellValueFactory(new PropertyValueFactory<User_Service,Integer>("valid_Serv"));
            
            id_col_prop_serv.setCellValueFactory(new PropertyValueFactory<User_Service,Integer>("idUser"));
            nom_user_col.setCellValueFactory(new PropertyValueFactory<User_Service,String>("nomUser"));
            prenom_user_col.setCellValueFactory(new PropertyValueFactory<User_Service,String>("prneomUser"));
            email_user_col.setCellValueFactory(new PropertyValueFactory<User_Service,String>("mailUser"));
            tel_user_col.setCellValueFactory(new PropertyValueFactory<User_Service,Integer>("telUser"));
            //TableAffiche.setItems(null);
            TableAfficheServ_User.setEditable(true);
            TableAfficheServ_User.setRowFactory(null);
            TableAfficheServ_User.setItems(data3);
            TableAfficheServ_User.setVisible(true);
        } catch (SQLException ex) {
            System.out.println("Error on building Data");
            Logger.getLogger(AfficherProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    @FXML
    private void ValiderProduitAction(ActionEvent event) {
        
        
           
    }

    @FXML
    private void setEditableAction(ActionEvent event) {
        
                 nom_root.setEditable(true);
                 prnm_root.setEditable(true);
                 age_root.setEditable(true);
             //    sexe_root.setEditable(true);
                 email_root.setEditable(true);
                 login_root.setEditable(true);
                 pwd_root.setEditable(true);
    }

    
         // ma méthode
    // Recherche Filtrée
    public void FiltredSearchOffreServiceUser(){
            // Filtered List
        FilteredList<User_Service> filteredServiceListData = new FilteredList<>(data3,e->true);
        
        fieldSearch_Service.setOnKeyReleased(e -> {
                fieldSearch_Service.textProperty().addListener((observableValue,oldValue,newValue) -> {
                filteredServiceListData.setPredicate((Predicate<? super User_Service>) user_serv -> {
                if (newValue==null || newValue.isEmpty()){
                return true;
                }
                String toLowerCaseNewValue = newValue.toLowerCase();
                
                /*  if (produit.getId_Produit()==newValue){
                return true;
                }*/ 
                if (user_serv.getCatgorie_Serv().toLowerCase().contains(toLowerCaseNewValue)){
                    return true;
                }
                return false;
                   });
              });
        });
        
         //Sorted List
              SortedList<User_Service>  sortedServiceListData = new SortedList<>(filteredServiceListData);
              
              sortedServiceListData.comparatorProperty().bind(TableAfficheServ_User.comparatorProperty());
              TableAfficheServ_User.setItems(sortedServiceListData);
    }

    @FXML
    private void versProfileUser(ActionEvent event) throws IOException {
        
        Stage stage = null ;
        Parent root = null ;
        
        if (event.getSource()==btn_profile){
            
            // appel au méthode déclaré au dessus
            OnSelectGetPropertyUserProfile();
             //get reference to the button's stage
            stage=(Stage) btn_profile.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("ProfileUser.fxml"));
            
        }
        //create a new scene with root and set the stage// Creer un nouveau scène 
         Scene scene=new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Interface Ajout Offres Produits");
         stage.show();
         
    }
    
    private void OnSelectGetPropertyUserProfile(){
           //// changement de validité du Service lorsqu'on clique sur le bouton "valider Service"
        TableAfficheServ_User.setOnMouseClicked(e -> {
               
          // String requete="select * from offre_service where id_serv_prop=? ";
          String requete="select S.id_serv_prop,S.desc_serv_prop,S.date_serv_prop,S.categorie_serv_prop,S.valid_serv,U.id_user,U.nom_user,U.prenom_user,U.email_user,U.tel_user from offre_service S INNER JOIN utilisateur U on S.id_user_rel=U.id_user where id_serv_prop=? and id_user=?";
         
            User_Service offreUserService=(User_Service)TableAfficheServ_User.getSelectionModel().getSelectedItem();
       //      User user=new User();
            try {
                pst=c.prepareStatement(requete);
                pst.setInt(1,offreUserService.getId_Serv());
                pst.setInt(2,offreUserService.getIdUser());
                res=pst.executeQuery();
                
                  while (res.next()){ 
                 // remplissage des textField aprés selection dans le tableaux
                      id_propr_Non_Visible.setText(res.getString("id_user"));
                      // id_user_static_get=Integer.parseInt(res.getString("id_user"));
                      id_user_static_get=Integer.parseInt(id_propr_Non_Visible.toString());
                     }
           } catch (SQLException ex) {
               Logger.getLogger(EspaceAdminController.class.getName()).log(Level.SEVERE, null, ex);
           }
            btn_valid_serv.setOnAction(ev-> {
                
                try {
                    OffreService Offservice=new OffreService();
                    String sql="update offre_service set valid_serv=1 where id_serv_prop=?";
                    pst=c.prepareStatement(sql);
                 //   pst.setInt(1,produit.getValidite());
                    pst.setInt(1,offreUserService.getId_Serv());
                    // pst.setInt(1,ComboBox_Id.getSelectionModel().getSelectedItem());
                      
                    pst.executeUpdate();
                  
                   
                    TableAfficheServ_User.refresh();
                    ShowObservableArrayFxListOffreService();
                    FiltredSearchOffreServiceUser();
                      
                } catch (SQLException ex) {
                    Logger.getLogger(EspaceAdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
               
        });
    }
    
    /*public void getEmailReclamant(){
    
    
    table_Rec_ServRec.setOnMouseClicked(e -> {
    
    String sql="select u.id_user,u.nom_user,u.prenom_user,u.email_user,s.id_serv_prop,s.categorie_serv_prop,r.id_rec,r.desc_rec,r.date_rec,r.raison,r.nbRec_Serv from utilisateur u INNER JOIN reclamations r ON u.id_user=r.id_user_rec INNER JOIN offre_service s ON r.id_service_rec=s.id_serv_prop where u.id_user=? and s.id_serv_prop=? and r.id_rec=?";
    Rec_User_Service rec=(Rec_User_Service)table_Rec_ServRec.getSelectionModel().getSelectedItems();
    
    try {
    
    pst=c.prepareStatement(sql);
    pst.setInt(1,rec.getIdUser());
    pst.setInt(2,rec.getId_Serv());
    pst.setInt(3,rec.getId_rec());
    
    res=pst.executeQuery();
    
    while(res.next()){
    field_get_email_reclamant.setText(res.getString("id_user"));
    }
    } catch (SQLException ex) {
    Logger.getLogger(EspaceAdminController.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    
    });
    
    
    }*/
    @FXML
      public void getMail(){
          field_get_email_reclamant.setText(table_Rec_ServRec.getSelectionModel().getSelectedItem().getMailUser());
      }
    
    private void showObservableArrayListServiceReclame_UserReclamant_Reclamation(){
          
        try {
            
            Rec_User_Service rec=new Rec_User_Service();
            
            data4=FXCollections.observableArrayList();
        //    data3=FXCollections.observableArrayList();
           String sql="select u.id_user,u.nom_user,u.prenom_user,u.email_user,s.id_serv_prop,s.categorie_serv_prop,r.id_rec,r.desc_rec,r.date_rec,r.raison,r.nbRec_Serv from utilisateur u INNER JOIN reclamations r ON u.id_user=r.id_user_rec INNER JOIN offre_service s ON r.id_service_rec=s.id_serv_prop";         
            //String sql="select * from reclamations";
           //  String sql="select desc_serv_prop,date_serv_prop,categorie_serv_prop,valid_serv from offre_service";
           /* pst=c.prepareStatement(sql);
           pst.setInt(1,rec.getIdUser());
           pst.setInt(2,rec.getId_Serv());
           pst.setInt(3,rec.getId_rec());
           res=pst.executeQuery();*/
            res=c.createStatement().executeQuery(sql);
            while (res.next()){
                // lahné f res.getX("Attribut eli mwjoud fl base de données selon type eli 7atetou enty")
              //  data2.addAll(new OffreService(res.getString("desc_serv_prop"),res.getString("date_serv_prop"),res.getString("categorie_serv_prop"),res.getInt("valid_serv")),new User(res.getString("nom_user"),res.getString("prenom_user"),res.getString("email_user"),res.getInt("tel_user")));
            //    data2.addAll(new OffreService(res.getString("desc_serv_prop"),res.getString("date_serv_prop"),res.getString("categorie_serv_prop"),res.getInt("valid_serv")));
             //   data3.addAll(new User(res.getString("nom_user"),res.getString("prenom_user"),res.getString("email_user"),res.getInt("tel_user")));
               data4.addAll(new Rec_User_Service(res.getInt("id_user"),res.getString("nom_user"),res.getString("prenom_user"),res.getString("email_user"),res.getInt("id_serv_prop"),res.getString("categorie_serv_prop"),res.getInt("id_rec"),res.getString("desc_rec"),res.getString("date_rec"),res.getString("raison"),res.getInt("nbRec_Serv")));

            }
           
            // tabulation utilisateur reclamant au service
            id_Rec_user.setCellValueFactory(new PropertyValueFactory<Rec_User_Service,Integer>("idUser"));
            nom_Rec.setCellValueFactory(new PropertyValueFactory<Rec_User_Service,String>("nomUser"));
            prnm_Rec.setCellValueFactory(new PropertyValueFactory<Rec_User_Service,String>("prneomUser"));
            email_Rec.setCellValueFactory(new PropertyValueFactory<Rec_User_Service,String>("mailUser"));
            // tabulation service réclamé 
            id_Serv_Rec.setCellValueFactory(new PropertyValueFactory<Rec_User_Service,Integer>("id_Serv"));
            categ_Serv_Rec.setCellValueFactory(new PropertyValueFactory<Rec_User_Service,String>("catgorie_Serv"));
            // tabulation Reclamation
            id_rec_object.setCellValueFactory(new PropertyValueFactory<Rec_User_Service,Integer>("id_rec"));
            desc_rec.setCellValueFactory(new PropertyValueFactory<Rec_User_Service,String>("desc_rec"));
            date_rec.setCellValueFactory(new PropertyValueFactory<Rec_User_Service,String>("date_rec"));
            rasion_rec.setCellValueFactory(new PropertyValueFactory<Rec_User_Service,String>("raisonRec"));
            nb_Pts_Rec.setCellValueFactory(new PropertyValueFactory<Rec_User_Service,Integer>("nbre_rec"));

            //TableAffiche.setItems(null);
            table_Rec_ServRec.setEditable(true);
            table_Rec_ServRec.setItems(data4);
            table_Rec_ServRec.setVisible(true);
            
    }   catch (SQLException ex) {
            Logger.getLogger(EspaceAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
     }

   // partie Gestion Cadeaux
    
         /// Affichage des données dans un observableArrayList()
  
   public void ShowObservableArrayFxListCadeauxt(){
        
        try {
            
            dataCadeaux=FXCollections.observableArrayList();
            String sql="select * from cadeaux";
            res=c.createStatement().executeQuery(sql);
            while (res.next()){
                // lahné f res.getX("Attribut eli mwjoud fl base de données selon type eli 7atetou enty")
                dataCadeaux.add(new Cadeaux(res.getInt("id_cadeau"),res.getInt("nbr_pts_bonus_cad"),res.getString("descrp_cad")));
            } 
           
            id1.setCellValueFactory(new PropertyValueFactory<>("id_cad"));
            p1.setCellValueFactory(new PropertyValueFactory<>("pts_bonus_cad"));
            d1.setCellValueFactory(new PropertyValueFactory<>("description_cad"));
            
            
            id2.setCellValueFactory(new PropertyValueFactory<>("id_cad"));
            p2.setCellValueFactory(new PropertyValueFactory<>("pts_bonus_cad"));
            d2.setCellValueFactory(new PropertyValueFactory<>("description_cad"));
            
            //TableAffiche.setItems(null);
            tab.setItems(dataCadeaux);
            tab.setVisible(true);
            tab2.setItems(dataCadeaux);
            tab2.setVisible(true);
            
        } catch (SQLException ex) {
            System.out.println("Error on building Data");
            Logger.getLogger(EspaceAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    
    @FXML
    private void Ajouter_Cadeaux(ActionEvent event) {
        
        Alert alertOk,alertErr;
        alertOk=new Alert(Alert.AlertType.INFORMATION);
        alertErr=new Alert(Alert.AlertType.WARNING);
        
         try {
            fis=new FileInputStream(file);

             try {
                 image=new Image(file.toURI().toURL().toString(),200,150,true,true);
             } catch (MalformedURLException ex) {
                 Logger.getLogger(EspaceAdminController.class.getName()).log(Level.SEVERE, null, ex);
             }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InscriUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            BufferedImage bif=ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(InscriUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //c.setImage(image);
        
        int pntbonus=Integer.parseInt(P_Bonus.getText());
        
        Cadeaux cado = new Cadeaux();
        
       
        
        cado.setPts_bonus_cad(pntbonus);
        cado.setDescription_cad(desc.getText());
        cado.setImage((InputStream)fis);
                //user.setPhotoUser((InputStream)fis);
        
        CadeauxDAO cadoDAO = new CadeauxDAO();
        if(cadoDAO.add(cado)){
            
          alertOk.setTitle("Etat Ajout");
            alertOk.setHeaderText("Ajout Cadeaux");
            alertOk.setContentText("Ajout a été éffectué avec succés");
            alertOk.showAndWait();
         
            //Clear All Fields After Adding Successfuly
            
          
        }else if (!cadoDAO.add(cado)){
            //Dialogs.showWarningDialog(stage,"Ajout a été échoué","Information Dialog", "Etat Ajout Offre Produit");
            alertErr.setTitle("Etat Ajout");
            alertErr.setHeaderText("Ajout Cadeaux");
            alertErr.setContentText("Ajout a été échoué");
            alertErr.showAndWait();
            
        }
        
        
        
                tab.refresh();
                tab2.refresh();
                ShowObservableArrayFxListCadeauxt();
                P_Bonus.setText("");
                desc.setText("");
                img.setText("");
                imageAjoutCadeaux.setImage(null);
    }
    

    @FXML
    private void Modifier_Cadeaux(ActionEvent event) {
         
        Alert alertOk,alertErr;
        alertOk=new Alert(Alert.AlertType.INFORMATION);
        alertErr=new Alert(Alert.AlertType.WARNING);
        
        
        Stage stage = null;
        int pnt=Integer.parseInt(P_Bonus2.getText());
        
        int id_nonVisible_Modif=Integer.parseInt(iddd.getText());
        Cadeaux cadeaux = new Cadeaux();
        
        cadeaux.setPts_bonus_cad(pnt);
        cadeaux.setDescription_cad(desc2.getText());
        cadeaux.setId_cad(id_nonVisible_Modif);
        
        CadeauxDAO cadDAO=new CadeauxDAO();
        if(cadDAO.Modifier(cadeaux)){
            alertOk.setTitle("Etat Modification");
            alertOk.setHeaderText("Modification Cadeaux");
            alertOk.setContentText("Modification a été éffectué avec succés");
            alertOk.showAndWait();
         
            //Clear All Fields After Adding Successfuly
            
          
        }else if (!cadDAO.Modifier(cadeaux)){
            //Dialogs.showWarningDialog(stage,"Ajout a été échoué","Information Dialog", "Etat Ajout Offre Produit");
            alertErr.setTitle("Etat Modification");
            alertErr.setHeaderText("Modification Cadeaux");
            alertErr.setContentText("Modification a été échoué");
            alertErr.showAndWait();
            
        }
        
        tab.refresh();
        tab2.refresh();
     
        ShowObservableArrayFxListCadeauxt();
        
        P_Bonus2.setText("");
        desc2.setText("");
        
    }

    @FXML
    private void Supprimer_Cadeaux(ActionEvent event) {
       
        Alert alertOk,alertErr;
        alertOk=new Alert(Alert.AlertType.INFORMATION);
        alertErr=new Alert(Alert.AlertType.WARNING);
        
        Stage stage = null;
        Cadeaux cadeaux=new Cadeaux();
        int id=Integer.parseInt(iddd.getText());
        CadeauxDAO pr=new CadeauxDAO();
        if(pr.supprimer(id)){
            alertOk.setTitle("Etat Suppression");
            alertOk.setHeaderText("Suppression Cadeaux");
            alertOk.setContentText("Suppression a été éffectué avec succés");
            alertOk.showAndWait();
         
            //Clear All Fields After Adding Successfuly
            
          
        }else if (!pr.supprimer(id)){
            //Dialogs.showWarningDialog(stage,"Ajout a été échoué","Information Dialog", "Etat Ajout Offre Produit");
            alertErr.setTitle("Etat Suppressiob");
            alertErr.setHeaderText("Suppression Cadeaux");
            alertErr.setContentText("Suppression a été échoué");
            alertErr.showAndWait();
            
        }    
        
        
                tab.refresh();
                tab2.refresh();
                ShowObservableArrayFxListCadeauxt();
        P_Bonus2.setText("");
        desc2.setText("");

    }

    
    @FXML
    private void AdvertissementAction(ActionEvent event) throws MessagingException {
        
                generateAndSendEmailAvertissement();
                progress_bar.setProgress(100); 
    }

      public  void generateAndSendEmailAvertissement() throws AddressException, MessagingException {
 
          Alert alerOkMail;
          alerOkMail=new Alert(Alert.AlertType.INFORMATION);
		// Step1
		System.out.println("\n 1st ===> setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		System.out.println("Mail Server Properties have been setup successfully..");
 
		// Step2
		System.out.println("\n\n 2nd ===> get Mail Session..");
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(field_get_email_reclamant.getText()));
		generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("omar.ghlila@esprit.tn"));
		generateMailMessage.setSubject("Avertissement");
		String emailBody = "Vous etes récalmés par un de nos chers clients,ceçi est un premier avertissement,à la 3éme reprise vous allez etre banis" + "<br><br> Cordialement <br>Administrateur All For Deal Walid Zhani";
		generateMailMessage.setContent(emailBody, "text/html");
		System.out.println("Mail Session has been created successfully..");
 
		// Step3
		System.out.println("\n\n 3rd ===> Get Session and Send mail");
		Transport transport = getMailSession.getTransport("smtp");
 
		// Enter your correct gmail UserID and Password
		// if you have 2FA enabled then provide App Specific Password
		transport.connect("smtp.gmail.com", "validos11@gmail.com", "googleTech14isTheBest");
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
                   
                alerOkMail.setTitle("Email Etat");
                alerOkMail.setHeaderText("Succés d'envoie");
                alerOkMail.setContentText("Email a été bien envoyé au destinataire : "+field_get_email_reclamant.getText());
                alerOkMail.showAndWait();
         
		transport.close();
	}
   
    
    
    
    @FXML
    private void rechercher_img(ActionEvent event) {
    }

   
    

}
    

