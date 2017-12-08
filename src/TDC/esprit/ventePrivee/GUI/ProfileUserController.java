/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDC.esprit.ventePrivee.GUI;

import static TDC.esprit.ventePrivee.GUI.EspaceAdminController.id_user_static_get;
import TDC.esprit.ventePrivee.entities.OffreService;
import TDC.esprit.ventePrivee.entities.User;
import TDC.esprit.ventePrivee.technique.DataBase;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
public class ProfileUserController implements Initializable {
    
    @FXML
    private TextField nom_user_profile;
    @FXML
    private TextField id_user_static;
    @FXML
    private Button btn_Retour_Profil_versAdmin;
    @FXML
    private TextField prnm_user_profile;
    @FXML
    private TextField Address_user_profile;
    @FXML
    private TextField email_user_profile;
    @FXML
    private TextField tel_user_profile;
    @FXML
    private TextField statut_user_profile;
    @FXML
    private TextField sex_user_profile;
    @FXML
    private TextField Objet_mail;
    @FXML
    private TextField destinataire_mail;
    @FXML
    private TextArea msg_mail;
    @FXML
    private Button btn_contact_mail;
    @FXML
    private Button btn_sendMail;
    @FXML
    private Button btnSMS_Contact;
    @FXML
    private TextField destinataire_sms;
    @FXML
    private TextField Objet_sms;
    @FXML
    private TextArea msg_sms;
    @FXML
    private Button btnSendSMS;
    @FXML
    private Text txt_nom_profil_user;
    @FXML
    private Text prnm_profile_user;
   
    Connection c=DataBase.getInstance().getConnection();
    PreparedStatement pst;
    ResultSet res;
    
    // API mail attributes
        static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
        /*   private static final String destMail=destinataire_mail.getText();
        static String subjectMail=Objet_mail.getText();
        static String bodyMail=msg_mail.getText();*/
    
    @FXML
    private void RetourVersAdminAction(ActionEvent event) throws IOException {
        
        Stage stage = null ;
        Parent root = null ;
        
        if (event.getSource()==btn_Retour_Profil_versAdmin){
             //get reference to the button's stage
            stage=(Stage) btn_Retour_Profil_versAdmin.getScene().getWindow();
             //load up OTHER FXML document
            root=FXMLLoader.load(getClass().getResource("AdminSpace.fxml"));
            
        }
        //create a new scene with root and set the stage// Creer un nouveau scène 
         Scene scene=new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Interface Profile Utilisateur");
         stage.show();
         
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

      //  String requete="select S.id_serv_prop,S.desc_serv_prop,S.date_serv_prop,S.categorie_serv_prop,S.valid_serv,U.id_user,U.nom_user,U.prenom_user,U.email_user,U.tel_user from offre_service S INNER JOIN utilisateur U on S.id_user_rel=U.id_user where id_serv_prop=? and id_user="+id_user_static_get;
        
        String sql="select * from utilisateur where id_user="+id_user_static_get;
        try {
            
            User user=new User();
            OffreService offreServ=new OffreService();
            
            pst=c.prepareStatement(sql);
         //   pst.setInt(1,offreServ.getId_Serv());
          //  pst.setInt(2,id_user_static_get); 
            res=pst.executeQuery();
            if (res.next()){
                // champs du profile
                id_user_static.setText(res.getString("id_user"));
                nom_user_profile.setText(res.getString("nom_user"));
                prnm_user_profile.setText(res.getString("prenom_user"));
                Address_user_profile.setText(res.getString("adresse_user"));
                email_user_profile.setText(res.getString("email_user"));
                tel_user_profile.setText(res.getString("tel_user"));
                statut_user_profile.setText(res.getString("statut_user"));
                sex_user_profile.setText(res.getString("sexe_user"));
                // header du profile
                txt_nom_profil_user.setText(res.getString("nom_user"));
                prnm_profile_user.setText(res.getString("prenom_user"));
                // remplir champs @email auto et n°Tel auto
                destinataire_mail.setText(res.getString("email_user"));
                destinataire_sms.setText(res.getString("tel_user"));
              // id_user_static.setText(String.valueOf(id_user_static_get)); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfileUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
  } 

    @FXML
    private void faireMailContact(ActionEvent event)  {
        
        destinataire_mail.setVisible(true);
        Objet_mail.setVisible(true);
        msg_mail.setVisible(true);
        btn_sendMail.setVisible(true);
    }

    @FXML
    private void SendMailAction(ActionEvent event) throws AddressException, MessagingException{
            
            generateAndSendEmail();  
            System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");

  }

    @FXML
    private void ContactSMS(ActionEvent event) {
        
        destinataire_sms.setVisible(true);
        Objet_sms.setVisible(true);
        msg_sms.setVisible(true);
        btnSendSMS.setVisible(true);
    }

    @FXML
    private void sendSmsAction(ActionEvent event) {
    }
    
    public  void generateAndSendEmail() throws AddressException, MessagingException {
 
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
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(destinataire_mail.getText()));
		generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("omar.ghlila@esprit.tn"));
		generateMailMessage.setSubject(Objet_mail.getText());
		String emailBody = msg_mail.getText() + "<br><br> Cordialement <br>Administrateur All For Deal Walid Zhani";
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
                alerOkMail.setContentText("Email a été bien envoyé au destinataire : "+destinataire_mail.getText());
                alerOkMail.showAndWait();
         
		transport.close();
	}
   
}       
