<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="adoptions">
    <h2><fmt:message key="adoptions"/></h2>
    
    <td>	
   	<spring:url value="/adoptions/selectPets" var="adoptionsUrl">
    </spring:url>
    <a class="btn btn-default" href="${fn:escapeXml(adoptionsUrl)}"><span class="glyphicon glyphicon-plus"></span> <fmt:message key="putUpForAdoption"/></a>    
    </td>
    <td>	
   	<spring:url value="/applications/applicationsList" var="applicationsUrl">
    </spring:url>
    <a class="btn btn-default" href="${fn:escapeXml(applicationsUrl)}"><span class="glyphicon glyphicon-plus"></span> <fmt:message key="myApplications"/></a>    
    </td>
    <br> </br>
    
    <h2><fmt:message key="listPetsForAdoption"/></h2>
    
    <table class="table table-striped">
         <tr>
          
            <th><fmt:message key="name"/></th>
            <th><fmt:message key="birthDate"/></th>
            <th><fmt:message key="type"/></th>
            <th><fmt:message key="newAdoption"/></th>
 		
        </tr>
        <tr>
	         <c:forEach var="pet" items="${pets}">
	         
	         <td>
	         <c:out value="${pet.name}"/>
	         </td>
	          <td>
	         <petclinic:localDate date="${pet.birthDate}" pattern="yyyy-MM-dd"/>
	         </td>
	         <td>
	         <c:out value="${pet.type.name}"/>
	         </td>
	         <td>
	         <spring:url value="/applications/createApplicationForm/{petId}" var="applicationsUrl">
	               <spring:param name="petId" value="${pet.id}"/>
	         </spring:url>
	         <a href="${fn:escapeXml(applicationsUrl)}"><fmt:message key="adopt"/></a>
	         </td>
         </tr>
         </c:forEach>
                
        
     </table>
</petclinic:layout>