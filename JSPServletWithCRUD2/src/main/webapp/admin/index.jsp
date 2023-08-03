<%@page import="dto.CommObj"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp" %>
<%
	CommObj loggedUser = (CommObj) session.getAttribute("loggedUser");
	if(loggedUser == null || loggedUser.getErrorMessage() != null) {
		response.sendRedirect("../login");
	}
%>	
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<% pageContext.setAttribute("title", "Index"); %>
<jsp:include page="../components/common/header.jsp" />
<body>
	<div id="wrapper">
		<jsp:include page="../components/common/topbar-nav.jsp" />
		<jsp:include page="../components/common/sidebar-nav.jsp" />
		<div id="page-wrapper">
			<div id="page-inner">
				<div class="row">
					<div class="col-md-12">
						<h2>Welcome Admin</h2>
			

			
					</div>
				</div>
				<!-- /. ROW  -->
			</div>
			<!-- /. PAGE INNER  -->
		</div>
		<!-- /. PAGE WRAPPER  -->
	</div>
	<!-- /. WRAPPER  -->
	<jsp:include page="../components/common/footer.jsp" />
	<jsp:include page="../components/common/javascripts.html" />
</body>
</html>