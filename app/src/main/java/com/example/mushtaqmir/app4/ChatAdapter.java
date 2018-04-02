package com.example.mushtaqmir.app4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.Gravity;
import android.view.View;
import java.util.List;

/**
 * Created by Mushtaq.Mir on 3/26/2018.
 */

public class ChatAdapter extends ArrayAdapter<Message> {
    public static final int employeeTemplate=1;
    public static final int customerTemplate=0;
    //public int isEmployee;
    private View customeView;
    private Context context;
    private List<Message> messages;

    public ChatAdapter(@NonNull Context context,List<Message> templateMessages) {
        super(context,R.layout.custom_employee_row , templateMessages);
       this.messages=templateMessages;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater templateInflater=LayoutInflater.from(getContext());
        if(getItemViewType(position) == employeeTemplate){
            customeView =templateInflater.inflate(R.layout.custom_employee_row,parent,false);
            String message=getItem(position).getMessage();
            TextView templateText=(TextView)customeView.findViewById(R.id.employeeText);
           templateText.setGravity(Gravity.CENTER|Gravity.RIGHT|Gravity.CENTER_VERTICAL);
            // templateText.setBackgroundColor(Color.YELLOW);
            ImageView templateImage=(ImageView)customeView.findViewById(R.id.employeeImage);
            templateText.setText(message);
            templateImage.setImageResource(R.drawable.employee);
        }else{
            customeView=templateInflater.inflate(R.layout.custom_customer_row,parent,false);
            String message=getItem(position).getMessage();
            TextView templateText=(TextView)customeView.findViewById(R.id.customerText);
           templateText.setGravity(Gravity.CENTER|Gravity.LEFT|Gravity.CENTER_VERTICAL);
            // templateText.setBackgroundColor(Color.GREEN);
            ImageView templateImage=(ImageView)customeView.findViewById(R.id.customerImage);
            templateText.setText(message);
            templateImage.setImageResource(R.drawable.customer);
        }


        return customeView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        if(messages.get(position).isEmployee()){
            return 1;
        }
        return 0;
    }
}
