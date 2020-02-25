import cn.itcast.job.Application;
import cn.itcast.job.pojo.JobInfo;
import cn.itcast.job.pojo.JobInfoField;
import cn.itcast.job.pojo.JobResult;
import cn.itcast.job.service.JobInfoService;
import cn.itcast.job.service.JobRepositoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class esTest {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private JobInfoService jobInfoService;
    @Autowired
    private JobRepositoryService jobRepositoryService;

    @Test
    public void createIndex() {
        this.elasticsearchTemplate.createIndex(JobInfoField.class);
        this.elasticsearchTemplate.putMapping(JobInfoField.class);
    }

    /**
     * 将工作信息从索引库中查出然后添加到数据库中
     */
    @Test
    public void JobInfoData() {
        //声明页码数，从1开始
        int p = 0;
        int pageSize = 0;
        do {
            //声明页码数，从1开始
            Page<JobInfo> page = jobInfoService.findAll(1, 500);
            List<JobInfoField> list = new ArrayList<JobInfoField>();
            for (JobInfo jobInfo : page.getContent()) {
                JobInfoField jobInfoField = new JobInfoField();
                BeanUtils.copyProperties(jobInfo, jobInfoField);
                list.add(jobInfoField);
            }
            jobRepositoryService.saveAll(list);
            p++;
            pageSize=page.getContent().size();
        } while (pageSize == 200);
    }
    @Test
    public void Search(){
        JobResult jobResult = jobRepositoryService.search("*-*", "上班地址", "java", 1);
        for (JobInfoField field : jobResult.getRows()) {
            System.out.println(field.toString());
        }
    }
}
