package com.example.mushtaqmir.app4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.TimeZone;

/**
 * Created by Mushtaq.Mir on 3/28/2018.
 */

public class DbHandler extends SQLiteOpenHelper {


    private  String TABLE_TEMPLATES;
    private  String COLUMN_ID;
    private  String COLUMN_MESSAGES;

      //Changes for order history
      public  String TABLE_NAME;
      public  String COLUMN_TIMESTAMP;
      public  String ID;
      public  String FUEL_TYPE;
      public  String FUEL_CATEGORY;
      public  String FUEL_QTY;
      public  String FUEL_AMOUNT;
      public  String FULL_TANK;
      public  String MODE_OF_PAYMENT;




      public DbHandler(Context context, String dataBase, SQLiteDatabase.CursorFactory factory, int version) throws IOException {
        super(context, dataBase, factory, version);
         //Template Table
          TABLE_TEMPLATES=Util.getProperty("TABLE_TEMPLATES",context);
          COLUMN_ID=Util.getProperty("COLUMN_ID",context);
          COLUMN_MESSAGES=Util.getProperty("COLUMN_MESSAGES",context);

          //Table Order
          TABLE_NAME=Util.getProperty("TABLE_NAME",context);
          COLUMN_TIMESTAMP=Util.getProperty("COLUMN_TIMESTAMP",context);
          ID=Util.getProperty("ID",context);
          FUEL_TYPE=Util.getProperty("FUEL_TYPE",context);
          FUEL_CATEGORY=Util.getProperty("FUEL_CATEGORY",context);
          FUEL_QTY=Util.getProperty("FUEL_QTY",context);
          FUEL_AMOUNT=Util.getProperty("FUEL_AMOUNT",context);
          FULL_TANK=Util.getProperty("FULL_TANK",context);
          MODE_OF_PAYMENT=Util.getProperty("MODE_OF_PAYMENT",context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getTemplateQuery());
        db.execSQL(getOrderHistoryQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_TEMPLATES);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);

        onCreate(db);
    }
    public  void addTemplateMessages(TemplateMessages message){
        ContentValues values=new ContentValues();
        values.put(COLUMN_MESSAGES,message.getMessage());
        SQLiteDatabase db=getWritableDatabase();
       long i= db.insert(TABLE_TEMPLATES,null,values);
        db.close();
    }

    public  void deleteTemplateMessages(TemplateMessages message){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_TEMPLATES+" WHERE "+COLUMN_MESSAGES + "=\"" + message.getMessage()+"\";");
        db.close();
    }
    public List getAllMessages(){
    List<String> messageList=new ArrayList<String>();
        SQLiteDatabase db=getWritableDatabase();
        String query="SELECT * FROM "+TABLE_TEMPLATES+";";
        Cursor c=db.rawQuery(query, null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("messages")) !=null){
                messageList.add(c.getString(c.getColumnIndex("messages")));
            }
            c.moveToNext();
        }
        db.close();
        return messageList;
    }


    //Changes for order history------------------------
    public boolean insertOrder(OrderDetails orderDetails){
        ContentValues contentValue=new ContentValues();

        //Changes for concatination of some long string values
        // Changes for category
        String categoryToBeChanged = orderDetails.getCategory();
        String changedCategoryString = "";
        categoryToBeChanged = categoryToBeChanged.trim();// If in case user has given space initially
        String[] splitCategory = categoryToBeChanged.split(" ");
        for(int i=1;i<splitCategory.length;i++){
            changedCategoryString = changedCategoryString + splitCategory[i]+" ";
        }
        changedCategoryString = changedCategoryString.trim();

        //Changes for payment mode
        String paymentToBeChanged = orderDetails.getModeOfPayment();
        String changedPaymentString = "";
        paymentToBeChanged = paymentToBeChanged.trim();// If in case user has given space initially
        String[] splitPayment = paymentToBeChanged.split(" ");
        for(int i=2;i<splitPayment.length;i++){
            changedPaymentString = changedPaymentString + splitPayment[i]+" ";
        }
        changedPaymentString =  changedPaymentString.trim();
        // Changes end
        contentValue.put(FUEL_TYPE,orderDetails.getFuelType());
        contentValue.put(FUEL_CATEGORY,changedCategoryString);
        contentValue.put(FUEL_QTY,orderDetails.getFuelQty());
        contentValue.put(FUEL_AMOUNT,orderDetails.getFuelAmount());
        contentValue.put(FULL_TANK,orderDetails.isFullTank());
        contentValue.put(MODE_OF_PAYMENT,changedPaymentString);
        contentValue.put(COLUMN_TIMESTAMP,insertDateFormat());
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
            singleOrder.setOrderDateTime(c.getString(c.getColumnIndex(COLUMN_TIMESTAMP)));
            allOrders.add(singleOrder);
            c.moveToNext();
        }

        db.close();
        return allOrders;
    }


    public String insertDateFormat(){
        String stringDate = new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(new Date());
        return stringDate;
    }



    public String getOrderHistoryQuery(){

        String CREATE_TABLE_ORDER_HISTORY =
                "CREATE TABLE " + TABLE_NAME + "("
                        + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + FUEL_TYPE + " TEXT,"
                        + FUEL_CATEGORY + " TEXT,"
                        + FUEL_QTY + " DECIMAL(17,2),"
                        + FUEL_AMOUNT + " DECIMAL(17,2),"
                        + FULL_TANK + " BOOLEAN,"
                        + MODE_OF_PAYMENT + " TEXT,"
                        + COLUMN_TIMESTAMP + " TEXT "
                        + ")";
        return CREATE_TABLE_ORDER_HISTORY;
    }

    public String getTemplateQuery(){

        String query="CREATE TABLE "+ TABLE_TEMPLATES +" (" +
                COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MESSAGES + " TEXT " +
                ");";
        return query;
    }

}
