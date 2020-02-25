package cn.itcast.job.task;

import cn.itcast.job.pojo.JobInfo;
import cn.itcast.job.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class SpringDataPipeLine implements Pipeline {
    @Autowired
    private JobInfoService jobInfoService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        JobInfo jobInfo = resultItems.get("jobInfo");
        if (jobInfo != null) {
            jobInfoService.save(jobInfo);
        }
    }
}
