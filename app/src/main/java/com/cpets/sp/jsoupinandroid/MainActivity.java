package com.cpets.sp.jsoupinandroid;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    String url1 = "https://firebase.google.com/";
    String url2 = "https://github.com/";
    String url = "https://www.google.com/";
    ProgressDialog progressDialog;
    private TextView textView;
    private ImageView imageView;
    String title;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=(TextView)findViewById(R.id.title);
        imageView=(ImageView)findViewById(R.id.image);

        AsyncTaskRunner runner=new AsyncTaskRunner();
        runner.execute();

    }

    private class AsyncTaskRunner extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //Connect to the website
                Document document = Jsoup.connect(url).get();
                //Get the logo source of the website
                Element img = document.select("img").first();
                // Locate the src attribute
                String imgSrc = img.absUrl("src");
                // Download image from URL
                InputStream input = new java.net.URL(imgSrc).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);

                //Get the title of the website
                title = document.title();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            imageView.setImageBitmap(bitmap);
            textView.setText(title);
            progressDialog.dismiss();
        }
    }
}
