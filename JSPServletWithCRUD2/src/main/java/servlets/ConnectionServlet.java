package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import util.DBHelper;

@WebServlet("/ConnectionServlet")
public class ConnectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void init() throws ServletException {
		DBHelper.init(getServletContext());
		DBHelper.connect();
	}

	public void destroy() {
		DBHelper.disconnect();
	}

}
