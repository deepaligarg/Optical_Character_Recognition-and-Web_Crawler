package com.example.mayank.cvtest;

/**
 * Created by Mayank on 10/17/2016.
 */
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;


public class text_show extends AppCompatActivity {

    Button button;

    TextToSpeech t1;
    EditText ed1;
    Button b1;
    public int flag = 1;


    String s;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        s = getIntent().getStringExtra("deepali");

        EditText tv = (EditText) findViewById(com.example.mayank.cvtest.R.id.T4);
        tv.setText(s);

        b1=(Button)findViewById(R.id.bb1);

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);

                    flag = 0;

                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = s;
                //new sing().execute(toSpeak);

                //while(ma.flag==1);

                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                Log.e("flag","1");

            }

        });


    }

    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

}
