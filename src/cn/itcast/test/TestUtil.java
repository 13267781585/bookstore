package cn.itcast.test;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import cn.itcast.bookstore.user.dao.UserDao;
import cn.itcast.bookstore.user.domin.User;

public class TestUtil {
	//≤‚ ‘UserDao add
	@Test
	public void fun() throws SQLException {
		User user = new  User("123", "zhansan", "333", "we32", "dsfsd", true);
		UserDao userDao = new UserDao();
		userDao.add(user);
		
	}

	//≤‚ ‘ UserDao findUserByName findUserByEmail
	@Test
	public void fun1() throws SQLException {
		UserDao userDao = new UserDao();
		User user = userDao.findUserByName("zhansan");
		if(user != null)
			System.out.println(user.toString());
		else
			System.out.print("Œ™ø’!");
		
	}
	
	
}
