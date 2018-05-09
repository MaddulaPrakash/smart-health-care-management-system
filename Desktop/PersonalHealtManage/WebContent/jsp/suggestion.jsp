<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.springframework.web.servlet.ModelAndView"%>
<%@page import="java.util.List"%>
<%@page import="com.model.AJAXResponse,java.util.List,com.model.Message"%>
<%@page import="com.constants.PersonalHealtConstantsIF"%>
<%@page import="com.model.HealtInfo"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
 function preventBack(){window.history.forward();}
  setTimeout("preventBack()", 0);
  window.onunload=function(){null};
</script>
</head>
<body background="<%=request.getContextPath()%>/images/background.jpg">
<jsp:include page="/jsp/customer.jsp"></jsp:include>
<jsp:include page="/jsp/patientcormenu.jsp"></jsp:include>


<%
	AJAXResponse ajax=(AJAXResponse)request.getAttribute(PersonalHealtConstantsIF.MODEL_NAME);

if(null==ajax)
{
	
}
else
{	
	List<Message> ebErrors=ajax.getEbErrors();
	
	if(null==ebErrors)
	{
		
	
		
		
		
		
	}
	else
	{
	Message msg=ebErrors.get(0);
%>
<div class="errMessage"><%=msg.getErrMessage()%></div>
<%
	}

	HealtInfo diabeticsInfo=(HealtInfo)ajax.getModel();
	
	if(null==diabeticsInfo)
	{
%>

<div class="errMsg">Diabetics Information was Empty</div>

<%
		
	}
	else
	{
		List<SuggestionVO> suggestionVOList=diabeticsInfo.getSugVOList();
		
		for(SuggestionVO suggestionVO:suggestionVOList)
		{
		
%>

<div class="sugInfo" >
<%=suggestionVO.getSug()%>
</div>

<%			
			
		}
		
		int totalRating=diabeticsInfo.getTotalRating();
		String type=diabeticsInfo.getType();
	%>
	<table>
	<tr><td>Total Rating:</td><td><%=totalRating%></td></tr>
	<tr><td>Type:</td><td><%=type%></td></tr>
	</table>
		


<%

	boolean isHighDiabetics=diabeticsInfo.isHigh();
	if(isHighDiabetics==true)
	{
%>

<div class="appointReq">
<form action="<%=request.getContextPath()%>/dia/appReq.do" >

<table>
<tr><td>Do you need an Appointment Request?</td></tr>
<tr> <td><input type="submit" value="Request Appointment" /></td></tr>
</table>



</form>

</div>
	
	
<%	
	}

	}
	
}


%>
</body>

<%@page import="com.model.SuggestionVO"%></html>