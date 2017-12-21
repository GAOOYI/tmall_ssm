package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Order;

import java.util.List;

public interface OrderService {
    List<Order> list();

    void add(Order order);

    Order get(int oid);

    void update(Order o);

    List<Order> listAll(Integer id);

    void delete(int oid);
}
