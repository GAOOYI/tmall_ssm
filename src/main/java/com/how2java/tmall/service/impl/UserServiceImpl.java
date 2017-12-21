package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.UserMapper;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.pojo.UserExample;
import com.how2java.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> list() {
        return userMapper.selectByExample(new UserExample());
    }

    @Override
    public User get(Integer uid) {
        return userMapper.selectByPrimaryKey(uid);
    }

    @Override
    public User login(User user) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(user.getName());
        criteria.andPasswordEqualTo(user.getPassword());
        List<User> list = userMapper.selectByExample(example);
        if (list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public String register(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(user.getName());
        List<User> users = userMapper.selectByExample(example);
        if(users != null && users.size() > 0){
            return "no";
        }
        userMapper.insert(user);
        return "yes";
    }
}
