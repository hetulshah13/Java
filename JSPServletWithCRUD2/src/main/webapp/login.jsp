<%@page import="dto.CommObj"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp" %>
<!DOCTYPE html>
<jsp:useBean id="loggedUser" class="dto.CommObj" scope="session" />
<html xmlns="http://www.w3.org/1999/xhtml">
<% request.setAttribute("title", "Login"); %>
<jsp:include page="components/common/header.jsp" />
<body>
	<div id="wrapper">
		<jsp:include page="components/common/sidebar-nav.jsp" />
		<div id="page-wrapper">
			<div id="page-inner">
				<div class="row">
					<div class="col-md-12">
						<h2>Login page</h2>
					   	<form action="login" name="loginForm" method="post">
					    
							<div class="row">
								<div class="col-lg-4 col-md-4">
									<div class="form-group">
										<label>User name</label> <input class="form-control" type="text" placeholder="User name" name="username" />
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-4 col-md-4">
									<div class="form-group">
										<label>Password</label> <input class="form-control" type="password" placeholder="Password" name="password" />
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-4 col-md-4">
									<label>Remember me</label>
									<div class="form-group">
										<input class="radio radio-inline" 
													type="checkbox"
													value="checked" 
													name="remember" /> 
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-2 col-md-2">
									<button type="submit" name="submit" value="LOGIN" class="btn btn-danger btn-lg btn-block">Login</button>
								</div>
								<div class="col-lg-2 col-md-2">
									<a href="index.jsp" class="btn btn-default btn-lg btn-block">Cancel</a>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-4 col-md-4">
									<a href="forget-password.jsp" class="btn btn-default btn-lg btn-block">Forget password?</a>
								</div>
							</div>
						</form>
						<% if(loggedUser.getErrorMessage() != null) { %>
						<div class="alert alert-danger" role="alert">
							<jsp:getProperty property="errorMessage" name="loggedUser"/>
							<button type="button" class="close" data-dismiss="alert" aria-label="Close">
							    <span aria-hidden="true">&times;</span>
							  </button>
						</div>
						<% } %>
						
						<% if(loggedUser.getWarnMessage() != null) { %>
						<div class="alert alert-warning" role="alert">
							<jsp:getProperty property="warnMessage" name="loggedUser"/>
							<button type="button" class="close" data-dismiss="alert" aria-label="Close">
							    <span aria-hidden="true">&times;</span>
							  </button>
						</div>
						<% } %>
					    
					</div>
				</div>
				<!-- /. ROW  -->
			</div>
			<!-- /. PAGE INNER  -->
		</div>
		<!-- /. PAGE WRAPPER  -->
	</div>
	<!-- /. WRAPPER  -->
	<jsp:include page="components/common/footer.jsp" />
	<jsp:include page="components/common/javascripts.html" />
</body>
</html>