<!DOCTYPE html> 
<html>
	<head>
	<%@page import="com.model.RegisterVerifyMsgs"%>
	<%@page import="com.constants.PersonalHealtConstantsIF"%>
		<title>Register</title>
		
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
		
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/demo.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/sky-forms.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/errormsg.css">
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
	
	<div class="isa_success" hidden="true">
     <i class="fa fa-check"></i>
     	
     </div>
	
	<%
			RegisterVerifyMsgs loginVerify=(RegisterVerifyMsgs) request.getAttribute(PersonalHealtConstantsIF.Keys.OBJ);
		if(null==loginVerify)
		{
			
		}
		else
		{
			String  errMsg=loginVerify.getSeverMessage();
			if(null==errMsg)
			{
				
			}
			else
			{
		%>

<div class="isa_error" name="userNameErr">
<i class="fa fa-times-circle"></i>
<%= errMsg%>
</div>
<%	
	}
}
%>

<%
if(null==loginVerify)
{
	
}
else
{
	String  sucessMsg=loginVerify.getSucessMsg();
	if(null==sucessMsg)
	{
		
	}
	else
	{
%>

<div class="isa_success">
     <i class="fa fa-check"></i>
<%= sucessMsg%>
</div>
<%	
	}
}
%>




		<%
		if(loginVerify!=null)
		{
			String u=loginVerify.getUserNameErrMsg();
			if(null==u)
			{
				
			}
			else
			{
				%> 
				<div class="isa_error" name="userNameErr">
				<i class="fa fa-times-circle"></i>
				<%=u%>
				</div>
		<%
			
			
			}
			}%>
		<%
		if(loginVerify!=null)
		{
			String p=loginVerify.getPasswordErrMsg();
			if(null==p)
			{
				
			}
			else
			{
				%> 
				<div class="isa_error">
				<i class="fa fa-times-circle"></i>
				<%=p%>
				</div>
		<%
			
			
			}
			}%>

			<%
			if(loginVerify!=null)
			{
			String firstNameErr=loginVerify.getFirstNameErrMsg();
			if(null==firstNameErr || firstNameErr.isEmpty())
			{
				
			}
			else
			{
				%> 
				<div class="isa_error">
				<i class="fa fa-times-circle"></i>
				<%=firstNameErr%>
				</div>
			<%
			
			}
			}%>


			<%
			if(loginVerify!=null)
			{
			String lastNameErr=loginVerify.getLastNameErrMsg();
			if(null==lastNameErr || lastNameErr.isEmpty())
			{
				
			}
			else
			{
				%> 
				
				<div class="isa_error">
				<i class="fa fa-times-circle"></i>
				<%=lastNameErr%>
				</div>
			<%
			
			}
			}%>


				<%
				if(loginVerify!=null)
				{
			String emailErr=loginVerify.getEmailErrMsg();
			if(null==emailErr)
			{
				
			}
			else
			{
				%>
				<div class="isa_error">
				<i class="fa fa-times-circle"></i>
				<%=emailErr%>
				</div>
				<%
			
			}
			}%>


				<%
				if(loginVerify!=null)
				{
			String  heartAttackErr=loginVerify.getHeartAttackErrMsg();
			if(null==heartAttackErr)
			{
				
			}
			else
			{
				%> 
				<div class="isa_error">
				<i class="fa fa-times-circle"></i>
				<%=heartAttackErr%>
				</div>
				<%
			
			}
			}%>


				<%
				if(loginVerify!=null)
				{
			String  diabeticErr=loginVerify.getDiabeticErrMsg();
			if(null==diabeticErr)
			{
				
			}
			else
			{
				%> 
				<div class="isa_error">
				<i class="fa fa-times-circle"></i>
				<%=diabeticErr%>
				</div>
				<%
			
			}
			}%>


				<%
				if(loginVerify!=null)
				{
			String  bpErr=loginVerify.getBPErrMsg();
			if(null==bpErr)
			{
				
			}
			else
			{
				%> 
				<div class="isa_error">
				<i class="fa fa-times-circle"></i>
				<%=bpErr%>
				</div>
				<%
			
			}
			}%>


				<%
				if(loginVerify!=null)
				{
			String  obesityErr=loginVerify.getObesityErrMsg();
			if(null==obesityErr)
			{
				
			}
			else
			{
				%> 
				<div class="isa_error">
				<i class="fa fa-times-circle"></i>
				<%=obesityErr%>
				</div>
				<%
			
			}
			}%>

				<%
				if(loginVerify!=null)
				{
			String  sexErr=loginVerify.getSexErrMsg();
			if(null==sexErr)
			{
				
			}
			else
			{
				%> 
				<div class="isa_error">
				<i class="fa fa-times-circle"></i>
				<%=sexErr%>
				</div>
				<%
			
			}
			}%>


				<%
				if(loginVerify!=null)
				{
			String  ageErr=loginVerify.getAgeErrMsg();
			if(null==ageErr)
			{
				
			}
			else
			{
				%> 
				<div class="isa_error">
				<i class="fa fa-times-circle"></i>
				<%=ageErr%>
				</div>
				<%
			
			}
			}%>

				<%
				if(loginVerify!=null)
				{
			String  dobErr=loginVerify.getDobErrMsg();
			if(null==dobErr)
			{
				
			}
			else
			{
				%> <div class="isa_error">
					<i class="fa fa-times-circle"></i>
					<%=dobErr%></div>
				<%
			
			}
			}%>


		<div class="body body-s">
		
			<form action="<%=request.getContextPath()%>/dia/registerUserInitial.do" method="post" class="sky-form">
				<header>Preliminary Questions</header>
					
				<fieldset>
							
					<section>
						<label class="select">
							<select name="answer1">
								<option value="" selected disabled>Age group (in years)</option>
								<option value="1">20 to 25 years</option>
								<option value="2">26 to 30 years</option>
								<option value="3">31 to 40  years</option>
								<option value="4">Above 40 years</option>
							</select>
							<i></i>
						</label>
					</section>
					
					<section>
						<label class="select">
							<select name="answer2">
								<option value="" selected disabled>Income in Lakhs of Rupees per annum</option>
								<option value="1">< 3 Lakhs</option>
								<option value="2">3 to 4 lakhs</option>
								<option value="3">>4 and < 6  lakhs</option>
								<option value="4">>6 lakhs</option>
							</select>
							<i></i>
						</label>
					</section>
					
					<section>
						<label class="select">
							<select name="answer3">
								<option value="" selected disabled>Years of experience in the profession</option>
								<option value="1"><2 years</option>
								<option value="2">2 to 5 years</option>
								<option value="3">>5 and <8 years</option>
								<option value="4"> >8 years</option>
							</select>
							<i></i>
						</label>
					</section>
					
					
					<section>
						<label class="select">
							<select name="sex">
								<option value="" selected disabled>Sex</option>
								<option value="1">Male</option>
								<option value="2">Female</option>
							</select>
							<i></i>
						</label>
					</section>
					
				</fieldset>
				<footer>
					<a href="<%=request.getContextPath()%>/jsp/welcome.jsp" class="button">Home</a>
					<button type="submit" class="button">Submit</button>
				</footer>
			</form>
			
		</div>
	</body>
</html>