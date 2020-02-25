package jsoup;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Set;

public class JsoupFirstTest {
    @Test
    public void testUrl() throws Exception {
        Document document = Jsoup.parse(new URL("http://www.itcast.cn"), 1000);
        String content = document.getElementsByTag("title").first().text();
        System.out.println(content);
    }

    @Test
    public void testString() throws Exception {
        String content = FileUtils.readFileToString(new File("C:\\Users\\14551\\Desktop\\user.html"), "utf8");
        Document doc = Jsoup.parse(content);
        String title = doc.getElementsByTag("title").first().text();
        System.out.println(title);
    }

    @Test
    public void testFile() throws Exception {
        Document document = Jsoup.parse(new File("C:\\Users\\14551\\Desktop\\user.html"), "utf8");
        String title = document.getElementsByTag("title").first().text();
        System.out.println(title);
    }

    @Test
    public void testDOM() throws Exception {
        Document document = Jsoup.parse(new File("C:\\Users\\14551\\Desktop\\user.html"), "utf8");
        //通过id获取
        /*Element element = document.getElementById("city_bj");*/
        //通过标签获取
        /*Element element = document.getElementsByTag("span").first();*/
        //通过class获取
        /*  Elements element = document.getElementsByClass("logo-mini");*/
        //通过属性获取
        Element element = document.getElementsByAttribute("data-toggle").first();
        System.out.println(element.text());
    }

    @Test
    public void testData() throws Exception {
        Document document = Jsoup.parse(new File("C:\\Users\\14551\\Desktop\\user.html"), "utf8");
        Element element = document.getElementById("app");
        String str = "";
        //从元素中获取id
        /*str=element.id();*/
        //从元素中获取所有className
        /*str=element.className();*/
        //从元素中获取属性的值attr
        /*str=element.attr("class");*/
        //从元素中获取所有class,将每个class放到一个set数组中
        /*Set<String> classSet=element.classNames();
        for (String s:classSet){
            System.out.println(s);
        }*/
        /*  System.out.println(str);*/
        //从元素中获取所有属性
        /*Attributes attributes = element.attributes();
        System.out.println(attributes.toString());*/
        //从元素中获取所有内容
        String text = element.text();
        System.out.println(text);
    }

    @Test
    public void testSelector() throws Exception {
        Document document = Jsoup.parse(new File("C:\\Users\\14551\\Desktop\\user.html"), "utf8");
        //通过标签查找元素
        /*Elements elements = document.select("span");
        for (Element element:elements){
            System.out.println(element.text());
        }*/
        //通过id查找元素
        /*Element element=document.select("#app").first();
        System.out.println("获取的结果是："+element.text());*/
        //.class 通过class查找元素
        /*Element element1 = document.select(".wrapper").first();
        System.out.println(element1.text());*/
        //通过属性去获取 [attribute]:利用属性查找元素，比如[abc]
        /*Element element = document.select("[aria-valuemin]").first();
        System.out.println(element);*/
        Elements elements = document.select("[aria-valuemin]");
        for (Element element : elements) {
            System.out.println(element);
        }
    }

    @Test
    public void testSelector2() throws Exception {
        //解析html文件，获取document对象
        Document document = Jsoup.parse(new File("C:\\Users\\14551\\Desktop\\user.html"), "utf8");
        //
        /*Element element = document.select("h3 .pull-right").first();
        Elements select = document.select("#app .logo-mini");*/
        /*Elements select = document.select(".sidebar-toggle[data-toggle]");*/
        /*Elements select = document.select("[data-toggle].sidebar-toggle");*/
        /*Elements select = document.select(".menu>li");*/
        /*for (Element element1 : select) {
            System.out.println(element1);
        }*/
        Element element = document.select(".menu>li").first();
        System.out.println(element);
    }
}
