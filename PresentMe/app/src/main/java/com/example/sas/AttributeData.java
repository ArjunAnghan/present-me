package com.example.sas;

public class AttributeData {
    private static final String url="https://smart-att-sys.herokuapp.com/api_student";

    public static String getUrl() {
        return url;
    }

    public static final String FILENAME = "sas";

    public static String APP_KEY = "XXXXXXXXXXXXXXXXXXXXXX";
    public static String APP_SECRET = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
    public static String APP_CONFIG = "" #For privacy

    public static void stopApp()
    {
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        System.exit(0);
    }
}
