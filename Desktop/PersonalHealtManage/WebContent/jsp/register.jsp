<!DOCTYPE html> 
<html>
<%@page import="com.model.RegisterVerifyMsgs"%>
<%@page import="com.constants.PersonalHealtConstantsIF"%>
	<head>
		<title>Sky Forms</title>
		
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
		
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/demo.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/sky-forms.css">
		<!--[if lt IE 9]>
			<link rel="stylesheet" href="css/sky-forms-ie8.css">
		<![endif]-->
		
		<!--[if lt IE 10]>
			<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
			<script src="js/jquery.placeholder.min.js"></script>
		<![endif]-->		
		<!--[if lt IE 9]>
			<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
			<script src="js/sky-forms-ie8.js"></script>
		<![endif]-->
		<script type="text/javascript">
		function preventBack(){window.history.forward();}
			setTimeout("preventBack()", 0);
			window.onunload=function(){null};
		</script>
	</head>
	<body class="bg-cyan">
	<jsp:include page="/jsp/customer.jsp"></jsp:include>
	<jsp:include page="/jsp/menu.jsp"></jsp:include>
	<%
		RegisterVerifyMsgs loginVerify=(RegisterVerifyMsgs) request.getAttribute(PersonalHealtConstantsIF.Keys.OBJ);
	if(null==loginVerify)
	{
		
	}
	else
	{
		String  errMsg=loginVerify.getSeverMessage();
		if(null==errMsg)
		{
			
		}
		else
		{
	%>

<div class="errMsg"><%= errMsg%></div>
<%	
	}
}
%>

<div id="errMsgField">
		<%
		if(loginVerify!=null)
		{
			String u=loginVerify.getUserNameErrMsg();
			if(null==u)
			{
				
			}
			else
			{
				%> <%=u%></div>
		<%
			
			
			}
			}%>
</div>
<div id="errMsgField">
		<%
		if(loginVerify!=null)
		{
			String p=loginVerify.getPasswordErrMsg();
			if(null==p)
			{
				
			}
			else
			{
				%> <%=p%></div>
		<%
			
			
			}
			}%>
</div>
<div id="errMsgField">
			<%
			if(loginVerify!=null)
			{
			String firstNameErr=loginVerify.getFirstNameErrMsg();
			if(null==firstNameErr || firstNameErr.isEmpty())
			{
				
			}
			else
			{
				%> <%=firstNameErr%></div>
			<%
			
			}
			}%>
</div>
<div id="errMsgField">
			<%
			if(loginVerify!=null)
			{
			String lastNameErr=loginVerify.getLastNameErrMsg();
			if(null==lastNameErr || lastNameErr.isEmpty())
			{
				
			}
			else
			{
				%> <%=lastNameErr%></div>
			<%
			
			}
			}%>
</div>
<div id="errMsgField">
				<%
				if(loginVerify!=null)
				{
			String emailErr=loginVerify.getEmailErrMsg();
			if(null==emailErr)
			{
				
			}
			else
			{
				%> <%=emailErr%></div>
				<%
			
			}
			}%>
</div>
<div id="errMsgField">
				<%
				if(loginVerify!=null)
				{
			String  heartAttackErr=loginVerify.getHeartAttackErrMsg();
			if(null==heartAttackErr)
			{
				
			}
			else
			{
				%> <%=heartAttackErr%></div>
				<%
			
			}
			}%>
</div>
<div id="errMsgField">
				<%
				if(loginVerify!=null)
				{
			String  diabeticErr=loginVerify.getDiabeticErrMsg();
			if(null==diabeticErr)
			{
				
			}
			else
			{
				%> <%=diabeticErr%></div>
				<%
			
			}
			}%>
</div>
<div id="errMsgField">
				<%
				if(loginVerify!=null)
				{
			String  bpErr=loginVerify.getBPErrMsg();
			if(null==bpErr)
			{
				
			}
			else
			{
				%> <%=bpErr%></div>
				<%
			
			}
			}%>
</div>
<div id="errMsgField">
				<%
				if(loginVerify!=null)
				{
			String  obesityErr=loginVerify.getObesityErrMsg();
			if(null==obesityErr)
			{
				
			}
			else
			{
				%> <%=obesityErr%></div>
				<%
			
			}
			}%>
</div>
<div id="errMsgField">
				<%
				if(loginVerify!=null)
				{
			String  sexErr=loginVerify.getSexErrMsg();
			if(null==sexErr)
			{
				
			}
			else
			{
				%> <%=sexErr%></div>
				<%
			
			}
			}%>
</div>
<div id="errMsgField">
				<%
				if(loginVerify!=null)
				{
			String  ageErr=loginVerify.getAgeErrMsg();
			if(null==ageErr)
			{
				
			}
			else
			{
				%> <%=ageErr%></div>
				<%
			
			}
			}%>
</div>
<div id="errMsgField">
				<%
				if(loginVerify!=null)
				{
			String  dobErr=loginVerify.getDobErrMsg();
			if(null==dobErr)
			{
				
			}
			else
			{
				%> <%=dobErr%></div>
				<%
			
			}
			}%>
