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
	 * 1. ��װ�����ݵ�form������
	 * 2. ��ȫ��uid��code
	 * 3. ����У��
	 *   > ���������Ϣ��form��request��ת����regist.jsp
	 * 4. ����service�������ע��
	 *   > ���������Ϣ��form��request��ת����regist.jsp
	 * 5. ���ʼ�
	 * 6. ����ɹ���Ϣת����msg.jsp
	 */
	public String regist(HttpServletRequest request,HttpServletResponse response)
	{
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		form.setUid(CommonUtils.uuid());  //��ȫ��Ϣ
		form.setCode(CommonUtils.uuid() + CommonUtils.uuid());
		
		Map<String,String> error = new HashMap<String,String>();  //���������Ϣ
		
		String username = form.getUsername();
		String email = form.getEmail();
		String password = form.getPassword();
		
		//������֤
		if(username == null || username.trim().isEmpty())
			error.put("username", "�û�������Ϊ�գ�");
		else
			if(username.length() < 3)
				error.put("username","�û����ĳ��Ȳ���С��3");
		
		if(email == null || email.trim().isEmpty())
			error.put("email", "Email����Ϊ�գ�");
		else
			if(!email.matches("\\w+@\\w+\\.\\w+"))
				error.put("email","Email��ʽ����");
		
		if(password == null || password.trim().isEmpty())
			error.put("password", "���벻��Ϊ�գ�");
		else
			if(password.length() < 3)
				error.put("password","����ĳ��Ȳ���С��3");
		
		//�ж��Ƿ��д���
		if(error.size() > 0)
		{
			request.setAttribute("error", error);  //���������Ϣ��request����
			request.setAttribute("form",form);   //������������ڻ���
			return "f:/jsps/user/regist.jsp";    //ת����ע��ҳ��
		}
		
		try {
			userService.regist(form);
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());  //���������Ϣ
			request.setAttribute("form", form);          //���������
			return "f:/jsps/user/regist.jsp";             //ת����ע��ҳ��
		}
		
		//���������ļ�
		Properties prop = new Properties();
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("email-information.properties");
		try {
			prop.load(input);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		//��ȡ����������Ϣ
		String host = prop.getProperty("host");
		String user = prop.getProperty("user");
		String psd = prop.getProperty("password");
		String subject = prop.getProperty("subject");
		String content = prop.getProperty("content");
		String from = prop.getProperty("from");
		String to = form.getEmail();
		String code = form.getCode();
		content = MessageFormat.format(content,code);     //�ü������滻ռλ��
		
		//���ʼ�
		Session session = MailUtils.createSession(host, user, psd);
		Mail mail = new Mail(from,to,subject,content);
		try {
			MailUtils.send(session, mail);
		} catch (Exception e) {
			new RuntimeException(e);
		}
		
		request.setAttribute("msg", "ע��ɹ�!");
		//ע�����  ת����ע��ɹ�ҳ��
		return "f:/jsps/msg.jsp";
	}
	
	//�˺ż���
	public String active(HttpServletRequest request,HttpServletResponse response)
	{
		String code = request.getParameter("code");  //��ȡ������
		
		//��֤�������Ƿ�Ϊ��
		if(code == null || code.trim().isEmpty())
		{
			request.setAttribute("msg", "������Ϊ�գ�");
			return "f:/jsps/msg.jsp";
		}
		
		try {
			userService.active(code);
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());        //����׳��쳣  �����쳣��Ϣ ת��
			return "f:/jsps/msg.jsp";
		}
		
		//���û���쳣  ����ɹ���Ϣ  ת��
		request.setAttribute("msg", "����ɹ�!");
		return "f:/jsps/msg.jsp";
	}
	
	//��¼
	public String login(HttpServletRequest request,HttpServletResponse response)
	{
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);    //��ȡ������
		
		//��֤����
		String username = form.getUsername();
		String password = form.getPassword();
		if(username == null || username.trim().isEmpty())
		{
			request.setAttribute("msg", "�û�������Ϊ��!");
			request.setAttribute("form", form);                //��������� ����
			return "f:/jsps/user/login.jsp";
		}
		if(password == null || password.trim().isEmpty())
		{
			request.setAttribute("msg", "���벻��Ϊ�գ�");
			request.setAttribute("form", form);
			return "f:/jsps/user/login.jsp";
		}
		
		try {
			User user = userService.login(form);                  
			      
			if(request.getSession() != null)
				request.getSession().invalidate(); //��ע��֮ǰ��session
			
			HttpSession session = request.getSession();  
			session.setAttribute("user", user);        //��½�ɹ�  �����û���Ϣ�� session��
			session.setAttribute("cart", new Cart());     //�������ﳵ������session��
			return "r:/index.jsp";                           //ת������ҳ
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/jsps/user/login.jsp";
		}
		
			
	}
	

	//�˳���¼
	public String quit(HttpServletRequest request,HttpServletResponse response)
	{
		request.getSession().invalidate();   //ע����¼
		return "r:/index.jsp";              //ת������ҳ
		
	}
	
	
	
	
	
}
