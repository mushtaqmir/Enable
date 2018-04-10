package com.example.mushtaqmir.app4;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnDpWidthModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

/**
 * Created by Anshul.Kumar on 4/3/2018.
 */

public class OrderHistory extends ToolBarActivity {

    String[] spaceProbesHeader={"ID","Type","Category","Full Tank","Quantity","Amount","Date","Payment Mode"};
    String [][] spaceProbes;
    ShortById sbID = new ShortById();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_history);

        final SortableTableView<String[]> tb = (SortableTableView<String[]>) findViewById(R.id.tableHistoryView);
        tb.setColumnCount(8);
        tb.setHeaderBackgroundColor(Color.parseColor("#ffc400"));
        tb.setColumnWeight(2,2);
        tb.setColumnWeight(3,1);
        tb.setColumnWeight(4,1);
        tb.setColumnWeight(6,2);
        tb.setColumnWeight(7,2);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<OrderDetails> orderHistoryList = (ArrayList<OrderDetails>) args.getSerializable("ARRAYLIST");

        //populate list
      orderDetailsHis(orderHistoryList);

        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this,spaceProbesHeader));
        tb.setDataAdapter(new SimpleTableDataAdapter(this,spaceProbes));

        tb.setColumnComparator(0, sbID);

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
