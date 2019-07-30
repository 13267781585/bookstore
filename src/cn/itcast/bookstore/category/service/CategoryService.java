package cn.itcast.bookstore.category.service;

import java.util.List;

import cn.itcast.bookstore.book.dao.BookDao;
import cn.itcast.bookstore.category.dao.CategoryDao;
import cn.itcast.bookstore.category.domin.Category;
import cn.itcast.bookstore.category.domin.CategoryException;

public class CategoryService {
	private CategoryDao categoryDao = new CategoryDao();
	private BookDao bookDao = new BookDao();
	//��ѯ���з���
	public List<Category> findAll()
	{
		return categoryDao.findAll();
	}
	
	//��ӷ���
	public void add(Category category)
	{
		categoryDao.add(category);
	}
	
	//ɾ������
	public void delete(String cid) throws CategoryException
	{
		/*
		 * ��ѯ�÷������Ƿ���ͼ��
		 * ��ͼ���׳��쳣
		 * û��ɾ������
		 */
		
		int count = bookDao.getBookCountByCid(cid);
		
		if(count > 0)
			throw new CategoryException("�÷�������ͼ�飬����ɾ����");
		
		categoryDao.delete(cid);
	}
	
	//�޸ķ���ǰ�Ĳ���
	public Category editPre(String cid)
	{
		return categoryDao.findCategoryByCid(cid);
		
	}
	
	//�޸ķ���
	public void update(Category category)
	{
		categoryDao.update(category);
	}
	
	
	
	
	
	
	
	
}
