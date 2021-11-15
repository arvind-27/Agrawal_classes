<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="container" style="padding-left: 5%; padding-right: 5%;">
    <div class="row justify-content-center">
        <h2>${submessage1}</h2>
    </div>
    <div class="row mb-3">
        <div class="col-md-12 text-right">
            <h2>
                <a class="btn btn-outline-success" href="/${role}/academics/batches" role="button">All Batches</a>
            </h2>
        </div>
    </div>
    <div class="row shadow bg-white rounded" style="border: 1px solid whitesmoke; padding: 0 40px;">
        <table class="table table-borderless mt-4">
            <tr>
                <th style="width: 40%;"></th>
                <th style="width: 10%;"></th>
                <td style="width: 50%; text-align: right;">
                    <sec:authorize access='hasAnyRole("ROLE_ADMIN", "ROLE_STUDENT")'>
                    <a class="btn btn-success" href="/${role}/academics/courses/${batch.course.courseId}/${batch.batchId}/enrollments/add"
                        role="button">Enroll</a>
                    </sec:authorize>
                    <sec:authorize access='hasRole("ROLE_ADMIN")'>
                    <a class="btn btn-primary" href="/${role}/academics/courses/${batch.course.courseId}/${batch.batchId}/enrollments"
                        role="button">View Students</a>
                    <a class="btn btn-primary" href="/${role}/academics/courses/${batch.course.courseId}/${batch.batchId}/edit"
                        role="button">Edit Batch</a>
                    </sec:authorize>
                    </td>
                </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Batch ID</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${batch.batchId}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Batch Name</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${batch.batchName}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Course</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${batch.course.courseName} - ${batch.course.courseId}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Fee</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${batch.fee}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Room Number</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${batch.roomNumber}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Start Time</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%"><fmt:formatDate pattern="HH:mm:ss" value="${batch.startTime}" /></td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">End Time</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%"><fmt:formatDate pattern="HH:mm:ss" value="${batch.endTime}" /></td>
            </tr>
        </table>
        <div class="col-12" style="text-align: center;">
            <hr>
            <h5>Staffs</h5>
        </div>
        <sec:authorize access='hasRole("ROLE_ADMIN")'>
        <div class="col-12" style="text-align: right;">
            <a class="btn btn-primary" href="/${role}/academics/courses/${batch.course.courseId}/${batch.batchId}/add-staff"
                role="button">Add / Edit Staff</a>
        </div>
        </sec:authorize>
        <div class="col-12 mt-2">
        <table class="table table-hover mt-4 table-sort">
            <thead>
                <tr>
                    <th>Employee ID</th>
                    <th>Name</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${staffs}" var="staff">
                <tr>
                    <td><a href="/${role}/staffs/ES${staff.employee.employeeId}">ES${staff.employee.employeeId}</a></td>
                    <td>${staff.employee.user.firstName} ${staff.employee.user.middleName} ${staff.employee.user.lastName}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </div>
        <div class="col-12" style="text-align: center;">
            <hr>
            <h5>Teachers</h5>
        </div>
        <sec:authorize access='hasRole("ROLE_ADMIN")'>
        <div class="col-12" style="text-align: right;">
            <a class="btn btn-primary" href="/${role}/academics/courses/${batch.course.courseId}/${batch.batchId}/add-teacher"
                role="button">Add / Edit Teacher</a>
        </div>
        </sec:authorize>
        <div class="col-12 mt-2">
        <table class="table table-hover mt-4 table-sort">
            <thead>
                <tr>
                    <th>Employee ID</th>
                    <th>Name</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${teachers}" var="teacher">
                <tr>
                    <td><a href="/${role}/teachers/ET${teacher.employee.employeeId}">ET${teacher.employee.employeeId}</a></td>
                    <td>${teacher.employee.user.firstName} ${teacher.employee.user.middleName} ${teacher.employee.user.lastName}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
