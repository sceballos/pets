package net.project.pets.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MessageDialog {

    /**
     * shows dialog with closing app feature
     * @param context
     * @param message
     * @param closeApp
     */

    public static void showDialog(final Context context, String message, final boolean closeApp) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);

        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        if (closeApp) {
                            System.exit(0);
                        }
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
