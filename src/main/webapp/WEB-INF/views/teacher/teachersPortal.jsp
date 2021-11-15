<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="container-fluid custom-container">
    <div class="div text-right">
        <a class="btn btn-primary" href="/${role}/teachers/add" role="button" >Add Teacher</a>
    </div>
    <div class="table-responsive mt-2">
        <table class="table table-hover mt-4 table-sort">
            <thead>
                <tr>
                    <th>Employee ID</th>
                    <th>Name</th>
                    <th>Gender</th>
                    <th>Subject</th>
                    <th>Username</th>
                    <th>Email Address</th>
                    <th>Date Created</th>
                    <th>Activated?</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${teachers}" var="teacher">
                <tr>
                    <td><a href="/${role}/teachers/ET${teacher.employee.employeeId}">ET${teacher.employee.employeeId}</a></td>
                    <td>${teacher.employee.user.firstName} ${teacher.employee.user.middleName} ${teacher.employee.user.lastName}</td>
                    <td>${teacher.gender}</td>
                    <td>${teacher.subject.subjectName}</td>
                    <td>${teacher.employee.user.username}</td>
                    <td>${teacher.employee.user.emailAddress}</td>
                    <td>
                        <fmt:formatDate pattern="dd-MM-yyyy" value="${teacher.employee.user.dateCreated}" />
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${teacher.employee.user.isActive == true}">Yes</c:when>
                            <c:otherwise><a class="btn btn-outline-success btn-sm"
                                    onclick="getRequest('/${role}/users/${teacher.employee.user.userId}/activate')" role="button">Activate</a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a class="btn btn-outline-primary btn-sm" href="/${role}/teachers/ET${teacher.employee.employeeId}/edit-teacher"
                            role="button">Edit</a>
                        <a class="btn btn-outline-danger btn-sm" onclick="getRequestWithConfirmation('/${role}/users/${teacher.employee.user.userId}/delete',
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
