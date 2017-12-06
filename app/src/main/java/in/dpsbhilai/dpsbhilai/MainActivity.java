package in.dpsbhilai.dpsbhilai;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button b1;
    TextView tv1;
    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=(Button)findViewById(R.id.b1);
        tv1=(TextView)findViewById(R.id.tv1);
        wv=(WebView)findViewById(R.id.wv);
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                wv.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        wv.getSettings().setLoadsImagesAutomatically(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv.loadUrl("http://www.google.com");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadWebPageTask task = new DownloadWebPageTask();
                task.execute(new String[] { "http://www.google.com" });
            }
        });

    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                try {
                    URL urlObj = new URL(url);
                    HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
                    InputStream is = urlConnection.getInputStream();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            tv1.setText(Html.fromHtml(result));
        }
    }
}
