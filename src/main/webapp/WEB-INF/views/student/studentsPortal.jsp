<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="container-fluid custom-container">
    <div class="div text-right">
        <a class="btn btn-primary" href="/${role}/students/add" role="button" >Add Student</a>
    </div>
    <div class="table-responsive mt-2">
        <table class="table table-hover mt-4 table-sort">
            <thead>
                <tr>
                    <th>Student ID</th>
                    <th>Name</th>
                    <th>Gender</th>
                    <th>Date of Birth</th>
                    <th>Username</th>
                    <th>Email Address</th>
                    <th>Date Created</th>
                    <th>Activated?</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${students}" var="student">
                <tr>
                    <td><a href="/${role}/students/ST${student.studentId}">ST${student.studentId}</a></td>
                    <td>${student.user.firstName} ${student.user.middleName} ${student.user.lastName}</td>
                    <td>${student.gender}</td>
                    <td>
                        <fmt:formatDate pattern="dd-MM-yyyy" value="${student.dateOfBirth}" />
                    </td>
                    <td>${student.user.username}</td>
                    <td>${student.user.emailAddress}</td>
                    <td>
                        <fmt:formatDate pattern="dd-MM-yyyy" value="${student.user.dateCreated}" />
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${student.user.isActive == true}">Yes</c:when>
                            <c:otherwise><a class="btn btn-outline-success btn-sm"
                                    onclick="getRequest('/${role}/users/${student.user.userId}/activate')" role="button">Activate</a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a class="btn btn-outline-primary btn-sm" href="/${role}/students/ST${student.studentId}/edit-student"
                            role="button">Edit</a>
                        <a class="btn btn-outline-danger btn-sm" onclick="getRequestWithConfirmation('/${role}/users/${student.user.userId}/delete',
                        'Do you want to delete this User? \nWarning! This action is destructible!')"
                            role="button">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
