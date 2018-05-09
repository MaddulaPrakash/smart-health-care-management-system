<%@page import="com.constants.PersonalHealtConstantsIF"%>
<%@page import="com.model.AJAXResponse"%>
<%@page import="com.model.Information"%>
<%@page import="com.model.AnswerPuzzle"%>
<%@page import="java.util.List"%>
<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Questions</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/errormsg.css">

<script>
	function preventBack() {
		window.history.forward();
	}
	setTimeout("preventBack()", 0);
	window.onunload = function() {
		null
	};
</script>

</head>

<body background="<%=request.getContextPath()%>/img/slider/banner.jpg">
	<a href="<%=request.getContextPath()%>/jsp/patient.jsp"
		class="btn btn-blue btn-effect">User Home</a>
		
		<div class="isa_info">
			<i class="fa fa-check">Provided Answers are in Green and Wrong Answers are in Red</i>
		</div>
	<%
		AJAXResponse ajaxResponse = (AJAXResponse) request
			.getAttribute(PersonalHealtConstantsIF.Keys.OBJ);
		
			Information information=(Information)ajaxResponse.getModel();
			
			if(null==information){
		
			}else{
		
		List<AnswerPuzzle> answerPuzzleList =information.getPuzzleAnswers();
		
		for(AnswerPuzzle answerPuzzle:answerPuzzleList){

		String quesDesc =answerPuzzle.getQuestionDesc();
		
		String providedAnswer=answerPuzzle.getProvidedAnswer();
		
		String correctAnswer =answerPuzzle.getCorrectAnswer();
		
		String providedAnswerDesc=answerPuzzle.getProvidedAnswerDesc();
		
		String correctAnswerDesc=answerPuzzle.getCorrectAnswerDesc();
		
		boolean isStatus = answerPuzzle.isAnswerStatus();
	%>

	<div class="isa_success">
		<i class="fa fa-check"><%=quesDesc%></i>
	</div>

	<div class="isa_success">
		<i class="fa fa-check"><%=correctAnswerDesc%></i>
	</div>
	<% if(!isStatus){ %>
	<div class="isa_error">
		<i class="fa fa-check"><%=providedAnswerDesc%></i>
	</div>
	<%
     	     }
     	     %>


	<%
				
			}
	%>


	<%
		}
		
	%>
</body>
</html>