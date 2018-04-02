package com.example.mushtaqmir.app4;

import android.content.Context;
import android.view.Gravity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Mushtaq.Mir on 3/21/2018.
 */

public class CustomAdapter extends ArrayAdapter<String> {

    public CustomAdapter(@NonNull Context context, List templateMessages) {
        super(context,R.layout.custom_row , templateMessages);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater templateInflater=LayoutInflater.from(getContext());
        View customeView=templateInflater.inflate(R.layout.custom_row,parent,false);
            String message=getItem(position);
        TextView templateText=(TextView)customeView.findViewById(R.id.templateText);
        templateText.setGravity(Gravity.CENTER|Gravity.LEFT|Gravity.CENTER_VERTICAL);
       ImageView templateImage=(ImageView)customeView.findViewById(R.id.templateImage);
       templateText.setText(message);
       templateImage.setImageResource(R.drawable.speakerphone);
        return customeView;
    }
}
