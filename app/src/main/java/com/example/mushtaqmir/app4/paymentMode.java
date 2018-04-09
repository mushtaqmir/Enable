package com.example.mushtaqmir.app4;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.view.Menu;


public class paymentMode extends ToolBarActivity {
    String txtWindshield ="";
    String txtFreeOil ="";
    int buttonEnable = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_type);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        if(intent != null) {
            final OrderDetails orderDetails = (OrderDetails) intent.getSerializableExtra(startFuelDetails.ORDER_DET_CONSTANT);
            txtWindshield =  intent.getStringExtra("txtFreeOil");
            txtFreeOil =  intent.getStringExtra("txtWindshield");
            final RadioGroup modeOfPayRadioGrp = (RadioGroup) findViewById(R.id.modeOfPayRadioGrp);
            final RadioButton payByShellCashCard = (RadioButton) findViewById(R.id.payByShellCashCard);
            final RadioButton payByCash = (RadioButton) findViewById(R.id.payByCash);
            final RadioButton payByCard = (RadioButton) findViewById(R.id.payByCard);

            final Button confirmPayBtn = (Button) findViewById(R.id.confirmPayBtn);
            if (orderDetails != null) {

                modeOfPayRadioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        if(payByShellCashCard.isChecked()){
                            orderDetails.setModeOfPayment(payByShellCashCard.getText().toString());
                            buttonEnable=1;
                        }
                        else if(payByCash.isChecked()){
                            orderDetails.setModeOfPayment(payByCash.getText().toString());
                            buttonEnable=1;
                        }
                        else if(payByCard.isChecked()){
                            orderDetails.setModeOfPayment(payByCard.getText().toString());
                            buttonEnable=1;
                        }
                    }
                });

                //Button OnCLickLISTNER
                confirmPayBtn.setOnClickListener(
                        new View.OnClickListener(){
                            public void onClick(View v){
                                if(buttonEnable ==1) {

                                    Intent launchOrderReview = new Intent(paymentMode.this, OrderReview.class);
                                    launchOrderReview.putExtra("txtFreeOil", txtFreeOil);
                                    launchOrderReview.putExtra("txtWindshield", txtWindshield);
                                    launchOrderReview.putExtra("Order with payment", orderDetails);
                                    startActivity(launchOrderReview);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Please enter Payment Type",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        } );

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        return true;
    }
}
