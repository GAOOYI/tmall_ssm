package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.ProductMapper;
import com.how2java.tmall.mapper.ProductimageMapper;
import com.how2java.tmall.mapper.PropertyvalueMapper;
import com.how2java.tmall.pojo.*;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    PropertyvalueMapper propertyvalueMapper;
    @Autowired
    ProductimageMapper productimageMapper;
    @Autowired
    ReviewService reviewService;

    @Override
    public List<Product> list(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        List<Product> products = productMapper.selectByExample(example);
        for (Product p:products
             ) {
            Productimage image = productimageMapper.findFirstImage(p.getId());
            p.setFirstProductImage(image);
        }
        return products;
    }

    @Override
    public void add(Product product) {
        productMapper.insert(product);
    }

    @Override
    public Product get(int id) {
        Product p = productMapper.selectByPrimaryKey(id);
        p.setFirstProductImage(productimageMapper.findFirstImage(id));
        ProductimageExample example = new ProductimageExample();
        ProductimageExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(id);
        criteria.andTypeEqualTo("type_single");
        p.setProductSingleImages(productimageMapper.selectByExample(example));
        ProductimageExample example1 = new ProductimageExample();
        ProductimageExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andPidEqualTo(id);
        criteria1.andTypeEqualTo("type_detail");
        p.setProductDetailImages(productimageMapper.selectByExample(example1));
        //获取评价数
        int num = reviewService.getNumByPid(id);
        p.setReviewCount(num);
        return p;
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKey(product);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Propertyvalue> findPropertyValueById(int pid) {
        return propertyvalueMapper.findPropertyValueById(pid);
    }

    @Override
    public int total() {
        return 0;
    }

    @Override
    public List<Product> search(String keyword) {
        ProductExample example = new ProductExample();
        example.createCriteria().andNameLike("%" + keyword +"%");
        List<Product> products = productMapper.selectByExample(example);
        for (Product p:products
                ) {
            Productimage image = productimageMapper.findFirstImage(p.getId());
            p.setFirstProductImage(image);
        }
        return products;
    }
}
