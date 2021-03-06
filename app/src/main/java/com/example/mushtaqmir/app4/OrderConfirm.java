package com.example.mushtaqmir.app4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anshul.Kumar on 3/27/2018.
 */

public class OrderConfirm extends ToolBarActivity  {

    String txtWindshield = "";
    String txtFreeOil = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_confirm);


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        if (intent != null) {
            final OrderDetails orderDetails = (OrderDetails) intent.getSerializableExtra("Order Details Class");
            txtWindshield =  intent.getStringExtra("txtFreeOil");
            txtFreeOil =  intent.getStringExtra("txtWindshield");

            final TextView fuelType = (TextView) findViewById(R.id.FuelType);
            final TextView fuelCategory = (TextView) findViewById(R.id.fuelCategory);
            final TextView fuel = (TextView) findViewById(R.id.Fuel);
            final TextView fuelTxt = (TextView) findViewById(R.id.FuelTxt);
            final TextView paymentMode = (TextView) findViewById(R.id.paymentMode);
            final TextView WindshieldCleanReview = (TextView) findViewById(R.id.WindshieldCleanReview);
            final TextView freeOilRev = (TextView) findViewById(R.id.freeOilRev);



            if (orderDetails != null) {
                fuelType.setText(orderDetails.getFuelType());
                fuelCategory.setText(orderDetails.getCategory());
                paymentMode.setText(orderDetails.getModeOfPayment());
                WindshieldCleanReview.setText(txtWindshield);
                freeOilRev.setText(txtFreeOil);
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



           }
        }
    }


}
