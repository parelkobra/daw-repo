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
        <c:set var="activeCatalog" value="active" scope="request" />
        <jsp:include page="sections/navMenu.jsp"/>
        <section class="container-fluid">
            <div class="row">
                <div class="col-lg-8 col-sm-12">
                    <c:forEach items="${articles}" var="article">
                        <div class="col-sm-6 col-md-3" style="padding-bottom: 15px">
                            <div class="thumbnail">
                                <div class="caption">
                                    <p>${article.name}</p>
                                    <img style="max-width: 100px;" src="<spring:url value="/images/${article.image}" />" >
                                    <p>${article.description}</p>
                                    <p> <fmt:formatNumber value="${article.price}" minFractionDigits="2" /> <spring:message code= "catalog.currency.label"/></p>
                                    <p>
                                        <a href=" <spring:url value= '/addToCart?reference=${article.reference}' /> " class="btn btn-info">
                                            <span class="glyphicon glyphicon-shopping-cart"/></span> <spring:message code= "catalog.articles.addToShoppingCart.label"/>
                                        </a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>      
                <div class="col-lg-4 col-sm-12">
                    <div class="panel panel-info">
                        <div class="panel-heading"><spring:message code= "catalog.shoppingCart.header.label"/></div>
                        <div class="panel-body" style="max-height: 400px;overflow-y: scroll;">
                            <c:forEach items="${shoppingCart.articles}" var="article">
                                <div class="col-md-4">
                                    <p>
                                        <a href=" <spring:url value= '/removeFromCart?reference=${article.key.reference}' /> " class="btn btn-sm btn-danger">
                                            <span class="glyphicon glyphicon-remove"/></span>
                                        </a>
                                    </p>
                                    <img style="max-width: 65px; padding-bottom: 15px;" src="<spring:url value="/images/${article.key.image}" />" >
                                    <p>
                                        <a href=" <spring:url value= '/addToCart?reference=${article.key.reference}' />" class="btn btn-xs btn-default">
                                            <span class="glyphicon glyphicon-plus"/></span>
                                        </a>
                                        <a href=" <spring:url value= '/decreaseFromCart?reference=${article.key.reference}' /> " class="btn btn-xs btn-default">
                                            <span class="glyphicon glyphicon-minus"/></span>
                                        </a>
                                    </p>   
                                </div>
                                <div class="col-md-8">
                                    <h4>${article.key.name}</h4>
                                    <p>${article.key.description}</p>
                                    <p>
                                        <spring:message code= "catalog.shoppingCart.price.label"/> <fmt:formatNumber value="${article.key.price}" minFractionDigits="2" /> <spring:message code= "catalog.currency.label"/><br>
                                        <spring:message code= "catalog.shoppingCart.quantity.label"/> ${article.value}<br>
                                        <spring:message code= "catalog.shoppingCart.total.label"/> <fmt:formatNumber value="${article.key.price * article.value}" minFractionDigits="2" /> <spring:message code= "catalog.currency.label"/><br>
                                    </p>                             
                                </div>
                            </c:forEach>
                        </div>
                        <c:if test="${shoppingCart.articles.size() > 0}">
                            <div class="panel-footer">
                                <h5><spring:message code= "catalog.shoppingCart.all.total.label"/> <fmt:formatNumber value="${shoppingCart.getTotalPrice()}" minFractionDigits="2" /> <spring:message code= "catalog.currency.label"/></h5>
                                <p>
                                    <a href=" <spring:url value='/secured/shopping' /> " class="btn btn-primary">
                                        <span class="glyphicon glyphicon-shopping-cart"/></span> <spring:message code= "catalog.shoppingCart.goShop.label"/>
                                    </a>
                                </p>
                            </div>
                        </c:if>
                    </div>  
                </div>
            </div>
        </section>
    </body>
</html>
