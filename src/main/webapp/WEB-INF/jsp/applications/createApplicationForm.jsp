<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">
    
       
	 <h1><fmt:message key="createApplication"/></h1>
    
     <h2><fmt:message key="description"/></h2>


    <table class="table table-striped">
        <tr>
            <th><fmt:message key="name"/></th>
            <td><b><c:out value="${application.adoption.name}"/></b></td>
        </tr>
        <tr>
            <th><fmt:message key="currentOwner"/></th>
            <td><b><c:out value="${application.adoption.owner.firstName}"/></b></td>
        </tr>
        <tr>
            <th><fmt:message key="description"/></th>
            <td><c:out value="${application.adoption.description}"/></td>
        </tr>
    </table>
    
    <form:form modelAttribute="application" class="form-horizontal" id="add-application-form" action="/applications/new/${application.adoption.id}" >
   
   
            <petclinic:inputFieldInt name="application"/>        
  
	
			<input type="hidden" name="adoptionId" id="adoptionId" value="${application.adoption.id}"/>
         
             <button class="btn btn-default" type="submit"><fmt:message key="createApplication"/></button>
	
	         
    </form:form>
</petclinic:layout>