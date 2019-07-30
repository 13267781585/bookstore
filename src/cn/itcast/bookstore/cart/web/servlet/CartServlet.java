package cn.itcast.bookstore.cart.web.servlet;

import cn.itcast.bookstore.book.domin.Book;
import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.bookstore.cart.domin.Cart;
import cn.itcast.bookstore.cart.domin.CartItem;
import cn.itcast.bookstore.cart.service.CartService;
import cn.itcast.servlet.BaseServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CartServlet")
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	private CartService cartService = new CartService();
	
	//添加商品
	public String add(HttpServletRequest request,HttpServletResponse response)
	{
		/*
		 * 1.获得购物车
		 */
		Cart cart = (Cart)request.getSession().getAttribute("cart");
//		if(cart == null)
//		{
//			request.setAttribute("msg", "您还未登录!不能进行该操作!");
//			return "f:/jsps/msg.jsp";
//		}
			/*
		 * 2.得到CartItem
		 *   得到bid
		 *   调用BookService类的load方法的到对应的Book对象
		 *   新建新的商品条目
		 *   添加条目到购物车
		 *   转发到/jsps/cart/list.jsp
		 */ 
		String bid = request.getParameter("bid");
		int count = Integer.parseInt(request.getParameter("count"));
		Book book = new BookService().load(bid);
		
		CartItem cartItem = new CartItem();
		cartItem.setBook(book);
		cartItem.setCount(count);
		
		cart.add(cartItem);
		return "f:/jsps/cart/list.jsp";
	}
	
	//清空购物车
	public String clear(HttpServletRequest request,HttpServletResponse response)
	{
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		cart.clear();
		return "f:/jsps/cart/list.jsp";
	}
	
	//删除商品
	public String delete(HttpServletRequest request,HttpServletResponse response)
	{
		String bid = request.getParameter("bid");
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		
		cart.delete(bid);
		return "f:/jsps/cart/list.jsp";
		
	}
	
	
}
