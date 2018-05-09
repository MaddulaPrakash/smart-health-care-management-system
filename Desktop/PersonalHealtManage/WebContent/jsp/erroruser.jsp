<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.constants.PersonalHealtConstantsIF"%>
<%@page import="java.util.List"%>
<%@page import="com.model.AJAXResponse,java.util.List,com.model.Message"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
var contextPath='<%=request.getContextPath()%>
	';
	var updatable = false;
</script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/errormsg.css">
<script type="text/javascript">
	function preventBack() {
		window.history.forward();
	}
	setTimeout("preventBack()", 0);
	window.onunload = function() {
		null
	};
</script>

</head>
<body background="<%=request.getContextPath()%>/img/1920x1080/01.jpg">
	<jsp:include page="/jsp/patientmenu.jsp"></jsp:include>

	<%
		AJAXResponse ajaxResponse = (AJAXResponse) request
			.getAttribute(PersonalHealtConstantsIF.Keys.OBJ);

			List<Message> list = ajaxResponse.getEbErrors();

			if (list != null && !list.isEmpty()) {

		for (Message msg : list) {
	%>
	<div class="isa_error">
		<i class="fa fa-check"><%=msg.getErrMessage()%></i>
	</div>
	<%
		}
		}
	%>
</body>
</html>