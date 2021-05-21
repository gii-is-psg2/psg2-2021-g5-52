<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">
    
       
	 <h1><fmt:message key="createDonation"/></h1>
    
     <h2><fmt:message key="causeInformation"/></h2>


    <table class="table bg-warning">
        <tr>
            <th><fmt:message key="name"/></th>
            <td><b><c:out value="${donation.cause.name}"/></b></td>
        </tr>
        <tr>
            <th><fmt:message key="description"/></th>
            <td><c:out value="${donation.cause.description}"/></td>
        </tr>
         <tr>
            <th><fmt:message key="budgetAchieved"/></th>
            <td><c:out value="${donation.cause.sumaDonaciones}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="budgetTarget"/></th>
            <td><c:out value="${donation.cause.budgetTarget}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="organization"/></th>
            <td><c:out value="${donation.cause.organization}"/></td>
        </tr>
    </table>
    
    <form:form modelAttribute="donation" class="form-horizontal" id="add-donation-form" action="/donations/new/${donation.cause.id}" >
   
   
            <petclinic:inputField label="cantidad" name="amount"/>        
  
	
			<input type="hidden" name="causeId" id="causeId" value="${donation.cause.id}"/>
         
             <button class="btn btn-default" type="submit"><fmt:message key="createDonation"/></button>
	
	         
    </form:form>
</petclinic:layout>
