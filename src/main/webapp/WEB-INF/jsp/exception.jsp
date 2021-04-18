<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<petclinic:layout pageName="error">

    <spring:url value="/resources/images/perro-error.jpg" var="petsImage"/>
    <img class="img-responsive errorImg"  width="500" height="500" src="${petsImage}"/>


    <h2><fmt:message key="somethingHappened..."/></h2>


    <p>${exception.message}</p>

</petclinic:layout>
