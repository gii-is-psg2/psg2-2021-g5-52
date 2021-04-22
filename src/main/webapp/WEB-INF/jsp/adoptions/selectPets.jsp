<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<petclinic:layout pageName="adoptions">

    <h2><fmt:message key="selectPetsForAdoption"/></h2>
    
	<table class="table table-striped">
          <tr>
          
            <th><fmt:message key="name"/></th>
            <th><fmt:message key="birthDate"/></th>
            <th><fmt:message key="type"/></th>
            <th><fmt:message key="givePet"/></th>
 		
      	  </tr>
        
         <c:forEach var="pet" items="${pets}">
         <tr>
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
		         <spring:url value="/adoptions/pet/{petId}" var="petUrl">
		               <spring:param name="petId" value="${pet.id}"/>
		         </spring:url>
		         <a href="${fn:escapeXml(petUrl)}"><fmt:message key="givePet"/></a>
	         </td>
	      </tr>
         <br>
         </c:forEach>
                
        
     </table>
                   

</petclinic:layout>
