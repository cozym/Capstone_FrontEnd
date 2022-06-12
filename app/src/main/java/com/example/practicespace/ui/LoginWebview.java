package com.example.practicespace.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practicespace.R;
import com.example.practicespace.WebViewActivity;

public class LoginWebview extends AppCompatActivity {
    private SharedPreferences preferences;
    static MyJavascriptInterface js = new MyJavascriptInterface();
    private String TAG = WebViewActivity.class.getSimpleName();
    String USER_AGENT = "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";
    private WebView webView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = (WebView) findViewById(R.id.webview);

        webView.setWebViewClient(new YourWebClient());
        webView.getSettings().setUserAgentString(USER_AGENT);
        webView.loadUrl("javascript:document.body.style.setProperty(\"color\", \"#776a5f\");");
        webView.getSettings().setJavaScriptEnabled(true);   // 자바스크립트 사용

        webView.clearCache(true);
        webView.clearHistory();

        webView.addJavascriptInterface(js, "Android");

        webView.setBackgroundColor(0);
        webView.setBackgroundResource(R.drawable.loading);
        webView.loadUrl("http://5gradekgucapstone.xyz:8080/oauth2/authorization/google");
    }



    private class YourWebClient extends WebViewClient {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d("ttaagg", url);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(url.contains("google?state=")) {
                Intent intent = new Intent(getApplicationContext(), LoginBridge.class);
                startActivity(intent);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:document.body.style.setProperty(\"color\", \"#776a5f\");");
            view.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('pre')[0].innerHTML);");


        }
    }

}
