package com.example.mushtaqmir.app4;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import org.apache.http.entity.StringEntity;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Locale;

public class FeedBack extends ToolBarActivity {
    private EditText feedbackText;
    private Button fSubmitBtn;
    private TextToSpeech textToSpeech;
    private String ratingSpeech;
    private String thanksRatingSpeech;
    private String message ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ratingSpeechOut();
        feedbackText =(EditText)findViewById(R.id.feedbackText);
        fSubmitBtn=(Button)findViewById(R.id.fSubmitBtn);
        //Added for rating bar
//        ratingBar = (RatingBar) findViewById(R.id.ratingBarFeedback);
  //      ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
    /*        public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                switch (String.valueOf(rating)) {
                    case "1.0":
                        ratingSentToMsg = "Very Bad !!";
                        break;
                    case "2.0":
                        ratingSentToMsg = "Bad !!";
                        break;
                    case "3.0":
                        ratingSentToMsg = "Good !!";
                        break;
                    case "4.0":
                        ratingSentToMsg = "Very Good !!";
                        break;
                    case "5.0":
                        ratingSentToMsg = "Excellent !!";
                        break;
                    default:
                        ratingSentToMsg = "";
                        break;

                }
                feedbackText.setText(ratingSentToMsg + " " + message);
            }
        });
    */    //Setting it outside as it should not ovverride

            fSubmitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String messageFinal = feedbackText.getText().toString().trim();

                if(!messageFinal.equals("")) {

                    //new AzureConnector().connect(FeedBack.this,message);
                    new AzureConnector().getRequest(FeedBack.this,messageFinal);
                    //reseting all the values
                    message = "";
                    feedbackText.setText("");
                    thanksRatingSpeechOut();
                    Toast.makeText(FeedBack.this,"Feedback Sent",Toast.LENGTH_LONG).show();
                    //on submit redirect to main activity
                 //  onButtonShowPopupWindowClick(v);
                    openMainActivity();
                }
            }
        });


      /*
      *     public void sendEventClick(View view) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://<namespace>.servicebus.windows.net/<eventhub name>/publishers/<ClientId>/messages";
        client.addHeader("Authorization","<SAS Signature>");
        JSONObject params = new JSONObject();
        try {
            params.put("DeviceId", 12);
            params.put("Temperature", 98);
            StringEntity entity = new StringEntity(params.toString());
            client.post(this,url,entity,"application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                }
                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                }
            });
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
      *
      * */
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
    public void onTap(View v){
        if(v.getId()==R.id.microphoneBtn){
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
            Toast.makeText(FeedBack.this,"Sorry! Your device doen't support language",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int request_code,int result_code,Intent intent){
        super.onActivityResult(request_code, result_code,intent);

        if(request_code == 200) {
            if (result_code == RESULT_OK && intent != null) {
                ArrayList<String> result = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String message1 ="";
                message1 = result.get(0);
                message = feedbackText.getText().toString().trim();

                if (!message1.equals("") ) {
                    message1 = message +" "+ message1;
                    message1 = message1.trim() +".";
                    feedbackText.setText(message1);

                }
            }
        }
    }
    public void onClear(View v){
        if(v.getId()==R.id.clearBtn){
            message = "";
            feedbackText.setText("");
        }
      }
    public void openMainActivity(){
        Intent intent=new Intent(this,ActivityMain.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
//popup
public void onButtonShowPopupWindowClick(View view) {

    // inflate the layout of the popup window
    LayoutInflater inflater = (LayoutInflater)
            getSystemService(LAYOUT_INFLATER_SERVICE);
    View popupView = inflater.inflate(R.layout.popup_window, null);

    // create the popup window
    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
    boolean focusable = true; // lets taps outside the popup also dismiss it
    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

    // show the popup window
    // which view you pass in doesn't matter, it is only used for the window tolken
    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    // dismiss the popup window when touched
//    popupView.setOnTouchListener(new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            popupWindow.dismiss();
//            return true;
//        }
//    });
}

    public void ratingSpeechOut(){
        ratingSpeech="Please give your feedback.";
        Toast.makeText(FeedBack.this,ratingSpeech,Toast.LENGTH_LONG).show();
        textToSpeech=new TextToSpeech(FeedBack.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.speak(ratingSpeech, TextToSpeech.QUEUE_ADD, null);
                }
            }
        });

    }
    public void thanksRatingSpeechOut(){
        thanksRatingSpeech="Thank you for the feedback.";
        //Toast.makeText(FeedBack.this,thanksRatingSpeech,Toast.LENGTH_LONG).show();
        textToSpeech=new TextToSpeech(FeedBack.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.speak(thanksRatingSpeech, TextToSpeech.QUEUE_ADD, null);
                }
            }
        });

    }

}
