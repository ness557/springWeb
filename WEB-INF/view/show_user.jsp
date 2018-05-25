<%--
  Created by IntelliJ IDEA.
  User: ness
  Date: 21.05.18
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Page</title>
</head>
<body>

<form action="add" method="get">
    <input type="submit" value="Add User"/>
</form>

<table>
    <tr>
        <th>First name</th>
        <th>Last name</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Roles</th>
        <th>Edit or Remove</th>
    </tr>

    <c:forEach var="user" items="${usersList}">

        <%--Create update link--%>
        <c:url var="updateLink" value="update">
            <c:param name="id" value="${user.id}"/>
        </c:url>

        <%--Create delete link--%>
        <c:url var="deleteLink" value="remove">
            <c:param name="id" value="${user.id}"/>
        </c:url>

        <tr>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.userInfo.email}</td>
            <td>${user.userInfo.phone}</td>
            <td>${user.rolesString}</td>

            <td>
                <a href="${updateLink}">Edit</a>
                <a href="${deleteLink}"
                   onclick="if(!(confirm('Are You sure you want to delete this user?')))
                       return false">Delete</a>

            </td>
        </tr>



    </c:forEach>


</table>

</body>
</html>
