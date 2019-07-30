package cn.itcast.bookstore.cart.dao;

import org.apache.commons.dbutils.QueryRunner;

import cn.itcast.jdbc.TxQueryRunner;

public class CartDao {
	private QueryRunner qr = new TxQueryRunner();
	
}
