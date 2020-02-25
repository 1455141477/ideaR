package cn.itcast.job.service.impl;

import cn.itcast.job.dao.JobInfoDao;
import cn.itcast.job.pojo.JobInfo;
import cn.itcast.job.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobInfoServiceImpl implements JobInfoService {
    @Autowired
    private JobInfoDao jobInfoDao;

    @Override
    public Page<JobInfo> findAll(int m, int n) {
        return jobInfoDao.findAll(PageRequest.of(m, n));
    }

    @Override
    @Transactional
    public void save(JobInfo jobInfo) {
        //根据url和时间查询数据
        JobInfo param = new JobInfo();
        param.setUrl(jobInfo.getUrl());
        param.setTime(jobInfo.getTime());
        List<JobInfo> list = this.findJobInfo(param);
        if (list.size() == 0) {
            this.jobInfoDao.saveAndFlush(jobInfo);
        }
    }

    @Override
    public List<JobInfo> findJobInfo(JobInfo jobInfo) {
        Example example = Example.of(jobInfo);
        List list = jobInfoDao.findAll(example);
        return list;
    }
}
