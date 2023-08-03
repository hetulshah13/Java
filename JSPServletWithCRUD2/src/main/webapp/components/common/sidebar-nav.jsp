<%@page import="dto.CommObj"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<% 
CommObj loggedUser = (CommObj) session.getAttribute("loggedUser");
loggedUser = loggedUser == null ? new CommObj() : loggedUser;
String role = loggedUser.getRole(); 
%>
<nav class="navbar-default navbar-side" role="navigation">
	<div class="sidebar-collapse">
		<ul class="nav" id="main-menu">
			<li><a href="index.jsp"><i class="fa fa-desktop "></i>Dashboard</a></li>
			
			<% if("ADMIN".equalsIgnoreCase(role)) { %>
			<li><a href="/JSPServletWithCRUD2/admin/user-list.jsp"><i class="fa fa-table "></i>Users list</a></li>
			<li><a href="/JSPServletWithCRUD2/admin/view-profile.jsp"><i class="fa fa-user "></i>View Profile</a></li>
			<% } else if("MEMBER".equalsIgnoreCase(role)) { %>
			<li><a href="/JSPServletWithCRUD2/member/view-profile.jsp"><i class="fa fa-user "></i>View Profile</a></li>
			<% } else if("MANAGER".equalsIgnoreCase(role)) { %>
			<li><a href="/JSPServletWithCRUD2/manager/add-user.jsp"><i class="fa fa-user "></i>Add User</a></li>
			<% } %>
			
			<% if(role != null && !role.isEmpty()) { %>
			<li><a href="change-password.jsp"><i class="fa fa-key "></i>Change Password</a></li>
			<li><a href="logout"><i class="fa fa-quit "></i>Logout</a></li>
			<% } else { %>
			<li><a href="login"><i class="fa fa-quit "></i>Login</a></li>
			<% } %>
		</ul>
	</div>
</nav>
<!-- /. NAV SIDE  -->
