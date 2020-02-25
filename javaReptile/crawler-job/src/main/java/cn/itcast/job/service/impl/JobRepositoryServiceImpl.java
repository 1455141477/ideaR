package cn.itcast.job.service.impl;

import cn.itcast.job.dao.JobInfoDao;
import cn.itcast.job.dao.JobRepository;
import cn.itcast.job.pojo.JobInfo;
import cn.itcast.job.pojo.JobInfoField;
import cn.itcast.job.pojo.JobResult;
import cn.itcast.job.service.JobRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobRepositoryServiceImpl implements JobRepositoryService {
    @Autowired
    private JobRepository jobRepository;

    @Override
    public void save(JobInfoField jobInfo) {
        jobRepository.save(jobInfo);
    }

    @Override
    public void saveAll(List<JobInfoField> list) {
        jobRepository.saveAll(list);
    }

    @Override
    public JobResult search(String salary, String jobaddr, String keyword, Integer page) {
        String[] salarys = salary.split("-");
        int salaryMin = 0, salaryMax = 0;
        //获取最低薪资
        if ("*".equals(salarys[0])) {
            //如果最小值是*，表示最低薪资是0
        } else {
            //如果最小值不是*，需要抓为数字类型，乘以10000
            salaryMin = Integer.parseInt(salarys[0]) * 10000;
        }
        if ("*".equals(salarys[1])) {
            salaryMax = 1000000;
        } else {
            salaryMax = Integer.parseInt(salarys[1]) * 10000;
        }
        Page<JobInfoField> pages = this.jobRepository.findBySalaryMinBetweenAndSalaryMaxBetweenAndJobAddrAndJobNameAndJobInfo(salaryMin, salaryMax, salaryMin, salaryMax, jobaddr, keyword, keyword, PageRequest.of(page - 1, 30));
        JobResult jobResult = new JobResult();
        jobResult.setRows(pages.getContent());
        jobResult.setPageTotal(pages.getTotalPages());
        return jobResult;
    }
}
