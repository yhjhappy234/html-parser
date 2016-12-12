package com.yhj.controller;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;

/**
 * Created by YHJ on 2016/10/23.
 */
@Controller
public class HtmlController {

    @RequestMapping("/parseByHtml")
    @ResponseBody
    public String parseByHtml(String html,String id){
        Document doc = Jsoup.parse(html);
        Element e = doc.getElementById(id);
        return print(e);
    }

    @RequestMapping("/parseByUrl")
    @ResponseBody
    public String parseByUrl(String url) throws IOException {
        Document doc = Jsoup.connect(url)
                .data("wd", "yhj")
                .userAgent("Apple")
                .cookie("auth", "YHJ")
                .timeout(3000)
                .post();
        return print(doc.body());
    }
    @RequestMapping("/parseByFile")
    @ResponseBody
    public String parseByFile(String path) throws IOException {
        File input = new File(path);
        Document doc = Jsoup.parse(input, "UTF-8", "http://www.yhj.com/");
        return print(doc.body());
    }
    @RequestMapping("/parseBySelecter")
    @ResponseBody
    public String parseBySelecter(String url,String selecter) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select(selecter);
        return print(elements.first());
    }

    private String print(Element e){
        if(e == null)
            return "Dom not exists";
        String source = e.html();
        String text = StringEscapeUtils.escapeHtml3(source);
        return "文本节点内容：<br/>"+text+"<hr/>解析的节点内容：<br>"+source;
    }
}
