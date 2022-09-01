<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h2>Create new Account</h2>
<!-- get-method: Data will appear in the URL. Submit to the docreate url mapping -->
<form:form id="details" method="post" action="${pageContext.request.contextPath}/createaccount" commandName="user">
    <table class="formtable">
        <tr><td class="label"> Username: </td> <td> <form:input path="username" class="control" name="username" type="text"/> <br/><div class="error"><form:errors path="username"/></div> </td></tr>
        <tr><td class="label"> Name: </td> <td> <form:input path="name" class="control" name="name" type="text"/> <br/><div class="error"><form:errors path="name"/></div> </td></tr>
        <tr><td class="label"> Email: </td> <td> <form:input path="email" class="control" name="email" type="text"/> <br/><div class="error"><form:errors path="email"/></div> </td></tr>
        <tr><td class="label"> Password: </td> <td> <form:input id="password" path="password" class="control" name="password" type="password"/> <br/><div class="error"><form:errors path="password"/></div> </td></tr>
        <tr><td class="label"> Repeat Password: </td> <td> <input id="confirmpass" class="control" name="confirmpass" type="password"/> <div id="matchpass"></div> </td></tr>
        <tr><td> </td> <td> <input class="control" value="Create Account" type="submit"/> </td></tr>
    </table>
</form:form>