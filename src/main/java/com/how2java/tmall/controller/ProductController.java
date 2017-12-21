package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.*;
import com.how2java.tmall.service.*;
import com.how2java.tmall.util.ImageUtil;
import com.how2java.tmall.util.Page;
import com.how2java.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class ProductController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyService propertyService;

    @RequestMapping("admin_product_list")
    public String list(int cid, Model model, Page page){
        //查询分类
        Category category = categoryService.get(cid);
        model.addAttribute("c",category);
        //查询product
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Product> list = productService.list(cid);
        int total = (int) new PageInfo<>(list).getTotal();
        page.setTotal(total);
        page.setParam("&cid=" + cid);
        model.addAttribute("page",page);
        model.addAttribute("ps",list);
        return "admin/listProduct";
    }

    @RequestMapping("admin_product_add")
    public String add(Product product){
        productService.add(product);
        return "redirect:admin_product_list?cid=" + product.getCid();
    }

    @RequestMapping("admin_product_edit")
    public String get(int id,Model model){
        Product product = productService.get(id);
        Category category = categoryService.get(product.getCid());
        product.setCategory(category);
        model.addAttribute("p",product);
        return "admin/editProduct";
    }

    @RequestMapping("admin_product_update")
    public String update(Product product){
        productService.update(product);
        return "redirect:admin_product_list?cid=" + product.getCid();
    }

    @RequestMapping("admin_product_delete")
    public String delete(int id){
        Product product = productService.get(id);
        productService.delete(id);
        return "redirect:admin_product_list?cid=" + product.getCid();
    }
    @RequestMapping("admin_propertyValue_edit")
    public String properyValueEdit(int pid , Model model){
        Product product = productService.get(pid);
        Category category = categoryService.get(product.getCid());
        product.setCategory(category);
        model.addAttribute("p",product);
//        List<Propertyvalue> list = productService.findPropertyValueById(pid);
        List<Property> l = propertyService.list(product.getCid());
        List<Propertyvalue> list = new ArrayList<>();
        for (Property p:l
             ) {
            List<Propertyvalue> propertyvalue = propertyValueService.getBypidAndptid(pid,p.getId());
            if( propertyvalue.isEmpty()){
                Propertyvalue propertyvalue1 = new Propertyvalue();
                propertyvalue1.setPid(pid);
                propertyvalue1.setPtid(p.getId());
                propertyValueService.add(propertyvalue1);
                propertyvalue1.setProperty(p);
                list.add(propertyvalue1);
            }else{
                //Propertyvalue propertyvalue1 = new Propertyvalue();
                propertyvalue.get(0).setProperty(p);
                list.add(propertyvalue.get(0));
            }
        }
        model.addAttribute("pvs",list);
        return "admin/editPropertyValue";
    }
    @RequestMapping("admin_propertyValue_update")
    public void PropertyValueUpdate(int id, String value, HttpServletResponse response){
        try {
            Propertyvalue propertyvalue = propertyValueService.get(id);
            propertyvalue.setValue(value);
            propertyValueService.update(propertyvalue);
            response.getWriter().write("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("admin_productImage_list")
    public String productImage(int pid ,Model model){
        Product product = productService.get(pid);
        List<Productimage> productimagesS = productImageService.list(pid,"type_single");
        List<Productimage> productimagesD = productImageService.list(pid,"type_detail");
        model.addAttribute("p",product);
        model.addAttribute("pisDetail",productimagesD);
        model.addAttribute("pisSingle",productimagesS);
        return "admin/listProductImage";
    }

    @RequestMapping("admin_productImage_add")
    public String addImage(Productimage p, UploadedImageFile uploadedImageFile, HttpSession session) throws IOException {
        //int id = productImageService.addAndReturnId(p);
        productImageService.add(p);
        String filepath = "";
        if (p.getType().equals("type_single"))
            filepath = "img/productSingle";
        else if (p.getType().equals("type_detail"))
            filepath = "img/productDetail";
        File imageFolder = new File(session.getServletContext().getRealPath(filepath));
        File file = new File(imageFolder,p.getId()+".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage image = ImageUtil.change2jpg(file);
        ImageIO.write(image,"jpg",file);
        return "redirect:admin_productImage_list?pid=" + p.getPid();
    }
    @RequestMapping("admin_productImage_delete")
    public String deleteImage(int id,HttpSession session){
        Productimage p = productImageService.get(id);
        String filepath = "";
        if (p.getType().equals("type_single"))
            filepath = "img/productSingle";
        else if (p.getType().equals("type_detail"))
            filepath = "img/productDetail";
        File imageFolder = new File(session.getServletContext().getRealPath(filepath));
        File file = new File(imageFolder,p.getId()+".jpg");
        file.delete();
        productImageService.delete(id);
        return "redirect:admin_productImage_list?pid=" + p.getPid();
    }
}
