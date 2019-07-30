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
	
	//查询所有图书
	public String findAllBook(HttpServletRequest request, HttpServletResponse response) {
		
		List<Book> list = bookService.findAll();
		request.setAttribute("book_list", list);
		return "f:/adminjsps/admin/book/list.jsp";
	}
	
	//加载图书
	/*
	 * 因为加载图书信息的页面需要显示所有分类
	 * 所以需要查找所有分类并保存在reques域中
	 */
	public String loadBook(HttpServletRequest request, HttpServletResponse response) {
		String bid = request.getParameter("bid");
		
		Book book = bookService.load(bid);
		List<Category> list = categoryService.findAll();
		
		request.setAttribute("book", book);
		request.setAttribute("category_list",list);
		
		return "f:/adminjsps/admin/book/desc.jsp";
	}
	
	//添加图书前的表单数据显示
	/*
	 * 查询所有分类
	 */
	public String addPre(HttpServletRequest request, HttpServletResponse response) {
		List<Category> list = categoryService.findAll();
		request.setAttribute("category_list",list);
		return "f:/adminjsps/admin/book/add.jsp";
	}
	
	//删除图书
	/*
	 * 不是真的删除
	 * 只是将book的del 属性修改为true 表示已经删除的图书
	 */
	public String deleteBook(HttpServletRequest request, HttpServletResponse response) {
		String bid = request.getParameter("bid");
		
		bookService.deleteBook(bid);
		request.setAttribute("msg", "删除成功!");
		return "f:/adminjsps/msg.jsp";
	}
	
	//修改图书
	public String updateBook(HttpServletRequest request, HttpServletResponse response) {
		
		Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		book.setCategory(category);
		
		bookService.updateBook(book);
		request.setAttribute("msg", "修改成功!");
		return "f:/adminjsps/msg.jsp";
	}
	
	
	
	

}
