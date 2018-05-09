<!DOCTYPE html>
<%@page import="java.util.ArrayList"%>
<%@page import="com.model.AJAXResponse"%>
<%@page import="com.model.Message"%>
<%@page import="com.constants.PersonalHealtConstantsIF"%>
<%@page import="java.util.List"%>
<html>
<head>
<%@page import="com.model.RegisterVerifyMsgs"%>
<%@page import="com.constants.PersonalHealtConstantsIF"%>
<title>Register</title>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0">

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/demo.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/sky-forms.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/errormsg.css">
<!--[if lt IE 9]>
			<link rel="stylesheet" href="css/sky-forms-ie8.css">
		<![endif]-->

<!--[if lt IE 10]>
			<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
			<script src="js/jquery.placeholder.min.js"></script>
		<![endif]-->
<!--[if lt IE 9]>
			<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
			<script src="js/sky-forms-ie8.js"></script>
		<![endif]-->
<script>
			function preventBack(){window.history.forward();}
			setTimeout("preventBack()", 0);
			window.onunload=function(){null};
		</script>
</head>
<body class="bg-cyan">

	<%
		AJAXResponse ajax=(AJAXResponse)request.getAttribute(PersonalHealtConstantsIF.Keys.OBJ);
		if(null==ajax){
			
		}else{	
		List<Message> errors= ajax.getEbErrors();
		if(null==errors){
			
		}else{
			for(Message msg :errors){
	%>
	<div class="isa_error">
		<i class="fa fa-check"></i>
		<%=msg.getErrMessage()%>

	</div>
	<% 				
				}
				
				
			}
			
	}
	%>


	<div class="body body-s">

		<form action="<%=request.getContextPath()%>/dia/savePuzzleQuestion.do"
			method="post" class="sky-form" method="POST" enctype="multipart/form-data">
			<header>Preliminary Questions</header>

			<fieldset>

				<section>
					<label class="select"> <select name="agegroup">
							<option value="" selected disabled>Age group (in years)</option>
							<option value="1">20 to 25 years</option>
							<option value="2">26 to 30 years</option>
							<option value="3">31 to 40 years</option>
							<option value="4">Above 45 years</option>
					</select> <i></i>
					</label>
				</section>

				<section>
					<label class="input"> <i class="icon-append icon-user"></i>
						<input type="text" name="questionDesc" placeholder="Question Desc">
						<b class="tooltip tooltip-bottom-right">Enter Question</b>
					</label>
				</section>

				<section>
					<label class="input"> <i class="icon-append icon-user"></i>
						<input type="file" name="file" placeholder="File">
						<b class="tooltip tooltip-bottom-right">Upload File</b>
					</label>
				</section>

				<section>
					<label class="input"> <i class="icon-append icon-user"></i>
						<input type="text" name="answer1" placeholder="Answer1"> <b
						class="tooltip tooltip-bottom-right">Answer1</b>
					</label>
				</section>

				<section>
					<label class="input"> <i class="icon-append icon-user"></i>
						<input type="text" name="answer2" placeholder="Answer2"> <b
						class="tooltip tooltip-bottom-right">Answer2</b>
					</label>
				</section>

				<section>
					<label class="input"> <i class="icon-append icon-user"></i>
						<input type="text" name="answer3" placeholder="Answer3"> <b
						class="tooltip tooltip-bottom-right">Answer3</b>
					</label>
				</section>

				<section>
					<label class="input"> <i class="icon-append icon-user"></i>
						<input type="text" name="answer4" placeholder="Answer4"> <b
						class="tooltip tooltip-bottom-right">Answer4</b>
					</label>
				</section>

				<section>
					<label class="select"> <select name="correctAnswer">
							<option value="" selected disabled>Correct Answer</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
					</select> <i></i>
					</label>
				</section>

			</fieldset>
			<footer>
				<a href="<%=request.getContextPath()%>/jsp/admin.jsp" class="button">Admin
					Home</a>
				<button type="submit" class="button">Submit</button>
			</footer>
		</form>

	</div>
</body>
</html>