import cn.itcast.MyApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;
import us.codecraft.webmagic.scheduler.RedisScheduler;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MyApplication.class)
public class WebMagicTest {
    @Test
    public void test() {
        Spider.create(new GithubRepoPageProcessor())
                //从http://www.xbiquge.la/12/13959/开始抓
                .addUrl("https://www.jd.com/?cu=true&utm_source=baidu-pinzhuan&utm_medium=cpc&utm_campaign=t_288551095_baidupinzhuan&utm_term=0f3d30c8dba7459bb52f2eb5eba8ac7d_0_03ad66dd051844cf91f955036ad4b682")
                //设置Scheduler，使用redis来管理队列
                /*.setScheduler(new RedisScheduler("localhost"))*/
                //设置Pipeinel，将结果以json格式保存到文件
                .addPipeline(new JsonFilePipeline("C:\\Users\\14551\\Desktop\\result\\data\\webmagic"))
                //开启5个线程同时执行
                .thread(5)
                .run();
    }
}
