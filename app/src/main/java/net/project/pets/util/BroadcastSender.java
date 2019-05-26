package net.project.pets.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class BroadcastSender {

    public static void updatePetsUI(Context context) {
        Intent intent = new Intent(Constants.UPDATE_DATA_UI_ACTION);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void updateConfigUI(Context context) {
        Intent intent = new Intent(Constants.UPDATE_CONFIG_UI_ACTION);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void showNetworkErrorUI(Context context) {
        Intent intent = new Intent(Constants.SHOW_ERROR_UI_ACTION);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
