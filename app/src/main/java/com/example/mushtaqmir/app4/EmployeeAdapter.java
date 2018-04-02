package com.example.mushtaqmir.app4;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.Gravity;

import java.util.List;

/**
 * Created by Mushtaq.Mir on 3/23/2018.
 */

public class EmployeeAdapter extends ArrayAdapter<String> {
    public EmployeeAdapter(@NonNull Context context, List templateMessages) {
        super(context,R.layout.custom_employee_row , templateMessages);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater templateInflater=LayoutInflater.from(getContext());
        View customeView=templateInflater.inflate(R.layout.custom_employee_row,parent,false);
        String message=getItem(position);
        TextView templateText=(TextView)customeView.findViewById(R.id.employeeText);
        templateText.setGravity(Gravity.CENTER|Gravity.CENTER|Gravity.CENTER_VERTICAL);
       // templateText.setBackgroundColor(Color.YELLOW);
        ImageView templateImage=(ImageView)customeView.findViewById(R.id.employeeImage);
        templateText.setText(message);
        templateImage.setImageResource(R.drawable.employee);

        return customeView;
    }
}
