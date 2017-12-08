/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDC.esprit.ventePrivee.GUI;


import TDC.esprit.ventePrivee.entities.demandeService;
import TDC.esprit.ventePrivee.technique.DataBase;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static jdk.nashorn.internal.runtime.Debug.id;


public class DemandesPublicController implements Initializable{
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
    private TextField id_use_inv;
    @FXML
    private TableColumn<demandeService,Integer> col_id_user;
    
     
    

    private ObservableList<demandeService> data=FXCollections.observableArrayList();
    demandeService dataSelected=new demandeService();
    Connection c;
    PreparedStatement pst;
    ResultSet res;
    final ObservableList options=FXCollections.observableArrayList();
    final ObservableList optionsCatgorie=FXCollections.observableArrayList();
    final ObservableList optionsLieu=FXCollections.observableArrayList();
    
    @FXML
    private AnchorPane table;
    @FXML
    private TableView<demandeService> tableAffiche2;
    @FXML
    private Button consulter_id;
    @FXML
    private TextField Categorie;
    
    public static int idDemServPublicStatic;
    public static int idUserDemServPublicStatic;
    @FXML
    private TableColumn<demandeService,Integer> col_id_demServ;
    @FXML
    private TextField id_dem_serv_inv;
    @FXML
    private Button btn_retour_menu_user;
  
      
    public DemandesPublicController() throws IOException {
        this.c = DataBase.getInstance().getConnection();
    }
    
