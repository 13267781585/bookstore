package cn.itcast.bookstore.user.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.itcast.bookstore.user.domin.User;

@WebFilter(
		urlPatterns = { 
				"/jsps/cart/*", 
				"/jsps/order/*",
				"/CartServlet",
				"/OrderServlet"
		}, 
		servletNames = { 
				"CartServlet", 
				"OrderServlet"
		})
public class UserFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpSession session = httpRequest.getSession();
		User user = (User) session.getAttribute("user");
		
		//�ж��û��Ƿ��¼
		if(user == null)
		{
			request.setAttribute("msg", "����û�е�¼�����ܲ鿴��ҳ�棡");
			httpRequest.getRequestDispatcher("/jsps/user/login.jsp").forward(httpRequest, response);
		}
			else
			chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
