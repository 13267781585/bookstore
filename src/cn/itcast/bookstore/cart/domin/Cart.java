package cn.itcast.bookstore.cart.domin;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
	private Map<String,CartItem> cart = new LinkedHashMap<String,CartItem>();
	
	//���ع��ﳵ��Ŀ�۸��ܺ�
	public double getCartTotal()
	{
		BigDecimal sum = new BigDecimal(0);
		//ѭ�����ж�����Ŀ  �ۼӼ۸�
		for(CartItem cartItem:cart.values())
		{
			BigDecimal p = new BigDecimal(cartItem.getCartItemTotal());
			sum = sum.add(p);
		}
		System.out.println(Double.parseDouble(sum.toString()));
		double b = Double.parseDouble(sum.toString());
		return b;

	}
	
	
	//������Ʒ
	public void add(CartItem cartItem)
	{
		if(cart.containsKey(cartItem.getBook().getBid()))
		{
			//�жϹ��ﳵ�Ƿ��и���Ʒ ����������һ   û�мӼ���
			CartItem tempt = cart.get(cartItem.getBook().getBid());
			tempt.setCount(cartItem.getCount() + tempt.getCount());      //��������Ʒ����
			cart.remove(cartItem.getBook().getBid());            //�Ƴ�ԭ����Ŀ
			cart.put(cartItem.getBook().getBid(), tempt);          //��������Ŀ
		}
		else
			cart.put(cartItem.getBook().getBid(), cartItem);
			
	}
	
	//��չ��ﳵ
	public void clear()
	{
		cart.clear();
	}
	
	//ɾ��
	public void delete(String bid)
	{
		cart.remove(bid);
	}
	
	//���ع��ﳵ������Ʒ
	public Collection<CartItem> getCartItems()
	{
		return cart.values();
	}
}
