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
		request.setAttribute("book_list", list);         //������༯��
		return "f:/jsps/book/list.jsp";                       //ת������Ӧjspҳ��
	}
	
	//�������ѯͼ��
	public String findBookById(HttpServletRequest request, HttpServletResponse response)
	{
		String cid = request.getParameter("cid");         //��ȡ�������
		List<Book> list = bookService.findBookById(cid);
		request.setAttribute("book_list", list);
		return "f:/jsps/book/list.jsp";
	}
	
	//��ѯͼ��ľ�����Ϣ
	public String load(HttpServletRequest request, HttpServletResponse response)
	{
		String bid = request.getParameter("bid");         //��ȡ�������
		Book book = bookService.load(bid);
		request.setAttribute("book", book);
		return "f:/jsps/book/desc.jsp";
	}
}
