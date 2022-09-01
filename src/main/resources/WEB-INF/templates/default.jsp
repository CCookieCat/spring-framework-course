<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<html>
    <head>
        <title> <tiles:insertAttribute name="title"></tiles:insertAttribute> </title>

        <!-- default header name is X-CSRF-TOKEN-->
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>

        <!-- used to access the stylesheet data -->
        <link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/jquery.js"></script>

        <tiles:insertAttribute name="includes"></tiles:insertAttribute>

    </head>
    <body>
        <div class="header"> <tiles:insertAttribute name="header"></tiles:insertAttribute> </div>

        <div class="toolbar"> <tiles:insertAttribute name="toolbar"></tiles:insertAttribute> </div>

        <div class="content"> <tiles:insertAttribute name="content"></tiles:insertAttribute> </div>

        <hr/>
        <div class="footer"> <tiles:insertAttribute name="footer"></tiles:insertAttribute> </div>
    </body>
</html>
