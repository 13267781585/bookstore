package cn.itcast.bookstore.user.service;

import cn.itcast.bookstore.user.dao.UserDao;
import cn.itcast.bookstore.user.domin.User;

public class UserService {
	UserDao userDao = new UserDao();
	
	//注册
	public void regist(User form) throws UserException 
	{
		String username = form.getUsername();
		String email = form.getEmail();
		User user = userDao.findUserByName(username);
		if(user != null)
			throw new UserException("该用户已被注册!");
		user = userDao.findUserByEmail(email);
		if(user != null)
			throw new UserException("该邮箱已被注册!");
		
		userDao.add(form);
	}
	
	//账号激活
	public void active(String code) throws UserException
	{
		User user = userDao.findUserByCode(code);
		
		//验证激活条件
		if(user == null)
			throw new UserException("激活码无效!");
		if(user.isState())
			throw new UserException("您已经激活账户，请不要重复激活！");
		
		//调用方法修改用户的激活状态
		userDao.updateState(code, true);
		
	}
	
	//登录
	public User login(User form) throws UserException
	{
		String username = form.getUsername();
		User user = userDao.findUserByName(username);
		
		if(user == null)
			throw new UserException("该用户不存在!");
		if(!user.getPassword().equals(form.getPassword()))
			throw new UserException("密码错误!");
		if(!user.isState())
			throw new UserException("该账户没有激活!");
		
		return user;
			
	}
	
	
	
	
	
	
}
