<html>
<%@page import="com.constants.PersonalHealtConstantsIF"%>
<%@page import="com.model.AJAXResponse"%>
<%@page import="com.model.AnswerVO"%>
<%@page import="com.model.HealtInfo"%>
<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/errmsg.css">
		
</head>
<body class="bg-cyan">

	<%
		AJAXResponse ajaxResponse = (AJAXResponse) request
		.getAttribute(PersonalHealtConstantsIF.Keys.OBJ);
		HealtInfo diabeticsInfo = (HealtInfo) ajaxResponse
		.getModel();
		
		String type=diabeticsInfo.getType();
	%>

	<div id="finalCordination">{type}</div>

	<div class="body body-s">

		<form class="sky-form">

			<a href="<%=request.getContextPath()%>/jsp/registerview.jsp"
				class="button">Register</a>
		</form>
	</div>
</body>