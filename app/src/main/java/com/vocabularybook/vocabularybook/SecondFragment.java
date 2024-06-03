package com.vocabularybook.vocabularybook;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SecondFragment extends Fragment {
    ListView mylist5;
    private Handler handler;
    public static final String TAG = "SecondFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        mylist5 = view.findViewById(R.id.mylist5);
        @SuppressLint("WrongViewCast")
        ProgressBar bar = view.findViewById(R.id.progressBar);
        TextView nodata = view.findViewById(R.id.nodata);
        mylist5.setEmptyView(nodata);
        VocabularyTableManager wordManager = new VocabularyTableManager(getContext());  //将数据库中的内容放入自定义列表中
        List<Word> testList = wordManager.listAll();

        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    ArrayList<Word> list2 = (ArrayList<Word>) msg.obj;
                    bar.setVisibility(View.GONE);
                    nodata.setText("No Data");
                    VocabularyTableManager manager = new VocabularyTableManager(getContext());  //将爬取的内容放入数据库中
                    for(Word word : list2){
                        Word item = new Word(word.getVocabulary(), word.getParaphrase(), word.getTimes());
                        manager.add(item);
                    }
                    refreshFragment();
                }
                super.handleMessage(msg);
            }
        };  //接受子进程信息
        Log.i(TAG,"vocabularyTable's length is " + testList.size());
        if(testList.size() == 0) {
            WordTask wordTask = new WordTask(handler);  //先数据提取,每次只执行一次 即获取数据库资源
            Thread t3 = new Thread(wordTask);
            t3.start();
        }

        bar.setVisibility(View.GONE);   //将加载环不可见

        ArrayList<Word> list1 = new ArrayList<Word>();
        for(Word word : wordManager.listAll()){
            Log.i(TAG,word.getVocabulary() + " " + word.getParaphrase() + " " + word.getTimes());
            list1.add(new Word(word.getVocabulary(),word.getParaphrase(),word.getTimes()));
        }
        WordAdapter adapter = new WordAdapter(getContext(),list1);   //获取上下文是getcontext()
        mylist5.setAdapter(adapter);    //设置适配器
        Log.i(TAG,"vocabularyTable's length is " + testList.size());
        return view;
    }
    private void refreshFragment() {    //重新更新布局
        ArrayList<Word> list1 = new ArrayList<Word>();
        VocabularyTableManager wordManager = new VocabularyTableManager(getContext());
        for(Word word : wordManager.listAll()){
            Log.i(TAG,word.getVocabulary() + " " + word.getParaphrase() + " " + word.getTimes());
            list1.add(new Word(word.getVocabulary(),word.getParaphrase(),word.getTimes()));
        }
        WordAdapter adapter = new WordAdapter(getContext(),list1);   //获取上下文是getcontext()
        mylist5.setAdapter(adapter);    //设置适配器
    }

}