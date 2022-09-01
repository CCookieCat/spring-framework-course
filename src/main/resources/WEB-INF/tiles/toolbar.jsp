<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<p>
    <c:choose>
        <c:when test="${hasOffer}">
            <a href="${pageContext.request.contextPath}/createoffer">Edit or delete your current Offer</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/createoffer">Add new offer</a>
        </c:otherwise>
    </c:choose>
&nbsp;
    <a href="${pageContext.request.contextPath}/offers">Show current offers</a>
&nbsp;
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <a href="${pageContext.request.contextPath}/admin">Admin</a>
    </sec:authorize>
&nbsp;
    <sec:authorize access="isAuthenticated()">
        <a href="<c:url value='/messages'/>">Messages (<span id="amountMessages">0</span>)</a>
    </sec:authorize>
</p>


<!-- Displaying Messages: -->
<script type="text/javascript">
    $(document).ready(onLoad);

    function onLoad() {
        console.log("Timer started. Setting Header");
        setHeader();
        updatePage();
        window.setInterval(updatePage,5000);
    }

    function setHeader() {
        var token = $("input[name='_csrf']").val();
        var header = "X-CSRF-Token";
        $(document).ajaxSend(function(e,xhr,options) {
            xhr.setRequestHeader(header,token);
        });
    }

    function updatePage() {
        console.log("Page has been updated!");
        var messagesServerUrl = "<c:url value='/getmessages'/>"
        $.getJSON(messagesServerUrl, updateMessageLink);

    }

    function updateMessageLink(data) {
        console.log(data.amount);
        $("#amountMessages").text(data.amount);
    }
</script>