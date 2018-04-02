package com.example.mushtaqmir.app4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.List;
import java.util.ArrayList;

  /**
 * Created by Mushtaq.Mir on 3/28/2018.
 */

public class DbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="app.db";
    private static final String TABLE_TEMPLATES ="template";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_MESSAGES="messages";


    public DbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+ TABLE_TEMPLATES +" (" +
                COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MESSAGES + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_TEMPLATES);
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
}
