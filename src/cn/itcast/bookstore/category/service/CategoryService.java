package cn.itcast.bookstore.category.service;

import java.util.List;

import cn.itcast.bookstore.book.dao.BookDao;
import cn.itcast.bookstore.category.dao.CategoryDao;
import cn.itcast.bookstore.category.domin.Category;
import cn.itcast.bookstore.category.domin.CategoryException;

public class CategoryService {
	private CategoryDao categoryDao = new CategoryDao();
	private BookDao bookDao = new BookDao();
	//查询所有分类
	public List<Category> findAll()
	{
		return categoryDao.findAll();
	}
	
	//添加分类
	public void add(Category category)
	{
		categoryDao.add(category);
	}
	
	//删除分类
	public void delete(String cid) throws CategoryException
	{
		/*
		 * 查询该分类下是否有图书
		 * 有图书抛出异常
		 * 没有删除分类
		 */
		
		int count = bookDao.getBookCountByCid(cid);
		
		if(count > 0)
			throw new CategoryException("该分类下有图书，不能删除！");
		
		categoryDao.delete(cid);
	}
	
	//修改分类前的操作
	public Category editPre(String cid)
	{
		return categoryDao.findCategoryByCid(cid);
		
	}
	
	//修改分类
	public void update(Category category)
	{
		categoryDao.update(category);
	}
	
	
	
	
	
	
	
	
}
