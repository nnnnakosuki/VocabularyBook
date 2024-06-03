package com.vocabularybook.vocabularybook;

public class HistoryWord {
    int id;
    String date,vocabulary,paraphrase;
    public HistoryWord() {
        super();
        vocabulary = "";
        paraphrase = "";
        date = "";
    }

    public HistoryWord(String vocabulary, String paraphrase, String date) {
        super();
        this.vocabulary = vocabulary;
        this.paraphrase = paraphrase;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(String vocabulary) {
        this.vocabulary = vocabulary;
    }

    public String getParaphrase() {
        return paraphrase;
    }

    public void setParaphrase(String paraphrase) {
        this.paraphrase = paraphrase;
    }
}
