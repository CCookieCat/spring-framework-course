<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!-- Display all current offers: -->
<table class="offers">
    <tr><td>Name</td><td>Email:</td><td>Offer</td></tr>
    <!-- iterating over attribute:List of Offers -->
    <c:forEach var="offer" items="${offers}">
        <tr>
            <td class="name"><c:out value="${offer.user.name}"/></td>
            <%-- <td><c:out value="${offer.user.email}"/></td> --%>
            <td class="contact"> <a href="<c:url value='/message?uid=${offer.username}'/>">Contact</a> </td>
            <td class="offer"><c:out value="${offer.information}"/></td>
        </tr>
    </c:forEach>
</table>