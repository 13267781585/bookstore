package cn.itcast.bookstore.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.bookstore.user.domin.User;
import cn.itcast.jdbc.TxQueryRunner;

public class UserDao {
	QueryRunner qr = new TxQueryRunner();
	
	//�û�����֤
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
	
	//������֤
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
	
	//ע������û�
	
	//CREATE TABLE tb_user(
	//  uid CHAR(32) PRIMARY KEY,/*����*/
	//  username VARCHAR(50) NOT NULL,/*�û���*/
	//  `password` VARCHAR(50) NOT NULL,/*����*/
	//  email VARCHAR(50) NOT NULL,/*����*/
	//  `code` CHAR(64) NOT NULL,/*������*/
	//  state BOOLEAN/*�û�״̬���������Ƿ񼤻�*/
	//);
	
	public void add(User user)
	{
		String sql = "insert into tb_user values(?,?,?,?,?,?);";
		Object[] params = {user.getUid(),user.getUsername(),user.getPassword(),user.getEmail(),
				user.getCode(),user.isState()};   //����
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//ͨ������������û�
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
	
	//�޸��û��ļ���״̬
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
