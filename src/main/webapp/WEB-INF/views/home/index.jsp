<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="container marketing">

    <div class="container">
        <c:if test="${empty pageContext.request.userPrincipal}">
            <div class="row my-lg-5 justify-content-center">
                <div class="feature-card p-lg-4 p-4 mx-4" onclick="location.href='/subjects'">
                    <span class="fa fa-book fa-3x" aria-hidden="true"></span>
                    <h3 class="my-3">SUBJECTS</h3>
                    <p>View all the subjects we teach</p>
                </div>
                <div class="feature-card p-lg-4 p-4 mx-4" onclick="location.href='/courses'">
                    <span class="fa fa-book-open fa-3x" aria-hidden="true"></span>
                    <h3 class="my-3">COURSES</h3>
                    <p>View all the courses.</p>
                </div>
            </div>
            <div class="row my-lg-5 justify-content-center">
                <div class="feature-card p-lg-4 p-4 mx-4" onclick="location.href='/user/login'">
                    <span class="fa fa-sign-in fa-3x" aria-hidden="true"></span>
                    <h3 class="my-3">LOGIN</h3>
                    <p>Login to your account.</p>
                </div>
                <div class="feature-card p-lg-4 p-4 mx-4" onclick="location.href='/user/register'">
                    <span class="fa fa-user-plus fa-3x" aria-hidden="true"></span>
                    <h3 class="my-3">REGISTER</h3>
                    <p>Create your account.</p>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty pageContext.request.userPrincipal}">
            <div class="row my-lg-5 justify-content-center">
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF', 'ROLE_STUDENT', 'ROLE_TEACHER')">
                    <div class="feature-card p-lg-4 p-4 mx-4" onclick="location.href='/${role}'">
                        <span class="fa fa-id-card fa-3x" aria-hidden="true"></span>
                        <h3 class="my-3">${fn:toUpperCase(role)} DASHBOARD</h3>
                        <p>Go to your dashboard</p>
                    </div>
                    <div class="feature-card p-lg-4 p-4 mx-4" onclick="location.href='/${role}/academics'">
                        <span class="fa fa-chalkboard-teacher fa-3x" aria-hidden="true"></span>
                        <h3 class="my-3">ACADEMIC PORTAL</h3>
                        <p>Manage all academic stuff.</p>
                    </div>
                </sec:authorize>
                <div class="feature-card p-lg-4 p-4 mx-4" onclick="location.href='/profile'">
                    <span class="fa fa-id-badge fa-3x" aria-hidden="true"></span>
                    <h3 class="my-3">PROFILE</h3>
                    <p>View or Edit your profile.</p>
                </div>
            </div>
            <c:if test="${role == 'admin'}"></c:if>
            <c:if test="${role == 'staff'}"></c:if>
            <c:if test="${role == 'teacher'}"></c:if>
            <c:if test="${role == 'student'}"></c:if>
        </c:if>
    </div>
</div>


<%@ include file="/WEB-INF/views/template/footer.jsp" %>
