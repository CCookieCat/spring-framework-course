<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<script type="text/javascript">
    function onDeleteClick(event) {
        event.preventDefault();

        var doDelete = confirm("Are you sure you want to delete this offer?");

        if(doDelete == false) {
            event.preventDefault();
        }
    }
    function onReady() {
        $("#delete").click(onDeleteClick);
    }
    $(document).ready(onReady);
</script>


<!-- get-method: Data will appear in the URL. Submit to the docreate url mapping -->
<form:form method="post" action="${pageContext.request.contextPath}/docreate" commandName="offer">

<!--Submit ID along with the offer. -->
<form:input type="hidden" name="id" path="id"/>

    <table class="formtable">
        <tr><td class="label"> Offer: </td> <td> <form:textarea path="information" class="control" name="information" rows="10" cols="20"></form:textarea> <br/><form:errors path="information" cssClass="error"/> </td></tr>
        <tr><td> </td> <td> <input class="control" value="Save advert" type="submit"/> </td></tr>

        <%-- Check if offer id exists(=offer has been created before)--%>
        <c:if test="${offer.id != 0}">
            <tr><td> </td> &nbsp; </td></tr>
            <tr><td> </td> <td> <input class="delete control" id="delete" name="delete" value="Delete offer" type="submit"/> </td></tr>        </c:if>
    </table>
</form:form>
