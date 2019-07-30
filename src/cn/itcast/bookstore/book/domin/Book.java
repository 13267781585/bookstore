package cn.itcast.bookstore.book.domin;

import cn.itcast.bookstore.category.domin.Category;

public class Book {
//	CREATE TABLE book (
//			  bid CHAR(32) PRIMARY KEY,/*主键*/
//			  bname VARCHAR(100),/*图书名*/
//			  price DECIMAL(5,1),/*单价*/
//			  author VARCHAR(20),/*作者*/
//			  image VARCHAR(200),/*图片*/
//			  cid CHAR(32),/*所属分类*/
//			  FOREIGN KEY (cid) REFERENCES category(cid)/*建立主外键关系*/
//			);
	
	private String bid;
	private String bname;
	private double price;
	private String author;
	private String image;
	private Category category;
	private boolean del;
	
	public boolean isDel() {
		return del;
	}
	public void setDel(boolean del) {
		this.del = del;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "Book [bid=" + bid + ", bname=" + bname + ", price=" + price + ", author=" + author + ", image=" + image
				+ ", category=" + category + "]";
	}

	
	
}
