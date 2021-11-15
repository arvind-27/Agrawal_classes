<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="container">
    <div class="row my-lg-5 justify-content-center">
        <div class="feature-card p-lg-4 p-4 mx-4" onclick="location.href='/${role}/academics/subjects'">
            <span class="fa fa-book fa-3x" aria-hidden="true"></span>
            <h3 class="my-3">SUBJECTS</h3>
            <p>View all the subjects we teach</p>
        </div>
        <div class="feature-card p-lg-4 p-4 mx-4" onclick="location.href='/${role}/academics/courses'">
            <span class="fa fa-book-open fa-3x" aria-hidden="true"></span>
            <h3 class="my-3">COURSES</h3>
            <p>View all the courses.</p>
        </div>
        <div class="feature-card p-lg-4 p-4 mx-4" onclick="location.href='/${role}/academics/batches'">
            <span class="fa fa-user-friends fa-3x" aria-hidden="true"></span>
            <h3 class="my-3">BATCHES</h3>
            <p>View all the batches.</p>
        </div>
    </div>
    <div class="row my-lg-5 justify-content-center">
        <sec:authorize access="hasAnyRole('ROLE_TEACHER', 'ROLE_STAFF')">
        <div class="feature-card p-lg-4 p-4 mx-4" onclick="location.href='/${role}/academics/batches-assigned'">
            <span class="fa fa-users-cog fa-3x" aria-hidden="true"></span>
            <h3 class="my-3">BATCHES ASSIGNED</h3>
            <p>View all the batches assigned to you</p>
        </div>
        </sec:authorize>
        <div class="feature-card p-lg-4 p-4 mx-4" onclick="location.href='/${role}/academics/tests'">
            <span class="fa fa-poll fa-3x" aria-hidden="true"></span>
            <h3 class="my-3">TESTS AND RESULTS PORTAL</h3>
            <p>View all the tests and their results.</p>
        </div>
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT')">
        <div class="feature-card p-lg-4 p-4 mx-4" onclick="location.href='/${role}/academics/enrollments'">
            <span class="fa fa-user-check fa-3x" aria-hidden="true"></span>
            <h3 class="my-3">ENROLLMENT PORTAL</h3>
            <p>View all the enrollments.</p>
        </div>
        </sec:authorize>
    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
