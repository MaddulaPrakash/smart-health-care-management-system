<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.springframework.web.servlet.ModelAndView"%>
<%@page import="java.util.List"%>
<%@page import="com.model.AJAXResponse,java.util.List,com.model.Message,com.model.RangeModel"%>
<%@page import="com.constants.PersonalHealtConstantsIF"%>
<%@page import="com.model.RangeModel"%>
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
<jsp:include page="/jsp/menu.jsp"></jsp:include>


<%
	AJAXResponse ajax=(AJAXResponse)request.getAttribute(PersonalHealtConstantsIF.MODEL_NAME);

if(null==ajax)
{
	
}
else
{
	RangeModel rangeModelObj=(RangeModel)ajax.getModel();
	
	if(null==rangeModelObj)
	{
%>
<div class="errMessage"> Range Model is Empty</div>
<%
	}
	
	if(rangeModelObj!=null)
	{
		
%>
<table border="1" >
<tr><td>Diabetics Type</td> <td>Low Range</td><td>High Range</td></tr>

<tr><td>LOW</td> <td><%=rangeModelObj.getR1LL()%></td> <td><%=rangeModelObj.getR1HL()%></td></tr>
<tr><td>MEDIUM</td> <td><%=rangeModelObj.getR2LL()%></td> <td><%=rangeModelObj.getR2HL()%></td></tr>
<tr><td>HIGH</td> <td><%=rangeModelObj.getR3LL()%></td> <td><%=rangeModelObj.getR3HL()%></td></tr>

<%
	


	}
	
	
}


%>

</table>
</body>

<%@page import="com.model.RangeModel"%></html>