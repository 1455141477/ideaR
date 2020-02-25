package cn.itcast.job.task;

import cn.itcast.domain.Item;
import cn.itcast.service.ItemService;
import cn.itcast.utils.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ItemTask {
    @Autowired
    private ItemService itemService;
    @Autowired
    private HttpUtils httpUtils;
    private static final ObjectMapper mapper = new ObjectMapper();

    //当下载任务完成后，间隔多长时间进行下一次任务
    @Scheduled(fixedDelay = 100 * 1000)
    public void itemTask() throws Exception {
        //声明需要下载解析的初始地址
        String url = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&wq=%E6%89%8B%E6%9C%BA&s=1&click=0&page=";
        //按照页面对手机的搜索结果进行遍历
        for (int i = 0; i < 10; i++) {
            String html = httpUtils.doGetHtml(url + i);
            System.out.println("=======");
            System.out.println(html);
            this.parse(html);
        }
        System.out.println("手机数据抓取成功");
    }

    public void parse(String html) throws Exception {
        //解析html获取document对象
        Document document = Jsoup.parse(html);
        //获取sky信息
        Elements elements = document.select("div#J_goodsList > ul > li");
        for (Element element : elements) {
            long spu = Long.parseLong(element.select("[data-spu]").attr("[data-spu]"));
            Elements elements1 = element.select(".ps-item");
            for (Element element1 : elements1) {
                long sku = Long.parseLong(element1.select("[data-sku]").attr("data-sku"));
                //根据sku查询商品数量
                Item item = new Item();
                item.setSku(sku);
                List<Item> items = itemService.findAll(item);
                if (items.size() > 0) {
                    continue;
                }
                item.setSpu(spu);
                String url = "https://item.jd.com/" + sku + ".html";
                item.setUrl(url);
                String picUrl = "https:" + element1.select("[data-sku]").first().attr("data-lazy-img");
                picUrl.replace("/n9/", "/n1/");
                String image = this.httpUtils.doGetImage(picUrl);
                item.setPic(image);
                String priceJson = this.httpUtils.doGetHtml("https://p.3.cn/prices/mgets?skuId=J_" + sku);
                double p = mapper.readTree(priceJson).get(0).get("p").asDouble();
                item.setPrice(p);
                //获取商品的标题
                String itemInfo = this.httpUtils.doGetHtml(item.getUrl());
                String title = Jsoup.parse(itemInfo).select("div.sku-name").text();
                item.setTitle(title);
                item.setCreated(new Date());
                item.setUpdated(item.getCreated());
                this.itemService.save(item);
            }

        }
    }
}
