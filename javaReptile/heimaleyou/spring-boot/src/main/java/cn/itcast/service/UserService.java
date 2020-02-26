package cn.itcast.service;

import cn.itcast.domain.User;
import cn.itcast.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public User findById(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }
}
