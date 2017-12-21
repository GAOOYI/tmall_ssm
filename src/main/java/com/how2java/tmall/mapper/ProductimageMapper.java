package com.how2java.tmall.mapper;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Productimage;
import com.how2java.tmall.pojo.ProductimageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductimageMapper {
    long countByExample(ProductimageExample example);

    int deleteByExample(ProductimageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Productimage record);

    int insertSelective(Productimage record);

    List<Productimage> selectByExample(ProductimageExample example);

    Productimage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Productimage record, @Param("example") ProductimageExample example);

    int updateByExample(@Param("record") Productimage record, @Param("example") ProductimageExample example);

    int updateByPrimaryKeySelective(Productimage record);

    int updateByPrimaryKey(Productimage record);

    int insertProduct(Productimage p);
    Productimage findFirstImage(int pid);
}