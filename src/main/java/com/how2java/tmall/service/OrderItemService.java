package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Orderitem;

import java.util.List;

public interface OrderItemService {
    void add(Orderitem oi);

    int getCartNum(Integer id);

    List<Orderitem> getOrderByUid(Integer id);

    void deleteOrderItem(int oiid);

    void update(Orderitem o);

    Orderitem get(int i);

    void updateOid(Orderitem oi);

    List<Orderitem> listByOid(Integer id);


    void deleteByOid(int oid);
}
