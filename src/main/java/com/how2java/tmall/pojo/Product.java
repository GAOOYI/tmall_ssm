package com.how2java.tmall.pojo;

import java.util.Date;
import java.util.List;

public class Product {
    private Integer id;

    private String name;

    private String subtitle;

    private Float originalprice;

    private Float promoteprice;

    private Integer stock;

    private Integer cid;

    private Date createdate;

    private Productimage firstProductImage;

    private Category category;

    private int saleCount;

    private int reviewCount;

    private List<Productimage> productSingleImages;

    private List<Productimage> productDetailImages;

    public List<Productimage> getProductDetailImages() {
        return productDetailImages;
    }

    public void setProductDetailImages(List<Productimage> productDetailImages) {
        this.productDetailImages = productDetailImages;
    }

    public List<Productimage> getProductSingleImages() {
        return productSingleImages;
    }

    public void setProductSingleImages(List<Productimage> productSingleImages) {
        this.productSingleImages = productSingleImages;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Productimage getFirstProductImage() {
        return firstProductImage;
    }

    public void setFirstProductImage(Productimage firstProductImage) {
        this.firstProductImage = firstProductImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    public Float getOriginalprice() {
        return originalprice;
    }

    public void setOriginalprice(Float originalprice) {
        this.originalprice = originalprice;
    }

    public Float getPromoteprice() {
        return promoteprice;
    }

    public void setPromoteprice(Float promoteprice) {
        this.promoteprice = promoteprice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}