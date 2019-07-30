package cn.itcast.bookstore.book.service;

import java.util.List;

import cn.itcast.bookstore.book.dao.BookDao;
import cn.itcast.bookstore.book.domin.Book;

public class BookService {
	private BookDao bookDao = new BookDao();
	
	//��������ͼ��
	public List<Book> findAll()
	{
		return bookDao.findAll();
	}

	//��������� ͼ��
	public List<Book> findBookById(String cid)
	{
		return bookDao.findBookById(cid);
	}
	
	//����ͼ����Ϣ
	public Book load(String bid)
	{
		return bookDao.load(bid);
	}
	
	//���ͼ��
	public void addBook(Book book)
	{
		bookDao.addBook(book);
	}
	
	//ɾ��ͼ��
	public void deleteBook(String bid)
	{
		bookDao.deleteBook(bid);
	}
	
	//�޸�ͼ��
	public void updateBook(Book book)
	{
		bookDao.updateBook(book);
	}
	
	
}
