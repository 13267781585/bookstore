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
		//�õ����з���
		List<Category> list = categoryService.findAll();
		
		request.setAttribute("category_list", list);
		return "f:/adminjsps/admin/category/list.jsp";
	}
	
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡ������  ��ȫ����
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		if(category.getCname() == null || category.getCname().trim().isEmpty())
		{
			request.setAttribute("msg", "�������Ʋ���Ϊ��!");
			return "f:/adminjsps/admin/category/add.jsp";
		}
		category.setCid(CommonUtils.uuid());
		
		categoryService.add(category);
		return findAll(request,response);
		
	}
	
	// ɾ������
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		
		try {
			categoryService.delete(cid);
		} catch (CategoryException e) {
			request.setAttribute("msg", "ɾ������ʧ��!");
			return "f:/adminjsps/msg.jsp";
		}
		request.setAttribute("msg", "ɾ������ɹ�!");
		return "f:/adminjsps/msg.jsp";
		
	}
	
	//�޸ķ���ǰ��ѯ������Ϣ
	public String editPre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//����cid ��ѯ��Ӧ�ķ�����Ϣ
		String cid = request.getParameter("cid");
		Category category = categoryService.editPre(cid);
		
		//������������� mod.jspҳ���������ʾ
		request.setAttribute("category", category);
		return "f:/adminjsps/admin/category/mod.jsp";
	}

	//�޸ķ���
	public String update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		
		categoryService.update(category);
		
		return findAll(request,response);
		
	}
	
	
		
}
