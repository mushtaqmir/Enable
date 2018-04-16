package com.example.mushtaqmir.app4;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.speech.tts.TextToSpeech;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.Gravity;
import android.widget.Toast;
import android.view.WindowManager;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Locale;

public class ChatBox extends ToolBarActivity implements TextWatcher{
    private ArrayAdapter customerAdapter;
    private List<Message> custMsgList;
    ListView custMsgListView;
    ArrayAdapter empAdapter;
    List<String> empMsgList;
    private  EditText custText;
    private  ImageButton custBtn;
    AutoCompleteTextView empText;
    ImageButton empBtn;
    TextToSpeech textToSpeech;
    DbHandler dbHandler;
   private  String[] suggtlist={"Hello","Welcome","Welcome2","Welcome3","Welcome4"};
   List list=new ArrayList(Arrays.asList(suggtlist));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        try{
            dbHandler=new DbHandler(this,Util.getProperty("DATABASE_NAME",this),null,1);
        list.addAll(dbHandler.getAllMessages());
        }
        catch (IOException e){
            e.printStackTrace();
        }

       this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        String[] messageList1={"Hi"};
        String[] messageList2={"Hello"};
        //for Customer
        custText =(EditText)findViewById(R.id.custText);
        custBtn=(ImageButton)findViewById(R.id.custBtn);
        custMsgList=new ArrayList<Message>();
        custMsgList.add(new Message("Hi! How can i help you?",true));
        customerAdapter= new ChatAdapter(this,custMsgList);
        custMsgListView =(ListView)findViewById(R.id.custMsgListView);
        custMsgListView.setAdapter(customerAdapter);
        custBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String message = custText.getText().toString().trim();
                if(!message.equals("")) {
                    Message msg=new Message(message,false);
                    custMsgList.add(msg);
                    custText.setText("");
                    customerAdapter.notifyDataSetChanged();

                }
            }
        });
    //Autocomplete i chat for suggestions
        empText=(AutoCompleteTextView) findViewById(R.id.empText);
        empText.addTextChangedListener(this);

        //stringArrayAdapter.notifyDataSetChanged();
        //this.empText.showDropDown();
        //Autocomplete
        empBtn=(ImageButton)findViewById(R.id.empBtn);
        empBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String message = empText.getText().toString().trim();
                if(!message.equals("")) {
                    Message msg=new Message(message,true);
                    custMsgList.add(msg);
                    empText.setText("");
                    customerAdapter.notifyDataSetChanged();
                }
            }
        });
        custMsgListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       Message messageObj= (Message) parent.getItemAtPosition(position);
                       if(messageObj.isEmployee()) {
                           String msg = String.valueOf(messageObj.getMessage());
                           //insert code here
                           textToSpeech = new TextToSpeech(ChatBox.this, new TextToSpeech.OnInitListener() {
                               @Override
                               public void onInit(int status) {
                                   if (status == TextToSpeech.SUCCESS) {
                                       textToSpeech.setLanguage(Locale.US);
                                       //   textToSpeech.setPitch((float) 0.6);
                                       textToSpeech.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
                                   }
                               }
                           });
                       }
                    }
                }
        );
    }
    public void onMicTap(View v){
        if(v.getId()==R.id.micBtn){
            promptSpeechInput();
        }

    }
    public void promptSpeechInput(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
      //  intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"");
        try {
            startActivityForResult(intent,200);
        }
        catch (ActivityNotFoundException e){
            Toast.makeText(ChatBox.this,"Sorry! Your device doen't support language",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int request_code,int result_code,Intent intent){
        super.onActivityResult(request_code, result_code,intent);

        if(request_code == 200) {
            if (result_code == RESULT_OK && intent != null) {
                ArrayList<String> result = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String message = result.get(0);
                if (!message.equals("")) {
                    Message msg=new Message(message,false);
                    custMsgList.add(msg);
                    custText.setText("");
                    customerAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, R.layout.suggestionlist,R.id.suggest, list);
        empText.setAdapter(stringArrayAdapter);
        empText.setThreshold(1);
        empText.requestFocus();
        empText.showDropDown();


    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
