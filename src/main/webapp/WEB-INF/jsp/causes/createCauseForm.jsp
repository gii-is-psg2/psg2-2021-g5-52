<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<petclinic:layout pageName="causes">
    <h2>
        <fmt:message key="newCause"/>
    </h2>
    <form:form modelAttribute="cause" class="form-horizontal" id="add-causes-form">
        <div class="form-group has-feedback">
            <petclinic:inputFieldInt  name="name"/>
            <petclinic:inputFieldInt  name="description"/>
            <petclinic:inputFieldInt name="budgetTarget"/>
            <petclinic:inputFieldInt  name="organization"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit"><fmt:message key="newCauses"/></button>
            </div>
        </div>
    </form:form>
</petclinic:layout>