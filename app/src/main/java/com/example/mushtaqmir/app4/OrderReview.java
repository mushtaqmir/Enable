package com.example.mushtaqmir.app4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;



public class OrderReview extends AppCompatActivity{
    String txtWindshield ="";
    String txtFreeOil ="";
    DbHandler mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_review);

        mydb = new DbHandler(this,null,null,1);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        if(intent != null) {
           final OrderDetails orderDetails = (OrderDetails) intent.getSerializableExtra("Order with payment");

            txtWindshield =  intent.getStringExtra("txtFreeOil");
            txtFreeOil =  intent.getStringExtra("txtWindshield");

            final TextView fuelType = (TextView) findViewById(R.id.FuelType);
            final TextView fuelCategory = (TextView) findViewById(R.id.fuelCategory);
            final TextView fuel = (TextView) findViewById(R.id.Fuel);
            final TextView fuelTxt = (TextView) findViewById(R.id.FuelTxt);
            final Button confirmBtn = (Button) findViewById(R.id.confirmBtn);
            final Button editBtn = (Button) findViewById(R.id.editBtn);
            final TextView paymentMode = (TextView) findViewById(R.id.paymentMode);

            final TextView WindshieldCleanReview = (TextView) findViewById(R.id.WindshieldCleanReview);
            final TextView freeOilRev = (TextView) findViewById(R.id.freeOilRev);


            if(orderDetails!=null) {
                fuelType.setText(orderDetails.getFuelType());
                fuelCategory.setText(orderDetails.getCategory());
                paymentMode.setText(orderDetails.getModeOfPayment());
                WindshieldCleanReview.setText(txtWindshield);
                freeOilRev.setText(txtFreeOil);
                if(orderDetails.getFuelQtyAmtIndentifier() == 0){
                    fuelTxt.setVisibility(View.INVISIBLE);
                    fuel.setText("Please fill FULL TANK");
                }
                else{
                    fuelTxt.setVisibility(View.VISIBLE);
                    if(orderDetails.getFuelQtyAmtIndentifier() == 1){//Amount selected
                        fuelTxt.setText("Fuel Amount");
                        fuel.setText(""+orderDetails.getFuelAmount()+" "+"₹");
                    }
                   else if(orderDetails.getFuelQtyAmtIndentifier() == 2){//Quantity selected
                        fuelTxt.setText("Fuel Quantity");
                        fuel.setText(""+orderDetails.getFuelQty()+" "+"Ltr");
                    }
                }

                //Button OnCLickLISTNER
                confirmBtn.setOnClickListener(
                        new View.OnClickListener(){
                            public void onClick(View v){
                                boolean isInserted = mydb.insertOrder(orderDetails);
                                if(isInserted) {
                                    Intent launchOrderReview = new Intent(OrderReview.this, OrderConfirm.class);
                                    launchOrderReview.putExtra("Order Details Class", orderDetails);
                                    launchOrderReview.putExtra("txtFreeOil",txtFreeOil);
                                    launchOrderReview.putExtra("txtWindshield",txtWindshield);
                                    startActivity(launchOrderReview);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Data Cannot be inserted properly",
                                            Toast.LENGTH_LONG).show();
                                }

                            }
                        } );
                editBtn.setOnClickListener(
                        new View.OnClickListener(){
                            public void onClick(View v){
                                onBackPressed();

                            }
                        } );

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        return true;
    }

}
