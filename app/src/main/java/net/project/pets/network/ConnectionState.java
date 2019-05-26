package net.project.pets.network;
import android.content.Context;
import android.net.ConnectivityManager;

public class ConnectionState {

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
