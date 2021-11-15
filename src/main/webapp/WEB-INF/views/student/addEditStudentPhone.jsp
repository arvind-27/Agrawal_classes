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
            <tr>
                <th style="width: 60%;">
                    <h4>Step 3: Student Phone Number</h4>
                </th>
                <td style="width: 40%; text-align: right;">
                    <a href="#" onclick="window.location.reload();">Reset <i class="fa fa-refresh" aria-hidden="true"></i></a>
                </td>
            </tr>
        </table>
        <div class="container">

            <div class="row">
                <div class="col-sm-5" style="text-align: center;">
                    Phone Numbers:
                </div>
                <div class="col-sm-7">
                    <c:forEach var="phoneNumber" items="${studentPhoneNumbers}">
                        <div>${phoneNumber.phoneNumber} &emsp;
                            <a href="#" onclick="postRequest('/profile/users/${userId}/phoneNumber/delete',
                            {'phoneNumber': '${phoneNumber.phoneNumber}'})"> Remove
                            </a>
                        </div>
                    </c:forEach>

                    <input type="text" id="studentPhoneNumber">
                    <a class="btn btn-outline-danger btn-sm" onclick="postRequest('/profile/users/${userId}/phoneNumber/add',
                    {'phoneNumber': $('#studentPhoneNumber').val()})" role="button">Add</a>
                </div>
            </div>
        </div>
        <table class="table table-borderless mt-4">
            <tr>
                <th style="width: 60%;">
                    <h4>Step 4: Guardian Phone Number</h4>
                </th>
                <td style="width: 40%; text-align: right;">
                    <a href="#" onclick="window.location.reload();">Reset <i class="fa fa-refresh" aria-hidden="true"></i></a>
                </td>
            </tr>
        </table>
        <div class="container">

            <div class="row">
                <div class="col-sm-5" style="text-align: center;">
                    Phone Numbers:
                </div>
                <div class="col-sm-7">
                    <c:forEach var="phoneNumber" items="${guardianPhoneNumbers}">
                        <div>${phoneNumber.phoneNumber} &emsp;
                            <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')">
                                <a href="#" onclick="postRequest('/${role}/students/ST${studentId}/delete-guardian-phone',
                                            {'phoneNumber': '${phoneNumber.phoneNumber}'})"> Remove</a>
                            </sec:authorize>
                            <c:if test="${role == 'student'}">
                                <a href="#" onclick="postRequest('/profile/student/delete-guardian-phone',
                                            {'phoneNumber': '${phoneNumber.phoneNumber}'})"> Remove</a>
                            </c:if>
                        </div>
                    </c:forEach>

                    <input type="text" id="guardianPhoneNumber">
                    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')">
                        <a class="btn btn-outline-danger btn-sm" onclick="postRequest('/${role}/students/ST${studentId}/add-guardian-phone',
                                                                        {'phoneNumber': $('#guardianPhoneNumber').val()})"
                            role="button">Add</a>
                    </sec:authorize>
                    <c:if test="${role == 'student'}">
                        <a class="btn btn-outline-danger btn-sm" onclick="postRequest('/profile/student/add-guardian-phone',
                                                                        {'phoneNumber': $('#guardianPhoneNumber').val()})"
                            role="button">Add</a>
                    </c:if>

                    <div id="error" style="color: red;"></div>
                </div>
            </div>
            <div class="row mt-4 mb-4">
                <div class="col-sm-7 offset-sm-5">
                    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')">
                        <a class="btn btn-primary" type="button" href="/${role}/students/ST${studentId}">${buttonmessage}</a>
                    </sec:authorize>
                    <c:if test="${role == 'student'}">
                        <a class="btn btn-primary" type="button" href="/profile/student">${buttonmessage}</a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
