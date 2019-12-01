package com.example.wordnotes.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class News_globaltimes {
    private String root_url = "http://www.globaltimes.cn/china/";
    private String url = "";
    private Document document;

    /**
     * 获取html Doc
     *
     * @param
     */
    public News_globaltimes() {


        Map<String, String> header = new HashMap<String, String>();
        header.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36");

        Connection connection = Jsoup.connect(this.root_url);
        Connection data = connection.headers(header);
        try {
            this.document = data.timeout(5000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析其中的doc 获取国内信息栏
     *
     * @return
     */
    public ArrayList<String[]> getNews() {
        try {
            Document doc = this.document;

            ArrayList<String[]> arrayList = new ArrayList<>();
            for (int i = 1; i < 5; i++) {
                String[] str = new String[2];
                str[0] = doc.select("#channel-list > div:nth-child(" + i + ") > div.span10 > h4").text();
                str[1] = doc.select("#channel-list > div:nth-child(" + i + ") > div.span10 > p").text();
            }

            return arrayList;
        } catch (Exception e) {
            System.out.println("Get News Error");
            return null;
        }
    }
//    public static void main(String[] args){
//
//    }

}
