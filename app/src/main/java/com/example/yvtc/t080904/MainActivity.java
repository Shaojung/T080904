package com.example.yvtc.t080904;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);
    }

    public void click1(View v)
    {
        new GetData().start();

    }

    class GetData extends Thread
    {
        @Override
        public void run() {
            super.run();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("0809", "test1");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv.setText("test2");
                }
            });

        }
    }
}
