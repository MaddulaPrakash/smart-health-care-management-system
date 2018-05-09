<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.constants.PersonalHealtConstantsIF"%>
<%@page import="com.model.AJAXResponse"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<%=request.getContextPath()%>/css/errormsg.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript" >
var contextPath='<%=request.getContextPath()%>';
var updatable=false;
</script>

<script type="text/javascript">
 function preventBack(){window.history.forward();}
  setTimeout("preventBack()", 0);
  window.onunload=function(){null};
</script>

</head>
<body> 
<jsp:include page="/jsp/adminmenu.jsp"></jsp:include>

<%
	AJAXResponse ajax=(AJAXResponse)request.getAttribute(PersonalHealtConstantsIF.Keys.OBJ);
							if(null==ajax){
								
							}else{	
									String msg=ajax.getMessage();
									if(null==msg){
									}
									else{
%>
							<div class="isa_success">	<%=msg%></div>
								<%
										}
									}
								%>


</body>
</html>