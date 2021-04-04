<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<petclinic:layout pageName="vets">

    <h2><fmt:message key="vetInformation"/></h2>


    <table class="table table-striped">
        <tr>
            <th><fmt:message key="name"/></th>
            <td><b><c:out value="${vet.firstName} ${vet.lastName}"/></b></td>
        </tr>
        <tr>
            <th><fmt:message key="specialty"/></th>
            <td><c:out value="${vet.specialties}"/></td>
        </tr>
    </table>

    <spring:url value="{vetId}/edit" var="editUrl">
        <spring:param name="vetId" value="${vet.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default"><fmt:message key="editVet"/></a>

	<td>
    	<spring:url value="/vets" var="vetsUrl">
        </spring:url>
       	<a class="btn btn-default" href="${fn:escapeXml(vetsUrl)}"><span class="glyphicon glyphicon-th-list"></span>  <fmt:message key="veterinariansList"/></a>    
    </td>
</petclinic:layout>
