package com.vocabularybook.vocabularybook;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThirdFragment extends Fragment {
    Handler handler;
    EditText searchText;
    Button searchBtn;
    ListView historyList;
    public static final String TAG = "ThirdFragment";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        searchText = view.findViewById(R.id.searchText);
        searchBtn = view.findViewById(R.id.searchBtn);
        historyList = view.findViewById(R.id.historyList);
        ProgressBar bar = view.findViewById(R.id.progressBar);
        TextView nodata = view.findViewById(R.id.nodata);
        historyList.setEmptyView(nodata);
        bar.setVisibility(View.GONE);   //这里没必要显示加载环

        View.OnClickListener incrementListener = new View.OnClickListener() {   //设置按钮的监听
            @Override
            public void onClick(View v) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); //定义格式
                try {
                    Date date = df.parse(searchText.getText().toString());//将当前时间转换成特定格式的时间字符串，便于查找
                    String dateStr = df.format(date);
                    Log.i(TAG,"输入日期为:" + searchText.getText().toString());
                    if(searchText.getText().toString().length() == 0 || searchText.getText() == null){
                        Toast.makeText(getContext(), "请输入内容", Toast.LENGTH_SHORT).show();
                    }else{
                        HistoryTableManager manager = new HistoryTableManager(getContext());
                        ArrayList<HistoryWord> historyWordList = new ArrayList<HistoryWord>();
                        for(HistoryWord word : manager.listAll()){      //根据日期寻找当日错误的单词
                            String wordDate = word.getDate();
                            if(dateStr.equals(wordDate)){
                                historyWordList.add(word);
                            }
                        }
                        if(historyWordList.size() == 0){    //没有找到历史错误单词
                            Toast.makeText(getContext(), "未查询到历史错误单词", Toast.LENGTH_SHORT).show();
                            nodata.setText("NO DATA");
                        }else{
                            nodata.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "查询成功", Toast.LENGTH_SHORT).show();
                            HistoryWordAdapter adapter = new HistoryWordAdapter(getContext(),historyWordList);  //设置适配器
                            historyList.setAdapter(adapter);
                        }
                    }
                } catch (ParseException e) {
                    Toast.makeText(getContext(), "日期格式错误,或者未进行输入,请重新输入!", Toast.LENGTH_SHORT).show();
                }
                searchText.setText("");     //搜索时顺便清空一下内容
            }
        };
        searchBtn.setOnClickListener(incrementListener);  //将按钮捆绑点击事件
        return view;
    }
}