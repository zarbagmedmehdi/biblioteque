package util;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import service.EmployeService;

import java.sql.SQLException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
public class Send extends Thread{
    String to; String msg;
    ProgressIndicator p;
    public Send(String to, String msg,ProgressIndicator p) {
     this.to=to;
     this.msg=msg;
     this.p=p;
     this.start();
    }

    @Override
    public void run() {
        super.run();
        try {
            this.send(this.to,this.msg);
        } catch (MessagingException e) {
            try {
                System.out.println(EmployeService.deleteEmploye(this.to)+" deleted ");
            } catch (SQLException ex) {

            } catch (MessagingException ex) {
                ex.printStackTrace();
            }

            Platform.runLater(()->AlertUtil.showAlert("Erreur","Mail", Alert.AlertType.ERROR));
                Platform.runLater(()->this.p.setVisible(false));
        }
    }


    public  void send(String to, String msg) throws MessagingException {

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("services.dev5@gmail.com","Services.5");
                    }
                });

        //session.setDebug(true);
        Transport transport = session.getTransport();
        InternetAddress addressFrom = new InternetAddress("services.dev5@gmail.com");

        MimeMessage message = new MimeMessage(session);
        message.setSender(addressFrom);
        message.setSubject("Mot de passe Plateforme BIB");
        message.setContent("Votre mot de passe est:"+msg, "text/plain");
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        transport.connect();
        Transport.send(message);
        transport.close();
        this.p.setVisible(false );

        Platform.runLater(()->AlertUtil.showConfirmation("Confirmation","Employé est  crée "));

    }

}


