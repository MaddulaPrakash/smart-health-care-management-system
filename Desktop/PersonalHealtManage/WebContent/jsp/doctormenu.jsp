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
				href="<%=request.getContextPath()%>/jsp/doctor.jsp"><span><font
						size="4"><i>Home</i></font></span></a></li>

			<li class='has-sub '><a href='#'><span>Stress
						Information</span></a>
				<ul>
					<li><a
						href="<%=request.getContextPath()%>/jsp/createscreeningtest.jsp"><span><font
								size="4"><i>Screening Test</i></font></span></a></li>

					<li><a
						href="<%=request.getContextPath()%>/jsp/viewappointments.jsp"><span><font
								size="4"><i>View Appointments</i></font></span></a></li>

					<li><a
						href="<%=request.getContextPath()%>/jsp/viewspecficscreeningtest.jsp"><span><font
								size="4"><i>View Test</i></font></span></a></li>

					<li><a
						href="<%=request.getContextPath()%>/jsp/viewpateinthistorydoctor.jsp"><span><font
								size="4"><i>View Patient History</i></font></span></a></li>
				</ul></li>


			<li class='has-sub '><a href='#'><span>Disease
						Information</span></a>
				<ul>

					<li><a
						href="<%=request.getContextPath()%>/jsp/viewappointmentsdisease.jsp"><span><font
								size="4"><i>View Appointments</i></font></span></a></li>

					<li><a
						href="<%=request.getContextPath()%>/jsp/createscreeningtestdisease.jsp"><span><font
								size="4"><i>Disease Test</i></font></span></a></li>

					<li><a
						href="<%=request.getContextPath()%>/jsp/viewspecficscreeningtestdisease.jsp"><span><font
								size="4"><i>View Test</i></font></span></a></li>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/viewpateinthistorydoctordisease.jsp"><span><font
								size="4"><i>View Patient History</i></font></span></a></li>

				</ul></li>

			<li class='has-sub '><a href='#'><span>Classifier
						Information Naive</span></a>
				<ul>

					<li><a
						href="<%=request.getContextPath()%>/jsp/viewclassifierdoctor.jsp"><span><font
								size="4"><i>View Classifier</i></font></span></a></li>

					<li><a
						href="<%=request.getContextPath()%>/jsp/viewclassifierdoctorgraph.jsp"><span><font
								size="4"><i>Classifier Graph</i></font></span></a></li>

				</ul></li>
			
			<li class='has-sub '><a
				href="<%=request.getContextPath()%>/dia/logout.do"><font
					size="4"><i>Logout</i></font><span></span></a></li>



		</ul>
	</div>
</body>
</html>