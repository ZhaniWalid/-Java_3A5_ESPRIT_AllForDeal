
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
import javafx.fxml.Initializable;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author eib
 */
public class demandeController implements Initializable{
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
    private ImageView add ;
    private ImageView iv ;
    private Image im ;
    @FXML
    private ImageView confi ;
    private ImageView ivv ;
    private Image imm ;
    
     @FXML
    private ImageView cherch ;
    private ImageView iv1 ;
    private Image im1 ;
    
    @FXML
    private ImageView pou;
    private ImageView iv2 ;
    private Image im2 ;
    
    @FXML
    private ImageView cahier;
    private ImageView iv3 ;
    private Image im3 ;
    
    
    
     @FXML
    private ImageView gmap;
    private ImageView iv4 ;
    private Image im4 ;
    

    private ObservableList<demandeService> data=FXCollections.observableArrayList();
    demandeService dataSelected=new demandeService();
    Connection c;
    PreparedStatement pst;
    ResultSet res;
    final ObservableList options=FXCollections.observableArrayList();
    final ObservableList optionsCatgorie=FXCollections.observableArrayList();
    final ObservableList optionsLieu=FXCollections.observableArrayList();
    @FXML
    private Button btnSuppDemande;
    @FXML
    private TextField text_Supp;
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
    private TextField labelSearchField;
    @FXML
    private AnchorPane map ;
    //private ImageView confirmer;
    @FXML
    private AnchorPane poub ;
     @FXML
    private TableColumn<demandeService, Integer> col_validite ;
    @FXML
    private Button maping;
    
    
      
    public demandeController() throws IOException {
        this.c = DataBase.getInstance().getConnection();
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
        
        
     //   tableAffiche.getItems().clear();
        try {
            
            data=FXCollections.observableArrayList();
            String sql="select * from demande_service where id_user_demande="+idUser;
            res=c.createStatement().executeQuery(sql);
            while (res.next()){
                data.add(new demandeService(res.getInt("id"),res.getString("objet"),res.getString("description"),res.getString("cathegorie"),res.getString("lieu"),res.getString("date"),res.getInt("valid_serv_dem"),res.getInt("id_user_demande")));
            } 
          
            

            
            //TableAffiche.setItems(null);
            tableAffiche.setItems(data);
            tableAffiche.setVisible(true);
        } catch (SQLException ex) {
            System.out.println("Error on building Data");
            Logger.getLogger(demandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

            tableAffiche.setItems(data);
            tableAffiche.setVisible(true);
            tableAffiche.refresh();

        
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        FiltredSearch();
        
          String path = new File("src/TDC/esprit/ventePrivee/images/ajouter.png").getAbsolutePath();
        im = new Image(new File(path).toURI().toString());
        iv = new ImageView(im);
        add.setImage(im);
        
        
        
       String path1 = new File("src/TDC/esprit/ventePrivee/images/confirm.png").getAbsolutePath();
       
        imm = new Image(new File(path1).toURI().toString());
        ivv = new ImageView(imm);
        confi.setImage(imm);
        
        
        String path2 = new File("src/TDC/esprit/ventePrivee/images/recherche.png").getAbsolutePath();
       
        im1 = new Image(new File(path2).toURI().toString());
        iv1 = new ImageView(im1);
        cherch.setImage(im1);
        
        
        
        String path5 = new File("src/TDC/esprit/ventePrivee/images/gmap.png").getAbsolutePath();
       
        im4 = new Image(new File(path5).toURI().toString());
        iv4 = new ImageView(im4);
        gmap.setImage(im4);
        
        
        
        
        String path3 = new File("src/TDC/esprit/ventePrivee/images/deleteCategory.png").getAbsolutePath();
       
        im2 = new Image(new File(path3).toURI().toString());
        iv2 = new ImageView(im2);
        pou.setImage(im2);
        
         String path4 = new File("src/TDC/esprit/ventePrivee/images/editerCategory.png").getAbsolutePath();
       
        im3 = new Image(new File(path4).toURI().toString());
        iv3 = new ImageView(im3);
        cahier.setImage(im3);
        
        
        
        
        try {
            
            data=FXCollections.observableArrayList();
            String sql="select * from demande_service where id_user_demande="+idUser;
            res=c.createStatement().executeQuery(sql);
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
        
        
        
        ////////////////////
        
         
         /*  try {
            String req="select id from demandeservice";
            pst=c.prepareStatement(req);
            ResultSet res=pst.executeQuery();
            while (res.next()){
                options.add(res.getInt("id"));
            }
            id_box.setItems(options);
        } catch (SQLException ex) {
            Logger.getLogger(demandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        id_box.setOnAction(e -> {
        
            String req="select * from demandeservice where id=? ";
            try {
                pst=c.prepareStatement(req);
                pst.setInt(1,id_box.getSelectionModel().getSelectedItem());
                res=pst.executeQuery();
                
                while (res.next()){
                obj_mod.setText(res.getString("objet"));
                desc_modif.setText(res.getString("description"));
                optionsCatgorie.add(res.getString("cathegorie"));
                categorie_modif.getSelectionModel().getSelectedItem();
                optionsLieu.add(res.getString("lieu"));
                lieu_modif.getSelectionModel().getSelectedItem();
                date_mdoif.setText(res.getString("date"));
               // Text_Categorie.setItems(optionsCatgorie);
            }
            } catch (SQLException ex) {
                Logger.getLogger(demandeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });*/
        
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
          try {
            
            data=FXCollections.observableArrayList();
            String sql="select * from demande_service where id_user_demande="+idUser;
            res=c.createStatement().executeQuery(sql);
            while (res.next()){
                data.add(new demandeService(res.getInt("id"),res.getString("objet"),res.getString("description"),res.getString("cathegorie"),res.getString("lieu"),res.getString("date") ,res.getInt("valid_serv_dem"),res.getInt("id_user_demande")));
            } 
          
            

            
            //TableAffiche.setItems(null);
            tableAffiche.setItems(data);
            tableAffiche.setVisible(true);
        } catch (SQLException ex) {
            System.out.println("Error on building Data");
            Logger.getLogger(demandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

            tableAffiche.setItems(data);
            tableAffiche.setVisible(true);
            tableAffiche.refresh();
    }
    
    @FXML
    public void afficher_map(){
        Googlemap g=new Googlemap();
        g.start(new Stage());
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
        
    }
   
    @FXML
    public void remplir()
    {
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
    private void SuppDemande(MouseEvent event) {
        
    }
    
}
