<!DOCTYPE html> 
<html>
<%@page import="com.constants.PersonalHealtConstantsIF"%>
<%@page import="com.model.AJAXResponse"%>
<%@page import="com.model.AnswerVO"%>
<%@page import="com.model.HealtInfo"%>
<%@page import="com.model.SuggestionObj"%>
<%@page import="java.util.List"%>
<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>

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
<a href="<%=request.getContextPath()%>/jsp/patient.jsp" class="btn btn-blue btn-effect">Home</a>

	<%
		AJAXResponse ajaxResponse = (AJAXResponse) request
		.getAttribute(PersonalHealtConstantsIF.Keys.OBJ);
		HealtInfo diabeticsInfo = (HealtInfo) ajaxResponse
		.getModel();
		
		String type=diabeticsInfo.getType();
		
		List<SuggestionObj> suggestionObj = diabeticsInfo.getSuggestionObjList();
	%>

	<div class="isa_success"><%=type%></div>
	
	<%
			if(type!=null && type.equals(PersonalHealtConstantsIF.Keys.HIGHSTRESS)){
		%>
	<div class="body body-s">
		<form class="sky-form">
			<a href="<%=request.getContextPath()%>/dia/placeAppointment.do"
				class="btn btn-blue btn-effect">Place Appointment</a>
		</form>
		</div>
	<%
		}
	%>
	
	<div class="isa_success">
	<%
		for(SuggestionObj suggestionObj2:suggestionObj){
	%>
		<%=suggestionObj2.getSuggestion()%>
		<br/>
		
	<%	
		}
	
	%>
	</div>


