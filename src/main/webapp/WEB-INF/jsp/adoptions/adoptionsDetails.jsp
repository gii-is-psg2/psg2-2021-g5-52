<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="adoption">
      <h2><fmt:message key="adoptionInformation"/></h2>


    <table class="table table-striped">
        <tr>
            <th><fmt:message key="name"/></th>
            <td><b><c:out value="${adoption.name}"/></b></td>
        </tr>
        <tr>
            <th><fmt:message key="currentOwner"/></th>
            <td><b><c:out value="${adoption.owner.firstName}"/></b></td>
        </tr>
        <tr>
            <th><fmt:message key="description"/></th>
            <td><c:out value="${adoption.description}"/></td>
        </tr>
    </table>
    
    <h2><fmt:message key="applications"/></h2>

    <table id="adoptionDetailsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;"><fmt:message key="owner"/></th>
            <th style="width: 120px"><fmt:message key="description"/></th>
        </tr>
        </thead>
    <tbody>
        <c:forEach items="${adoption.applications}" var="application">
            <tr>
                <td>
                    <c:out value="${application.owner.firstName}"/>
                </td>
                <td>
                    <c:out value="${application.description}"/>
                </td>         
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
</petclinic:layout>