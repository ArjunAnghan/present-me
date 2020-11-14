package com.presentme.utility;

import java.util.Base64;
import java.util.Random;

public class OtpSender {

    private static final String subject = "OTP for Password Reset Present Me";
    private static final int low = 100000;
    private static final int high = 1000000;

    public static String sendOtpEmail(String username, String email) {
        String message = "Dear " + username + ", this is your One Time Password in order to reset your Project Management System Account .<br/>";
        Random r = new Random();
        int result = r.nextInt(high - low) + low;
        message = message + "\n<strong><h2>" + result+"</h2></strong>";

        final String messageSent = message;
        Thread t = new Thread(() -> SendMail.sendHtml(email, subject, messageSent));
        t.setDaemon(true);
        t.start();

        String s = BCrypt.hashpw(String.valueOf(result), BCrypt.gensalt());

        return Base64.getMimeEncoder().encodeToString(s.getBytes());
    }

    public static int validate(String otp, byte[] otp1) {

        String decoded = new String(Base64.getMimeDecoder().decode(otp1));

        if (BCrypt.checkpw(otp, decoded)){
            return 1;
        }
        return 0;
    }
}
