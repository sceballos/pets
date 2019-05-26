package net.project.pets.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


public class PermissionsUtil {

    public static final int REQUEST_CODE_WRITE_PERMISSION = 0;

    /**
     * check if write permission is available
     * @param context context where this function is called
     */

    public static boolean checkPermission(Context context) {
        int result = ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * request permission for write storage
     * @param context context where this function is called
     */

    public static void requestPermission(Context context) {
        Activity activity = (Activity) context;

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_WRITE_PERMISSION);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_WRITE_PERMISSION);
            }
        }
    }
}
