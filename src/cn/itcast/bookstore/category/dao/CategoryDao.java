package cn.itcast.bookstore.category.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.bookstore.category.domin.Category;
import cn.itcast.jdbc.TxQueryRunner;

public class CategoryDao {
	private QueryRunner qr = new TxQueryRunner();
	
	//�������з���
	public List<Category> findAll()
	{
		String sql = "select * from category;";
		try {
			return qr.query(sql, new BeanListHandler<Category>(Category.class));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//��ӷ���
	public void add(Category category)
	{
		String sql = "insert into category values(?,?);";
		
		try {
			qr.update(sql,category.getCid(),category.getCname());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//ɾ������
	public void delete(String cid)
	{
		String sql = "delete from category where cid=?;";
		try {
			qr.update(sql,cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//����cid ��ѯ��Ӧ����
	public Category findCategoryByCid(String cid)
	{
		String sql = "select * from category where cid=?;";
		try {
			return qr.query(sql, new BeanHandler<Category>(Category.class),cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//�޸ķ���
	public void update(Category category)
	{
		String sql = "update category set cname=? where cid=?;";
		Object[] params = {category.getCname(),category.getCid()};
		
		try {
			qr.update(sql,params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
