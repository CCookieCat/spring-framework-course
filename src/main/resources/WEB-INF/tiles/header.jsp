<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<c:url var="homeUrl" value="/"/>
<a class="title" href="${homeUrl}">Offers Homepage</a>

<c:url var="logoutUrl" value="/logout"/>
<sec:authorize access="isAuthenticated()">
    <form action="${logoutUrl}" method="POST">
        <input type="submit" value="Logout" />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</sec:authorize>
<c:url var="loginUrl" value="/login"/>
<sec:authorize access="!isAuthenticated()">
    <form action="${loginUrl}" method="POST">
        <input type="submit" value="Login" />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</sec:authorize>