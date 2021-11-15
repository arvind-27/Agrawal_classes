<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="container" style="padding-left: 5%; padding-right: 5%;">
    <div class="row justify-content-center mb-3">
        <h2>${submessage1}</h2>
    </div>
    <div class="row shadow bg-white rounded" style="border: 1px solid whitesmoke; padding: 0 40px;">
        <table class="table table-borderless mt-4">
            <form:form class="form-horizontal" action="${submiturl}"
                method="post" modelAttribute="student">
                <tr>
                    <th style="width: 40%;">
                        <h4>Step 1: Student Details</h4>
                    </th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%; text-align: right;">
                        <a href="#" onclick="window.location.reload();">Reset <i class="fa fa-refresh"
                                aria-hidden="true"></i></a>
                    </td>
                </tr>
                <c:if test="${edit == true}">
                <tr>
                    <th style="width: 40%; text-align: center;">Student ID</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">ST${student.studentId}</td>
                </tr>
                </c:if>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')">
                <tr>
                    <th style="width: 40%; text-align: center;">Name ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="user.firstName" class="form-control" required="true" placeholder="First Name"></form:input>
                        <form:errors path="user.firstName" style="color: red;"></form:errors>
                        <form:input type="text" path="user.middleName" class="form-control" placeholder="Middle Name"></form:input>
                        <form:errors path="user.middleName" style="color: red;"></form:errors>
                        <form:input type="text" path="user.lastName" class="form-control" placeholder="Last Name"></form:input>
                        <form:errors path="user.lastName" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Username ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="user.username" class="form-control" required="true"></form:input>
                        <form:errors path="user.username" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Email Address ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="email" path="user.emailAddress" class="form-control" required="true"></form:input>
                        <form:errors path="user.emailAddress" style="color: red;"></form:errors>
                    </td>
                </tr>
                </sec:authorize>
                <tr>
                    <th style="width: 40%; text-align: center;">Gender ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:select class="form-control" path="gender" required="true">
                            <form:option value="Male">Male</form:option>
                            <form:option value="Female">Female</form:option>
                            <form:option value="Not Specified">Not Specified</form:option>
                        </form:select>
                        <form:errors path="gender" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Date of Birth ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="date" path="dateOfBirth" class="form-control" required="true" max="2015-01-01" min="1995-01-01"></form:input>
                        <form:errors path="dateOfBirth" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Address ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="houseNumber" class="form-control" placeholder="House Number"></form:input>
                        <form:errors path="houseNumber" style="color: red;"></form:errors>
                        <form:input type="text" path="street" class="form-control" required="true" placeholder="Street"></form:input>
                        <form:errors path="street" style="color: red;"></form:errors>
                        <form:input type="text" path="city" class="form-control" required="true" placeholder="City"></form:input>
                        <form:errors path="city" style="color: red;"></form:errors>
                        <form:input type="text" path="state" class="form-control" required="true" placeholder="State"></form:input>
                        <form:errors path="state" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">School Attending ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="schoolAttending" class="form-control" required="true"></form:input>
                        <form:errors path="schoolAttending" style="color: red;"></form:errors>
                    </td>
                </tr>
                <c:if test="${edit == true}">
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')">
                <tr>
                    <th style="width: 40%; text-align: center;">Activated? ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:select class="form-control" path="user.isActive" required="true">
                            <form:option value="1">Yes</form:option>
                            <form:option value="0">No</form:option>
                        </form:select>
                        <form:errors path="user.isActive" style="color: red;"></form:errors>
                    </td>
                </tr>
                </sec:authorize>
                </c:if>
                <tr>
                    <th style="width: 40%;">
                        <h4>Step 2: Guardian Details</h4>
                    </th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%; text-align: right;"></td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Name ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="guardian.name" class="form-control" required="true"></form:input>
                        <form:errors path="guardian.name" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Occupation</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="guardian.occupation" class="form-control"></form:input>
                        <form:errors path="guardian.occupation" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Address ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="guardian.address" class="form-control" required="true"></form:input>
                        <form:errors path="guardian.address" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Email Address ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="email" path="guardian.email" class="form-control" required="true"></form:input>
                        <form:errors path="guardian.email" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Relation with student ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:select class="form-control" path="guardian.relationWithStudent" required="true">
                            <form:option value="Father">Father</form:option>
                            <form:option value="Mother">Mother</form:option>
                            <form:option value="Other">Other</form:option>
                        </form:select>
                        <form:errors path="guardian.relationWithStudent" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;"></th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <button class="btn btn-primary" type="submit">${buttonmessage}</button>
                    </td>
                </tr>
            </form:form>

        </table>
    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
