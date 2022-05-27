package com.example.practicespace;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;

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

        webView.getSettings().setJavaScriptEnabled(true);   // 자바스크립트 사용
        webView.getSettings().setAllowFileAccessFromFileURLs(true); // url통한 파일 접속 허용
        webView.getSettings().setAllowContentAccess(true);  // 컨텐츠 접근 허용
        //webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //webView.getSettings().setSupportMultipleWindows(true);

        //webView.getSettings().setDomStorageEnabled(true);


        webView.clearCache(true);
        webView.clearHistory();

        Log.d("tttttttttttttest", TAG);


        webView.addJavascriptInterface(new MyJavascriptInterface(), "Android");
        //webView.loadUrl("https://www.google.com/");  //test url
        webView.loadUrl("http://5gradekgucapstone.xyz:8080/oauth2/authorization/google");

    }

    private class YourWebClient extends WebViewClient {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    boolean loadingFinished = true;
    boolean redirect = false;

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        loadingFinished = false;
        //SHOW LOADING IF IT ISNT ALREADY VISIBLE
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        Log.d("kkkkkkkk",url);
        view.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('pre')[0].innerHTML);");

        //handleIntercept(url);
        if (!redirect) {
            loadingFinished = true;
            //HIDE LOADING IT HAS FINISHED
        } else {
            redirect = false;
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
        if (!loadingFinished) {
            redirect = true;
        }

        loadingFinished = false;
        Log.d("cccccc",urlNewString);
        webView.loadUrl(urlNewString);
        return true;
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        //Log.d("bbbbbbbbb",request.getRequestHeaders("content-type","text/plain").toString());
        if (request.getUrl().toString().contains("adsasd")) {// condition to intercept webview's request
            Log.d("wwwwwww","withwithwith");
            return handleIntercept(request);
        } else {
            Log.d("qqqqqqq","withwithwith");
            //Log.d("eeeeeeeeelse", request.getUrl().toString());
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
            //Log.d("wwwwwww",response.code());
            Log.d("aaaaaaaaaaaaaaaaaa", response.header("Authorization","text/plain"));
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

}

class MyJavascriptInterfaced {
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