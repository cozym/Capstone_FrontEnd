package com.example.practicespace;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class Login extends AppCompatActivity {

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

        webView.getSettings().setJavaScriptEnabled(true);   // 자바스크립트 사용

        webView.clearCache(true);
        webView.clearHistory();

        webView.addJavascriptInterface(new MyJavascriptInterface(), "Android");

        webView.loadUrl("http://5gradekgucapstone.xyz:8080/oauth2/authorization/google");

    }

    private class YourWebClient extends WebViewClient {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d("kkkkkkkk",url);
            view.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('pre')[0].innerHTML);");

            //super.onPageFinished(view, url);
        }

    }

}

class MyJavascriptInterface {
    @JavascriptInterface
    public void getHtml(String html) {
        GetUserInfo(html);
        Log.d("oooooooo", html);
    }

    public void GetUserInfo(String response) {
        Gson gson = new Gson();
        LoginInfo info = gson.fromJson(response, LoginInfo.class);
        Log.d("iiiiiii", info.data.token);
    }
}