    {
        
    }
     @Override
    public void initialize(URL location, ResourceBundle resources) {
        
     
        
       
        
        
        ShowObservableArrayFxListDemandes();
        FiltredSearch();
    
           try {
        
        data=FXCollections.observableArrayList();
        String sql="select * from demande_service";
        res=c.createStatement().executeQuery(sql);
        while (res.next()){
        data.add(new demandeService(res.getInt("id"),res.getString("objet"),res.getString("description"),res.getString("cathegorie"),res.getString("lieu"),res.getString("date"),res.getInt("id_user_demande")));
        }
        
        
        
        col_id_demServ.setCellValueFactory(new PropertyValueFactory<demandeService,Integer>("id"));
        col_obj.setCellValueFactory(new PropertyValueFactory<demandeService,String>("objet"));
        col_description.setCellValueFactory(new PropertyValueFactory<demandeService,String>("description"));
        col_categorie.setCellValueFactory(new PropertyValueFactory<demandeService,String>("cathegorie"));
        col_lieu.setCellValueFactory(new PropertyValueFactory<demandeService,String>("lieu"));
        col_date.setCellValueFactory(new PropertyValueFactory<demandeService,String>("date"));
        col_id_user.setCellValueFactory(new PropertyValueFactory<demandeService,Integer>("idUserDemServ"));

        
        
        //TableAffiche.setItems(null);
        tableAffiche2.setItems(data);
        tableAffiche2.setVisible(true);
        } catch (SQLException ex) {
        System.out.println("Error on building Data");
        Logger.getLogger(DemandesPublicController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        onSelectedGetPropertyDemandeId();
        
   
}

    private void consulterDemande(ActionEvent event) throws IOException {
        Stage stage = null ;
        Parent root = null ;
        if (event.getSource()==consulter_id) {
            onSelectedGetPropertyDemandeId();
            
            
            stage=(Stage) consulter_id.getScene().getWindow();
            
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("InterfaceConsultation.fxml"));
              }
              //create a new scene with root and set the stage// Creer un nouveau scène 
                 Scene scene=new Scene(root);
                 stage.setScene(scene);
                 stage.setTitle("Interface Adminsitrateur All For Deal");
                 stage.show();
        }
    
    private void onSelectedGetPropertyDemandeId(){
        //affichage lors de la selection
        tableAffiche2.setOnMouseClicked(e -> {
                String req="select * from  demande_service where id=? and id_user_demande=?";
                          //      String req="select * from  produit where id_produit=? and id_user=?";

            demandeService dem = (demandeService)tableAffiche2.getSelectionModel().getSelectedItem();
            try {
                
                pst=c.prepareStatement(req);
                pst.setInt(1,dem.getId());
                pst.setInt(2,dem.getIdUserDemServ());
                res=pst.executeQuery();
                
                
                while (res.next()){ 
                 // remplissage des textField aprés selection dans le tableaux
                    id_dem_serv_inv.setText(String.valueOf(res.getInt("id")));                      // id_user_static_get=Integer.parseInt(res.getString("id_user"));
                    // id_dem_serv_inv.setText(String.valueOf(tableAffiche2.getSelectionModel().getSelectedItem().getId()));
                     idDemServPublicStatic=Integer.parseInt(String.valueOf(res.getInt("id")));
                    //idDemServPublicStatic=Integer.parseInt(id.toString());
                      
                  //   id_use_inv.setText(String.valueOf(tableAffiche2.getSelectionModel().getSelectedItem().getIdUserDemServ()));
                    //  idUserDemServPublicStatic=Integer.parseInt(id_use_inv.toString());
                      
                      id_use_inv.setText(String.valueOf(res.getInt("id_user_demande")));
                      idUserDemServPublicStatic=Integer.parseInt(String.valueOf(res.getInt("id_user_demande")));
                      /*id_invisible.setText(res.getString("id_produit"));
                      //id_invisible.setText(String.valueOf(table_produit.getSelectionModel().getSelectedItem().getId_Produit()));
                      // id_user_static_get=Integer.parseInt(res.getString("id_user"));
                      //  idOffreStatic=Integer.parseInt(id_invisible.toString());
                      idOffreStatic=Integer.parseInt(res.getString("id_produit"));
                      
                      
                      
                      id_user_invisiblee.setText(res.getString("id_user"));
                      idUserStatic=Integer.parseInt(res.getString("id_user"));*/
                      // id_user_static_get=Integer.parseInt(res.getString("id_user"));
                   
                     }
                
                
            } catch (SQLException ex) {
                Logger.getLogger(DemandesPublicController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        });
    }
    
    
    
    
     
    
    // ma méthode
    // Recherche Filtrée
    // Recherche Filtrée
   
    
    public void FiltredSearch(){
            // Filtered List
        FilteredList<demandeService> filteredDemandeListData = new FilteredList<>(data,e->true);
        
        Categorie.setOnKeyReleased(e -> {
                Categorie.textProperty().addListener((observableValue,oldValue,newValue) -> {
                filteredDemandeListData.setPredicate((Predicate<? super demandeService>) dem -> {
                if (newValue==null || newValue.isEmpty()){
                return true;
                }
                String toLowerCaseNewValue = newValue.toLowerCase();
                
                /*  if (produit.getId_Produit()==newValue){
                return true;
                }*/ 
                if (dem.getCathegorie().toLowerCase().contains(toLowerCaseNewValue)){
                    return true;
                }else if(dem.getLieu().toLowerCase().contains(toLowerCaseNewValue)){
                    return true;
                }
                return false;
                   });
              });
        });
        
         //Sorted List
              SortedList<demandeService>  sortedDemandeListData = new SortedList<>(filteredDemandeListData);              
              sortedDemandeListData.comparatorProperty().bind(tableAffiche2.comparatorProperty());
              tableAffiche2.setItems(sortedDemandeListData);
    }
    public void ShowObservableArrayFxListDemandes(){
        
        try {
            
            data=FXCollections.observableArrayList();
            String sql="select * from demande_service";
            res=c.createStatement().executeQuery(sql);
            while (res.next()){
                // lahné f res.getX("Attribut eli mwjoud fl base de données selon type eli 7atetou enty")
                data.add(new demandeService(res.getInt("id"),res.getString("objet"),res.getString("description"),res.getString("cathegorie"),res.getString("lieu"),res.getString("date"),res.getInt("id_user_demande")));
            } 
            // lahné (" ") attribut eli mwjoud fl classe mte3k
            // Id_Col : hia fx:id ta3 colonne eli mssaméha fl sceneBuilder
            // PropertyValueFactory<EsmCLASSE,TypeAttribut fl classe>
            // w tanséch déclarehm l fou9 ba3d m t3ml "make controller" fl fichier.fxml
            //exmple :  @FXML private TableColumn<Produit,String> Categ_Col;
            // w aussi ta3ti type ta3 tableView l fou9 
            // exmple : @FXML private TableView<EsmClasseMte3k> TableAffiche;
            
            col_id_demServ.setCellValueFactory(new PropertyValueFactory<demandeService,Integer>("id"));
            //prix_Col.setCellValueFactory(new PropertyValueFactory<Produit,Double>("prix_prd"));
           col_obj.setCellValueFactory(new PropertyValueFactory<demandeService,String>("objet"));
            //Desc_Col.setCellValueFactory(new PropertyValueFactory<Produit,String>("despcription_prd"));
           col_description.setCellValueFactory(new PropertyValueFactory<demandeService,String>("description"));
            col_categorie.setCellValueFactory(new PropertyValueFactory<demandeService,String>("cathegorie"));
            col_lieu.setCellValueFactory(new PropertyValueFactory<demandeService,String>("lieu"));
             col_date.setCellValueFactory(new PropertyValueFactory<demandeService,String>("date"));
             col_id_user.setCellValueFactory(new PropertyValueFactory<demandeService,Integer>("idUserDemServ"));
            
            
            //id_user_col.setCellValueFactory(new PropertyValueFactory<Produit,Integer> ("id_user_prd"));
            
            
            
            
            tableAffiche2.setItems(data);
           tableAffiche2.layout();
            tableAffiche2.setVisible(true);
        } catch (SQLException ex) {
            System.out.println("Error on building Data");
            Logger.getLogger(DemandesPublicController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableAffiche2.setOnMouseClicked(e -> {
            
            
        });
    }

    @FXML
    private void versConsult(ActionEvent event) throws IOException {
        
        
        Stage stage = null ;
        Parent root = null ;
        if (event.getSource()==consulter_id) {
            onSelectedGetPropertyDemandeId();
            
            
            stage=(Stage) consulter_id.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("InterfaceConsultation.fxml"));
              }
              //create a new scene with root and set the stage// Creer un nouveau scène 
                 Scene scene=new Scene(root);
                 stage.setScene(scene);
                 stage.setTitle("Interface Adminsitrateur All For Deal");
                 stage.show();
        }

    @FXML
    private void RetourMenuUser(ActionEvent event) throws IOException {
        
        Stage stage = null ;
        Parent root = null ;
        if (event.getSource()==btn_retour_menu_user) {
            onSelectedGetPropertyDemandeId();
            
            
            stage=(Stage) btn_retour_menu_user.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("MenuUser.fxml"));
              }
              //create a new scene with root and set the stage// Creer un nouveau scène 
                 Scene scene=new Scene(root);
                 stage.setScene(scene);
                 stage.setTitle("Interface Utilisateur All For Deal");
                 stage.show();
    }
    }



