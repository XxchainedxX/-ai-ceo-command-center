package com.tiffany.aiceo;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        setContentView(webView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(false);
        settings.setSupportMultipleWindows(false);
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new AndroidDownload(), "AndroidDownload");
        webView.loadUrl("file:///android_asset/index.html");
    }

    private class AndroidDownload {
        @JavascriptInterface
        public void saveCsv(String filename, String content) {
            try {
                String safeName = filename == null ? "ai-ceo-export.csv" :
                    filename.replaceAll("[^A-Za-z0-9._-]", "_");
                if (!safeName.toLowerCase().endsWith(".csv")) safeName += ".csv";
                ContentValues values = new ContentValues();
                values.put(MediaStore.Downloads.DISPLAY_NAME, safeName);
                values.put(MediaStore.Downloads.MIME_TYPE, "text/csv");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    values.put(MediaStore.Downloads.RELATIVE_PATH, "Download");
                    values.put(MediaStore.Downloads.IS_PENDING, 1);
                }
                Uri uri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
                if (uri == null) throw new Exception("Could not create download file");
                try (OutputStream out = getContentResolver().openOutputStream(uri)) {
                    if (out == null) throw new Exception("Could not open download file");
                    out.write((content == null ? "" : content).getBytes(StandardCharsets.UTF_8));
                    out.flush();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    ContentValues done = new ContentValues();
                    done.put(MediaStore.Downloads.IS_PENDING, 0);
                    getContentResolver().update(uri, done, null, null);
                }
                runOnUiThread(() -> Toast.makeText(MainActivity.this,
                    "CSV saved to Downloads", Toast.LENGTH_LONG).show());
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this,
                    "Could not export CSV: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.removeJavascriptInterface("AndroidDownload");
            webView.destroy();
        }
        super.onDestroy();
    }
}
