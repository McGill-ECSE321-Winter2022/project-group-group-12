package ca.mcgill.ecse321.GSSS;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    // The webview of the main activity
    private WebView webView;

    /**
     * Method that runs when the app is opened
     * @param savedInstanceState To load the last state of the app
     * @author Wassim Jabbour
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Call superclass constructor
        setContentView(R.layout.activity_main); // Link to XML file
        webView = (WebView) findViewById(R.id.webView); // Find the webview by ID
        webView.setWebViewClient(new WebViewClient()); // To open URLs within WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JS
        webSettings.setDomStorageEnabled(true); // Enable DOM storage
        webView.loadUrl("https://grocerystore-frontend.herokuapp.com"); // Load our webapp
    }
}