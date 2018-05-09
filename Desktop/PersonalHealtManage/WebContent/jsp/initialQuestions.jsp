<%@page import="com.constants.PersonalHealtConstantsIF"%>
<%@page import="com.model.AJAXResponse"%>
<%@page import="com.model.AnswerVO"%>
<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html> 
<html>
<head>
		<title>Questions</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/errormsg.css">
	
		<script>
			function preventBack(){window.history.forward();}
			setTimeout("preventBack()", 0);
			window.onunload=function(){null};
		</script>
	
</head>

<body background="<%=request.getContextPath()%>/img/slider/banner.jpg">
<a href="<%=request.getContextPath()%>/jsp/welcome.jsp" class="btn btn-blue btn-effect">Home</a>
<div class="isa_success">

	<font color="red"></font>
	<form action="<%=request.getContextPath()%>/dia/initialAnswers.do" method="post">

		<table>
			<tr>
			<td>Age group (in years): </td>
			<td>
				<input type="radio" name="agegroup" value="1" checked> 20 to 25 <br>
  				<input type="radio" name="agegroup" value="2"> 26 to 30<br>
  				<input type="radio" name="agegroup" value="3"> 31 to 40<br>
  				<input type="radio" name="agegroup" value="3"> Above 45<br>
			
			</td>
			</tr>
			
			<tr>
			<td>Gender</td>
			<td>
				<input type="radio" name="sex" value="1" checked>Male<br>
  				<input type="radio" name="sex" value="2">Female<br>
  			</td>
			</tr>
			
			<tr>
			<td>Income in Lakhs of Rupees per annum</td>
			<td>
				<input type="radio" name="income" value="1" checked><3 lakhs<br>
  				<input type="radio" name="income" value="2">3 to 4 lakhs<br>
  				<input type="radio" name="income" value="3">>4 lakhs and < 6 lakhs<br>
  				<input type="radio" name="income" value="4">> 6 lakhs<br>
  			</td>
			</tr>
			
			
			<tr>
			<td>Years of experience in the profession</td>
			<td>
				<input type="radio" name="income" value="1" checked><2 years<br>
  				<input type="radio" name="income" value="2">2 to 5 years<br>
  				<input type="radio" name="income" value="3">>5 and <8 years<br>
  				<input type="radio" name="income" value="4"> >8 years<br>
  			</td>
			</tr>
			
			
		</table>

		
	</form>
</div>
</body>
</html>