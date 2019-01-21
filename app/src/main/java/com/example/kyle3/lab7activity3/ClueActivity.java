package com.example.kyle3.lab7activity3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

public class ClueActivity extends AppCompatActivity {
    static String gameRecord = ""; // used for passing game record back and forth

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clue);
        Intent p = getIntent();
        final String name = p.getStringExtra("userName");
        final WebView myWebView = (WebView)findViewById(R.id.webView);
        // Allows javascript and localStorage to be ran in webView
        WebSettings myWebSettings = myWebView.getSettings();
        myWebSettings.setJavaScriptEnabled(true);
        myWebSettings.setDomStorageEnabled(true);
        // Opens local asset file
        myWebView.loadUrl("file:///android_asset/clue.html");
        // Bypasses the username entry on .html page and uses native activity login
        myWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url)
            {
                myWebView.loadUrl("javascript:welcomeUser('" +name+ "')");
            }
        });

        // When continue is pressed in javascript
        myWebView.addJavascriptInterface(new Object()
        {
            // continue also passes game winner from history
            @JavascriptInterface
            public void bContinue(String jsonStr)throws JSONException {
                Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
                JSONObject winnerInfo = new JSONObject(jsonStr);
                i.putExtra("name", winnerInfo.getString("winner"));
                i.putExtra("count", winnerInfo.getString("guessCount"));
                i.putExtra("suspect", winnerInfo.getString("suspect"));
                i.putExtra("room", winnerInfo.getString("room"));
                i.putExtra("weapon", winnerInfo.getString("weapon"));
                startActivity(i);
            }
        }, "ok");
    }
}
