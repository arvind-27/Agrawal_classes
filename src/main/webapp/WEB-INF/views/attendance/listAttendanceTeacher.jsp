<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="container-fluid custom-container">
    <div class="div text-right mt-2">
        <a class="btn btn-primary" href="/${role}/mark-attendance/add" role="button" >Add Attendance</a>
    </div>
    <div class="table-responsive mt-2">
        <table class="table table-hover mt-4 table-sort">
            <thead>
                <tr>
                    <th>Employee</th>
                    <th>Date</th>
                    <th>Status</th>
                    <th>Remarks</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${attendances}" var="attendance">
                <tr>
                    <td>ET${attendance.employee.employeeId} - ${attendance.employee.user.name}</td>
                    <td><fmt:formatDate pattern="dd-MM-yyyy" value="${attendance.date}" /></td>
                    <td>
                        <c:if test="${attendance.isPresent == true}">Present</c:if>
                        <c:if test="${attendance.isPresent == false}"><span style="color: red;">Absent</span></c:if>
                    </td>
                    <td>${attendance.remarks}</td>
                    <td>
                        <a class="btn btn-outline-primary btn-sm" href='/${role}/mark-attendance/${date}/ET${attendance.employee.employeeId}/edit' role="button">Edit</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
