<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="petHotel">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#date").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
        <script>
            $(function () {
                $("#startdate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
        <script>
            $(function () {
                $("#enddate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2><c:if test="${petHotel['new']}">New </c:if>Visit</h2>

        <b>Pet</b>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Birth Date</th>
                <th>Type</th>
                <th>Owner</th>
            </tr>
            </thead>
            <tr>
                <td><c:out value="${petHotel.pet.name}"/></td>
                <td><petclinic:localDate date="${visit.pet.birthDate}" pattern="yyyy/MM/dd"/></td>
                <td><c:out value="${petHotel.pet.type.name}"/></td>
                <td><c:out value="${petHotel.pet.owner.firstName} ${visit.pet.owner.lastName}"/></td>
            </tr>
        </table>

        <form:form modelAttribute="petHotel" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="StartDate" name="startdate"/>
                <petclinic:inputField label="EndDate" name="enddate"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId" value="${petHotel.pet.id}"/>
                    <button class="btn btn-default" type="submit">Add Booking</button>
                </div>
            </div>
        </form:form>

        <br/>
        <b>Previous Visits</b>
        <table class="table table-striped">
            <tr>
                <th>Start Date</th>
                <th>End Date</th>
            </tr>
            <c:forEach var="petHotel" items="${petHotel.pet.visits}">
                <c:if test="${!petHotel['new']}">
                    <tr>
                        <td><petclinic:localDate date="${petHotel.startDate}" pattern="yyyy/MM/dd"/></td>
                        <td><c:out value="${petHotel.endDate}" pattern="yyyy/MM/dd"/></td>
                    </tr>
                </c:if>
            </c:forEach>
        </table>
    </jsp:body>

</petclinic:layout>
