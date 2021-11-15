<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="container">
    <div class="row my-lg-5 justify-content-center">
        <div class="feature-card p-lg-4 p-4 mx-4" onclick="location.href='/${role}/academics'">
            <span class="fa fa-chalkboard-teacher fa-3x" aria-hidden="true"></span>
            <h3 class="my-3">ACADEMIC PORTAL</h3>
            <p>Manage all academic stuff.</p>
        </div>
        <div class="feature-card p-lg-4 p-4 mx-4" onclick="location.href='/${role}/complaints'">
            <span class="fa fa-comment-alt fa-3x" aria-hidden="true"></span>
            <h3 class="my-3">COMPLAINTS PORTAL</h3>
            <p>Lodge any complaint.</p>
        </div>
        <div class="feature-card p-lg-4 p-4 mx-4" onclick="location.href='/${role}/feedbacks'">
            <span class="fa fa-comments fa-3x" aria-hidden="true"></span>
            <h3 class="my-3">FEEDBACK PORTAL</h3>
            <p>Send a message or feedback to a teacher.</p>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
