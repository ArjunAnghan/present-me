package com.presentme.utility;

import com.sendgrid.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class SendMail {

    static void sendHtml(String to, String sub, String msg) {
        Email from = new Email(user);
        Email toMail = new Email(to);
        Content content = new Content("text/html", msg);
        Mail mail = new Mail(from, sub, toMail, content);

        SendGrid sg = new SendGrid(apikey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


//    private static final String user = "pms.sgp.2019@gmail.com";
//    private static final String pass = "pms2019sgp@*";
//
//    static void sendHtml(String to, String sub,
//                            String msg) {
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        //below mentioned mail.smtp.port is optional
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//
//
//        Session session = Session.getInstance(props, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(user, pass);
//            }
//        });
//
//        try {
//
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(user));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//            message.setSubject(sub);
//            message.setText(msg, "UTF-8", "html");
//
//            Transport.send(message);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}