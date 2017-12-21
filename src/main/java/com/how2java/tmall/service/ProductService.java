package com.how2java.tmall.service;


import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Propertyvalue;

import java.util.List;

public interface ProductService {
    List<Product> list(int cid);

    void add(Product product);

    Product get(int id);

    void update(Product product);

    void delete(int id);

    List<Propertyvalue> findPropertyValueById(int pid);

    int total();

    List<Product> search(String keyword);
}
