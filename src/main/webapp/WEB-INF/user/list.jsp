<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: hieuhoang
  Date: 22/04/2024
  Time: 10:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List User</title>
</head>
<body>
<h1> User Management</h1>
<h2><a href="/users?action=create">Add New User</a></h2>
<c:if test="${requestScope['message'] != null}">
    <span> ${requestScope["message"]}</span>
</c:if>


<form method="post">
    <fieldset>
        <label>
            <input type="text" name="search" >
        </label>
        <input type="submit" value="Search">
    </fieldset>
</form>
<a href="/users?action=sortByName">Sort By Name</a>
<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Country</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="user" items="${userList}">
        <tr>
<%--            co the viet 2 kieu --%>

            <td><a href="/users?action=view&id=${user.getId()}"><c:out value="${user.getId()}"/></a></td>
            <td><c:out value="${user.getName()}"/></td>
            <td><c:out value="${user.email}"/></td>
            <td><c:out value="${user.country}"/></td>
            <td>
                <a href="/users?action=edit&id=${user.id}">Edit</a>
                <a href="/users?action=delete&id=${user.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
