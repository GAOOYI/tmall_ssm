package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.OrderMapper;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderExample;
import com.how2java.tmall.pojo.Orderitem;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    UserService userService;

    //后台订单查询
    @Override
    public List<Order> list() {
        List<Order> os = orderMapper.selectByExample(new OrderExample());
        for (Order o :os
             ) {
            List<Orderitem> ois = orderItemService.listByOid(o.getId());
            float total = 0;
            for (Orderitem oi:ois
                    ) {
                total = total + oi.getProduct().getPromoteprice() * oi.getNumber();
            }
            o.setTotal(total);
            o.setOrderitems(ois);
            o.setTotalNumber(ois.size());
            //获取User
            o.setUser(userService.get(o.getUid()));
        }
        return os;
    }

    @Override
    public void add(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public Order get(int oid) {
        Order o = orderMapper.selectByPrimaryKey(oid);
        //查询商品封装orderitem
        List<Orderitem> ois = orderItemService.listByOid(o.getId());
        float total = 0;
        for (Orderitem oi:ois
                ) {
            total = total + oi.getProduct().getPromoteprice() * oi.getNumber();
        }
        o.setTotal(total);
        o.setOrderitems(ois);
        o.setTotalNumber(ois.size());
        return o;
    }

    @Override
    public void update(Order o) {
        orderMapper.updateByPrimaryKey(o);
    }

    @Override
    public List<Order> listAll(Integer id) {
        //通过用户id获取所有订单
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andUidEqualTo(id);
        List<Order> os = orderMapper.selectByExample(orderExample);
        //通过oid获取orderitem
        for (Order o:os
             ) {
            List<Orderitem> ois = orderItemService.listByOid(o.getId());
            float total = 0;
            for (Orderitem oi:ois
                 ) {
                total = total + oi.getProduct().getPromoteprice() * oi.getNumber();
            }
            o.setTotal(total);
            o.setOrderitems(ois);
            o.setTotalNumber(ois.size());
        }
        return os;
    }

    @Override
    public void delete(int oid) {
        orderMapper.deleteByPrimaryKey(oid);
    }
}
