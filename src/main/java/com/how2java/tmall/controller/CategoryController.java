package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.util.ImageUtil;
import com.how2java.tmall.util.Page;
import com.how2java.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_category_list")
    public String list(Model model, Page page){
        //page.setTotal(categoryService.total());
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Category> cs = categoryService.list();
        int total = (int) new PageInfo<>(cs).getTotal();
        page.setTotal(total);
        model.addAttribute("cs",cs);
        model.addAttribute("page",page);
        return "admin/listCategory";
    }
    @RequestMapping("admin_category_add")
    public String add(Category category,UploadedImageFile uploadedImageFile, HttpSession session) throws IOException {
        categoryService.add(category);
        //获取图片
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,category.getId()+".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage image = ImageUtil.change2jpg(file);
        ImageIO.write(image,"jpg",file);
        return "redirect:admin_category_list";
    }

    @RequestMapping("admin_category_delete")
    public String delete(int id ,HttpSession session){
        categoryService.delete(id);
        //删除文件
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,id+".jpg");
        file.delete();
        return "redirect:admin_category_list";
    }
    @RequestMapping("admin_category_edit")
    public String get(int id, Model model){
        Category category = categoryService.get(id);
        model.addAttribute("c",category);
        return "admin/editCategory";
    }
    @RequestMapping("admin_category_update")
    public String update(Category category, UploadedImageFile uploadedImageFile, HttpSession session) throws IOException {
        categoryService.update(category);
        //判断是否上传文件
        if(uploadedImageFile.getImage() != null && !uploadedImageFile.getImage().isEmpty()){
            File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder,category.getId()+".jpg");
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            if(file != null && file.exists())
                file.delete();
            uploadedImageFile.getImage().transferTo(file);
            BufferedImage image = ImageUtil.change2jpg(file);
            ImageIO.write(image,"jpg",file);
        }

        return "redirect:admin_category_list";
    }

}
