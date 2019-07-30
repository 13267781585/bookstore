package cn.itcast.bookstore.book.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.bookstore.book.domin.Book;
import cn.itcast.bookstore.category.domin.Category;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class BookDao {
	private QueryRunner qr = new TxQueryRunner();
	
	//查询所有图书
	public List<Book> findAll()
	{
		String sql = "select * from book where del=false;";
		try {
			return qr.query(sql, new BeanListHandler<Book>(Book.class));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//按分类查询图书
		public List<Book> findBookById(String cid)
		{
			String sql = "select * from book where cid=? and del=false;";
			try {
				return qr.query(sql, new BeanListHandler<Book>(Book.class),cid);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	
		//加载图书信息
		public Book load(String bid)
		{
			String sql = "select * from book where bid=? and  del=false;";
			try {
				/*
				 * 因为在后台加载图书的具体信息时需要Book 中的Category 中的cid 数据
				 * 因此这里需要对 Book 中的category对象做一定的赋值操作
				 */
				Map<String, Object> map = qr.query(sql, new MapHandler(),bid);
				Category category = CommonUtils.toBean(map, Category.class);
				Book book = CommonUtils.toBean(map, Book.class);
				book.setCategory(category);
				return book;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		//查询在某一分类下图书的数量
		public int getBookCountByCid(String cid)
		{
			String sql = "select count(*) from book where cid=? and del=false;";
			try {
				Number num =  (Number)qr.query(sql, new ScalarHandler(),cid);
				return num.intValue();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 1;          //默认返回1；
		}
		
		//添加图书
		public void addBook(Book book)
		{
			String sql = "insert into book values(?,?,?,?,?,?);";
			Object[] params = {book.getBid(),book.getBname(),
					book.getPrice(),book.getAuthor(),
					book.getImage(),book.getCategory().getCid()};
			
			try {
				qr.update(sql,params);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		//删除图书-->修改book 的 del属性
		public void deleteBook(String bid)
		{
			String sql = "update book set del=true where bid=?;";
			try {
				qr.update(sql,bid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		//修改图书
		public void updateBook(Book book)
		{
			String sql = "update book set bname=? , price=? , author=?,"
					+ " image=? , cid=? where bid=?;";
			
			Object[] params = {book.getBname(),book.getPrice(),
					book.getAuthor(),book.getImage(),book.getCategory().getCid(),book.getBid()};
			
		    try {
				qr.update(sql,params);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    
		}
		
		
}
