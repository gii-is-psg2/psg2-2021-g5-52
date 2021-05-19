<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="home">
    <h2 class="welcomeText"><fmt:message key="welcome"/></h2>
    <div class="row">
        <div class="col-md-12">
            <spring:url value="/resources/images/panchorecortado.jpg" htmlEscape="true" var="petsImage"/>
            <img class="img-responsive welcomeImg"  width="500" height="500" alt="pets" src="${petsImage}"/>
            <h2 class=textoWelcome>Sobre nosotros</h2>
            <p>Petclinic es una aplicación web de una clínica para mascotas situada en la avenida Reina Mercedes(Sevilla). Nuestra web cuenta con funcionalidades muy interesantes para aquellos que tienen una mascota
            o tienen pensado tener una en un futuro. La funcionalidades con las que cuenta nuestra web son:<br/><br/></p>
            <p class=textoWelcome>
            -Poner una mascota en adopción.<br/>
            -Reservar para un hotel para tu mascota.<br/>
            -Adoptar una mascota.<br/><br/>
            </p>
            <p>Petclinic cuenta con los mejores trabajadores y veterinarios de todo el país para que disfrutes de la tranquilidad de saber que estás dejando a tu mascota en buenas manos. </p>
          
      
        </div>
    </div>
</petclinic:layout>