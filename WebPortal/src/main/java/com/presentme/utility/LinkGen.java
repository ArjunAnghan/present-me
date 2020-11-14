package com.presentme.utility;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class LinkGen {

    private static final String subject = "Account Activation for Present Me";
    private static final String body = "Click The below Link to activate your account and reset the password.<br/>";

    public static List<String> encUserandEmail(String user, String email, String userSalt, String emailSalt) {

        String userEnc = BCrypt.hashpw(user, userSalt);
        String emailEnc = BCrypt.hashpw(email, emailSalt);

        for (int i = 0; i < 3; i++) {
            emailEnc = Base64.getMimeEncoder().encodeToString(emailEnc.getBytes());
            userEnc = Base64.getMimeEncoder().encodeToString(userEnc.getBytes());
        }

        for (int i = 0; i < 3; i++) {
            emailEnc = Base64.getMimeEncoder().encodeToString(emailEnc.getBytes());
        }

        List<String> answers = new ArrayList<>();
        userEnc = userEnc.replaceAll("\r", "").replaceAll("\n", "");
        emailEnc = emailEnc.replaceAll("\r", "").replaceAll("\n", "");

        answers.add(userEnc);
        answers.add(emailEnc);

        return answers;
    }

    public static void sendActivationLink(String name, String user, String email) {
        email = email.toLowerCase();
        user = user.toUpperCase();

        String link = "<a href ='https://smart-att-sys.herokuapp.com/activate?#'>Click Here!</a>";
//        String link;
//        if (localhost != null) {
//            link = "<a href ='http://" + localhost.getHostAddress().trim() + ":8080/activate?#'>Click Here!</a>";
//        } else {
//            link = "<a href ='http://localhost:8080/activate?#'>Click Here!</a>";
//        }

        String userSalt = BCrypt.gensalt();
        String emailSalt = BCrypt.gensalt();

        List<String> ans = encUserandEmail(user, email, userSalt, emailSalt);

        String params = "u=" + user + "&i=" + ans.get(0) + "&k=" + ans.get(1) + "&m=" + userSalt + "&n=" + emailSalt;
        link = link.replace("#", params);

        String finalBody = "Respected " + name + ",<br/>" + body + link;

        sendLink(email, finalBody);
    }

    private static void sendLink(String email, String finalBody) {

        Thread t = new Thread(() -> SendMail.sendHtml(email, subject, finalBody));

        t.setDaemon(true);
        t.start();
    }
}
