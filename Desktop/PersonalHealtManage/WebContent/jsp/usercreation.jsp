<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.constants.PersonalHealtConstantsIF"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Energy Panel</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/extjs41/resources/css/ext-all.css" />
<script type="text/javascript" >
var contextPath='<%=request.getContextPath()%>';
var updatable=false;
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/extjs41/ext-all-debug.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/usercreation.js"></script>
<script type="text/javascript">
 function preventBack(){window.history.forward();}
  setTimeout("preventBack()", 0);
  window.onunload=function(){null};
</script>
</head>


<body class="bd">
<jsp:include page="/jsp/customer.jsp"></jsp:include>
<jsp:include page="/jsp/adminmenu.jsp"></jsp:include>

<div id="content">
</div>
<div id="confirmationMessage" ></div>

<div id="rolediv"></div>

</body>
</html>