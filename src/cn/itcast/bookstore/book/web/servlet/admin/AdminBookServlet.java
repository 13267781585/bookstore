package cn.itcast.bookstore.book.web.servlet.admin;

import cn.itcast.bookstore.book.domin.Book;
import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.bookstore.category.domin.Category;
import cn.itcast.bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/AdminBookServlet")
public class AdminBookServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private BookService bookService = new BookService();
	private CategoryService categoryService = new CategoryService();
	
	//��ѯ����ͼ��
	public String findAllBook(HttpServletRequest request, HttpServletResponse response) {
		
		List<Book> list = bookService.findAll();
		request.setAttribute("book_list", list);
		return "f:/adminjsps/admin/book/list.jsp";
	}
	
	//����ͼ��
	/*
	 * ��Ϊ����ͼ����Ϣ��ҳ����Ҫ��ʾ���з���
	 * ������Ҫ�������з��ಢ������reques����
	 */
	public String loadBook(HttpServletRequest request, HttpServletResponse response) {
		String bid = request.getParameter("bid");
		
		Book book = bookService.load(bid);
		List<Category> list = categoryService.findAll();
		
		request.setAttribute("book", book);
		request.setAttribute("category_list",list);
		
		return "f:/adminjsps/admin/book/desc.jsp";
	}
	
	//���ͼ��ǰ�ı�������ʾ
	/*
	 * ��ѯ���з���
	 */
	public String addPre(HttpServletRequest request, HttpServletResponse response) {
		List<Category> list = categoryService.findAll();
		request.setAttribute("category_list",list);
		return "f:/adminjsps/admin/book/add.jsp";
	}
	
	//ɾ��ͼ��
	/*
	 * �������ɾ��
	 * ֻ�ǽ�book��del �����޸�Ϊtrue ��ʾ�Ѿ�ɾ����ͼ��
	 */
	public String deleteBook(HttpServletRequest request, HttpServletResponse response) {
		String bid = request.getParameter("bid");
		
		bookService.deleteBook(bid);
		request.setAttribute("msg", "ɾ���ɹ�!");
		return "f:/adminjsps/msg.jsp";
	}
	
	//�޸�ͼ��
	public String updateBook(HttpServletRequest request, HttpServletResponse response) {
		
		Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		book.setCategory(category);
		
		bookService.updateBook(book);
		request.setAttribute("msg", "�޸ĳɹ�!");
		return "f:/adminjsps/msg.jsp";
	}
	
	
	
	

}
