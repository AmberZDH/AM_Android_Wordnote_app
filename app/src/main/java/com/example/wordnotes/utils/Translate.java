package com.example.wordnotes.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Translate {
    private String root_url = "http://dict.youdao.com/w/";
    private String url = "";
    private Document document;


    public Translate(String word) {
        word = word.replace(" ", "%20");
        this.url = this.root_url + word;

        Map<String, String> header = new HashMap<String, String>();
        header.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36");

        Connection connection = Jsoup.connect(this.url);
        Connection data = connection.headers(header);
        try {
            this.document = data.timeout(5000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取中/英字词的翻译
     *
     * @return
     */
    public String getTranslation() {
        try {
            Document doc = this.document;
//            String yinbiao = doc.select("#phrsListTab > h2 > div").text().+"\n";
            if (doc.select("#phrsListTab > div.trans-container > ul").text().equals(""))
                return null;
            String jieshi = doc.select("#phrsListTab > div.trans-container > ul").text() + "\n";
            return jieshi;

        } catch (Exception e) {
            System.out.println("Word Parase Fail");
            return null;
        }
    }


    public String getPhrase() {
        try {
            String allExample = "";
            Document doc = this.document;
            for (int i = 1; i < 4; i++) {
                if (doc.select("#wordGroup > p:nth-child(" + i + ")").text().equals(""))
                    return null;
                String ex = doc.select("#wordGroup > p:nth-child(" + i + ")").text()+"\n";

                allExample += ex;
            }

            return allExample;
        } catch (Exception e) {
            System.out.println("Sentence Parase Failed");
            return null;
        }
    }

    /**
     * 获取3条例句
     *
     * @return
     */
    public String getExSentence() {
        try {
            String allExample = "";
            Document doc = this.document;
            for (int i = 1; i < 4; i++) {
                if (doc.select("#bilingual > ul > li:nth-child(" + i + ") > p:nth-child(1)").text().equals(""))
                    return null;

                String ex = doc.select("#bilingual > ul > li:nth-child(" + i + ") > p:nth-child(1)").text()
                        + "\n" + doc.select("#bilingual > ul > li:nth-child(" + i + ") > p:nth-child(2)").text() + "\n\n";
                allExample += ex;
            }

            return allExample;
        } catch (Exception e) {
            System.out.println("Sentence Parase Failed");
            return null;
        }
    }




//    public static void main(String[] args) {
//        Translate translate = new Translate("cat");
//
//        System.out.println(translate.getTranslation());
//        System.out.println(translate.getPhrase());
//        System.out.println(translate.getExSentence());
//    }
}
