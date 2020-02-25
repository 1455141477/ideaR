package cn.itcast.job.task;

import cn.itcast.job.pojo.JobInfo;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

@Component
public class JobProcessor implements PageProcessor {
    private String url = "https://search.51job.com/list/000000,000000,0000,00,9,99,java,2,1.html?" +
            "lang=c&stype=&postchannel=0000&" +
            "workyear=99&cotype=99&degreefrom=99&" +
            "jobterm=99&companysize=99&providesalary=99" +
            "&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9" +
            "&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";

    @Override
    public void process(Page page) {
        //解析页面获取招聘信息详情的url地址
        List<Selectable> list = page.getHtml().css("div#resultList div.el").nodes();
        if (list.size() == 0) {
            this.saveJobInfo(page);
        } else {
            //如果不存在，表示这是列表页
            for (Selectable selectable : list) {
                String jobInfoUrl = selectable.links().toString();
                System.out.println(jobInfoUrl);
                //把获取的url地址放到任务队列中
                page.addTargetRequest(jobInfoUrl);
            }
            //获取下一页的url
            String bkUrl = page.getHtml().css("div.p_in li.bk").nodes().get(1).links().toString();
            page.addTargetRequest(bkUrl);
        }
    }

    public void saveJobInfo(Page page) {
        JobInfo jobInfo = new JobInfo();
        Html html = page.getHtml();
        /*jobInfo.setTime(getTime(Jsoup.parse(html.css("div.cn p.msg").toString()).text()));*/
        jobInfo.setTime("2-22");
        jobInfo.setUrl(page.getUrl().toString());
        jobInfo.setCompanyAddr(Jsoup.parse(html.css("div.bmsg p.fp").nodes().get(1).toString()).text());
        jobInfo.setCompanyInfo(Jsoup.parse(html.css("div.tCompany_main div.tBorderTop_box div.tmsg").nodes().toString()).text());
        jobInfo.setCompanyName(Jsoup.parse(html.css("div.cn p.cname a").nodes().get(0).toString()).text());
        jobInfo.setJobAddr(Jsoup.parse(html.css("div.bmsg p.fp").nodes().get(1).toString()).text());
        jobInfo.setJobInfo(Jsoup.parse(html.css("div.bmsg").nodes().get(0).toString()).text());
        jobInfo.setJobName(Jsoup.parse(html.css("div.in div.cn h1").toString()).text());
        jobInfo.setSalaryMax(5000);
        jobInfo.setSalaryMin(5000);
        page.putField("jobInfo", jobInfo);
    }

    public String getTime(String time) {
        String[] strings = time.split("\\|");
        return strings[strings.length - 1];
    }

    private Site site = Site.me()
            .setCharset("gbk")
            .setTimeOut(10 * 1000)
            .setRetrySleepTime(3000)
            .setRetryTimes(3);

    @Override
    public Site getSite() {
        return site;
    }
    @Autowired
    private SpringDataPipeLine springDataPipeLine;

    //initialDelay 当定时任务开启时，等待多久进行任务
    //fixedDelay 每隔多久执行方法
/*    @Scheduled(initialDelay = 1000, fixedDelay = 100 * 1000)*/
    public void process() {
        Spider.create(new JobProcessor())
                .addUrl(url)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .thread(10)
                .addPipeline(this.springDataPipeLine)
                .run();
    }
}
