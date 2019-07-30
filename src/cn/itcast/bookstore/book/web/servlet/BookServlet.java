package cn.itcast.bookstore.book.web.servlet;

import cn.itcast.bookstore.book.domin.Book;
import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.servlet.BaseServlet;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BookServlet")
public class BookServlet extends BaseServlet {
	private BookService bookService = new BookService();
	private static final long serialVersionUID = 1L;
	
	public String findAll(HttpServletRequest request, HttpServletResponse response) 
	{
		List<Book> list = bookService.findAll();
		request.setAttribute("book_list", list);         //保存分类集合
		return "f:/jsps/book/list.jsp";                       //转发至对应jsp页面
	}
	
	//按分类查询图书
	public String findBookById(HttpServletRequest request, HttpServletResponse response)
	{
		String cid = request.getParameter("cid");         //获取分类参数
		List<Book> list = bookService.findBookById(cid);
		request.setAttribute("book_list", list);
		return "f:/jsps/book/list.jsp";
	}
	
	//查询图书的具体信息
	public String load(HttpServletRequest request, HttpServletResponse response)
	{
		String bid = request.getParameter("bid");         //获取分类参数
		Book book = bookService.load(bid);
		request.setAttribute("book", book);
		return "f:/jsps/book/desc.jsp";
	}
}
