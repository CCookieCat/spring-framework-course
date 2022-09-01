<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h2>Send a message</h2>

<!-- get-method: Data will appear in the URL. Submit to the docreate url mapping -->
<form:form method="post" commandName="message">
    <!-- Key will be set by webflow, determines current step in execution -->
    <input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}"/>
    <!-- Event for transitioning to the next View-state: -->
    <input type="hidden" name="_eventId" value="send"/>

    <table class="formtable">
        <tr><td class="label"> Your name: </td> <td> <form:input path="sender" value="${fromName}" class="control" name="sender" type="text"/> <br/><div class="error"><form:errors path="sender"/></div> </td></tr>
        <tr><td class="label"> Your email: </td> <td> <form:input path="email" value="${fromEmail}" class="control" name="email" type="text"/> <br/><div class="error"><form:errors path="email"/></div> </td></tr>
        <tr><td class="label"> Subject: </td> <td> <form:input path="subject" class="control" name="subject" type="text"/> <br/><div class="error"><form:errors path="subject"/></div> </td></tr>
        <tr><td class="label"> Message: </td> <td> <form:textarea path="content" class="control" name="content" type="text"/> <br/><div class="error"><form:errors path="content"/></div> </td></tr>
        <tr><td> </td> <td> <input class="control" value="Send Message" type="submit"/> </td></tr>
    </table>
</form:form>