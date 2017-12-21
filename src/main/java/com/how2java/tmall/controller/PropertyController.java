package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.util.Page;
import com.how2java.tmall.vo.PropertyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class PropertyController {
    @Autowired
    PropertyService propertyService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_property_list")
    public String list(Model model, int cid, Page page){
        System.out.println(cid);
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Property> p = propertyService.list(cid);
        int total = (int) new PageInfo<>(p).getTotal();
        page.setTotal(total);
        page.setParam("&cid=" + cid);
        Category category = categoryService.get(cid);
        model.addAttribute("page",page);
        model.addAttribute("ps",p);
        model.addAttribute("c",category);
        return "admin/listProperty";
    }

    @RequestMapping("admin_property_add")
    public String add(Model model,Property property){
        propertyService.add(property);
        model.addAttribute("cid",property.getCid());
        return "redirect:admin_property_list";
    }

    @RequestMapping("admin_property_delete")
    public String delete(Model model,int id){
        Property property = propertyService.get(id);
        propertyService.delete(id);
        model.addAttribute("cid",property.getCid());
        return "redirect:admin_property_list";
    }

    @RequestMapping("admin_property_edit")
    public String get(int id, Model model){
        Property property = propertyService.get(id);
        PropertyVo p = new PropertyVo();
        Category category = categoryService.get(property.getCid());
        p.setCategory(category);
        p.setId(property.getId());
        p.setCid(property.getCid());
        p.setName(property.getName());
        model.addAttribute("p",p);
        return "admin/editProperty";
    }

    @RequestMapping("admin_property_update")
    public String update(Model model,Property property){
        propertyService.update(property);
        model.addAttribute("cid",property.getCid());
        return "redirect:admin_property_list";
    }

}
