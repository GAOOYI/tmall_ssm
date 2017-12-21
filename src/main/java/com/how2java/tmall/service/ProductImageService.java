package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Productimage;

import java.util.List;

public interface ProductImageService {
    List<Productimage> list(int pid, String type_single);

    void add(Productimage p);

    Productimage get(int id);

    void delete(int id);

    int addAndReturnId(Productimage p);

    Productimage getFirstImage(Integer id);
}
