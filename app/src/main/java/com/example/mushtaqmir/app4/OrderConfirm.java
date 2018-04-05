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

public class OrderConfirm extends AppCompatActivity  {
    DbHandler mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_confirm);

        try{
            mydb = new DbHandler(this,Util.getProperty("DATABASE_NAME",this),null,1);
        }
        catch (IOException e){
            e.printStackTrace();
        }

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

            final Button showOrderHistory = (Button) findViewById(R.id.showOrderHistory);

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
                                Intent launchStart= new Intent(OrderConfirm.this,ActivityMain.class);
                                startActivity(launchStart);
                            }

                        });
                showOrderHistory.setOnClickListener(
                        new View.OnClickListener(){
                            public void onClick(View v){
                                List<OrderDetails> allOrders = mydb.getAllOrders();
                               if( allOrders.isEmpty()){
                                   showMessage("Error","Nothing stored");
                               }
                               else {
                                   Intent launchOrderHistory = new Intent(OrderConfirm.this, OrderHistory.class);
                                   Bundle args = new Bundle();
                                   args.putSerializable("ARRAYLIST", (Serializable) allOrders);
                                   launchOrderHistory.putExtra("BUNDLE", args);
                                   startActivity(launchOrderHistory);
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
    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new  AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public String orderDetailsToString(List<OrderDetails> orderDetailsList){
        StringBuffer toPrint = new StringBuffer();
        if(orderDetailsList !=null) {
            for (OrderDetails order : orderDetailsList){
                toPrint.append("---------------- ");
                toPrint.append("Order NO :- ");
                toPrint.append(" " + order.getFuelQtyAmtIndentifier());
                toPrint.append("---------------- ");
                toPrint.append("\n");
                toPrint.append(" " +"Fuel Type :-" + order.getFuelType());
                toPrint.append("\n");
                toPrint.append(" " +"Category :-" + order.getCategory());
                toPrint.append("\n");
                toPrint.append(" " +"FuelQty :-" + order.getFuelQty());
                toPrint.append("\n");
                toPrint.append(" " +"Fuel Amount :-" + order.getFuelAmount());
                toPrint.append("\n");
                toPrint.append(" " +"DATE :-" + order.getOrderDateTime());
                toPrint.append("\n");
                toPrint.append(" " +"FULL TANK :-" + order.isFullTank());
                toPrint.append("\n");
                toPrint.append(" " +"Payment Mode :-" + order.getModeOfPayment());


                toPrint.append("\n");
            }
        }
        return toPrint.toString();
    }
}
