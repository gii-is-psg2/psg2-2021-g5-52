<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="petHotel">
    <h2>Your bookings</h2>

    <table id="bookingsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Start date</th>
            <th>End date</th>
            <th>Pet</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${myBookings}" var="booking">
            <tr>
                <td>
                    <c:out value="${booking.startDate}"/>
                </td>
                <td>
                     <c:out value="${booking.endDate}"/>
                </td>
                <td>
                	 <c:out value="${booking.pet.name}"/>
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <table class="table-buttons">
        <tr>
            <td>
                <a href="<spring:url value="/vets.xml" htmlEscape="true" />">View as XML</a>
            </td>            
        </tr>
    </table>
</petclinic:layout>
