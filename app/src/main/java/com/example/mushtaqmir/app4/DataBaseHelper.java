package com.example.mushtaqmir.app4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Anshul.Kumar on 4/3/2018.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME=DbHandler.DATABASE_NAME;
    public static final int DATABASE_VERSION=DbHandler.DATABASE_VERSION;
    public static final String TABLE_NAME = "order_history";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public static final String ID = "ORDER_ID";
    public static final String FUEL_TYPE = "FUEL_TYPE";
    public static final String FUEL_CATEGORY = "FUEL_CATEGORY";
    public static final String FUEL_QTY = "FUEL_QTY";
    public static final String FUEL_AMOUNT = "FUEL_AMOUNT";
    public static final String FULL_TANK = "FULL_TANK";
    public static final String MODE_OF_PAYMENT = "MODE_OF_PAYMENT";


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + FUEL_TYPE + " TEXT,"
                    + FUEL_CATEGORY + " TEXT,"
                    + FUEL_QTY + " DECIMAL(17,2),"
                    + FUEL_AMOUNT + " DECIMAL(17,2),"
                    + FULL_TANK + " BOOLEAN,"
                    + MODE_OF_PAYMENT + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";



    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertOrder(OrderDetails orderDetails){
            ContentValues contentValue=new ContentValues();
            contentValue.put(FUEL_TYPE,orderDetails.getFuelType());
            contentValue.put(FUEL_CATEGORY,orderDetails.getCategory());
            contentValue.put(FUEL_QTY,orderDetails.getFuelQty());
            contentValue.put(FUEL_AMOUNT,orderDetails.getFuelAmount());
            contentValue.put(FULL_TANK,orderDetails.isFullTank());
            contentValue.put(MODE_OF_PAYMENT,orderDetails.getModeOfPayment());

            SQLiteDatabase db=getWritableDatabase();
            Long result = db.insert(TABLE_NAME,null,contentValue);
            db.close();
            if(result == -1) {

                return false;
            }
            else {

                return true;
            }
    }

    public List getAllOrders(){
        List<OrderDetails> allOrders = new ArrayList<>();
        SQLiteDatabase db=getWritableDatabase();
        String query="SELECT * FROM "+TABLE_NAME+" desc;";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            OrderDetails singleOrder = new OrderDetails();
            singleOrder.setFuelQtyAmtIndentifier(c.getInt(c.getColumnIndex(ID)));
            singleOrder.setFuelType(c.getString(c.getColumnIndex(FUEL_TYPE)));
            singleOrder.setCategory(c.getString(c.getColumnIndex(FUEL_CATEGORY)));
            singleOrder.setFuelQty(c.getDouble(c.getColumnIndex(FUEL_QTY)));
            singleOrder.setFuelAmount(c.getDouble(c.getColumnIndex(FUEL_AMOUNT)));
            singleOrder.setFullTank(c.getInt(c.getColumnIndex(FULL_TANK))>0);
            singleOrder.setModeOfPayment(c.getString(c.getColumnIndex(MODE_OF_PAYMENT)));
            singleOrder.setOrderDateTime(changeDateFormat(c.getString(c.getColumnIndex(COLUMN_TIMESTAMP))));
            allOrders.add(singleOrder);
            c.moveToNext();
        }

        db.close();
        return allOrders;
    }

    public Date changeDateFormat(String date){
        DateFormat df = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss z");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date startDate =new Date();
        try {
            startDate = df.parse(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }
}
