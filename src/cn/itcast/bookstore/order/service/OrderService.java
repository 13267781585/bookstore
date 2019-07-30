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
			JdbcUtils.beginTransaction();       //开启事务  
			
			orderDao.add(order);               //保存订单
			orderDao.addOrderItems(order.getList());       //保存订单项
			
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
	
	//我的订单
	public List<Order> myOrder(String uid)
	{
		return orderDao.findOrderByUid(uid);
	}
	
	//加载订单
	public Order load(String oid)
	{
		return orderDao.load(oid);
	}
	
	//确认收货
	public void confirm(String oid) throws OrderException
	{
		int state = orderDao.findStateByOid(oid);
		
		/*
		 * 1. 校验订单状态，如果不是3，抛出异常
		 */
		/*
		 * 2. 修改订单状态为4，表示交易成功
		 */
		if(state != 3)
			throw new OrderException("不能执行确认收货操作");
		else
			orderDao.updateState(oid, 4);
		
	}
	
	//银行回馈
	public void back(String oid)
	{
		int state = orderDao.findStateByOid(oid);
		//如果订单状态为1 修改状态
		if(state == 1)
		{
			orderDao.updateState(oid, 2);
		}
		
	}
}
