package com.presentme.utility;

import java.util.Base64;

public class BCrypyGen {
    public static void main(String[] args) {

        String key = "cnn";

        for (int i=0;i<4;i++){
            key = Base64.getMimeEncoder().encodeToString(key.getBytes());
        }

        System.out.println(key);
    }
}
