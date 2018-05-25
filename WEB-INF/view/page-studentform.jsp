<%--
  Created by IntelliJ IDEA.
  User: ness
  Date: 17.05.18
  Time: 19:37
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Student Register form</title>
</head>
<body>

<form:form action="process" method="post" modelAttribute="student">

Name:
<form:input path="name" title="${student.name}"/>
<br><br>

Group
<form:input path="group" title="${student.group}"/>
<br><br>

<input type="submit" value="Submit">

</form:form>
</body>
</html>
