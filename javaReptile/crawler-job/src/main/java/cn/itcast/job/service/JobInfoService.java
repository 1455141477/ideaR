package cn.itcast.job.service;

import cn.itcast.job.pojo.JobInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JobInfoService {
    public void save(JobInfo jobInfo);

    public List<JobInfo> findJobInfo(JobInfo jobInfo);

    Page<JobInfo> findAll(int m, int n);
}
