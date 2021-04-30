<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="adoptions">
    <h2><fmt:message key="myAdoptionsApplications"/></h2>
    
   
    <table class="table table-striped">
         <tr>
          
            <th><fmt:message key="owner"/></th>
            <th><fmt:message key="petName"/></th>
            <th><fmt:message key="description"/></th>
            <th><fmt:message key="acceptOrReject"/></th>
 		
        </tr>
        <tr>
	         <c:forEach var="application" items="${applicationList}">
	         
	         <td>
	         <c:out value="${application.owner.user.username}"/>
	         </td>
	          <td>
	         <c:out value="${application.pet.name}"/>
	         </td>
	         <td>
	         <c:out value="${application.description}"/>
	         </td>
	         <td>
	         <spring:url value="/applications/acept/{applicationId}" var="newAdoptionUrl">
	               <spring:param name="applicationId" value="${application.id}"/>
	         </spring:url>
	         <a href="${fn:escapeXml(newAdoptionUrl)}"><span class="glyphicon glyphicon-ok"></span><fmt:message key="accept"/></a>
	         <spring:url value="/applications/reject/{applicationId}" var="rejectAdoptionUrl">
	               <spring:param name="applicationId" value="${application.id}"/>
	         </spring:url>
	         <a href="${fn:escapeXml(rejectAdoptionUrl)}"><span class="glyphicon glyphicon-remove"></span><fmt:message key="reject"/></a>
	         </td>
         </tr>
         </c:forEach>
                
        
     </table>
</petclinic:layout>