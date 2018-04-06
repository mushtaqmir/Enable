package com.example.mushtaqmir.app4;

import android.content.Intent;
import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Locale;

public class ActivityMain extends AppCompatActivity {

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.history:
                //your code here
                Toast.makeText(ActivityMain.this,"settings working",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
