<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vets">

    <h2><fmt:message key="vets"/></h2>
    
    	<td>
    	<spring:url value="/vets/new" var="vetsUrl">
        </spring:url>
       	<a class="btn btn-default" href="${fn:escapeXml(vetsUrl)}"><span class="glyphicon glyphicon-plus"></span> <fmt:message key="newVeterinarians"/></a>    
    </td>

    <table id="vetsTable" class="table table-striped">
        <thead>
        <tr>
            <th><fmt:message key="firstName"/></th>
            <th><fmt:message key="specialties"/></th>
            <th><fmt:message key="delete"/></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vets.vetList}" var="vet">
            <tr>
                <td>
                    <spring:url value="/vets/{vetId}" var="vetUrl">
                        <spring:param name="vetId" value="${vet.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(vetUrl)}"><c:out value="${vet.firstName} ${vet.lastName}"/></a>
                </td>
                <td>
                    <c:forEach var="specialty" items="${vet.specialties}">
                        <c:out value="${specialty.name} "/>
                    </c:forEach>
                    <c:if test="${vet.nrOfSpecialties == 0}">none</c:if>
                </td>
                <td>
                <spring:url value="/vets/{vetId}/delete" var="deleteVetUrl">
                       <spring:param name="vetId" value="${vet.id}"/>
                       </spring:url>
                       <a href="${fn:escapeXml(deleteVetUrl)}"><fmt:message key="deleteVet"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</petclinic:layout>
