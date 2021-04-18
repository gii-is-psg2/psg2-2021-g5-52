<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="adoptions">
    <h2><fmt:message key="adoptions"/></h2>
    
    <td>	
   	<spring:url value="/adoptions/new" var="adoptionsUrl">
    </spring:url>
    <a class="btn btn-default" href="${fn:escapeXml(adoptionsUrl)}"><span class="glyphicon glyphicon-plus"></span> <fmt:message key="newAdoptions"/></a>    
    </td>
    <br> </br>
    <table id="adoptionsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;"><fmt:message key="name"/></th>
            <th style="width: 200px;"><fmt:message key="description"/></th>
            <th style="width: 20px"> <fmt:message key="seeDetails"/></th>
            <th style="width: 20px"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${adoptions}" var="adoption">
            <tr>
                <td>
                    <c:out value="${adoption.name}"/>
                </td>
                <td>
                    <c:out value="${adoption.description}"/>
                </td>
                <td>
                <spring:url value="/adoptions/{adoptionId}" var="adoptionUrl">
                        <spring:param name="adoptionId" value="${adoption.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(adoptionUrl)}">
                    	<span class="glyphicon glyphicon-eye-open"></span></a>
                </td>
                 <td>
                <spring:url value="/applications/new/{adoptionId}" var="adoptionUrl">
                        <spring:param name="adoptionId" value="${adoption.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(adoptionUrl)}">
                    	<span class="glyphicon glyphicon-plus"></span></a>
                </td>               
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>