package com.example.practicespace;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebViewActivity extends AppCompatActivity {

    private String TAG = WebViewActivity.class.getSimpleName();
    String USER_AGENT = "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";

    private WebView webView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = (WebView) findViewById(R.id.webview);

        webView.setWebViewClient(new YourWebClient());
        //webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setUserAgentString(USER_AGENT);

        //webView.setWebChromeClient(new WebChromeClient());

        //webView.getSettings().setLoadWithOverviewMode(true);
        //webView.getSettings().setUseWideViewPort(true);

        //webView.getSettings().setSupportZoom(false);
        //webView.getSettings().setBuiltInZoomControls(false);

        //webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //webView.getSettings().setSupportMultipleWindows(true);

        //webView.getSettings().setDomStorageEnabled(true);


        webView.clearCache(true);
        webView.clearHistory();

        Log.d("tttttttttttttest", TAG);


        webView.loadUrl("http://5gradekgucapstone.xyz:8080/oauth2/authorization/google");  //test url

    }

    private class YourWebClient extends WebViewClient {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        //Log.d("bbbbbbbbb",request.getRequestHeaders("content-type","text/plain").toString());
        if (request.getUrl().toString().contains("aafwfw")) {// condition to intercept webview's request
            return handleIntercept(request);
        } else {
            Log.d("eeeeeeeeelse", TAG);
            return super.shouldInterceptRequest(view, request);
            //return handleIntercept(request);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private WebResourceResponse handleIntercept(WebResourceRequest request){
        OkHttpClient okHttpClient = new OkHttpClient();
        final Call call = okHttpClient.newCall(new Request.Builder()
                .url(request.getUrl().toString())
                .method(request.getMethod(),null)
                .headers(Headers.of(request.getRequestHeaders()))
                .build()
        );
        try {
            final Response response = call.execute();
            response.headers();// get response header here
            Log.d("aaaaaaaaaaaaaaaaaa", response.header("Authorization","text/plain").toString());
            return new WebResourceResponse(
                    response.header("content-type", "text/plain"), // You can set something other as default content-type
                    response.header("content-encoding", "utf-8"),  //you can set another encoding as default
                    response.body().byteStream()
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    }


    private Map<String, String> getCustomHeaders()
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("YOURHEADER", "VALUE");
        return headers;
    }


    /* public void GetUserInfo(String response) {
        Gson gson = new Gson();
        UserList userList = gson.fromJson(response, UserList.class);
    } */

}