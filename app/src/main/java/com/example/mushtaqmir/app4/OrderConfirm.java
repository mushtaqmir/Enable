package com.example.mushtaqmir.app4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Anshul.Kumar on 3/27/2018.
 */

public class OrderConfirm extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_confirm);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        if (intent != null) {
            final OrderDetails orderDetails = (OrderDetails) intent.getSerializableExtra("Order Details Class");


            final TextView fuelType = (TextView) findViewById(R.id.FuelType);
            final TextView fuelCategory = (TextView) findViewById(R.id.fuelCategory);
            final TextView fuel = (TextView) findViewById(R.id.Fuel);
            final TextView fuelTxt = (TextView) findViewById(R.id.FuelTxt);
            final Button StartNew = (Button) findViewById(R.id.StartNew);
            final TextView paymentMode = (TextView) findViewById(R.id.paymentMode);
            if (orderDetails != null) {
                fuelType.setText(orderDetails.getFuelType());
                fuelCategory.setText(orderDetails.getCategory());
                paymentMode.setText(orderDetails.getModeOfPayment());
                if (orderDetails.getFuelQtyAmtIndentifier() == 0) {
                    fuelTxt.setVisibility(View.INVISIBLE);
                    fuel.setText("Please fill FULL TANK");
                } else {
                    fuelTxt.setVisibility(View.VISIBLE);
                    if (orderDetails.getFuelQtyAmtIndentifier() == 1) {//Amount selected
                        fuelTxt.setText("Fuel Amount");
                        fuel.setText("" + orderDetails.getFuelAmount() + " " + "₹");
                    } else if (orderDetails.getFuelQtyAmtIndentifier() == 2) {//Quantity selected
                        fuelTxt.setText("Fuel Quantity");
                        fuel.setText("" + orderDetails.getFuelQty() + " " + "Ltr");
                    }
                }

                String toPrint = "Order Confirm " + " " + "Fuel Type :-" + orderDetails.getFuelType() + " " +
                        "Fuel Category :-" + " " + orderDetails.getCategory() + " " +
                        "Fuel Quantity :-" + " " + orderDetails.getFuelQty() + " " +
                        "Fuel Amount :-" + orderDetails.getFuelAmount() + " " +
                        "Fuel Full tank option :-" + orderDetails.isFullTank();
                Toast.makeText(getApplicationContext(), toPrint,
                        Toast.LENGTH_LONG).show();

                //Button OnCLickLISTNER
                StartNew.setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View v) {
                                Intent launchStart= new Intent(OrderConfirm.this,StartActivity.class);
                                startActivity(launchStart);
                            }

                        });

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
