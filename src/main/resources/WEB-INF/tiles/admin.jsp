<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Administrator Area</h2>

<table class="formtable">
    <tr> <td>Username</td> <td>Email</td> <td>Role</td> <td>Verified</td> </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td><c:out value="${user.username}"></c:out></td>
            <td><c:out value="${user.email}"></c:out></td>
            <td><c:out value="${user.authority}"></c:out></td>
            <td><c:out value="${user.enabled}"></c:out></td>
        </tr>
    </c:forEach>
</table>