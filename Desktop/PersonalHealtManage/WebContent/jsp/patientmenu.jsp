<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<%=request.getContextPath()%>/css/styles.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript">
	function preventBack() {
		window.history.forward();
	}
	setTimeout("preventBack()", 0);
	window.onunload = function() {
		null
	};
</script>
</head>
<body>
	<div id='cssmenu'>
		<ul>
			<li class='active '><a
				href="<%=request.getContextPath()%>/jsp/patient.jsp"><span><font
						size="4"><i>Home</i></font></span></a></li>
			<li class='active '><a
				href="<%=request.getContextPath()%>/dia/viewscreeningtestuser.do"><span><font
						size="4"><i>Stress Test</i></font></span></a></li>

			<li class='active '><a
				href="<%=request.getContextPath()%>/dia/viewdiseasetestuser.do"><span><font
						size="4"><i>Disease Test</i></font></span></a></li>

			<li class='has-sub '><a href='#'><span>View
						Appointments</span></a>
				<ul>

					<li><a href="<%=request.getContextPath()%>/jsp/dashboard.jsp"><span><font
								size="4"><i>Stress</i></font></span></a></li>

					<li><a
						href="<%=request.getContextPath()%>/jsp/dashboarddisease.jsp"><span><font
								size="4"><i>Disease</i></font></span></a></li>
				</ul>
			</li>

			
			<li class='has-sub '><a href='#'><span>Track History</span></a>
				<ul>

			<li><a
				href="<%=request.getContextPath()%>/jsp/trackhistory.jsp"><span><font
						size="4"><i>Stress</i></font></span></a></li>

			<li><a
				href="<%=request.getContextPath()%>/jsp/trackhistorydisease.jsp"><span><font
						size="4"><i>Disease</i></font></span></a></li>
						
				</ul>
			</li>

			<li class='active '><a
				href="<%=request.getContextPath()%>/dia/mypuzzle.do"><span><font
						size="4"><i>My Puzzle</i></font></span></a></li>

			<li class='active '><a
				href="<%=request.getContextPath()%>/jsp/navbayes.jsp"> <span><font
						size="4"><i>Naive Bayes</i></font></span></li>

			<li class='has-sub '><a
				href='<%=request.getContextPath()%>/dia/logout.do'><span><font
						size="4"><i>Logout</i></font></span></a></li>



		</ul>
	</div>
</body>
</html>