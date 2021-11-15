<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="login-form section text-center py-5">
    <div class="container">
        <div class="row">
            <div class="col-sm-8 offset-sm-2">
                <div class="mt-5">
                    <div class="card card-info">
                        <div class="card-header" style="color: #fff; background-color:#333;">
                            REGISTER
                        </div>
                        <div class="card-body">
                            <form:form class="form-horizontal" action="/user/register" method="post" modelAttribute="user">
                                <div class="form-group row">
                                    <label class="col-3 control-label">Username: ${mandatory}</label>
                                    <div class="col-9">
                                        <form:input type="text" path="username" class="form-control" placeholder="Enter the username" autofocus="true" required="true"></form:input>
                                        <div class="ml-2" style="text-align: left;">
                                            <form:errors path="username" style="color: red;"></form:errors>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-3 control-label">Password: ${mandatory}</label>
                                    <div class="col-9">
                                        <form:input type="password" path="password" class="form-control" placeholder="Enter the password" required="true"></form:input>
                                        <span toggle="#password-field" class="fa fa-lg fa-eye toggle-password mr-2"
                                            style="float: right; margin-top: -25px; cursor: pointer;" onclick="passwordToggleClick()"></span>
                                        <div class="ml-2" style="text-align: left;">
                                            <form:errors path="password" style="color: red;"></form:errors>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-3 control-label">Name: ${mandatory}</label>
                                    <div class="col-3">
                                        <form:input type="text" path="firstName" class="form-control" placeholder="First name" required="true"></form:input>
                                    </div>
                                    <div class="col-3">
                                        <form:input type="text" path="middleName" class="form-control" placeholder="Middle name"></form:input>
                                    </div>
                                    <div class="col-3">
                                        <form:input type="text" path="lastName" class="form-control" placeholder="Last name"></form:input>
                                    </div>
                                    <div class="col-9 offset-3 ml-2">
                                        <form:errors path="firstName" style="color: red;"></form:errors>
                                        <form:errors path="middleName" style="color: red;"></form:errors>
                                        <form:errors path="lastName" style="color: red;"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-3 control-label">Email Address: ${mandatory}</label>
                                    <div class="col-9">
                                        <form:input type="email" path="emailAddress" class="form-control" placeholder="Enter the email address" required="true"></form:input>
                                        <div class="ml-2" style="text-align: left;">
                                            <form:errors path="emailAddress" style="color: red;"></form:errors>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-3 control-label">Registering as: ${mandatory}</label>
                                    <div class="col-9">
                                        <form:select class="form-control" path="role" required="true">
                                            <form:option value="ROLE_STUDENT">Student</form:option>
                                            <form:option value="ROLE_STAFF">Staff</form:option>
                                            <form:option value="ROLE_TEACHER">Teacher</form:option>
                                        </form:select>
                                        <form:errors path="role" style="color: red;"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group mt-2">
                                        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
                                    </div>
                                </div>
                            </form:form>
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-md-6 control">
                                        <div>
                                            Already Registered! <a href="/user/login">Login Here </a>
                                        </div>
                                    </div>
                                    <div class="col-md-6 control">
                                        <div>
                                            Forgot password! <a href="/user/forgot-password"> Click Here </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
