<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="container-fluid custom-container">
    <div class="div text-right">
        <input type="date" id="date">
        <button class="btn btn-outline-success btn-sm ml-5" onclick="location.href='/${role}/attendance?date=' + $('#date').val()">Filter</a>
    </div>
    <div class="div text-right mt-2">
        <a class="btn btn-primary" href="/${role}/attendance/add" role="button">Add Attendance</a>
    </div>
    <div class="table-responsive mt-2">
        <table class="table table-hover mt-4 table-sort">
            <thead>
                <tr>
                    <th>Employee</th>
                    <th>Days Present</th>
                    <th>Days Absent</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${employees}" var="employee" varStatus="loop">
                <tr>
                    <td>
                        <c:if test="${employee.user.role == 'ROLE_TEACHER'}">
                            <a href="/${role}/teachers/ET${employee.employeeId}">ET${employee.employeeId} -
                                ${employee.user.name}</a>
                        </c:if>
                        <c:if test="${employee.user.role == 'ROLE_STAFF'}">
                            <a href="/${role}/staffs/ES${employee.employeeId}">ES${employee.employeeId} -
                                ${employee.user.name}</a>
                        </c:if>
                    </td>
                    <td>${daysPresent[loop.count-1].count}</td>
                    <td style="color: red;">${daysAbsent[loop.count-1].count}</td>
                    <td>
                        <c:if test="${employee.user.role == 'ROLE_TEACHER'}">
                            <a class="btn btn-outline-success btn-sm"
                                href="/${role}/attendance/ET${employee.employeeId}" role="button">View All</a>
                        </c:if>
                        <c:if test="${employee.user.role == 'ROLE_STAFF'}">
                            <a class="btn btn-outline-success btn-sm"
                                href="/${role}/attendance/ES${employee.employeeId}" role="button">View All</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
    document.querySelector('#date').value = (new Date()).toISOString().substr(0, 10);
</script>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
