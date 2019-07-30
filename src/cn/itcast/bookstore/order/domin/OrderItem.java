package cn.itcast.bookstore.order.domin;

import cn.itcast.bookstore.book.domin.Book;

public class OrderItem {
	
//	/*�������*/
//	CREATE TABLE orderitem (
//	  iid CHAR(32) PRIMARY KEY,/*����*/
//	  `count` INT,/*����*/
//	  subtotal DECIMAL(10,0),/*С��*/
//	  oid CHAR(32),/*��������*/
//	  bid CHAR(32),/*��������ָ����Ʒ*/
//	  FOREIGN KEY (oid) REFERENCES orders (oid),/*�����������ϵ*/
//	  FOREIGN KEY (bid) REFERENCES book (bid)/*�����������ϵ*/
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
