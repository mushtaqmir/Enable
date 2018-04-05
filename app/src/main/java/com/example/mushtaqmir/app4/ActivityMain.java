package com.example.mushtaqmir.app4;

import android.content.Intent;
import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Locale;

public class ActivityMain extends Activity {

    private Button templateBtn;
    private Button chatBtn;
    private ImageButton speakerBtn;
    private  TextToSpeech textToSpeech;
    private Button startBtn;
    private String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        templateBtn=(Button)findViewById(R.id.templateStartBtn);
        chatBtn=(Button)findViewById(R.id.chatBtn);
        speakerBtn=(ImageButton)findViewById(R.id.speakerBtn);
        startBtn=(Button)findViewById(R.id.startBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAction();
            }
        });
        speakerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut();
            }
        });
        templateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openTemplateActivity();
            }
        });
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChatActivity();
            }
        });
    }
    public void startAction(){
        Intent intent=new Intent(this,startFuelDetails.class);
        startActivity(intent);
    }

    public void speakOut(){
        message="Namastey! I cannot speak or hear. Please use this app to help me to serve you.";
        Toast.makeText(ActivityMain.this,message,Toast.LENGTH_LONG).show();
        textToSpeech=new TextToSpeech(ActivityMain.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    textToSpeech.setLanguage(Locale.US);
                    //   textToSpeech.setPitch((float) 0.6);
                    textToSpeech.speak(message, TextToSpeech.QUEUE_ADD, null);
                }
            }
        });

            Toast.makeText(ActivityMain.this,message,Toast.LENGTH_LONG).show();


    }
   public void openTemplateActivity(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void openChatActivity(){
       // Intent intent=new Intent(this,ChatBox.class);
        Intent intent=new Intent(this,ChatBox.class);
        startActivity(intent);
    }
}
