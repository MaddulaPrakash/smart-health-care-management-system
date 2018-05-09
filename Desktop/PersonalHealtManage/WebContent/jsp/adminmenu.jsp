q<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<%=request.getContextPath()%>/css/styles.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript">
 function preventBack(){window.history.forward();}
  setTimeout("preventBack()", 0);
  window.onunload=function(){null};
</script>
</head>
<body>
<div id='cssmenu'>
<ul>
	<li class='active '><a
		href="<%=request.getContextPath()%>/jsp/admin.jsp"><span>Home</span></a></li>
	<li class='active '><a
		href="<%=request.getContextPath()%>/jsp/manageuserroles.jsp"><span>User Creation</span></a></li>
	
	<li class='has-sub '><a href='#'>Puzzle<span></span></a>
				<ul>
					<li><a
						href='<%=request.getContextPath()%>/jsp/puzzlequestions.jsp'><span>
								Puzzle Questions</span></a></li>
					<li><a
						href='<%=request.getContextPath()%>/jsp/viewpuzzles.jsp'><span>
							View Puzzle </span></a></li>
	</ul></li>	
	
		
		
	<li class='has-sub '><a href='#'>Classification<span></span></a>
				<ul>
					<li><a
						href='<%=request.getContextPath()%>/jsp/classificationage.jsp'><span>
								Classification-AGE</span></a></li>
					<li><a
						href='<%=request.getContextPath()%>/jsp/classificationsex.jsp'><span>
							Classification-Sex </span></a></li>
					<li><a
						href='<%=request.getContextPath()%>/jsp/classificationprofexp.jsp'><span>
							Classification-Prof Exp </span></a></li>
					<li><a
						href='<%=request.getContextPath()%>/jsp/classificationincome.jsp'><span>
							Classification-Income </span></a></li>

	</ul></li>
	
	
	<li class='has-sub '><a href='#'>Classification Disease<span></span></a>
				<ul>
					<li><a
						href='<%=request.getContextPath()%>/jsp/classificationagedisease.jsp'><span>
								Classification-AGE Disease</span></a></li>
					<li><a
						href='<%=request.getContextPath()%>/jsp/classificationsexdisease.jsp'><span>
							Classification-Sex Disease</span></a></li>
					<li><a
						href='<%=request.getContextPath()%>/jsp/classificationprofexpdisease.jsp'><span>
							Classification-Prof Exp Disease</span></a></li>
					<li><a
						href='<%=request.getContextPath()%>/jsp/classificationincomedisease.jsp'><span>
							Classification-Income Disease</span></a></li>

	</ul></li>
	
	<li class='has-sub '><a href='#'>Disease Classify<span></span></a>
				<ul>
					<li><a
						href='<%=request.getContextPath()%>/jsp/viewdatasets.jsp'><span>
								View Datasets</span></a></li>
					<li><a
						href='<%=request.getContextPath()%>/jsp/viewprobability.jsp'><span>
							View Probability</span></a></li>
				
						<li><a
						href='<%=request.getContextPath()%>/dia/doContigency.do'><span>
								Perform Contigency</span></a></li>

					<li><a
						href='<%=request.getContextPath()%>/jsp/viewcontigency.jsp'><span>
								View Contigency</span></a></li>
					<li><a
						href='<%=request.getContextPath()%>/dia/doEnhanceContigency.do'><span>
								Enhance Contigency</span></a></li>

					<li><a
						href='<%=request.getContextPath()%>/jsp/viewenhancecontigency.jsp'><span>
								View Enhance Contigency</span></a></li>

					<li><a
						href='<%=request.getContextPath()%>/dia/doClassifier.do'><span>
								Do Classifier</span></a></li>

					<li><a
						href='<%=request.getContextPath()%>/jsp/viewclassifier.jsp'><span>
								View Classifier</span></a></li>
					<li><a
						href='<%=request.getContextPath()%>/jsp/viewclassifiergraph.jsp'><span>
								Graph</span></a></li>
	
	</ul></li>
		
			
	<li class='has-sub '><a href='<%=request.getContextPath()%>/dia/logout.do'>Logout<span></span></a>
	</li>
	

	
</ul>
</div>
</body>
</html>