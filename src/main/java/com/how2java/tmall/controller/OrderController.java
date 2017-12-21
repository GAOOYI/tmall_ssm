package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("admin_order_list")
    public String list(Page page, Model model){
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Order> list = orderService.list();
        int total = (int) new PageInfo<>(list).getTotal();
        page.setTotal(total);
        model.addAttribute("page",page);
        model.addAttribute("os",list);
        return "admin/listOrder";
    }

    //前台催发货
    @RequestMapping("admin_order_delivery")
    public String delivery(int id){
        Order o = orderService.get(id);
        o.setDeliverydate(new Date());
        o.setStatus("waitConfirm");
        orderService.update(o);
        return "redirect:admin_order_list";
    }
}
