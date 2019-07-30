package cn.itcast.bookstore.user.service;

import cn.itcast.bookstore.user.dao.UserDao;
import cn.itcast.bookstore.user.domin.User;

public class UserService {
	UserDao userDao = new UserDao();
	
	//ע��
	public void regist(User form) throws UserException 
	{
		String username = form.getUsername();
		String email = form.getEmail();
		User user = userDao.findUserByName(username);
		if(user != null)
			throw new UserException("���û��ѱ�ע��!");
		user = userDao.findUserByEmail(email);
		if(user != null)
			throw new UserException("�������ѱ�ע��!");
		
		userDao.add(form);
	}
	
	//�˺ż���
	public void active(String code) throws UserException
	{
		User user = userDao.findUserByCode(code);
		
		//��֤��������
		if(user == null)
			throw new UserException("��������Ч!");
		if(user.isState())
			throw new UserException("���Ѿ������˻����벻Ҫ�ظ����");
		
		//���÷����޸��û��ļ���״̬
		userDao.updateState(code, true);
		
	}
	
	//��¼
	public User login(User form) throws UserException
	{
		String username = form.getUsername();
		User user = userDao.findUserByName(username);
		
		if(user == null)
			throw new UserException("���û�������!");
		if(!user.getPassword().equals(form.getPassword()))
			throw new UserException("�������!");
		if(!user.isState())
			throw new UserException("���˻�û�м���!");
		
		return user;
			
	}
	
	
	
	
	
	
}
