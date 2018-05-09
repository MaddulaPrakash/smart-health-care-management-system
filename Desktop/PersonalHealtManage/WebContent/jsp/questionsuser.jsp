<%@page import="com.constants.PersonalHealtConstantsIF"%>
<%@page import="com.model.AJAXResponse"%>
<%@page import="com.model.AnswerVO"%>
<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html> 
<html>
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
<a href="<%=request.getContextPath()%>/jsp/patient.jsp" class="btn btn-blue btn-effect">User Home</a>
<div class="isa_success">

	<font color="red"></font>
	<form action="<%=request.getContextPath()%>/dia/onlineTestUser.do" method="post">

		<%
			AJAXResponse ajaxResponse = (AJAXResponse) request
						.getAttribute(PersonalHealtConstantsIF.Keys.OBJ);
				AnswerVO questionTableValue = (AnswerVO)ajaxResponse.getModel();
		%>

		<table>
			<tr>
				<td><input type="hidden" name="patName"
					value="<%=questionTableValue.getPatName()%>" /></td>
			</tr>

			<tr>

					<div style="border-color: black; background-color: aqua;">
						<table>
							<tr>
								<td><%=questionTableValue.getQuestionVO().getQuestDesc()%>
									<input type="hidden" name="questionVO.questDesc"
									value="<%=questionTableValue.getQuestionVO().getQuestDesc()%>" />
								</td>
							</tr>
							<tr>
								<td><input type="hidden" name="questionVO.quesId"
									value="<%=questionTableValue.getQuestionVO().getQuesId()%>" />
									<input type="hidden" name="questionVO.pageNumber"
									value="<%=questionTableValue.getQuestionVO().getPageNumber()%>" />
									<input type="hidden" name="pageNumberGlobal"
									value="<%=questionTableValue.getQuestionVO().getPageNumber()%>" />

									<% String ans1 = questionTableValue.getQuestionVO().getAns1();
											if(ans1!=null && !ans1.isEmpty()){
										%> <input type="hidden" name="questionVO.ans1"
									value="<%=ans1%>" />
							<tr>
								<td><%=ans1%></td>
								<td><input type="radio" name="questionVO.selectedAnswer"
									value="1" /></td>
							</tr>
							<%
											}
										%>

							<% String ans2 = questionTableValue.getQuestionVO().getAns1();
											if(ans2!=null && !ans2.isEmpty()){
										%>
							<input type="hidden" name="questionVO.ans2" value="<%=ans2%>" />

							<tr>
								<td><%=questionTableValue.getQuestionVO().getAns2()%></td>
								<td><input type="radio" name="questionVO.selectedAnswer"
									value="2" /></td>
							</tr>
							<%
											}
										%>


							<% String ans3 = questionTableValue.getQuestionVO().getAns3();
											if(ans3!=null && !ans3.isEmpty()){
										%>
							<input type="hidden" name="questionVO.ans3" value="<%=ans3%>" />
							<tr>
								<td><%=questionTableValue.getQuestionVO().getAns3()%></td>
								<td><input type="radio" name="questionVO.selectedAnswer"
									value="3" /></td>
							</tr>
							<%
											}
										%>

							<% String ans4 = questionTableValue.getQuestionVO().getAns4();
											if(ans4!=null && !ans4.isEmpty()){
										%>
							<input type="hidden" name="questionVO.ans4" value="<%=ans4%>" />
							<tr>
								<td><%=questionTableValue.getQuestionVO().getAns4()%></td>
								<td><input type="radio" name="questionVO.selectedAnswer"
									value="4" /></td>
							</tr>
							<%
											}
										%>

							<tr>
								<input type="hidden" name="questionVO.rating1"
									value="<%=questionTableValue.getQuestionVO().getRating1()%>" />
								<input type="hidden" name="questionVO.rating2"
									value="<%=questionTableValue.getQuestionVO().getRating2()%>" />
								<input type="hidden" name="questionVO.rating3"
									value="<%=questionTableValue.getQuestionVO().getRating3()%>" />
								<input type="hidden" name="questionVO.rating4"
									value="<%=questionTableValue.getQuestionVO().getRating4()%>" />
								<input type="hidden" name="questionVO.testName"
									value="<%=questionTableValue.getQuestionVO().getTestName()%>" />
								<input type="hidden" name="questionVO.sug1"
									value="<%=questionTableValue.getQuestionVO().getSug1()%>" />
								<input type="hidden" name="questionVO.sug2"
									value="<%=questionTableValue.getQuestionVO().getSug2()%>" />
								<input type="hidden" name="questionVO.sug3"
									value="<%=questionTableValue.getQuestionVO().getSug3()%>" />
								<input type="hidden" name="questionVO.sug4"
									value="<%=questionTableValue.getQuestionVO().getSug4()%>" />
								</td>

							</tr>
						</table>
					</div>
			</tr>
		</table>
</div>
		<table>
			<tr>
				<td><input type="submit" name="actionCode" class="btn btn-blue btn-effect" value="NEXT" /></td>
				<%
					int pageNumberTemp =questionTableValue.getQuestionVO().getPageNumber();
				
					if(pageNumberTemp>1){
				%>
				<td><input type="submit" class="btn btn-blue btn-effect" name="actionCode" value="BACK" /></td>
				<%
					}
				%>
			</tr>
		</table>
	</form>
</div>
</body>
</html>