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
				href="<%=request.getContextPath()%>/jsp/patientcor.jsp"><span><font
						size="4"><i>Home</i></font></span></a></li>

			<li class='has-sub '><a href='#'><span>Appointments</span></a>
				<ul>

					<li><a
						href="<%=request.getContextPath()%>/jsp/viewappointmentspc.jsp"><span><font
								size="4"><i>Stress</i></font></span></a></li>

					<li><a
						href="<%=request.getContextPath()%>/jsp/viewappointmentspcdisease.jsp"><span><font
								size="4"><i>Disease</i></font></span></a></li>

				</ul></li>

			<li class='has-sub '><a href='#'><span>Track History</span></a>
				<ul>

					<li><a
						href="<%=request.getContextPath()%>/jsp/viewpateinthistory.jsp"><span><font
								size="4"><i>View History</i></font></span></a></li>

					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/viewpateinthistorydisease.jsp"><span><font
								size="4"><i>View Disease History</i></font></span></a></li>

				</ul></li>

			<li class='has-sub '><a
				href='<%=request.getContextPath()%>/dia/logout.do'><span><font
						size="4"><i>Logout</i></font></span></a></li>

		</ul>
	</div>
</body>
</html>