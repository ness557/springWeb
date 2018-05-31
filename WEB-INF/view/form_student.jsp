<%--
  Created by IntelliJ IDEA.
  User: ness
  Date: 21.05.18
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Student add page</title>
</head>
<body>

<h2>Student Form</h2>

<h3>Save student</h3>

<form action="save" method="post">

    <tags:bind path="student.id">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </tags:bind>

    <tags:bind path="student.name">
        <p>Name <input type="text" name="${status.expression}" value="${status.value}"/></p>
    </tags:bind>

    <tags:bind path="student.group">
        <p>Group <input type="text" name="${status.expression}" value="${status.value}"/></p>
    </tags:bind>
    <br>

    <input type="submit" value="Save">
</form>


</body>
</html>
