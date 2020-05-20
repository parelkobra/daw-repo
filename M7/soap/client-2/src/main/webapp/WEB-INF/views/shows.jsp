<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
        <title>Concerts</title>
    </head>
    <body>
        <section>
            <div class="jumbotron">
                <div class="container">
                    <h1>Concerts</h1>
                    <p>Llista de concerts</p>
                </div>
            </div>
        </section>
        <section class="container">
            <div class="row">
                <c:forEach items="${shows}" var="show">
                    <div class="col-sm-6 col-md-3" style="padding-bottom: 15px">
                        <div class="thumbnail">
                            <div class="caption">
                                <h3>${show.name}</h3>
                                <p>${show.location}</p>                                
                                <p>Hi ha ${show.availableTickets} tickets disponibles</p>                                
                                <p>
                                    <a href=" <spring:url value= "/makeReservation?id=${show.id}" /> " class="btn btn-primary">
                                        <span class="glyphicon-info-sign glyphicon"/></span> Reservar una entrada
                                    </a>
                                </p>
                                <p>
                                    <a href=" <spring:url value= "/cancelReservation?id=${show.id}" /> " class="btn btn-primary">
                                        <span class="glyphicon-info-sign glyphicon"/></span> Anul·lar una reserva
                                    </a>
                                </p>
                            </div>
                        </div>
                    </div>
                </c:forEach>

            </div>
        </section>
    </body>
</html>
