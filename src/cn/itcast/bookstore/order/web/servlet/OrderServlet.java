package cn.itcast.bookstore.order.web.servlet;

import cn.itcast.bookstore.cart.domin.Cart;
import cn.itcast.bookstore.cart.domin.CartItem;
import cn.itcast.bookstore.order.domin.Order;
import cn.itcast.bookstore.order.domin.OrderItem;
import cn.itcast.bookstore.order.service.OrderException;
import cn.itcast.bookstore.order.service.OrderService;
import cn.itcast.bookstore.user.domin.User;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/OrderServlet")
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private OrderService orderService = new OrderService();
	
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//生成订单
		Order order = new Order();
		HttpSession session = request.getSession();
		Cart cart = (Cart)session.getAttribute("cart");   //获取session 中的购物车
		User owner = (User) session.getAttribute("user");  //获取session 中的用户
		
		//设置订单信息
		order.setOid(CommonUtils.uuid());
		order.setOrdertime(new Date());
		order.setOwner(owner);
		order.setState(1);
		order.setTotal(cart.getCartTotal());
		
		/*
		 * 创建订单条目集合
		 * 
		 * cartItemList --> orderItemList
		 */
		List<OrderItem> list = new ArrayList<OrderItem> ();
		for(CartItem cartItem:cart.getCartItems())
		{
			//通过购物车信息生成订单条目
			OrderItem orderItem = new OrderItem();
			orderItem.setIid(CommonUtils.uuid());
			orderItem.setBook(cartItem.getBook());
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getCartItemTotal());
			orderItem.setOrder(order);
			
			list.add(orderItem);
			
		}
		
		order.setList(list);
		
		orderService.add(order);
		
		// 清空购物车
		cart.clear();
				
		request.setAttribute("order", order);
		return "f:/jsps/order/desc.jsp";
	}
	
	public String myOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");  //获取用户信息
		String uid = user.getUid();
		
		List<Order> orders = orderService.myOrder(uid);
		request.setAttribute("orders", orders);
		return "f:/jsps/order/list.jsp";
		
	}
	
	//点击付款时加载订单
	public String load(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String oid = request.getParameter("oid");
		
		Order order = orderService.load(oid);
		request.setAttribute("order", order);
		return "f:/jsps/order/desc.jsp";
	}
	
	//确认收货
	public String confirm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oid = request.getParameter("oid");
		
		//有异常 保存异常信息
		try {
			orderService.confirm(oid);
		} catch (OrderException e) {
			request.setAttribute("msg", e.getMessage());
			return "f:/jsps/order/msg.jsp";
		}
		request.setAttribute("msg", "确认收货成功!");
		return "f:/jsps/order/msg.jsp";
		
	}
	
	//支付操作  通过易宝跳转到银行页面
	/*
	 * 准备银行需要的13+1 参数
	 */
	public String pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Properties props = new Properties();
		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream("merchantInfo.properties");
		props.load(input);
		/*
		 * 准备13参数
		 */
		String p0_Cmd = "Buy";
		String p1_MerId = props.getProperty("p1_MerId");
		String p2_Order = request.getParameter("oid");
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		String p8_Url = props.getProperty("p8_Url");
		String p9_SAF = "";
		String pa_MP = "";
		String pd_FrpId = request.getParameter("pd_FrpId");
		String pr_NeedResponse = "1";

		/*
		 * 计算hmac
		 */
		String keyValue = props.getProperty("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);

		/*
		 * 连接易宝的网址和13+1个参数
		 */
		StringBuilder url = new StringBuilder(props.getProperty("url"));
		url.append("?p0_Cmd=").append(p0_Cmd);
		url.append("&p1_MerId=").append(p1_MerId);
		url.append("&p2_Order=").append(p2_Order);
		url.append("&p3_Amt=").append(p3_Amt);
		url.append("&p4_Cur=").append(p4_Cur);
		url.append("&p5_Pid=").append(p5_Pid);
		url.append("&p6_Pcat=").append(p6_Pcat);
		url.append("&p7_Pdesc=").append(p7_Pdesc);
		url.append("&p8_Url=").append(p8_Url);
		url.append("&p9_SAF=").append(p9_SAF);
		url.append("&pa_MP=").append(pa_MP);
		url.append("&pd_FrpId=").append(pd_FrpId);
		url.append("&pr_NeedResponse=").append(pr_NeedResponse);
		url.append("&hmac=").append(hmac);

		System.out.println(url);

		/*
		 * 重定向到易宝
		 */
		response.sendRedirect(url.toString());

		
		return null;
	}
	
	//银行回馈调用的方法
	public String back(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 * 1. 获取11 + 1
		 */
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");

		String hmac = request.getParameter("hmac");

		/*
		 * 2. 校验访问者是否为易宝！
		 */
		Properties props = new Properties();
		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream("merchantInfo.properties");
		props.load(input);
		String keyValue = props.getProperty("keyValue");

		boolean bool = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		
		if(!bool) {//如果校验失败
			request.setAttribute("msg", "您不是什么好东西！");
			return "f:/jsps/msg.jsp";
		}
		
		/*
		 * 3. 获取状态订单，确定是否要修改订单状态，以及添加积分等业务操作
		 */
		orderService.back(r6_Order);//有可能对数据库进行操作，也可能不操作！
		
		/*
		 * 4. 判断当前回调方式
		 *   如果为点对点，需要回馈以success开头的字符串
		 */
		if(r9_BType.equals("2")) {
			response.getWriter().print("success");
		}
		
		/*
		 * 5. 保存成功信息，转发到msg.jsp
		 */
		request.setAttribute("msg", "支付成功！等待卖家发货！你慢慢等~");
		return "f:/jsps/msg.jsp";

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
