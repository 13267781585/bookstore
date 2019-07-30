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
	
	//查找所有分类
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
	
	//添加分类
	public void add(Category category)
	{
		String sql = "insert into category values(?,?);";
		
		try {
			qr.update(sql,category.getCid(),category.getCname());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//删除分类
	public void delete(String cid)
	{
		String sql = "delete from category where cid=?;";
		try {
			qr.update(sql,cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//根据cid 查询对应分类
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
	
	//修改分类
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
