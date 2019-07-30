package cn.itcast.bookstore.order.domin;

import java.util.Date;
import java.util.List;

import cn.itcast.bookstore.user.domin.User;

public class Order {
//	CREATE TABLE orders (
//			  oid CHAR(32) PRIMARY KEY,/*主键*/
//			  ordertime DATETIME,/*订单生成时间*/
//			  total DECIMAL(10,0),/*订单合计*/
//			  state SMALLINT(1),/*订单状态：未付款、已付款但未发货、已发货但未确认收货、收货已结束*/
//			  uid CHAR(32),/*订单的主人*/
//			  address VARCHAR(200),/*订单的收货地址*/
//			  FOREIGN KEY (uid) REFERENCES tb_user(uid)/*建立主外键关系*/
//			);
	
	private String oid;
	private Date ordertime;
	private double total;
	private int state;
	private User owner;
	private String address;
	
	private List<OrderItem> list;
	
	public List<OrderItem> getList() {
		return list;
	}
	public void setList(List<OrderItem> list) {
		this.list = list;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Order [oid=" + oid + ", ordertime=" + ordertime + ", total=" + total + ", state=" + state + ", owner="
				+ owner + ", address=" + address + ", list=" + list + "]";
	}
	
	
}
