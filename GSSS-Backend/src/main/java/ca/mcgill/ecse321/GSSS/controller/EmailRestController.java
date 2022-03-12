package ca.mcgill.ecse321.GSSS.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class EmailRestController {



//  public static void sendEmail(String email, String username) {
//    
//    String host    = "tls://smtp.gmail.com";
//    int port    = 587;
//    boolean debug   = true;
//
//    String password = "Beatles2022!";
//
//    String senderEmail = "beatlesofgsss@gmail.com";
//
////    String host = "localhost";
//    
//    // Set Properties
//    Properties props = new Properties();
//    
//    props.put( "mail.smtp.auth", "true" );  
//    props.put( "mail.smtp.host", host );
//    props.put( "mail.smtp.port", port );
//    props.put( "mail.smtp.starttls.enable", "true" ); 
//    props.put( "mail.debug", debug );
//    props.put( "mail.smtp.socketFactory.port", port );
//    //props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
//    props.put( "mail.smtp.socketFactory.fallback", "false" );
//    props.put( "mail.smtp.ssl.trust", host ); 
//    
////    Properties properties = System.getProperties();
////    properties.setProperty("mail.smtp.host", host);
////    Session session = Session.getDefaultInstance(props);
//    
//    Session session = Session.getDefaultInstance(
//        props,
//        new javax.mail.Authenticator() {
//
//            protected PasswordAuthentication getPasswordAuthentication() {
//
//                return new PasswordAuthentication( username, password );
//            }
//        }
//    );
//
//    try {
//      // Create a default MimeMessage object.
//      MimeMessage message = new MimeMessage(session);
//      // Set From: header field of the header.
//      message.setFrom(new InternetAddress(senderEmail));
//      // Set To: header field of the header.
//      message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
//      // Set Subject: header field
//      message.setSubject("Set Password");
//      // Now set the actual message
//      message.setText("Hello " + username + ",\n"+"Please use this link to set your password");
//      // Send message
//      Transport.send(message);
//    } catch (MessagingException mex) {
//      mex.printStackTrace();
//    }
//  }
  
  @RequestMapping(value = "/sendemail")
  public static String sendEmail(String email, String username) throws AddressException, MessagingException, IOException {
     sendmail(email, username);
     return "Email sent successfully";   
  }
  
  private static void sendmail(String email, String username) throws AddressException, MessagingException, IOException {
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    
    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
       protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication("beatlesofgsss@gmail.com", "Beatles2022!");
       }
    });
    Message msg = new MimeMessage(session);
    msg.setFrom(new InternetAddress("beatlesofgsss@gmail.com", false));

    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
    msg.setSubject("Set Password");
//    msg.setContent("Tutorials point email", "text/html");
    msg.setText("Hello " + username + ",\n"+"Please use this link to set your password");
//    msg.setSentDate(new Date());

//    MimeBodyPart messageBodyPart = new MimeBodyPart();
//    messageBodyPart.setContent("Tutorials point email", "text/html");
//
//    Multipart multipart = new MimeMultipart();
//    multipart.addBodyPart(messageBodyPart);
//    MimeBodyPart attachPart = new MimeBodyPart();

    Transport.send(msg);   
 }

  
  public static void main(String[] args) throws AddressException, MessagingException, IOException {
    EmailRestController.sendEmail("wassim.jabbour@mail.mcgill.ca", "Wassim");
  }

}
