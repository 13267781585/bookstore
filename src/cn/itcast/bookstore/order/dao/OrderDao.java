package cn.itcast.bookstore.order.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.itcast.bookstore.book.domin.Book;
import cn.itcast.bookstore.order.domin.Order;
import cn.itcast.bookstore.order.domin.OrderItem;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class OrderDao {
	private QueryRunner qr = new TxQueryRunner();
	
	//���涩��
	public void add(Order order)
	{
		String sql = "insert into orders values(?,?,?,?,?,?);";
		/*
		 * ����util��Dateת����sql��Timestamp
		 * Ҳ���Բ�ת��  ϵͳû�����쳣
		 */
		Timestamp timestamp = new Timestamp(order.getOrdertime().getTime());
		Object[] params = {order.getOid(),timestamp,
				order.getTotal(),order.getState(),order.getOwner().getUid(),
				order.getAddress()};
		
		try {
			qr.update(sql,params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//�������涩����
	public void addOrderItems(List<OrderItem> list)
	{
		String sql = "insert into orderItem values(?,?,?,?,?);";
		int num = list.size();
		Object[][] params = new Object[num][];
		
		for(int i = 0; i < num; i++)
		{
			OrderItem orderItem = list.get(i);
			params[i] = new Object[]{orderItem.getIid(),orderItem.getCount(),
					orderItem.getSubtotal(),orderItem.getOrder().getOid(),
					orderItem.getBook().getBid()}; 
			
		}
		
		try {
			qr.batch(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//��ѯ���ж���
	public List<Order> findOrderByUid(String uid)
	{
		String sql = "select * from orders where uid=?;";
		List<Order>list = null;
		try {
			 list = qr.query(sql, new BeanListHandler<Order>(Order.class),uid);
						
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		//����Order��OrderItem
		/*
		 * ѭ������ÿһ��order
		 * ��ѯ���ɶ�����Ŀ
		 * ��ֵ��order
		 */
		for(Order order:list)
		{
			List<OrderItem> orderItems = toOrderItems(order);    
			order.setList(orderItems);
		}
		
		return list;
	}
	
	
	public List<OrderItem> toOrderItems(Order order)
	{
		//����ѯ
		String sql = "select * from book b ,orderitem o where b.bid=o.bid and oid=?;";
		List<OrderItem> orderItem_list = new ArrayList<OrderItem>();
		try {
			/*
			 * ��Ϊһ�н������Ӧ�Ĳ�����һ��javabean�����Բ�����ʹ��BeanListHandler������MapListHandler
			 * һ�а���һ��Book �� һ�� OrderItem ����  Ϊ��Ӧ��ϵ
			 */
			List<Map<String,Object>> map_list = qr.query(sql, new MapListHandler(),order.getOid());      
			
			for(Map<String,Object> map:map_list)
			{
				OrderItem orderItem = toOrderItem(map);           //��һ�м�¼ת��Ϊһ��Book��һ��OrderItem����
				orderItem_list.add(orderItem);
				
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return orderItem_list;
		
	}
	
	
	public OrderItem toOrderItem(Map<String,Object> map)
	{
		OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);    
		Book book = CommonUtils.toBean(map,Book.class);
		orderItem.setBook(book);
		return orderItem;
	}
	
	//���ض���
	public Order load(String oid)
	{
		String sql = "select * from orders where oid=?;";
		Order order = null;
		try {
			order = qr.query(sql, new BeanHandler<Order>(Order.class),oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		List<OrderItem> orderItems = toOrderItems(order);    
		order.setList(orderItems);
		return order;
	}
	
	//���ݶ����Ų�ѯ����״̬
	public int findStateByOid(String oid)
	{
		String sql = "select * from orders where oid=?";
		Order order = null;
		try {
			order = qr.query(sql, new BeanHandler<Order>(Order.class),oid);
			
		} catch (SQLException e) {
			new RuntimeException(e);
		}
		int state = order.getState();
		return state;
	}
	
	//���ݶ������޸Ķ���״̬
	public void updateState(String oid,int state)
	{
		String sql = "update orders set state=? where oid=?;";
		try {
			qr.update(sql,state,oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
