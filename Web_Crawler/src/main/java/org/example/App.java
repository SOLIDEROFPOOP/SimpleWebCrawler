package org.example;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App {
    private final static String url = "https://en.wikipedia.org/";
    public static void main( String[] args ) {
        crawl(1,url, new ArrayList<String>());
    }

    private static void crawl(int level, String url, ArrayList<String> visited) {
        if (level <= 5){
            Document doc = request(url,visited);
            if (doc != null){
                for (Element link : doc.select("a[href]")){
                    String next_link = link.absUrl("href");
                    if (visited.contains(next_link) == false){
                        crawl(level++, next_link, visited);
                    }
                }
            }
        }

    }
    private static Document request(String url, ArrayList<String> v){
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();
            if (con.response().statusCode() == 200){
                System.out.println("Link " + url + " " + " statuscode: " + con.response().statusCode());
                System.out.println(doc.title());
                v.add(url);
                return doc;
            }
            return null;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }
}
