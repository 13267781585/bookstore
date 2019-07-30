package cn.itcast.bookstore.cart.domin;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
	private Map<String,CartItem> cart = new LinkedHashMap<String,CartItem>();
	
	//返回购物车条目价格总和
	public double getCartTotal()
	{
		BigDecimal sum = new BigDecimal(0);
		//循环所有订单条目  累加价格
		for(CartItem cartItem:cart.values())
		{
			BigDecimal p = new BigDecimal(cartItem.getCartItemTotal());
			sum = sum.add(p);
		}
		System.out.println(Double.parseDouble(sum.toString()));
		double b = Double.parseDouble(sum.toString());
		return b;

	}
	
	
	//增加商品
	public void add(CartItem cartItem)
	{
		if(cart.containsKey(cartItem.getBook().getBid()))
		{
			//判断购物车是否有该商品 有则数量加一   没有加加入
			CartItem tempt = cart.get(cartItem.getBook().getBid());
			tempt.setCount(cartItem.getCount() + tempt.getCount());      //设置新商品数量
			cart.remove(cartItem.getBook().getBid());            //移除原有条目
			cart.put(cartItem.getBook().getBid(), tempt);          //增加新条目
		}
		else
			cart.put(cartItem.getBook().getBid(), cartItem);
			
	}
	
	//清空购物车
	public void clear()
	{
		cart.clear();
	}
	
	//删除
	public void delete(String bid)
	{
		cart.remove(bid);
	}
	
	//返回购物车所有商品
	public Collection<CartItem> getCartItems()
	{
		return cart.values();
	}
}
