<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="container" style="padding-left: 5%; padding-right: 5%;">
    <div class="row justify-content-center mb-3">
        <h2>${submessage1}</h2>
    </div>
    <div class="row shadow bg-white rounded" style="border: 1px solid whitesmoke; padding: 0 40px;">
        <c:if test="${not empty successmessage}">
            <div class="alert alert-success alert-dismissible mt-4" style="width: 100%; text-align: center;">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                ${successmessage}
            </div>
        </c:if>
        <table class="table table-borderless mt-4">
            <tr>
                <th style="width: 40%;"></th>
                <th style="width: 10%;"></th>
                <td style="width: 50%; text-align: right;">
                    <c:if test="${role == 'admin' || role == 'staff'}">
                        <a class="btn btn-primary" href="/${role}/students/ST${student.studentId}/edit-student" role="button">Edit Student</a>
                    </c:if>
                    <c:if test="${role == 'student'}">
                        <a class="btn btn-outline-success" href="/profile/change-password" role="button">Change Password</a>
                        <a class="btn btn-primary" href="/profile/student/edit-student" role="button">Edit Student</a>
                    </c:if>
                </td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Student ID</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">ST${student.studentId}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Name</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${student.user.firstName} ${student.user.middleName} ${student.user.lastName}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Username</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${student.user.username}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Email Address</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${student.user.emailAddress}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Gender</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${student.gender}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Date of Birth</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%"><fmt:formatDate pattern="dd-MM-yyyy" value="${student.dateOfBirth}" /></td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Address</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${student.houseNumber}, ${student.street}, ${student.city}, ${student.state}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">School Attending</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${student.schoolAttending}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Activated?</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">
                    <c:if test="${student.user.isActive == true}"><span style="color: blue;">Yes</span></c:if>
                    <c:if test="${student.user.isActive == false}"><span style="color: red;">No</span></c:if>
                </td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Date Created</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%"><fmt:formatDate pattern="dd-MM-yyyy" value="${student.user.dateCreated}" /></td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Last Login</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">
                    <fmt:formatDate pattern="dd-MM-yyyy" value="${student.user.lastLoginDate}" />
                    <fmt:formatDate pattern="HH:mm:ss" value="${student.user.lastLoginTime}" />
                </td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Phone Numbers</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%"><c:forEach var="userPhoneNumber" items="${userPhoneNumbers}">
                    <div>${userPhoneNumber.phoneNumber}</div>
                </c:forEach></td>
            </tr>
        </table>
        <div class="col-12" style="text-align: center;">
            <hr>
            <h5>Guardian Details</h5>
        </div>
        <table class="table table-borderless mt-4">
            <tr>
                <th style="width: 40%; text-align: center;">Name</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${student.guardian.name}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Occupation</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${student.guardian.occupation}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Address</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${student.guardian.address}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Email Address</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${student.guardian.email}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Relation With Student</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${student.guardian.relationWithStudent}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Phone Numbers</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">
                    <c:forEach var="guardianPhoneNumber" items="${guardianPhoneNumbers}">
                        <div>${guardianPhoneNumber.phoneNumber}</div>
                    </c:forEach>
                </td>
            </tr>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
