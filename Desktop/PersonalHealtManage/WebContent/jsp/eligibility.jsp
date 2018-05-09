<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Screening Panel</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/extjs41/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/customer.css" />
<script type="text/javascript" >
var contextPath='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/extjs41/ext-all-debug.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/eligibility.js"></script>
<script type="text/javascript">
 function preventBack(){window.history.forward();}
  setTimeout("preventBack()", 0);
  window.onunload=function(){null};
</script>
</head>


<body class="cordinatorBody">
<jsp:include page="/jsp/patientcormenu.jsp"></jsp:include>
<div id="confirmationMessage" hidden="true"></div>

<div id="emptyMessage">
&nbsp
</div>

<div id="appContainer"></div>

</body>
</html>