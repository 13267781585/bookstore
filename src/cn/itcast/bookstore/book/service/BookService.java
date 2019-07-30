package cn.itcast.bookstore.book.service;

import java.util.List;

import cn.itcast.bookstore.book.dao.BookDao;
import cn.itcast.bookstore.book.domin.Book;

public class BookService {
	private BookDao bookDao = new BookDao();
	
	//查找所有图书
	public List<Book> findAll()
	{
		return bookDao.findAll();
	}

	//按分类查找 图书
	public List<Book> findBookById(String cid)
	{
		return bookDao.findBookById(cid);
	}
	
	//加载图书信息
	public Book load(String bid)
	{
		return bookDao.load(bid);
	}
	
	//添加图书
	public void addBook(Book book)
	{
		bookDao.addBook(book);
	}
	
	//删除图书
	public void deleteBook(String bid)
	{
		bookDao.deleteBook(bid);
	}
	
	//修改图书
	public void updateBook(Book book)
	{
		bookDao.updateBook(book);
	}
	
	
}
