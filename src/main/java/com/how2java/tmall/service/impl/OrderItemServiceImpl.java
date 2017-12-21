package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.OrderitemMapper;
import com.how2java.tmall.pojo.Orderitem;
import com.how2java.tmall.pojo.OrderitemExample;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderitemMapper orderitemMapper;
    @Autowired
    ProductService productService;

    @Override
    public void add(Orderitem oi) {
        orderitemMapper.insert(oi);
    }

    @Override
    public int getCartNum(Integer id) {
        OrderitemExample example = new OrderitemExample();
        OrderitemExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(id);
        criteria.andOidIsNull();
        List<Orderitem> ois = orderitemMapper.selectByExample(example);

        return ois.size();
    }

    @Override
    public List<Orderitem> getOrderByUid(Integer id) {
        OrderitemExample example = new OrderitemExample();
        OrderitemExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(id);
        criteria.andOidIsNull();
        List<Orderitem> orderitems = orderitemMapper.selectByExample(example);
        for (Orderitem o:orderitems
             ) {
            o.setProduct(productService.get(o.getPid()));
        }
        return orderitems;
    }

    @Override
    public void deleteOrderItem(int oiid) {
        orderitemMapper.deleteByPrimaryKey(oiid);
    }

    @Override
    public void update(Orderitem o) {
        OrderitemExample example = new OrderitemExample();
        OrderitemExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(o.getPid());
        criteria.andUidEqualTo(o.getUid());
        List<Orderitem> orderitems = orderitemMapper.selectByExample(example);
        if(orderitems != null && orderitems.size() > 0){
            orderitems.get(0).setNumber(o.getNumber());
            orderitemMapper.updateByPrimaryKey(orderitems.get(0));
        }else{
            orderitemMapper.insert(o);
        }
    }

    @Override
    public Orderitem get(int i) {
        Orderitem o =  orderitemMapper.selectByPrimaryKey(i);
        o.setProduct(productService.get(o.getPid()));
        return o;
    }

    @Override
    public void updateOid(Orderitem oi) {
        if (oi.getId() != null){
            orderitemMapper.updateByPrimaryKey(oi);
        }else {
            orderitemMapper.insert(oi);
        }

    }

    @Override
    public List<Orderitem> listByOid(Integer id) {
        OrderitemExample example = new OrderitemExample();
        example.createCriteria().andOidEqualTo(id);
        List<Orderitem> ois = orderitemMapper.selectByExample(example);
        for (Orderitem o:ois
                ) {
            o.setProduct(productService.get(o.getPid()));
        }
        return ois;
    }

    @Override
    public void deleteByOid(int oid) {
        OrderitemExample example = new OrderitemExample();
        example.createCriteria().andOidEqualTo(oid);
        orderitemMapper.deleteByExample(example);
    }


}
