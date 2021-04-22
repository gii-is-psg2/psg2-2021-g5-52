<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<petclinic:layout pageName="pets">
    <h2>
        <fmt:message key="newAdoption"/>
    </h2>
    <form:form modelAttribute="adoption" class="form-horizontal" id="add-adoptions-form">
        <div class="form-group has-feedback">
            <petclinic:selectField name="pets" label="Pets " names="${pets}" size="5" />
            <petclinic:inputFieldInt name="description"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit"><fmt:message key="addPet"/></button>
            </div>
        </div>
    </form:form>
</petclinic:layout>