</div>

		<div class="body body-s">
		
			<form action="<%=request.getContextPath()%>/dia/registerUser.do" method="post" class="sky-form">
				<header>Registration form</header>
				
				<fieldset>					
					<section>
						<label class="input">
							<i class="icon-append icon-user"></i>
							<input type="text" name="userId" placeholder="Username">
							<b class="tooltip tooltip-bottom-right">Enter Username</b>
						</label>
					</section>
					
					<section>
						<label class="input">
							<i class="icon-append icon-envelope-alt"></i>
							<input type="text" name="" placeholder="Email address">
							<b class="tooltip tooltip-bottom-right">Email</b>
						</label>
					</section>
					
					<section>
						<label class="input">
							<i class="icon-append icon-lock"></i>
							<input type="password" name="userPassword" placeholder="Password">
							<b class="tooltip tooltip-bottom-right">Only latin characters and numbers</b>
						</label>
					</section>
					
					<section>
						<label class="input">
							<i class="icon-append icon-lock"></i>
							<input type="date" name="dob" placeholder="Date of Birth">
							<b class="tooltip tooltip-bottom-right">Only latin characters and numbers</b>
						</label>
					</section>
				</fieldset>
					
				<fieldset>
					<div class="row">
						<section class="col col-6">
							<label class="input">
								<input type="text" name="firstName" placeholder="First name">
							</label>
						</section>
						<section class="col col-6">
							<label class="input">
								<input type="text" name="lastName" placeholder="Last name">
							</label>
						</section>
					</div>
					
					<fieldset>
							
					<section>
						<label class="select">
							<select name="answer1">
								<option value="" selected disabled>Age group (in years)</option>
								<option value="1">20 to 25 years</option>
								<option value="2">26 to 30 years</option>
								<option value="3">31 to 40  years</option>
								<option value="4">Above 40 years</option>
							</select>
							<i></i>
						</label>
					</section>
					
					<section>
						<label class="select">
							<select name="answer2">
								<option value="" selected disabled>Income in Lakhs of Rupees per annum</option>
								<option value="1">< 3 Lakhs</option>
								<option value="2">3 to 4 lakhs</option>
								<option value="3">>4 and < 6  lakhs</option>
								<option value="4">>6 lakhs</option>
							</select>
							<i></i>
						</label>
					</section>
					
					<section>
						<label class="select">
							<select name="answer3">
								<option value="" selected disabled>Years of experience in the profession</option>
								<option value="1"><2 years</option>
								<option value="2">2 to 5 years</option>
								<option value="3">>5 and <8 years</option>
								<option value="4"> >8 years</option>
							</select>
							<i></i>
						</label>
					</section>
					
					
					<section>
						<label class="select">
							<select name="sex">
								<option value="" selected disabled>Sex</option>
								<option value="1">Male</option>
								<option value="2">Female</option>
							</select>
							<i></i>
						</label>
					</section>
					
				</fieldset>
					
					<section>
						<label class="select">
							<select name="sex">
								<option value="-1" selected disabled>Sex</option>
								<option value="0">Male</option>
								<option value="1">Female</option>
							</select>
							<i></i>
						</label>
					</section>
					
					<section>
						<label class="select">
						<select name="age">
							<option value="0">0</option>
							<option value="16">16</option>
							<option value="17">17</option>
							<option value="18">18</option>
							<option value="19">19</option>
							<option value="20">20</option>
							<option value="21">21</option>
							<option value="22">22</option>
							<option value="23">23</option>
							<option value="24">24</option>
							<option value="25">25</option>
							<option value="26">26</option>
							<option value="27">27</option>
							<option value="28">28</option>
							<option value="29">29</option>
							<option value="30">30</option>
							<option value="31">31</option>
							<option value="32">32</option>
							<option value="33">33</option>
							<option value="34">34</option>
							<option value="35">35</option>
							<option value="36">36</option>
							<option value="37">37</option>
							<option value="38">38</option>
							<option value="39">39</option>
							<option value="40">40</option>
							<option value="41">41</option>
							<option value="42">42</option>
							<option value="43">43</option>
							<option value="44">44</option>
							<option value="45">45</option>
							<option value="46">46</option>
							<option value="47">47</option>
							<option value="48">48</option>
							<option value="49">49</option>
							<option value="50">50</option>
							<option value="51">51</option>
							<option value="52">52</option>
							<option value="53">53</option>
							<option value="54">54</option>
							<option value="55">55</option>
							<option value="56">56</option>
							<option value="57">57</option>
							<option value="58">58</option>
							<option value="59">59</option>
							<option value="60">60</option>
							<option value="61">61</option>
							<option value="62">62</option>
							<option value="63">63</option>
							<option value="64">64</option>
							<option value="65">65</option>
							<option value="66">66</option>
							<option value="67">67</option>
							<option value="68">68</option>
							<option value="69">69</option>
							<option value="70">70</option>
							<option value="71">71</option>
							<option value="72">72</option>
							<option value="73">73</option>
							<option value="74">74</option>
							<option value="75">75</option>
							<option value="76">76</option>
							<option value="77">77</option>
							<option value="78">78</option>
							<option value="79">79</option>
							<option value="80">80</option>
							<option value="81">81</option>
							<option value="82">82</option>
							<option value="83">83</option>
							<option value="84">84</option>
							<option value="85">85</option>
							<option value="86">86</option>
							<option value="87">87</option>
							<option value="88">88</option>
							<option value="89">89</option>
							<option value="90">90</option>
							<option value="91">91</option>
							<option value="92">92</option>
							<option value="93">93</option>
							<option value="94">94</option>
							<option value="95">95</option>
							<option value="96">96</option>
							<option value="97">97</option>
							<option value="98">98</option>
							<option value="99">99</option>
							<option value="100">100</option>
						</select>
						<i></i>
						</label>
					</section>
					
					<section>
						<label class="checkbox"><input type="checkbox" name="checkbox"><i></i>I agree to the Terms of Service</label>
					</section>
				</fieldset>
				<footer>
					<button type="submit" class="button">Submit</button>
				</footer>
			</form>
			
		</div>
	</body>
</html>