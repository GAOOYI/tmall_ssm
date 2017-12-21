package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.ProductimageMapper;
import com.how2java.tmall.pojo.Productimage;
import com.how2java.tmall.pojo.ProductimageExample;
import com.how2java.tmall.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    ProductimageMapper productimageMapper;


    @Override
    public List<Productimage> list(int pid, String type) {
        ProductimageExample productimageExample = new ProductimageExample();
        ProductimageExample.Criteria criteria = productimageExample.createCriteria();
        criteria.andPidEqualTo(pid);
        criteria.andTypeEqualTo(type);

        return productimageMapper.selectByExample(productimageExample);
    }

    @Override
    public void add(Productimage p) {
        productimageMapper.insert(p);
    }

    @Override
    public Productimage get(int id) {
        return productimageMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(int id) {
        productimageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int addAndReturnId(Productimage p) {
        return productimageMapper.insertProduct(p);
    }

    @Override
    public Productimage getFirstImage(Integer id) {
        return productimageMapper.findFirstImage(id);
    }

}
