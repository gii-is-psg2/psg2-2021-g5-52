<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">
    
       
	<h2><fmt:message key="createApplication"/></h2>
	 <table class="table table-striped">
         <tr>
          
            <th><fmt:message key="name"/></th>
            <th><fmt:message key="birthDate"/></th>
            <th><fmt:message key="type"/></th>
 		
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
         </tr>
         </c:forEach>
     </table>
     
     

</petclinic:layout>