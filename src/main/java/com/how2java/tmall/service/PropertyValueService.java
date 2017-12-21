package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Propertyvalue;

import java.util.List;

public interface PropertyValueService {
    void update(Propertyvalue propertyvalue);

    Propertyvalue get(int id);

    List<Propertyvalue> getBypidAndptid(int pid, Integer id);

    void add(Propertyvalue p);

    List<Propertyvalue> list(int pid);
}
