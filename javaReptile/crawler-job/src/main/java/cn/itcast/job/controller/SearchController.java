package cn.itcast.job.controller;

import cn.itcast.job.pojo.JobResult;
import cn.itcast.job.service.JobRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    @Autowired
    private JobRepositoryService jobRepositoryService;

    @RequestMapping("search")
    public JobResult Search(String salary, String jobaddr, String keyword, Integer page) {
        JobResult jobResult = jobRepositoryService.search(salary, jobaddr, keyword, page);
        System.out.println("测试一下");
        return jobResult;
    }
}
