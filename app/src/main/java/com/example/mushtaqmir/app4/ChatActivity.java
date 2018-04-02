package com.example.mushtaqmir.app4;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.speech.tts.TextToSpeech;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.view.Gravity;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Locale;
public class ChatActivity extends Activity {
   private ArrayAdapter customerAdapter;
   private List<Message> custMsgList;
    ListView custMsgListView;
    ArrayAdapter empAdapter;
    List<String> empMsgList;
  private  EditText custText;
  private  Button custBtn;
    EditText empText;
    Button empBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        String[] messageList1={"Hi"};
        String[] messageList2={"Hello"};
        //for Customer
        custText =(EditText)findViewById(R.id.custText);
        custBtn=(Button)findViewById(R.id.custBtn);
        custMsgList=new ArrayList<Message>();
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
                   // empMsgList.add("");
                    custText.setText("");
                    customerAdapter.notifyDataSetChanged();
                   // empAdapter.notifyDataSetChanged();
                }
            }
        });


        //for Employee
//               empMsgList=new ArrayList<String>(Arrays.asList(messageList2));
//        empAdapter= new EmployeeAdapter(this,empMsgList);
//
//        ListView empMsgListView=(ListView)findViewById(R.id.empMsgListView);
//        empMsgListView.setAdapter(empAdapter);
        empText=(EditText)findViewById(R.id.empText);
        empBtn=(Button)findViewById(R.id.empBtn);
        empBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String message = empText.getText().toString().trim();
                if(!message.equals("")) {
                    Message msg=new Message(message,true);
                    custMsgList.add(msg);
                   // custMsgList.add("");
                    empText.setText("");
                    //empAdapter.notifyDataSetChanged();
                    customerAdapter.notifyDataSetChanged();
                }
            }
        });





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
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"");
        try {
            startActivityForResult(intent,100);
        }
        catch (ActivityNotFoundException e){
            Toast.makeText(ChatActivity.this,"Sorry! Your device doen't support language",Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int request_code,int result_code,Intent intent){
    super.onActivityResult(request_code, result_code,intent);
    switch(request_code){

        case 100:if(result_code==RESULT_OK && intent != null){
            ArrayList<String> result= intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String message = result.get(0);;
            if(!message.equals("")) {
                Message msg=new Message(message,false);
                custMsgList.add(msg);
              //  empMsgList.add("");
                custText.setText("");
                customerAdapter.notifyDataSetChanged();
              //  empAdapter.notifyDataSetChanged();
            }
        }
        break;
    }
    }
}
