package com.vocabularybook.vocabularybook;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FirstFragment extends Fragment {
    private Handler handler;
    private String TAG = "FirstFragment",ans = "";
    Button choice1,choice2,choice3,choice4;
    TextView wordText;
    private int length,num,r1,r2,r3;
    private List<Word> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        generateRandomNumbers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        wordText = view.findViewById(R.id.WordText);
        choice1 = view.findViewById(R.id.choice1);
        choice2 = view.findViewById(R.id.choice2);
        choice3 = view.findViewById(R.id.choice3);
        choice4 = view.findViewById(R.id.choice4);
        VocabularyTableManager manager = new VocabularyTableManager(getContext());
        list = manager.listAll();    //全部的数据
        length = list.size();   //长度值改变
        Log.i(TAG,"length = " + length);
        Bundle args = getArguments();
        num = args.getInt("num");
        r1 = args.getInt("r1");
        r2 = args.getInt("r2");
        r3 = args.getInt("r3");
        if(num == r1 && r1 == r2 && r2 == r3){      //是为了修复刚进入会出现
            refreshFragment();       // 刷新 Fragment 页面的操作
        }

        Word word = list.get(num);  //获取这次要背的单词
        ArrayList<String> ansList = new ArrayList<String>();
        ans = list.get(num).getParaphrase(); //取出答案
        wordText.setText(word.getVocabulary());     //显示单词
        ansList.add(ans);
        ansList.add(list.get(r1).getParaphrase());
        ansList.add(list.get(r2).getParaphrase());
        ansList.add(list.get(r3).getParaphrase());
        Collections.shuffle(ansList);   //打乱答案顺序

        Log.i(TAG,"ans:   " + ans); //打印一下答案
        for(int i = 0;i < 4;i ++ ){     //打印一下备选项
            Log.i(TAG,"choice:   " + ansList.get(i));
        }

        choice1.setText(ansList.get(0));
        choice2.setText(ansList.get(1));
        choice3.setText(ansList.get(2));
        choice4.setText(ansList.get(3));

        View.OnClickListener incrementListener = new View.OnClickListener() {   //设置按钮的监听
            @Override
            public void onClick(View v) {
                Log.i(TAG,"click:");
                choice1.setEnabled(false);  //由于有延迟判断 所以要把按钮设置为不可点击
                choice2.setEnabled(false);
                choice3.setEnabled(false);
                choice4.setEnabled(false);
                Word updateWord = new Word(word.getVocabulary(),word.getParaphrase(),word.getTimes() + 1);
                manager.update(updateWord);     //更新学习次数
                boolean flag = true;
                if(v.getId() == R.id.choice1){
                    if(choice1.getText().equals(ans)){
                        choice1.setBackgroundColor(Color.GREEN);
                    }else{
                        flag = false;
                        choice1.setBackgroundColor(Color.RED);
                        if(choice2.getText().equals(ans)) choice2.setBackgroundColor(Color.GREEN);
                        else if(choice3.getText().equals(ans)) choice3.setBackgroundColor(Color.GREEN);
                        else choice4.setBackgroundColor(Color.GREEN);
                    }
                }
                else if(v.getId() == R.id.choice2){
                    if(choice2.getText().equals(ans)){
                        choice2.setBackgroundColor(Color.GREEN);
                    }else{
                        flag = false;
                        choice2.setBackgroundColor(Color.RED);
                        if(choice1.getText().equals(ans)) choice1.setBackgroundColor(Color.GREEN);
                        else if(choice3.getText().equals(ans)) choice3.setBackgroundColor(Color.GREEN);
                        else choice4.setBackgroundColor(Color.GREEN);
                    }
                }
                else if(v.getId() == R.id.choice3){
                    if(choice3.getText().equals(ans)){
                        choice3.setBackgroundColor(Color.GREEN);
                    }else{
                        flag = false;
                        choice3.setBackgroundColor(Color.RED);
                        if(choice2.getText().equals(ans)) choice2.setBackgroundColor(Color.GREEN);
                        else if(choice1.getText().equals(ans)) choice3.setBackgroundColor(Color.GREEN);
                        else choice4.setBackgroundColor(Color.GREEN);
                    }
                }
                else {
                    if(choice4.getText().equals(ans)){
                        choice4.setBackgroundColor(Color.GREEN);
                    }else{
                        flag = false;
                        choice4.setBackgroundColor(Color.RED);
                        if(choice2.getText().equals(ans)) choice2.setBackgroundColor(Color.GREEN);
                        else if(choice3.getText().equals(ans)) choice3.setBackgroundColor(Color.GREEN);
                        else choice1.setBackgroundColor(Color.GREEN);
                    }
                }
                if(!flag){  //将错误单词放入数据库中
                    HistoryTableManager historyTableManager = new HistoryTableManager(getContext());    //历史错误数据库
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); //定义格式
                    java.util.Date date = new Date();   //获得当前时间
                    String today = df.format(date);  //将当前时间转换成特定格式的时间字符串,这样便可以插入到数据库中
                    Log.i(TAG,"today is: " + today);
                    HistoryWord wrongWord = new HistoryWord(list.get(num).getVocabulary(),list.get(num).getParaphrase(),today);
                    historyTableManager.add(wrongWord);
                }
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        choice1.setEnabled(true);
                        choice2.setEnabled(true);
                        choice3.setEnabled(true);
                        choice4.setEnabled(true);
                        int initialColor = Color.parseColor("#673AB7"); // 设置回重新的颜色
                        choice1.setBackgroundColor(initialColor);
                        choice2.setBackgroundColor(initialColor);
                        choice3.setBackgroundColor(initialColor);
                        choice4.setBackgroundColor(initialColor);
                        refreshFragment();       // 刷新 Fragment 页面的操作
                    }
                }, 1000); // 1000 毫秒即 1 秒
            }
        };
        choice1.setOnClickListener(incrementListener);  //将按钮捆绑点击事件
        choice2.setOnClickListener(incrementListener);
        choice3.setOnClickListener(incrementListener);
        choice4.setOnClickListener(incrementListener);
        return view;
    }
    private void generateRandomNumbers() {  //生成随机数后放入bundle中
        Bundle args = new Bundle();
        args.putInt("num", (int) (Math.random() * length));
        args.putInt("r1", (int) (Math.random() * length));
        args.putInt("r2", (int) (Math.random() * length));
        args.putInt("r3", (int) (Math.random() * length));
        setArguments(args);
    }
    private void refreshFragment() {    //重新更新布局
        generateRandomNumbers(); // 重新生成随机数

        VocabularyTableManager manager = new VocabularyTableManager(getContext());

        Bundle args = getArguments();
        num = args.getInt("num");
        r1 = args.getInt("r1");
        r2 = args.getInt("r2");
        r3 = args.getInt("r3");

        Word word = list.get(num);  // 获取这次要背的单词
        ArrayList<String> ansList = new ArrayList<String>();
        ans = list.get(num).getParaphrase(); // 取出答案
        wordText.setText(word.getVocabulary());     // 显示单词
        ansList.add(ans);
        ansList.add(list.get(r1).getParaphrase());
        ansList.add(list.get(r2).getParaphrase());
        ansList.add(list.get(r3).getParaphrase());
        Collections.shuffle(ansList);   // 打乱答案顺序

        // 更新按钮上的文字
        choice1.setText(ansList.get(0));
        choice2.setText(ansList.get(1));
        choice3.setText(ansList.get(2));
        choice4.setText(ansList.get(3));

        // 重置按钮的背景颜色
        int initialColor = Color.parseColor("#673AB7");
        choice1.setBackgroundColor(initialColor);
        choice2.setBackgroundColor(initialColor);
        choice3.setBackgroundColor(initialColor);
        choice4.setBackgroundColor(initialColor);
    }

}