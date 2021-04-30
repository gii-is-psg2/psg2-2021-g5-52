<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<petclinic:layout pageName="booking">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#date").datepicker({
                	dateFormat: 'yy/mm/dd',
                	minDate: 0});
            });
        </script>
        <script>
            $(function () {
                $("#startDate").datepicker({
                	dateFormat: 'yy/mm/dd',
                	minDate: 0});
            });
        </script>
        <script>
            $(function () {
                $("#endDate").datepicker({
                	dateFormat: 'yy/mm/dd',
                	minDate: 0});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2><fmt:message key="booking"/></h2>

        <b><fmt:message key="pet"/></b>
        <table class="table table-striped">
            <thead>
            <tr>
                <th><fmt:message key="name"/></th>
                <th><fmt:message key="birthDate"/></th>
                <th><fmt:message key="type"/></th>
                <th><fmt:message key="owner"/></th>
            </tr>
            </thead>
            <tr>
                <td><c:out value="${booking.pet.name}"/></td>
                <td><petclinic:localDate date="${booking.pet.birthDate}" pattern="yyyy/MM/dd"/></td>
                <td><c:out value="${booking.pet.type.name}"/></td>
                <td><c:out value="${booking.pet.owner.firstName} ${visit.pet.owner.lastName}"/></td>
            </tr>
        </table>

        <form:form modelAttribute="booking" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Start Date" name="startDate"/>
                <petclinic:inputField label="End Date" name="endDate"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId" value="${booking.pet.id}"/>
                    <button class="btn btn-default" type="submit"><fmt:message key="addBooking"/></button>
                </div>
            </div>
        </form:form>

        <br/>
        <b><fmt:message key="previousBookings"/></b>
        <table class="table table-striped">
            <tr>
                <th><fmt:message key="startDate"/> </th>
                <th><fmt:message key="endDate"/>   </th>
            </tr>
            <c:forEach var="booking" items="${booking.pet.bookings}">
                <c:if test="${!booking['new']}">
                    <tr>
                        <td><petclinic:localDate date="${booking.startDate}" pattern="yyyy/MM/dd"/></td>
                        <td><petclinic:localDate date="${booking.endDate}" pattern="yyyy/MM/dd"/></td>
                    </tr>
                </c:if>
            </c:forEach>
        </table>
    </jsp:body>

</petclinic:layout>
