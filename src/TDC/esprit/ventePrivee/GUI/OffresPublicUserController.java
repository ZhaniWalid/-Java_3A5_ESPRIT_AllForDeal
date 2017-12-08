/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDC.esprit.ventePrivee.GUI;

import static TDC.esprit.ventePrivee.GUI.OffreProduitController.idOffreStatic;
import static TDC.esprit.ventePrivee.GUI.OffreProduitController.idUserStatic;
import TDC.esprit.ventePrivee.entities.Produit;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Edzio
 */
public class OffresPublicUserController implements Initializable {
   
    @FXML
    private Button btnRetourEspace;
    
    
    private ObservableList<Produit> data=FXCollections.observableArrayList();
    Produit dataSelected=new Produit();
    Connection c=DataBase.getInstance().getConnection();
    PreparedStatement pst;
    PreparedStatement pst2;
    ResultSet res;
    ResultSet res2;
    @FXML
    private TableColumn<Produit, Integer> id_col;
    @FXML
    private TableColumn<Produit, Double> prix_col;
    @FXML
    private TableColumn<Produit, String> libelle_col;
    @FXML
    private TableColumn<Produit, String> description_col;
    @FXML
    private TableColumn<Produit, String> date_col;
    @FXML
    private TableColumn<Produit, String> categorie_col;
    @FXML
    private TableColumn<Produit, Integer> validite_col;
    @FXML
    private Button retour_id;
    @FXML
    private TableView<Produit> table_produit;
    @FXML
    private Button consulter_id;
    @FXML
    private TextField categorie_id;
    @FXML
    private TextField id_invisible;
    
    
    @FXML
    private Button statistique_id;
    @FXML
    private TableColumn<Produit,Integer> id_user_col;
   
    public static int idOffreStatic;
    public static int idUserStatic;
    
