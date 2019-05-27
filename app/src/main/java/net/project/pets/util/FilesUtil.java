package net.project.pets.util;

import android.content.Context;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FilesUtil {

    /**
     * red data of file downloaded from server
     * @param filename name of the downloaded file
     */

    public static String readJsonData(Context context, String filename) {
        BufferedReader input = null;
        File file = null;
        try {
            file = new File(context.getCacheDir(), filename); // Pass getFilesDir() and "MyFile" to read file

            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line);
            }

            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  "";

    }

    /**
     * read data of raw file for testing purposes
     * @param context context where this function is called
     * @param resourceId id of the raw file
     */

    public static String readFileFromRawDirectory(Context context, int resourceId){
        InputStream iStream = context.getResources().openRawResource(resourceId);
        ByteArrayOutputStream byteStream = null;
        try {
            byte[] buffer = new byte[iStream.available()];
            iStream.read(buffer);
            byteStream = new ByteArrayOutputStream();
            byteStream.write(buffer);
            byteStream.close();
            iStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteStream.toString();
    }
}
