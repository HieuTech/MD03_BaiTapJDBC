<%--
  Created by IntelliJ IDEA.
  User: hieuhoang
  Date: 22/04/2024
  Time: 10:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
<h1> Update User
</h1>

<a href="/users">Come Back List Product</a>
<table>
    <form method="post">
        <fieldset>
            <legend>Form Update User</legend>

            <tr>
                <td>User Name</td>
                <td>
                    <input type="text" name="name" id="name" value="${user.getName()}">
                </td>

            </tr>
            <tr>
                <td>User Email</td>
                <td>
                    <input type="text" name="email" id="email" value="${user.getEmail()}">
                </td>
            </tr>
            <tr>
                <td>User Country</td>
                <td>
                    <input type="text" name="country" id="country" value="${user.getCountry()}">
                </td>
            </tr>

            <tr>
                <td><input type="submit" value="Update User"></td>
            </tr>
        </fieldset>
    </form>
</table>
</body>
</html>
