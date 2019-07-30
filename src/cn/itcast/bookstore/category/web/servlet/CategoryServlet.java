package cn.itcast.bookstore.category.web.servlet;

import cn.itcast.bookstore.category.domin.Category;
import cn.itcast.bookstore.category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	private CategoryService categoryService = new CategoryService();
	
	//查找所有分类
	public String findAll(HttpServletRequest request,HttpServletResponse response)
	{
		List<Category> list = categoryService.findAll();
		request.setAttribute("category_list", list);         //保存分类集合
		return "f:/jsps/left.jsp";                       //转发至对应jsp页面
	}
}
