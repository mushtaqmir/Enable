package com.example.mushtaqmir.app4;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

/**
 * Created by Anshul.Kumar on 4/3/2018.
 */

public class OrderHistory extends AppCompatActivity {

    String[] spaceProbesHeader={"ID","Fuel Type","Fuel Category","Full Tank","Fuel Quantity","Fuel Amount","Date","Mode of payment"};
    String [][] spaceProbes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_history);

        final TableView<String[]> tb = (TableView<String[]>) findViewById(R.id.tableHistoryView);
        tb.setColumnCount(8);
        tb.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<OrderDetails> orderHistoryList = (ArrayList<OrderDetails>) args.getSerializable("ARRAYLIST");

        //populate list
      orderDetailsHis(orderHistoryList);

        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this,spaceProbesHeader));
        tb.setDataAdapter(new SimpleTableDataAdapter(this,spaceProbes));
    }

    public void orderDetailsHis(ArrayList<OrderDetails> orderDetailsList) {

        if (orderDetailsList != null) {
            spaceProbes = new String[orderDetailsList.size()][8];

            for(int i=0;i<orderDetailsList.size();i++){
                OrderDetails orderDetails = orderDetailsList.get(i);

                spaceProbes[i][0]=""+orderDetails.getFuelQtyAmtIndentifier();
                spaceProbes[i][1] =""+orderDetails.getFuelType();
                spaceProbes[i][2] =""+orderDetails.getCategory();
                spaceProbes[i][3] =""+orderDetails.isFullTank();
                spaceProbes[i][4] =""+orderDetails.getFuelQty();
                spaceProbes[i][5] =""+orderDetails.getFuelAmount();
                spaceProbes[i][6] =""+orderDetails.getOrderDateTime();
                spaceProbes[i][7] =""+orderDetails.getModeOfPayment();
            }
        }
    }

}
