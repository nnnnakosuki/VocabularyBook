package com.vocabularybook.vocabularybook;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter {

    public WordAdapter(@NonNull Context context, ArrayList<Word> list) {
        super(context, 0,list);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_item,parent,false);
        }
        Word item = (Word) getItem(position);
        TextView title = (TextView) itemView.findViewById(R.id.itemTitle);
        TextView detail = (TextView) itemView.findViewById(R.id.itemDetail);
//        TextView time = (TextView) itemView.findViewById(R.id.itemTime);  //这里是为了显示学习次数

        title.setText(item.getVocabulary());
        detail.setText("释义: " + item.getParaphrase());
//        time.setText("学习次数: " + item.getTimes());     //这里也是为了显示学习次数
        int defaultColor = ContextCompat.getColor(getContext(),R.color.white);
        itemView.setBackgroundColor(defaultColor);
        try{
            float rate = item.getTimes();
            float blueIntensity = (float) rate / 900f;
            int blueColor = Color.argb((int) (255 * blueIntensity),0,0,255);
            itemView.setBackgroundColor(blueColor);

        }catch (NumberFormatException ex){
        }
        return itemView;
    }
}
