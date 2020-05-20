<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="sections/head.jsp"/>
    </head>
    <body>
        <jsp:include page="sections/navMenu.jsp"/>
        <div class="container-fluid" style="background-color:#F44336;color:#fff;height:200px;">
            <h1><spring:message code= "shopping.header.label"/></h1>
            <h3><spring:message code= "shopping.subHeader.label"/></h3>
        </div>
    </body>
</html>
