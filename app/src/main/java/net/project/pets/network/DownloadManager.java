package net.project.pets.network;

import android.content.Context;
import net.project.pets.util.BroadcastSender;
import net.project.pets.util.Constants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadManager {

    public static void downloadFileAsync(final Context context, final String downloadUrl,
                                         final String fileName, final int requestType) {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url(downloadUrl).build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                if (requestType == Constants
                        .REQUEST_TYPE_DOWNLOAD_DATA) {
                    BroadcastSender.showNetworkErrorUI(context);
                }
            }
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful()) {
                    if (requestType == Constants
                            .REQUEST_TYPE_DOWNLOAD_DATA) {
                        BroadcastSender.showNetworkErrorUI(context);
                    }
                }

                File file;
                FileOutputStream outputStream;
                try {
                    file = new File(context.getCacheDir(), fileName);
                    outputStream = new FileOutputStream(file);
                    outputStream.write(response.body().bytes());
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                switch (requestType) {
                    case Constants
                            .REQUEST_TYPE_DOWNLOAD_CONFIG :
                        BroadcastSender.updateConfigUI(context);
                        break;

                    case Constants
                            .REQUEST_TYPE_DOWNLOAD_DATA :
                        BroadcastSender.updatePetsUI(context);
                        break;
                }
            }
        });
    }
}
