<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="container">
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
        <div class="feature-card p-lg-4 p-4 mx-4" onclick="location.href='/user/forgot-password'">
            <span class="fa fa-key fa-3x" aria-hidden="true"></span>
            <h3 class="my-3">FORGOT PASSWORD</h3>
            <p>Reset your password.</p>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
