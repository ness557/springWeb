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
    <title>Student Page</title>
</head>
<body>

<form action="add" method="get">
    <input type="submit" value="Add Student"/>
</form>

<table>
    <tr>
        <th>Name</th>
        <th>Group</th>
    </tr>

    <c:forEach var="student" items="${studentsList}">

        <%--Create update link--%>
        <c:url var="updateLink" value="update">
            <c:param name="id" value="${student.id}"/>
        </c:url>

        <%--Create delete link--%>
        <c:url var="deleteLink" value="remove">
            <c:param name="id" value="${student.id}"/>
        </c:url>

        <tr>
            <td>${student.name}</td>
            <td>${student.group}</td>

            <td>
                <a href="${updateLink}">Edit</a>
                <a href="${deleteLink}"
                   onclick="if(!(confirm('Are You sure you want to delete this student?')))
                       return false">Delete</a>
            </td>
        </tr>
    </c:forEach>


</table>

</body>
</html>
