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
    <title>User add page</title>
</head>
<body>

<h2>User Form</h2>

<h3>Save user</h3>

<form action="save" method="post">

    <tags:bind path="user.id">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </tags:bind>

    <tags:bind path="user.firstName">
        <p>first name</p><input type="text" name="${status.expression}" value="${status.value}"/>
    </tags:bind>

    <tags:bind path="user.lastName">
        <p>last name<input type="text" name="${status.expression}" value="${status.value}"/></p>
    </tags:bind>

    <tags:bind path="user.userInfo.email">
        <p>Email </p><input type="text" name="${status.expression}" value="${status.value}"/>
    </tags:bind>

    <tags:bind path="user.userInfo.id">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </tags:bind>

    <tags:bind path="user.userInfo.phone">
        <p>Phone </p><input type="number" name="${status.expression}" value="${status.value}"/>
    </tags:bind>

    <tags:bind path="user.rolesString">
        <p>Phone </p><input type="text" name="${status.expression}" value="${status.value}"/>
    </tags:bind>

    <br>

    <input type="submit" value="Save">
</form>


</body>
</html>
