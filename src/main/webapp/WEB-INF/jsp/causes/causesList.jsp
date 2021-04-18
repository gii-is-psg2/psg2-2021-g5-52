<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causes">
    <h2><fmt:message key="causes"/></h2>
    
    <td>	
   	<spring:url value="/causes/new" var="causesUrl">
    </spring:url>
    <a class="btn btn-default" href="${fn:escapeXml(causesUrl)}"><span class="glyphicon glyphicon-plus"></span> <fmt:message key="newCauses"/></a>    
    </td>
    <br> </br>
    <table id="causesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;"><fmt:message key="name"/></th>
            <th style="width: 200px;"><fmt:message key="budgetAchieved"/></th>
            <th style="width: 120px"><fmt:message key="budgetTarget"/></th>
            <th style="width: 20px"> <fmt:message key="seeDetails"/></th>
            <th style="width: 20px"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${causes}" var="cause">
            <tr>
                <td>
                    <c:out value="${cause.name}"/>
                </td>
                <td>
                    <c:out value="${cause.sumaDonaciones}"/>
                </td>
                <td>
                    <c:out value="${cause.budgetTarget}"/>
                </td>
                <td>
                <spring:url value="/causes/{causeId}" var="causeUrl">
                        <spring:param name="causeId" value="${cause.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(causeUrl)}">
                    	<span class="glyphicon glyphicon-eye-open"></span></a>
                </td>         
                <td>
                <spring:url value="/donations/new/{causeId}" var="causeUrl">
                        <spring:param name="causeId" value="${cause.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(causeUrl)}">
                    	<span class="glyphicon glyphicon-plus"></span></a>
                </td>             
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>