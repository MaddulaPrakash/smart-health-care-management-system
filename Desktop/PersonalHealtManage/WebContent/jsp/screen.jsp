<%@page import="com.constants.PersonalHealtConstantsIF"%>
<%@page import="com.model.AJAXResponse"%>
<%@page import="com.model.AnswerVO"%>
<html>

<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<body background="<%=request.getContextPath()%>/jsp/images/spirit1.jpg ">

	<font color="red"><html:errors /></font>
	<html:form action="<%=request.getContextPath()%>/dia/onlineTest.do">

		<%
			AJAXResponse ajaxResponse = (AJAXResponse) request
						.getAttribute(PersonalHealtConstantsIF.Keys.OBJ);

				AnswerVO questionTableValue = (AnswerVO)ajaxResponse.getModel();
		%>

		<table>
			<tr>
				<td><input type="hidden" name="patName"
					value="${questionTableValue.patName}" /></td>
			</tr>

			<tr>
				<c:forEach items="${questionTableValue.questionVO}"
					var="questionTableVal">
					<fieldset>
						<div style="border-color: black; background-color: aqua;">
							<table>
								<tr>
									<td>${questionTableVal.questDesc}</td>
								</tr>
								<tr>
									<td><input type="hidden" name="quesId"
										value="${questionTableVal.quesId}" /> <input type="hidden"
										name="pageNumber" value="${questionTableVal.pageNumber}" /> <input
										type="hidden" name="pageNumberGlobal"
										value="${questionTableVal.pageNumber}" /> <input
										type="hidden" name="ans1" value="${questionTableVal.ans1}" />
										<input type="hidden" name="ans2"
										value="${questionTableVal.ans2}" /> <input type="hidden"
										name="ans3" value="${questionTableVal.ans3}" /> <input
										type="hidden" name="ans4" value="${questionTableVal.ans4}" />
										<input type="hidden" name="rating1"
										value="${questionTableVal.rating1}" /> <input type="hidden"
										name="rating2" value="${questionTableVal.rating2}" /> <input
										type="hidden" name="rating3"
										value="${questionTableVal.rating3}" /> <input type="hidden"
										name="rating4" value="${questionTableVal.rating4}" /> <input
										type="hidden" name="testName"
										value="${questionTableVal.testName}" /> <input type="hidden"
										name="sug1" value="${questionTableVal.sug1}" /> <input
										type="hidden" name="sug2" value="${questionTableVal.sug2}" />
										<input type="hidden" name="sug3"
										value="${questionTableVal.sug3}" /> <input type="hidden"
										name="sug4" value="${questionTableVal.sug4}" /></td>

								</tr>
								<tr>
									<td>${questionTableVal.ans1}
									<td><input type="radio" name="selectedAnswer" value="1"
										checked="checked" /></td>
								</tr>
								<tr>
									<td>${questionTableVal.ans2}
									<td><input type="radio" name="selectedAnswer" value="2" /></td>
								</tr>

								<tr>
									<td>${questionTableVal.ans3}
									<td><input type="radio" name="selectedAnswer" value="3" /></td>
								</tr>

								<tr>
									<td>${questionTableVal.ans4}
									<td><input type="radio" name="selectedAnswer" value="4" /></td>
								</tr>

							</table>
						</div>
					</fieldset>
				</c:forEach>
			</tr>
		</table>
		<table>
			<tr>
				<td><input type="submit" value="next" /></td>
			</tr>
		</table>
	</html:form>