package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.PropertyvalueMapper;
import com.how2java.tmall.pojo.Propertyvalue;
import com.how2java.tmall.pojo.PropertyvalueExample;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    @Autowired
    PropertyvalueMapper propertyvalueMapper;
@Autowired
    PropertyService propertyService;

    @Override
    public void update(Propertyvalue propertyvalue) {
        propertyvalueMapper.updateByPrimaryKey(propertyvalue);
    }

    @Override
    public Propertyvalue get(int id) {
        return propertyvalueMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Propertyvalue> getBypidAndptid(int pid, Integer id) {
        PropertyvalueExample example = new PropertyvalueExample();
        PropertyvalueExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        criteria.andPtidEqualTo(id);
        return propertyvalueMapper.selectByExample(example);
    }

    @Override
    public void add(Propertyvalue p) {
        propertyvalueMapper.insert(p);
    }

    @Override
    public List<Propertyvalue> list(int pid) {
        PropertyvalueExample example = new PropertyvalueExample();
        example.createCriteria().andPidEqualTo(pid);
        List<Propertyvalue> list = propertyvalueMapper.selectByExample(example);
        for (Propertyvalue p :list
             ) {
            p.setProperty(propertyService.get(p.getPtid()));
        }
        return list;
    }
}
