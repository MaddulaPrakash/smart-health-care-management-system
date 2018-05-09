<%@page import="com.constants.PersonalHealtConstantsIF"%>
<%@page import="com.model.AJAXResponse"%>
<%@page import="com.model.PuzzleVO"%>

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
			function preventBack(){window.history.forward();}
			setTimeout("preventBack()", 0);
			window.onunload=function(){null};
		</script>

</head>

<body background="<%=request.getContextPath()%>/img/slider/banner.jpg">
	<a href="<%=request.getContextPath()%>/jsp/patient.jsp"
		class="btn btn-blue btn-effect">User Home</a>




	<%
		AJAXResponse ajaxResponse = (AJAXResponse) request
					.getAttribute(PersonalHealtConstantsIF.Keys.OBJ);
			List<PuzzleVO> puzzles = (List<PuzzleVO>)ajaxResponse.getModel();
			
		if(puzzles!=null && !puzzles.isEmpty()){
			
			String pathForImage= PersonalHealtConstantsIF.Keys.PATH_FOR_IMAGES+"";
	%>
	<form action="<%=request.getContextPath()%>/dia/processPuzzle.do">
	
	<%			
		for(PuzzleVO puzzleVO:puzzles){
	%>
		<div class="isa_success">
		<table>
			<tr>
				<td><%=puzzleVO.getQuestionDesc()%></td>
			</tr>
			
			<tr>
			<td>
				<img src="<%=request.getContextPath()%>/images/file/<%=puzzleVO.getName()%>"></img>
			</td>
			</tr>

			<tr>
			<td>
			<select name="select_<%=puzzleVO.getPuzzleQId()%>">
			
				<option value="1"><%=puzzleVO.getAnswer1()%></option>
  				<option value="2"><%=puzzleVO.getAnswer2()%></option>
  				<% if(puzzleVO.getAnswer3()!=null && !puzzleVO.getAnswer3().isEmpty()){
				
				%>
  				<option value="3"><%=puzzleVO.getAnswer3()%></option>
  				<%
				}
				%>
				<% if(puzzleVO.getAnswer4()!=null && !puzzleVO.getAnswer4().isEmpty()){
				
				%>
  				<option value="4"><%=puzzleVO.getAnswer4()%></option>
  				<%
				}
				%>
  			</select>
  			</td>
			</tr>
			
		</table>
		</div>

	<%		
					
				}
				
		}
	%>
	
	<input type="submit" value="Submit Puzzle">
</form>
</body>
</html>