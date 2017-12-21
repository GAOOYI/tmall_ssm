package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Review;
import com.sun.org.apache.regexp.internal.RE;

import java.util.List;

public interface ReviewService {
    List<Review> list(int pid);

    int getNumByPid(int id);


    void add(Review review);
}
