<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
	<h1> memJoinOk2 </h1>
	ID : ${mem.memId}<br />
	PW : ${mem.memPw}<br />
	Mail : ${mem.memMail} <br />
	Phone : ${mem.memPhone1} - ${mem.memPhone2} - ${mem.memPhone3} <br />
	
	<a href="/pages/memJoin.html"> Go MemberJoin </a>
</body>
</html>
