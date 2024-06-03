package com.vocabularybook.vocabularybook;

public class Word {
    private String vocabulary,paraphrase;
    private int times;
    private int id;

    public Word() {
        super();
        vocabulary = "";
        paraphrase = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Word(String vocabulary, String paraphrase, int times) {
        super();
        this.vocabulary = vocabulary;
        this.paraphrase = paraphrase;
        this.times = times;
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

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
