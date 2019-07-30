package cn.itcast.bookstore.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.bookstore.user.domin.User;
import cn.itcast.jdbc.TxQueryRunner;

public class UserDao {
	QueryRunner qr = new TxQueryRunner();
	
	//用户名验证
	public User findUserByName(String username) 
	{
		String sql = "select * from tb_user where username = ?;";
		User user = null;
		try {
			user = qr.query(sql, new BeanHandler<User>(User.class),username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	//邮箱验证
	public User findUserByEmail(String email) 
	{
		String sql = "select * from tb_user where email = ?;";
		try {
			return qr.query(sql, new BeanHandler<User>(User.class),email);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//注册添加用户
	
	//CREATE TABLE tb_user(
	//  uid CHAR(32) PRIMARY KEY,/*主键*/
	//  username VARCHAR(50) NOT NULL,/*用户名*/
	//  `password` VARCHAR(50) NOT NULL,/*密码*/
	//  email VARCHAR(50) NOT NULL,/*邮箱*/
	//  `code` CHAR(64) NOT NULL,/*激活码*/
	//  state BOOLEAN/*用户状态，有两种是否激活*/
	//);
	
	public void add(User user)
	{
		String sql = "insert into tb_user values(?,?,?,?,?,?);";
		Object[] params = {user.getUid(),user.getUsername(),user.getPassword(),user.getEmail(),
				user.getCode(),user.isState()};   //参数
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//通过激活码查找用户
	public User findUserByCode(String code)
	{
		String sql = "select * from tb_user where code = ?;";
		try {
			return qr.query(sql, new BeanHandler<User>(User.class),code);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//修改用户的激活状态
	public void updateState(String code,boolean state)
	{
		String sql = "update tb_user set state = ? where code = ?;";
		try {
			qr.update(sql,state,code);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
