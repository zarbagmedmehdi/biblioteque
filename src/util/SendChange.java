package util;

import helper.EmpItemHelper;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import service.EmployeService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.SQLException;
import java.util.Properties;

public class SendChange extends Thread{
    EmpItemHelper selected;
    ProgressIndicator p;
    String newpassword;
    public SendChange( EmpItemHelper selected, ProgressIndicator p) {

     this.selected=selected;
     this.p=p;
     this.p.setProgress(-1);
        this.p.setVisible(true);
     this.start();
    }

    @Override
    public void run() {
        super.run();
        try {
            this.newpassword=MotDePasse.generate(4);
            this.send(this.selected.getLogin(),newpassword);
        } catch (MessagingException e) {


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public  void send(String to, String msg) throws MessagingException, SQLException {

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
                new Authenticator() {
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
        selected.setPasword(this.newpassword);
        EmployeService.updateEmploye(selected);

    }

}


