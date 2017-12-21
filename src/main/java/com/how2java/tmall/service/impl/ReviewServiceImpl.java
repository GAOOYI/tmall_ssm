package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.ReviewMapper;
import com.how2java.tmall.pojo.Review;
import com.how2java.tmall.pojo.ReviewExample;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.ReviewService;
import com.how2java.tmall.service.UserService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewMapper reviewMapper;
    @Autowired
    UserService userService;
    @Override
    public List<Review> list(int pid) {
        ReviewExample example = new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        List<Review> reviews = reviewMapper.selectByExample(example);
        for (Review r:reviews
             ) {
            User u = userService.get(r.getUid());
            String name = u.getName();
            StringBuffer buffer = new StringBuffer(name);
            buffer.replace(1,name.length()-1,"*");
            u.setAnonymousName(buffer.toString());
            r.setUser(u);
        }
        return reviews;
    }

    @Override
    public int getNumByPid(int pid) {
        return reviewMapper.getNumByPid(pid);
    }

    @Override
    public void add(Review review) {
        reviewMapper.insert(review);
    }
}
