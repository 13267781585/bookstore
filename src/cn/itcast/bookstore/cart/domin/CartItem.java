package cn.itcast.bookstore.cart.domin;

import java.math.BigDecimal;

import cn.itcast.bookstore.book.domin.Book;

public class CartItem {
	private Book book;    //图书
	private int count;   //数量
	
	//用BigDecimal 类型运算防止数据丢失
	public double getCartItemTotal()
	{
		BigDecimal p = new BigDecimal(book.getPrice());
		BigDecimal c = new BigDecimal(count);
		double b =  Double.parseDouble(p.multiply(c).toString());
		return b;
	}
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
