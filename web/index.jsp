<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SpringMVC</title>
</head>
<body>

	<a href="/rone/echarts">ECharts</a>
	<br/><br/>
	<a href="rone/helloworld">Hello World</a>
	<br/><br/>
	
	<form action="/rone/testMethod" method="post">
		<input type="submit" value="submit"/>
	</form>
	<br/><br/>
	
	<a href="rone/testAnt_rone_test/test/rone/ant">testAnt</a>
	<br/><br/>
	
	<a href="rone/testPathVariable/ok">testPathVariable</a>
	<br/><br/>
	
	<form action="rone/testREST/user" method="post">
		<input type="hidden" name="_method" value="get">
		<input type="submit" value="REST_get">
	</form>
	<br/><br/>
	
	<form action="rone/testREST" method="post">
		<input type="hidden" name="_method" value="post">
		<input type="submit" value="REST_post">
	</form>
	<br/><br/>
	
	<form action="rone/testREST/user" method="post">
		<input type="hidden" name="_method" value="delete">
		<input type="submit" value="REST_delete">
	</form>
	<br/><br/>
	
	<form action="rone/testREST/user" method="post">
		<input type="hidden" name="_method" value="put">
		<input type="submit" value="REST_put">
	</form>
	<br/><br/>
	
	<a href="rone/testRequestParam?username=rone&password=123">testRequestParam</a>
	<br/><br/>
	
	<form action="rone/testPOJO" method="post">
		userName: <input type="text" name="username"/><br/>
		passWord: <input type="password" name="password"/><br/>
		email: <input type="text" name="email"/><br/>
		province: <input type="text" name="address.province"/><br/>
		city: <input type="text" name="address.city"/><br/>
		<input type="submit" value="POJO">
	</form>
	<br/><br/>
	
	<a href="rone/testServletAPI">testServletAPI</a>
	<br/><br/>
	
	<a href="rone/testModelAndView">testModelAndView</a>
	<br/><br/>
	
	<a href="rone/testMap">testMap</a>
	<br/><br/>
	
	<a href="rone/testSessionAttributes">testSessionAttributes</a>
	<br/><br/>
	
	<form action="rone/testModelAttributes" method="post">
		userName: <input type="text" name="username"/><br/>
		email: <input type="text" name="email"/><br/>
		province: <input type="text" name="address.province"/><br/>
		city: <input type="text" name="address.city"/><br/>
		<input type="submit" value="ModelAttributes">
	</form>
	<br/><br/>
	
	<a href="rone/testResponseBody">testResponseBody</a>
	<br/><br/>
	
</body>
</html>