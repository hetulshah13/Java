package servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import util.DBHelper;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CommObj commObj = new CommObj();
		try {
			boolean isLoginFound = false;
			Cookie[] cookies = request.getCookies();
			if(cookies != null && cookies.length > 0) {
				for(Cookie c : cookies) {
					if(c.getName().equalsIgnoreCase("loggedUsername")) {
						String username = c.getValue();
						String query = "SELECT * FROM demousers WHERE username = ?";
						Map<String, Object> params = new LinkedHashMap<>();
						params.put("username", username);
						doLoginProcess(commObj, query, params, null, username, request, response);
						c.setMaxAge(30*60); // 30 Minutes
						response.addCookie(c);
						isLoginFound = true;
						break;
					}
				}
			}
			if(!isLoginFound) {
				HttpSession session = request.getSession();
				CommObj loggedUser = (CommObj) session.getAttribute("loggedUser");
				if(loggedUser != null && loggedUser.getUsername() != null) {
					String username = loggedUser.getUsername();
					String query = "SELECT * FROM demousers WHERE username = ?";
					Map<String, Object> params = new LinkedHashMap<>();
					params.put("username", username);
					doLoginProcess(commObj, query, params, null, username, request, response);
					isLoginFound = (commObj.getUsername() != null);
					
				}
			}
			
			if(!isLoginFound) {
				response.sendRedirect("login.jsp");
			} else {
				redirectProcess(commObj, response);
			}
		} catch(SQLException ex) {
			commObj.setErrorMessage(ex.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CommObj commObj = new CommObj();
		try {
			String submitAction = request.getParameter("submit");
			if("LOGIN".equals(submitAction)) {
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				String remember = request.getParameter("remember");
				String query = "SELECT * FROM demousers WHERE username = ? AND password = ?";
				Map<String, Object> params = new LinkedHashMap<>();
				params.put("username", username);
				params.put("password", password);
				
				doLoginProcess(commObj, query, params, remember, username, request, response);
				
				redirectProcess(commObj, response);
			}
			
		} catch(SQLException ex) {
			commObj.setErrorMessage(ex.getMessage());
		}
	}

	private void redirectProcess(CommObj commObj, HttpServletResponse response) throws ServletException, IOException {
		String role = commObj.getRole();
		
		String redirectURL = "login.jsp";
		if("ADMIN".equalsIgnoreCase(role)) {
			redirectURL = "admin/index.jsp";  
		} else if("MEMBER".equalsIgnoreCase(role)) {
			redirectURL = "member/index.jsp";  
		} else if("MANAGER".equalsIgnoreCase(role)) {
			redirectURL = "manager/index.jsp";  
		}   
		response.sendRedirect(redirectURL);
	}
	
	private void doLoginProcess(CommObj commObj, String query, Map<String, Object> params, String remember, String username, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ResultSet result = DBHelper.executeQuery(query, params);
		if(result == null) {
			String errMsg = DBHelper.getErrorMessage();
			DBHelper.clearErrorMessage();
			commObj.setErrorMessage(errMsg);
		} else if(result.next()) {
			commObj.setUsername(result.getString("username"));
			commObj.setRole(result.getString("role"));
			if("CHECKED".equalsIgnoreCase(remember)) {
				Cookie cookie = new Cookie("loggedUsername", username);
				cookie.setMaxAge(30*60); // 30 Minutes
				response.addCookie(cookie);
			}
		} else {
			commObj.setErrorMessage("Invalid user credentials");
		}
		HttpSession session = request.getSession();
		session.setAttribute("loggedUser", commObj);
	}
}
