/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.services.recipe;

import blastandburn.services.event.*;
import blastandburn.iservices.event.IMailSender;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author HP
 */
public class ServiceMail2 implements IMailSender{

    @Override
    public void sendmailfunc(String emailto, String message) {
    
         //Get properties object    
          Properties props = new Properties();    
          props.put("mail.smtp.host", "smtp.gmail.com");    
          props.put("mail.smtp.socketFactory.port", "465");    
          props.put("mail.smtp.socketFactory.class",    
                    "javax.net.ssl.SSLSocketFactory");    
          props.put("mail.smtp.auth", "true");    
          props.put("mail.smtp.port", "465");    
          //get Session   
          Session session = Session.getDefaultInstance(props,    
           new javax.mail.Authenticator() {    
           protected PasswordAuthentication getPasswordAuthentication() {    
           return new PasswordAuthentication("mohamedamine.balti@esprit.tn","191JMT2665");  
           }    
          });    
          
           //compose message    

          try {    
              
           Multipart emailContent = new MimeMultipart();

           MimeMessage msg = new MimeMessage(session);    
           msg.addRecipient(Message.RecipientType.TO,new InternetAddress(emailto));    
           
           	//Attachment body part.
			MimeBodyPart pdfAttachment = new MimeBodyPart();
			pdfAttachment.attachFile("C:\\Users\\oussama\\OneDrive\\Bureau\\PIdev\\BLAST\\src\\blastandburn\\resources\\images\\OUTDOOR.jpg");
           //Attach body parts
			emailContent.addBodyPart(pdfAttachment);
           msg.setSubject("QR EVENT");    
           msg.setText(message);    
           msg.setContent(emailContent);

           //send message  
           Transport.send(msg);    
           System.out.println("message sent successfully");    
          } catch (MessagingException e) {throw new RuntimeException(e);} catch (IOException ex) {    
            Logger.getLogger(ServiceMail2.class.getName()).log(Level.SEVERE, null, ex);
        }    
} 
    }
    

