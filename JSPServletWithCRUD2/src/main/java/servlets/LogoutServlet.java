package servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.CommObj;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		session.setMaxInactiveInterval(0);
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0) {
			for(Cookie c : cookies) {
				if(c.getName().equalsIgnoreCase("loggedUsername")) {
					c.setMaxAge(0); // 0 Minutes
					response.addCookie(c);
					break;
				}
			}
		}
		CommObj commObj = new CommObj();
		commObj.setWarnMessage("You are successfully logout");
		session = request.getSession(true);
		session.setAttribute("loggedUser", commObj);
		response.sendRedirect("login.jsp");
	}

}
