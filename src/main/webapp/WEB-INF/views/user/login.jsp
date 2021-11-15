<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="login-form section text-center py-5">
    <div class="container">
        <div class="row">
            <div class="col-sm-6 offset-sm-3">
                <div class="mt-5">
                    <div class="card card-info">
                        <div class="card-header" style="color: #fff; background-color:#333;">
                            LOGIN
                        </div>
                        <div class="card-body">
                            <form class="form-horizontal" action="/user/login" method="post">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <c:if test="${not empty successmessage}">
                                            <div class="alert alert-success alert-dismissible">
                                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                                ${successmessage}
                                            </div>
                                        </c:if>
                                        <c:if test="${not empty errormessage}">
                                            <div class="alert alert-danger alert-dismissible">
                                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                                ${errormessage}
                                            </div>
                                        </c:if>
                                        <div class="input-group mt-2">
                                            <span class="input-group-addon" style="color: #fff; background-color: #333;">
                                                <i class="fa fa-user" style="margin: 10px 15px;"></i>
                                            </span>
                                            <input id="username" type="text" class="form-control" name="username" value=""
                                                placeholder="Username" autofocus="true" required>
                                        </div>

                                        <div class="input-group mt-4">
                                            <span class="input-group-addon" style="color: #fff; background-color:#333;">
                                                <i class="fa fa-lock" style="margin: 10px 15px;"></i>
                                            </span>
                                            <input id="password" type="password" class="form-control" name="password"
                                                placeholder="Password" required>
                                        </div>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                                        <div class="input-group mt-4">
                                            <button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-md-6 control">
                                            <div style="padding-top:15px;">
                                                Don't have an account! <br><a href="/user/register"> Register Here </a>
                                            </div>
                                        </div>
                                        <div class="col-md-6 control">
                                            <div style="padding-top:15px;">
                                                Forgot password! <br><a href="/user/forgot-password"> Click Here </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
