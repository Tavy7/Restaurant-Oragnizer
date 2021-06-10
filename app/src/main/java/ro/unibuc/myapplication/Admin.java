package ro.unibuc.myapplication;

import android.app.Application;

public class Admin extends Application {
    protected static final String dbName = "Restaurant-Database";

    private static Admin adminInstance = null;

    public static Admin getAdminInstance(){
        if (adminInstance == null){
            adminInstance = new Admin();
        }

        return adminInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
