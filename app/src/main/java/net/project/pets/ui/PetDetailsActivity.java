package net.project.pets.ui;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import net.project.pets.R;
import net.project.pets.data.Pet;

import java.util.Objects;

public class PetDetailsActivity extends AppCompatActivity {

    private Pet pet;
    private WebView detailsWebView ;
    boolean loadingFinished = true;
    boolean redirect = false;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);
        pet = Objects.requireNonNull(getIntent().getExtras()).getParcelable("pet_info");

        detailsWebView = findViewById(R.id.pet_details_wv);
        progressBar = findViewById(R.id.pet_details_wv_pb);

        detailsWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view, WebResourceRequest request) {
                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;
                detailsWebView.loadUrl(request.getUrl().toString());
                return false;
            }

            @Override
            public void onPageStarted(
                    WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadingFinished = false;
                showProgressBar(false);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!redirect) {
                    loadingFinished = true;
                    showProgressBar(true);
                } else {
                    redirect = false;
                }
            }
        });

        detailsWebView.loadUrl(pet.getContentUrl());
    }

    @Override
    public void onBackPressed() {
        if (detailsWebView.canGoBack()) {
            detailsWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    void showProgressBar(boolean hide){
        if (hide) {
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}