    @FXML
    private TextField id_user_invisiblee;
    
    
    @FXML
    private void RetourEspaceUser(ActionEvent event) throws IOException {
        
         Stage stage=null;
        Parent root=null;
        
        
        if (event.getSource()==btnRetourEspace){
             //get reference to the button's stage
            stage=(Stage) btnRetourEspace.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("MenuUser.fxml"));
         //   pr.DisplayobservableArrayList();  
        }
        //create a new scene with root and set the stage// Creer un nouveau scène 
         Scene scene=new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Espace Personelle");
         stage.show();
    }

    @FXML
    private void afficherStatistique(ActionEvent event) {
    }


     // ma méthode 
   /// Affichage des données dans un observableArrayList()
    public void ShowObservableArrayFxListOffreProduit(){
        
        try {
            
            int valid=1;
            
            data=FXCollections.observableArrayList();
            String sql="select * from produit";  
            res=c.createStatement().executeQuery(sql); 
            while (res.next()){ 
                // lahné f res.getX("Attribut eli mwjoud fl base de données selon type eli 7atetou enty")
                data.add(new Produit(res.getInt("id_Produit"),res.getDouble("prix_prd"),res.getString("libelle_prd"),res.getString("description_prd"),res.getString("dateAjout_prd"),res.getString("categorie_prd"),res.getInt("id_user_prd")));
            } 
            // lahné (" ") attribut eli mwjoud fl classe mte3k
            // Id_Col : hia fx:id ta3 colonne eli mssaméha fl sceneBuilder
            // PropertyValueFactory<EsmCLASSE,TypeAttribut fl classe>
            // w tanséch déclarehm l fou9 ba3d m t3ml "make controller" fl fichier.fxml
            //exmple :  @FXML private TableColumn<Produit,String> Categ_Col;
            // w aussi ta3ti type ta3 tableView l fou9 
            // exmple : @FXML private TableView<EsmClasseMte3k> TableAffiche;
            
            id_col.setCellValueFactory(new PropertyValueFactory<Produit,Integer>("Id_Produit"));
            //prix_Col.setCellValueFactory(new PropertyValueFactory<Produit,Double>("prix_prd"));
           libelle_col.setCellValueFactory(new PropertyValueFactory<Produit,String>("libelle_prd"));
            //Desc_Col.setCellValueFactory(new PropertyValueFactory<Produit,String>("despcription_prd"));
           categorie_col.setCellValueFactory(new PropertyValueFactory<Produit,String>("categorie_prd"));
            date_col.setCellValueFactory(new PropertyValueFactory<Produit,String>("dateAjout_prd"));
            id_user_col.setCellValueFactory(new PropertyValueFactory<Produit,Integer>("id_user_prd"));
            
            
            //id_user_col.setCellValueFactory(new PropertyValueFactory<Produit,Integer> ("id_user_prd"));
            
            
            
            
            table_produit.setItems(data);
            table_produit.layout();
            table_produit.setVisible(true);
        } catch (SQLException ex) {
            System.out.println("Error on building Data");
            Logger.getLogger(OffreProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        table_produit.setOnMouseClicked(e -> {
            
            
        });
    }

    @FXML
    private void RetourPrincipale(ActionEvent event) {
    }

    @FXML
    private void consulterProduit(ActionEvent event) throws IOException {
        Stage stage = null ;
        Parent root = null ;
        if (event.getSource()==consulter_id) {
            onSelectedGetPropertyOffreId();
            
            
            stage=(Stage) consulter_id.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("InterfaceConsultationProduit.fxml"));
              }
              //create a new scene with root and set the stage// Creer un nouveau scène 
                 Scene scene=new Scene(root);
                 stage.setScene(scene);
                 stage.setTitle("Interface Consultation Produit All For Deal");
                 stage.show();
        }
    
    private void onSelectedGetPropertyOffreId(){
        //affichage lors de la selection
        table_produit.setOnMouseClicked(e -> {
                String req="select * from  produit where id_produit=?";
                Produit produit=(Produit) table_produit.getSelectionModel().getSelectedItem();
            try {
                
                pst=c.prepareStatement(req);
                pst.setInt(1,produit.getId_Produit());
                res=pst.executeQuery();
                
                
                while (res.next()){ 
                 // remplissage des textField aprés selection dans le tableaux
                      id_invisible.setText(res.getString("id_produit"));
                      // id_user_static_get=Integer.parseInt(res.getString("id_user"));
                      idOffreStatic=Integer.parseInt(id_invisible.toString());
                     }
                
                
            } catch (SQLException ex) {
                Logger.getLogger(OffreProduitController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        });
    }
    
    // ma méthode
    // Recherche Filtrée
    // Recherche Filtrée
    public void FiltredSearch(){
            // Filtered List
        FilteredList<Produit> filteredProduitListData = new FilteredList<>(data,e->true);
        
        categorie_id.setOnKeyReleased(e -> {
                categorie_id.textProperty().addListener((observableValue,oldValue,newValue) -> {
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
              
              sortedProduitListData.comparatorProperty().bind(table_produit.comparatorProperty());
              table_produit.setItems(sortedProduitListData);
    }

    /*  @FXML
    private void afficherStatistique(ActionEvent event) {
    
    ProduitDAO prd = new ProduitDAO();
    int t=prd.displayAll1();
    int o=prd.displayAll2();
    int e=prd.displayAll3();
    int c=prd.displayAll4();
    int a=prd.displayAll5();
    int v=prd.displayAll6();
    
    DefaultPieDataset piedataset=new DefaultPieDataset();
    piedataset.setValue("telephonie", new Integer(t));
    piedataset.setValue("ordinateur", new Integer(o));
    piedataset.setValue("consoles videos", new Integer(e));
    piedataset.setValue("electromenager", new Integer(c));
    piedataset.setValue("autres", new Integer(a));
    piedataset.setValue("vetements", new Integer(v));
    
    //piedataset.setValue("Fiat", new Integer(f));
    //piedataset.setValue("BMW", new Integer(b));
    JFreeChart chart=ChartFactory.createPieChart3D("Statistiques sur les produits offert sur notre platforme",piedataset, true, true, true);
    org.jfree.chart.plot.PiePlot3D plot = (org.jfree.chart.plot.PiePlot3D) chart.getPlot();
    ChartFrame frame=new ChartFrame(null, chart);
    frame.setVisible(true);
    frame.setSize(450, 500);
    
    plot.setStartAngle(290);
    plot.setDirection(Rotation.CLOCKWISE);
    plot.setForegroundAlpha(0.5f);
    }*/

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
          FiltredSearch();
        ShowObservableArrayFxListOffreProduit();
        
       //affichage lors de la selection
        table_produit.setOnMouseClicked(e -> {
                String req="select * from  produit where id_produit=? and id_user_prd=?";
           //String req2="select * from utilisateur where id_user=?";
           // String req="select P.id_produit,P.libelle_prd,P.prix_prd,P.dateAjout_prd,U.id_user,U.nom_user,U.prenom_user,U.adresse_user,U.email_user,U.tel_user from produit P, utilisateur U on P.id_user_prd=U.id_user where id_produit=? and id_user=?";
             // String req="select P.id_produit,P.libelle_prd,P.prix_prd,P.dateAjout_prd,U.id_user,U.nom_user,U.prenom_user,U.adresse_user,U.email_user,U.tel_user from produit P, utilisateur U on P.id_user_prd=U.id_user where id_produit=? and id_user=?";
            Produit produit=(Produit) table_produit.getSelectionModel().getSelectedItem();
            try {
                
                pst=c.prepareStatement(req);
               
                pst.setInt(1,produit.getId_Produit());
                pst.setInt(2,produit.getIdUser());
                res=pst.executeQuery();
               
                
                
                while (res.next()){ 
                 // remplissage des textField aprés selection dans le tableaux
                      id_invisible.setText(res.getString("id_produit"));
                      // id_user_static_get=Integer.parseInt(res.getString("id_user"));
                      idOffreStatic=Integer.parseInt(res.getString("id_produit"));
                      
                     
                
                      id_user_invisiblee.setText(res.getString("id_user_prd"));
                      idUserStatic=Integer.parseInt(res.getString("id_user_prd"));
                      
                     }
               
                
            } catch (SQLException ex) {
                Logger.getLogger(OffreProduitController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        });
        
        FiltredSearch();
    
    }
}
