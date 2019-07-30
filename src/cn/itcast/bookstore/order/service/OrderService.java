package cn.itcast.bookstore.order.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.bookstore.order.dao.OrderDao;
import cn.itcast.bookstore.order.domin.Order;
import cn.itcast.jdbc.JdbcUtils;

public class OrderService {
	private OrderDao orderDao = new OrderDao();
	
	public void add(Order order) {
		try {
			JdbcUtils.beginTransaction();       //��������  
			
			orderDao.add(order);               //���涩��
			orderDao.addOrderItems(order.getList());       //���涩����
			
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {

			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			throw new RuntimeException();
		}

	}
	
	//�ҵĶ���
	public List<Order> myOrder(String uid)
	{
		return orderDao.findOrderByUid(uid);
	}
	
	//���ض���
	public Order load(String oid)
	{
		return orderDao.load(oid);
	}
	
	//ȷ���ջ�
	public void confirm(String oid) throws OrderException
	{
		int state = orderDao.findStateByOid(oid);
		
		/*
		 * 1. У�鶩��״̬���������3���׳��쳣
		 */
		/*
		 * 2. �޸Ķ���״̬Ϊ4����ʾ���׳ɹ�
		 */
		if(state != 3)
			throw new OrderException("����ִ��ȷ���ջ�����");
		else
			orderDao.updateState(oid, 4);
		
	}
	
	//���л���
	public void back(String oid)
	{
		int state = orderDao.findStateByOid(oid);
		//�������״̬Ϊ1 �޸�״̬
		if(state == 1)
		{
			orderDao.updateState(oid, 2);
		}
		
	}
}
