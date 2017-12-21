package com.how2java.tmall.service;

import com.how2java.tmall.pojo.User;

import java.util.List;

public interface UserService {
    List<User> list();

    User get(Integer uid);

    User login(User user);

    String register(User user);
}
