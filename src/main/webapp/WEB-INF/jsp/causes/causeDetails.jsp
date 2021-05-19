<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="cause">
      <h2><fmt:message key="causeInformation"/></h2>


    <table class="table table-striped">
        <tr>
            <th><fmt:message key="name"/></th>
            <td><b><c:out value="${cause.name}"/></b></td>
        </tr>
        <tr>
            <th><fmt:message key="description"/></th>
            <td><c:out value="${cause.description}"/></td>
        </tr>
         <tr>
            <th><fmt:message key="budgetAchieved"/></th>
            <td><c:out value="${cause.sumaDonaciones}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="budgetTarget"/></th>
            <td><c:out value="${cause.budgetTarget}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="organization"/></th>
            <td><c:out value="${cause.organization}"/></td>
        </tr>
    </table>
    
    <h2><fmt:message key="donations"/></h2>

    <table id="causeDetailsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;"><fmt:message key="owner"/></th>
            <th style="width: 120px;"><fmt:message key="amount"/></th>
            <th style="width: 120px"><fmt:message key="date"/></th>
        </tr>
        </thead>
    <tbody>
        <c:forEach items="${cause.donaciones}" var="donacion">
            <tr>
                <td>
                    <c:out value="${donacion.owner.firstName}"/>
                </td>
                <td>
                    <c:out value="${donacion.amount}"/>
                </td>
                <td>
                    <c:out value="${donacion.date}"/>
                </td>          
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
</petclinic:layout>