package com.example.mushtaqmir.app4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class ToolBarActivity extends AppCompatActivity {

    DbHandler mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar);
        try {
            mydb = new DbHandler(this, Util.getProperty("DATABASE_NAME", this), null, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.history: {
                List<OrderDetails> allOrders = mydb.getAllOrders();
                if (allOrders.isEmpty()) {
                    //  showMessage("Error", "Nothing stored");
                } else {
                    Intent launchOrderHistory = new Intent(ToolBarActivity.this, OrderHistory.class);
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST", (Serializable) allOrders);
                    launchOrderHistory.putExtra("BUNDLE", args);
                    startActivity(launchOrderHistory);
                }

            }

            return true;
            case R.id.home:
                Intent intent=new Intent(this,ActivityMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
