package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.how2java.tmall.pojo.*;
import com.how2java.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class ForeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    UserService userService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderService orderService;

    @RequestMapping("fore_homePage")
    public String home(Model model,HttpSession session){
        PageHelper.offsetPage(0,17);
        List<Category> list = categoryService.list();
        for (Category c: list
             ) {
            //获取产品
            c.setProducts(productService.list(c.getId()));
            //按行获取产品
            List<List<Product>> row = new ArrayList<>();
            for(int i = 0;i < 8;i++){
                PageHelper.offsetPage((i * 8),8);
                List<Product> list1 = productService.list(c.getId());
                row.add(list1);
            }
            c.setProductsByRow(row);
        }
        session.setAttribute("cs",list);
        //model.addAttribute("cs",list);
        return "fore/home";
    }

    @RequestMapping("forecategory")
    public String foreCategory(int cid, Model model){
        //回写category
        Category c = categoryService.get(cid);
        c.setProducts(productService.list(cid));
        model.addAttribute("c",c);
        return "fore/category";
    }

    @RequestMapping("foreproduct")
    public String foreProduct(int pid,Model model){
        Product p = productService.get(pid);
//        List<Category> cs = categoryService.list();
//        model.addAttribute("cs",cs);
        model.addAttribute("p",p);
        //查询评价
        List<Review> reviews = reviewService.list(pid);
        model.addAttribute("reviews",reviews);
        //获取属性和属性值
        List<Propertyvalue> pvs = propertyValueService.list(pid);
        model.addAttribute("pvs",pvs);
        return "fore/product";
    }

    @RequestMapping("foresearch")
    public String foreSearch(String keyword,Model model){
        List<Product> list = productService.search(keyword);
        model.addAttribute("ps",list);
//        List<Category> cs = categoryService.list();
//        model.addAttribute("cs",cs);
        return "fore/searchResult";
    }

    //请登录
    @RequestMapping("loginPage")
    public String loginPage(){
        return "fore/login";
    }
    @RequestMapping("forelogin")
    public String login(User user, HttpSession session){
        User u = userService.login(user);
        if (u != null){
            session.setAttribute("user",u);
            int catNum = orderItemService.getCartNum(u.getId());
            session.setAttribute("cartTotalItemNumber",catNum);

            return "redirect:fore_homePage";
        }
        return "forward:loginPage";
    }

    //用户退出
    @RequestMapping("forelogout")
    public String exit(HttpSession session){
        session.removeAttribute("user");
        session.removeAttribute("cartTotalItemNumber");
        return "forward:loginPage";
    }

    //用户注册
    @RequestMapping("registerPage")
    public String registerPage(Model model){
        List<Category> cs = categoryService.list();
        model.addAttribute("cs",cs);
        return "fore/register";
    }

    @RequestMapping("foreregister")
    public String register(User user,Model model,HttpSession session){
        String check = userService.register(user);
        if(check.equals("yes")){
            session.setAttribute("user",user);
            int cartNum = orderItemService.getCartNum(user.getId());
            session.setAttribute("cartTotalItemNumber",cartNum);
            return "fore/registerSuccess";
        }else if(check.equals("no")){
            model.addAttribute("msg","用户名已经被使用,不能使用");
        }
        return "forward:registerPage";
    }

    //立即购买
    @RequestMapping("forecheckLogin")
    public void forecheckLogin(HttpSession session, HttpServletResponse response) throws IOException {
        User user = (User) session.getAttribute("user");
        if(user != null){
            response.getWriter().write("success");
        }else{
            response.getWriter().write("error");
        }
    }
    @RequestMapping("foreloginAjax")
    public void foreloginAjax(User user ,HttpServletResponse response,HttpSession session) throws IOException {
        User u = userService.login(user);
        if (u != null){
            session.setAttribute("user",u);
            int cartNum = orderItemService.getCartNum(u.getId());
            session.setAttribute("cartTotalItemNumber",cartNum);
            response.getWriter().write("success");
        }else{
            response.getWriter().write("error");
        }
    }
    //立即购买
    @RequestMapping("forebuyone")
    public String Buy(int pid,int num,Model model,HttpSession session){
        List<Orderitem> orderitems = new ArrayList<>();
        Orderitem o = new Orderitem();
        o.setNumber(num);
        o.setPid(pid);
        Product p = productService.get(pid);
        o.setProduct(p);
        User user = (User) session.getAttribute("user");
        o.setUid(user.getId());
        orderitems.add(o);

        //model.addAttribute("ois",orderitems);
        session.setAttribute("ois",orderitems);
        //设置总额
        float total = num*p.getPromoteprice();
        model.addAttribute("total",total);
        return "fore/buy";
    }

    //加入购物车
    @RequestMapping("foreaddCart")
    public void addCar(int pid,int num,HttpSession session,HttpServletResponse response) throws IOException {
        try {
            Orderitem oi = new Orderitem();
            oi.setPid(pid);
            oi.setNumber(num);
            User u = (User) session.getAttribute("user");
            int cartNum = (int) session.getAttribute("cartTotalItemNumber");
            session.setAttribute("cartTotalItemNumber",++cartNum);
            oi.setUid(u.getId());
            orderItemService.add(oi);
            response.getWriter().write("success");
        }catch (Exception e){
            response.getWriter().write("error");
            e.printStackTrace();
        }
    }

    //打开购物车
    @RequestMapping("forecart")
    public String car(HttpSession session){
        User u = (User) session.getAttribute("user");
        List<Orderitem> orderitems = orderItemService.getOrderByUid(u.getId());
        session.setAttribute("ois",orderitems);
        return "fore/cart";
    }

    //删除购物车商品
    @RequestMapping("foredeleteOrderItem")
    public void deleteOrderItem(int oiid,HttpServletResponse response,HttpSession session){
        try {
            orderItemService.deleteOrderItem(oiid);
            //将购物车件数减一
            int cartNum = (int) session.getAttribute("cartTotalItemNumber");
            session.setAttribute("cartTotalItemNumber",--cartNum);
            //会写
            response.getWriter().write("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //修改商品件数
    @RequestMapping("forechangeOrderItem")
    public void changOrderItem(Orderitem o,HttpServletResponse response,HttpSession session){
        try {
            User u = (User) session.getAttribute("user");
            o.setUid(u.getId());
            orderItemService.update(o);
            response.getWriter().write("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //够买多件商品
    @RequestMapping("forebuy")
    public String buy(int[] oiid, HttpSession session,Model model){
        User user = (User) session.getAttribute("user");
        List<Orderitem> orderitems = new ArrayList<>();
        float total = 0;
        for (int i:oiid
             ) {
            Orderitem o = orderItemService.get(i);
            orderitems.add(o);
            total = total + o.getNumber() * o.getProduct().getPromoteprice();
        }
        //model.addAttribute("ois",orderitems);
        session.setAttribute("ois",orderitems);
        model.addAttribute("total",total);
        //session.setAttribute("total",total);
        return "fore/buy";
    }

    //创建订单
    @RequestMapping("forecreateOrder")
    public String createOrder(Order order,HttpSession session,Model model){
        User user = (User) session.getAttribute("user");
        List<Orderitem> orderitems = (List<Orderitem>) session.getAttribute("ois");
        order.setUid(user.getId());
        order.setStatus("waitPay");
        order.setCreatedate(new Date());
        //将订单放入数据库
        orderService.add(order);
        //修改orderitem中的oid
        float total = 0;
        for (Orderitem oi:orderitems
             ) {
            oi.setOid(order.getId());
            orderItemService.updateOid(oi);
            total = total + oi.getNumber() * oi.getProduct().getPromoteprice();
        }
        Param param = new Param();
        param.setOid(order.getId());
        param.setTotal(total);
        model.addAttribute("paramm",param);
        //需要更新购物车，如果直接购买不会更新
        int cartNum = orderItemService.getCartNum(user.getId());
        session.setAttribute("cartTotalItemNumber",cartNum);
        //清楚session中的订单
        session.removeAttribute("ois");
        return "fore/alipay";
    }
    //支付成功页面
    @RequestMapping("forepayed")
    public String payed(int oid, float total,Model model){
        Order o = orderService.get(oid);
        o.setPaydate(new Date());
        o.setStatus("waitDelivery");
        orderService.update(o);

        Param param = new Param();
        param.setOid(o.getId());
        param.setTotal(total);
        model.addAttribute("paramm",param);
        model.addAttribute("o",o);
        return "fore/payed";
    }

    //我的订单页面
    @RequestMapping("forebought")
    public String bought(Model model,HttpSession session){
        User u = (User) session.getAttribute("user");
        List<Order> os = orderService.listAll(u.getId());
        model.addAttribute("os",os);
        return "fore/bought";
    }
    //删除订单
    @RequestMapping("foredeleteOrder")
    public void deleteOrder(int oid,HttpServletResponse response){
        try {
            //删除订单
            orderService.delete(oid);
            //删除orderitem
            orderItemService.deleteByOid(oid);
            response.getWriter().write("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //订单中付款按钮
    @RequestMapping("forealipay")
    public String alipay(int oid,float total,Model model){
        Param param = new Param();
        param.setOid(oid);
        param.setTotal(total);
        model.addAttribute("paramm",param);
        return "fore/alipay";
    }

    //订单中确认收货按钮
    @RequestMapping("foreconfirmPay")
    public String configPay(int oid,Model model){
        //通过oid查询订单，返回页面
        Order o = orderService.get(oid);
        model.addAttribute("o",o);
        return "fore/confirmPay";
    }

    //确认收货中确认支付
    @RequestMapping("foreorderConfirmed")
    public String orderConfirm(int oid){
        //通过oid查询订单，返回页面
        Order o = orderService.get(oid);
        o.setConfirmdate(new Date());
        o.setStatus("waitReview");
        orderService.update(o);
        return "fore/orderConfirmed";
    }

    //订单中评价
    @RequestMapping("forereview")
    public String review(int oid,Model model){
        Order o = orderService.get(oid);
        model.addAttribute("o",o);
        //通过oid获取商品
        List<Orderitem> ois = orderItemService.listByOid(o.getId());
        if(ois == null || ois.size() == 0){
            return "redirect:forebought";
        }
        Product p = productService.get(ois.get(0).getPid());
        model.addAttribute("p",p);
        //设置参数
        Param param = new Param();
        param.setShowonly(false);
        model.addAttribute("paramm",param);
        return "fore/review";
    }
    //提交评价
    @RequestMapping("foredoreview")
    public String doReview(Review review,int oid,Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        review.setUid(user.getId());
        review.setCreatedate(new Date());
        reviewService.add(review);
        //更新order
        Order order = orderService.get(oid);
        order.setStatus("");
        orderService.update(order);

        //写返回值
        List<Review> rs = reviewService.list(review.getPid());
        model.addAttribute("reviews",rs);
        //写产品
        Product p = productService.get(review.getPid());
        model.addAttribute("p",p);
        //写参数
        Param param = new Param();
        param.setShowonly(true);
        model.addAttribute("paramm",param);

        return "fore/review";
    }

}
