package com.example.yvtc.t080904;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);
        img = (ImageView) findViewById(R.id.imageView);
    }

    public void click1(View v)
    {
        new GetData().start();
    }

    public void click2(View v)
    {
        new GetImage().start();
    }

    class GetData extends Thread
    {
        String str;
        @Override
        public void run() {
            super.run();
            String str_url = "http://data.ntpc.gov.tw/od/data/api/BF90FA7E-C358-4CDA-B579-B6C84ADC96A1?$format=json";
            try {
                URL url = new URL(str_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream stream = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
                str = br.readLine();
                br.close();
                stream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv.setText(str);
                }
            });

        }
    }
    class GetImage extends Thread
    {
        String str;
        int length;
        byte[] result;
        int total;
        @Override
        public void run() {
            super.run();
            String str_url = "http://images.meredith.com/content/dam/bhg/Images/2006/03/SIP943899.jpg.rendition.largest.jpg";
            try {
                URL url = new URL(str_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream stream = conn.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] data = new byte[64];
                total = 0;
                while ((length = stream.read(data)) != -1)
                {
                    outputStream.write(data, 0, length);
                    total += length;
                };
                result = outputStream.toByteArray();



                stream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0, total);
                    ImageView img = (ImageView) findViewById(R.id.imageView);
                    img.setImageBitmap(bitmap);
                }
            });

        }
    }
}
