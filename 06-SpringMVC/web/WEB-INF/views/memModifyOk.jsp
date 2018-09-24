<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
	<h1> memModifyOk </h1>
	
	<h3> memBef </h3>
	ID : ${memBef.memId}<br />
	PW : ${memBef.memPw}<br />
	Mail : ${memBef.memMail} <br />
	Phone : ${memBef.memPhone1} - ${memBef.memPhone2} - ${memBef.memPhone3} <br />
 <br />
	
	<h3> memAft </h3>
	ID : ${memAft.memId}<br />
	PW : ${memAft.memPw}<br />
	Mail : ${memAft.memMail} <br />
	Phone : ${memAft.memPhone1} - ${memAft.memPhone2} - ${memAft.memPhone3} <br />
	
	<P>  The time on the server is ${serverTime}. </P>
	
	<a href="/pages/index.html"> Go Main </a>
</body>
</html>
