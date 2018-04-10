package com.example.mushtaqmir.app4;



import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Locale;

public class TemplateActivity extends ToolBarActivity {

    TextToSpeech textToSpeech;
    String message;
    ArrayAdapter templateAdapter;
    List<String> msgList;
    EditText editText;
    Button addBtn;
    DbHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            dbHandler=new DbHandler(this,Util.getProperty("DATABASE_NAME",this),null,1);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        String[] messageList={"Hi, how are you.","How can i help you.","Welcome"};
        msgList=new ArrayList<String>(Arrays.asList(messageList));

       // ListAdapter templateAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,messageList);
        msgList.addAll(dbHandler.getAllMessages());
       templateAdapter= new CustomAdapter(this,msgList);
        ListView templateListView=(ListView)findViewById(R.id.templateListView);
        templateListView.setAdapter(templateAdapter);
        //For delete
        registerForContextMenu(templateListView);
        //Add Template
        editText=(EditText)findViewById(R.id.templateTextInput);
        addBtn=(Button)findViewById(R.id.templateAddButton);
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString().trim();
                if(!message.equals("")) {
                    msgList.add(message);
                  dbHandler.addTemplateMessages(new TemplateMessages(message));
                //  msgList.clear();
                 // msgList=dbHandler.getAllMessages();
                  editText.setText("");
                  templateAdapter.notifyDataSetChanged();
                }
            }
        });
        templateListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                       message=String.valueOf(parent.getItemAtPosition(position));
                      //insert code here
                        textToSpeech=new TextToSpeech(TemplateActivity.this, new TextToSpeech.OnInitListener() {
                            @Override
                            public void onInit(int status) {
                                if(status == TextToSpeech.SUCCESS){
                                    textToSpeech.setLanguage(Locale.US);
                                 //   textToSpeech.setPitch((float) 0.6);
                                    textToSpeech.speak(message, TextToSpeech.QUEUE_ADD, null);
                                }
                            }
                        });
                    }
                }
        );

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.context_menu_file,menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo obj= (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete:
                dbHandler.deleteTemplateMessages(new TemplateMessages(msgList.get(obj.position)));
                msgList.remove(obj.position);
               templateAdapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }


}
