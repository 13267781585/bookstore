package cn.itcast.bookstore.order.domin;

import cn.itcast.bookstore.book.domin.Book;

public class OrderItem {
	
//	/*订单项表*/
//	CREATE TABLE orderitem (
//	  iid CHAR(32) PRIMARY KEY,/*主键*/
//	  `count` INT,/*数量*/
//	  subtotal DECIMAL(10,0),/*小计*/
//	  oid CHAR(32),/*所属订单*/
//	  bid CHAR(32),/*订单项所指的商品*/
//	  FOREIGN KEY (oid) REFERENCES orders (oid),/*建立主外键关系*/
//	  FOREIGN KEY (bid) REFERENCES book (bid)/*建立主外键关系*/
//	);
	
	private String iid;
	private int count;
	private double subtotal;
	private Book book;
	private Order order;
	public String getIid() {
		return iid;
	}
	public void setIid(String iid) {
		this.iid = iid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	
}
