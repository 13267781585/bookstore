package cn.itcast.bookstore.user.web.servlet;

import cn.itcast.bookstore.cart.domin.Cart;
import cn.itcast.bookstore.user.domin.User;
import cn.itcast.bookstore.user.service.UserException;
import cn.itcast.bookstore.user.service.UserService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;


import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	UserService userService = new UserService();
	
	/*
	 * 1. 封装表单数据到form对象中
	 * 2. 补全：uid、code
	 * 3. 输入校验
	 *   > 保存错误信息、form到request域，转发到regist.jsp
	 * 4. 调用service方法完成注册
	 *   > 保存错误信息、form到request域，转发到regist.jsp
	 * 5. 发邮件
	 * 6. 保存成功信息转发到msg.jsp
	 */
	public String regist(HttpServletRequest request,HttpServletResponse response)
	{
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		form.setUid(CommonUtils.uuid());  //补全信息
		form.setCode(CommonUtils.uuid() + CommonUtils.uuid());
		
		Map<String,String> error = new HashMap<String,String>();  //保存错误信息
		
		String username = form.getUsername();
		String email = form.getEmail();
		String password = form.getPassword();
		
		//输入验证
		if(username == null || username.trim().isEmpty())
			error.put("username", "用户名不能为空！");
		else
			if(username.length() < 3)
				error.put("username","用户名的长度不能小于3");
		
		if(email == null || email.trim().isEmpty())
			error.put("email", "Email不能为空！");
		else
			if(!email.matches("\\w+@\\w+\\.\\w+"))
				error.put("email","Email格式错误！");
		
		if(password == null || password.trim().isEmpty())
			error.put("password", "密码不能为空！");
		else
			if(password.length() < 3)
				error.put("password","密码的长度不能小于3");
		
		//判断是否有错误
		if(error.size() > 0)
		{
			request.setAttribute("error", error);  //保存错误信息到request域中
			request.setAttribute("form",form);   //保存表单数据用于回显
			return "f:/jsps/user/regist.jsp";    //转发到注册页面
		}
		
		try {
			userService.regist(form);
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());  //保存错误信息
			request.setAttribute("form", form);          //保存表单数据
			return "f:/jsps/user/regist.jsp";             //转发到注册页面
		}
		
		//载入配置文件
		Properties prop = new Properties();
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("email-information.properties");
		try {
			prop.load(input);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		//获取邮箱属性信息
		String host = prop.getProperty("host");
		String user = prop.getProperty("user");
		String psd = prop.getProperty("password");
		String subject = prop.getProperty("subject");
		String content = prop.getProperty("content");
		String from = prop.getProperty("from");
		String to = form.getEmail();
		String code = form.getCode();
		content = MessageFormat.format(content,code);     //用激活码替换占位符
		
		//发邮件
		Session session = MailUtils.createSession(host, user, psd);
		Mail mail = new Mail(from,to,subject,content);
		try {
			MailUtils.send(session, mail);
		} catch (Exception e) {
			new RuntimeException(e);
		}
		
		request.setAttribute("msg", "注册成功!");
		//注册完成  转发到注册成功页面
		return "f:/jsps/msg.jsp";
	}
	
	//账号激活
	public String active(HttpServletRequest request,HttpServletResponse response)
	{
		String code = request.getParameter("code");  //获取激活码
		
		//验证激活码是否为空
		if(code == null || code.trim().isEmpty())
		{
			request.setAttribute("msg", "激活码为空！");
			return "f:/jsps/msg.jsp";
		}
		
		try {
			userService.active(code);
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());        //如果抛出异常  保存异常信息 转发
			return "f:/jsps/msg.jsp";
		}
		
		//如果没有异常  保存成功信息  转发
		request.setAttribute("msg", "激活成功!");
		return "f:/jsps/msg.jsp";
	}
	
	//登录
	public String login(HttpServletRequest request,HttpServletResponse response)
	{
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);    //获取表单数据
		
		//验证数据
		String username = form.getUsername();
		String password = form.getPassword();
		if(username == null || username.trim().isEmpty())
		{
			request.setAttribute("msg", "用户名不能为空!");
			request.setAttribute("form", form);                //保存表单数据 回显
			return "f:/jsps/user/login.jsp";
		}
		if(password == null || password.trim().isEmpty())
		{
			request.setAttribute("msg", "密码不能为空！");
			request.setAttribute("form", form);
			return "f:/jsps/user/login.jsp";
		}
		
		try {
			User user = userService.login(form);                  
			      
			if(request.getSession() != null)
				request.getSession().invalidate(); //先注销之前的session
			
			HttpSession session = request.getSession();  
			session.setAttribute("user", user);        //登陆成功  保存用户信息到 session中
			session.setAttribute("cart", new Cart());     //创建购物车保存在session中
			return "r:/index.jsp";                           //转发到主页
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/jsps/user/login.jsp";
		}
		
			
	}
	

	//退出登录
	public String quit(HttpServletRequest request,HttpServletResponse response)
	{
		request.getSession().invalidate();   //注销登录
		return "r:/index.jsp";              //转发到主页
		
	}
	
	
	
	
	
}
