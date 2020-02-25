package cn.itcast.job.service;

import cn.itcast.job.pojo.JobInfoField;
import cn.itcast.job.pojo.JobResult;

import java.util.List;

public interface JobRepositoryService {
    public void save(JobInfoField jobInfo);

    public void saveAll(List<JobInfoField> list);

    JobResult search(String salary, String jobaddr, String keyword, Integer page);
}
