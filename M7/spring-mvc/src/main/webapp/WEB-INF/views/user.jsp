<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="sections/head.jsp"/>
    </head>
    <body>
        <c:set var="activeCatalog" value="active" scope="request" />
        <jsp:include page="sections/navMenu.jsp"/>
        <section class="container">
            <form:form modelAttribute="newUser" class="form-horizontal">
                <fieldset>
                    <div class="thumbnail">
                        <div class="caption">
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger">
                                     <spring:message code= "user.form.error.label"/>
                                </div>
                            </c:if>
                            <sec:authorize access="hasAnyRole('ROLE_USER')">
                                <legend><spring:message code= "user.header.logged.label"/></legend>
                                <div class="form-group">
                                    <div class="col-lg-10">
                                        <form:input id="username" path="username" type="hidden" class="form:input-large"/>
                                    </div>
                                </div>
                            </sec:authorize>
                            <sec:authorize access="!hasAnyRole('ROLE_USER')">
                                <legend><spring:message code= "user.header.unlogged.label"/></legend>
                                <div class="form-group">
                                    <label class="control-label col-lg-2 col-lg-2" for="username"><spring:message code= "user.form.username.label"/></label>
                                    <div class="col-lg-10">
                                        <form:input id="username" path="username" type="email" class="form:input-large"/>
                                    </div>
                                </div>
                            </sec:authorize>
                            <div class="form-group">
                                <label class="control-label col-lg-2 col-lg-2" for="password"><spring:message code= "user.form.password.label"/></label>
                                <div class="col-lg-10">
                                    <form:input id="password" path="password" type="password" class="form:input-large"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-2 col-lg-2" for="firstName"><spring:message code= "user.form.firstName.label"/></label>
                                <div class="col-lg-10">
                                    <form:input id="firstName" path="firstName" type="text" class="form:input-large"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-2 col-lg-2" for="lastName"><spring:message code= "user.form.lastName.label"/></label>
                                <div class="col-lg-10">
                                    <form:input id="lastName" path="lastName" type="text" class="form:input-large"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-lg-offset-2 col-lg-10">
                                    <input type="submit" id="btnAdd" class="btn btn-success" value ="Submit"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </fieldset>
            </form:form>
        </section>        
    </body>
</html>
