package ro.unibuc.myapplication;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

public class CloudMessFirevase extends FirebaseMessagingService
{
    private static final String TAG = "tagg";

    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
    }
}
