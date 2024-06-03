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

public class HistoryWordAdapter extends ArrayAdapter {

    public HistoryWordAdapter(@NonNull Context context, ArrayList<HistoryWord> list) {
        super(context, 0,list);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_item,parent,false);
        }
        HistoryWord item = (HistoryWord) getItem(position);
        TextView title = (TextView) itemView.findViewById(R.id.itemTitle);
        TextView detail = (TextView) itemView.findViewById(R.id.itemDetail);

        title.setText(item.getVocabulary());
        detail.setText("释义: " + item.getParaphrase());

        return itemView;
    }
}
