package cn.itcast.bookstore.category.web.servlet.admin;

import cn.itcast.bookstore.category.domin.Category;
import cn.itcast.bookstore.category.domin.CategoryException;
import cn.itcast.bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	private CategoryService categoryService = new CategoryService();
	
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//得到所有分类
		List<Category> list = categoryService.findAll();
		
		request.setAttribute("category_list", list);
		return "f:/adminjsps/admin/category/list.jsp";
	}
	
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取表单数据  补全数据
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		if(category.getCname() == null || category.getCname().trim().isEmpty())
		{
			request.setAttribute("msg", "分类名称不能为空!");
			return "f:/adminjsps/admin/category/add.jsp";
		}
		category.setCid(CommonUtils.uuid());
		
		categoryService.add(category);
		return findAll(request,response);
		
	}
	
	// 删除分类
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		
		try {
			categoryService.delete(cid);
		} catch (CategoryException e) {
			request.setAttribute("msg", "删除分类失败!");
			return "f:/adminjsps/msg.jsp";
		}
		request.setAttribute("msg", "删除分类成功!");
		return "f:/adminjsps/msg.jsp";
		
	}
	
	//修改分类前查询分类信息
	public String editPre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//根据cid 查询对应的分类信息
		String cid = request.getParameter("cid");
		Category category = categoryService.editPre(cid);
		
		//保存分类对象对于 mod.jsp页面的数据显示
		request.setAttribute("category", category);
		return "f:/adminjsps/admin/category/mod.jsp";
	}

	//修改分类
	public String update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		
		categoryService.update(category);
		
		return findAll(request,response);
		
	}
	
	
		
}
