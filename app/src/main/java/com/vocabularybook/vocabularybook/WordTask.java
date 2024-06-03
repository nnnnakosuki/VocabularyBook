package com.vocabularybook.vocabularybook;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class WordTask implements Runnable {
    public static final String TAG = "WordTask";
    private Handler handler;
    public WordTask(Handler handler){
        this.handler = handler;
    }
    @Override
    public void run() {
        Log.i(TAG,"run: sonThread");
        Bundle retbundle = new Bundle();
        ArrayList<Word> itemlist = new ArrayList<Word>();
        try {
            String url = "https://nc.xdf.cn/gaozhong/gk/yy/202109/278626888.html";
            Document doc = Jsoup.connect(url).get();
            Element div = doc.getElementsByTag("div").get(41);
            Elements ps = div.getElementsByTag("p");
            for(Element p : ps) {
                char s = p.text().trim().charAt(2);
                if(( s >= 'a' && s <= 's' ) || ( s >= 'A' && s <= 'S' )){
                    String text = p.text();
                    int first = text.indexOf("[");
                    if(first == -1) continue;
                    String wordCut = text.substring(2,first);
                    String text_ = text.substring(first);
                    int second = text_.indexOf("]");
                    String paraphraseCut = text_.substring(second + 1);
                    Word item = new Word(wordCut,paraphraseCut,0);
                    itemlist.add(item);
                    Log.i(TAG,wordCut + "-->" + paraphraseCut);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Message msg = handler.obtainMessage(7,itemlist);
        handler.sendMessage(msg);
    }
}
