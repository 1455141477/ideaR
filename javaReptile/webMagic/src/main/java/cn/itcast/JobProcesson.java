package cn.itcast;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.Scheduler;

public class JobProcesson implements PageProcessor {
    public void process(Page page) {
/*        //css选择器
        page.putField("div", page.getHtml().css(".box_con #list dl dd").all());
        //xPath
        page.putField("div", page.getHtml().xpath("//div[@class=box_con]/div[@id=list]/dl/dd").all());
        //正则表达式
        page.putField("div",page.getHtml().css(".box_con #list dl dd").regex(".*心之所在.*").all());
        //获取一个
        page.putField("div",page.getHtml().css(".box_con #list dl dd").toString());
         page.putField("div",page.getHtml().css(".box_con #list dl dd").links().regex(".*56.html$").all());*/
        page.putField("div", page.getHtml().css(".box_con #list dl dd").all());
    }

    private Site site = Site.me()
            .setCharset("utf8")
            .setTimeOut(10000)  //设置超时时间
            .setRetrySleepTime(3000) //设置重试的超时时间
            .setRetryTimes(5) //设置重试的次数
            ;

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider spider = Spider.create(new JobProcesson()).addUrl("http://www.xbiquge.la/12/13959/")  //设置爬取数据的页面
                /*.addPipeline(new FilePipeline("C:\\Users\\14551\\Desktop\\result"))*/
                .thread(5)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)));//设置一个布隆过滤器进行过滤，指定最多对10000000数据进行过滤
        //执行爬虫
        spider.run();
    }
}
