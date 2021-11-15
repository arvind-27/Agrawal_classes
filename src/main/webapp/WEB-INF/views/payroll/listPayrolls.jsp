<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="container-fluid custom-container">
    <sec:authorize access='hasRole("ROLE_ADMIN")'>
    <div class="div text-right">
    <c:choose>
        <c:when test="${empty param.employee}">
            <select id="employee">
                <c:forEach var="employee" items="${employees}">
                    <c:if test="${employee.user.role == 'ROLE_TEACHER'}">
                        <option value="ET${employee.employeeId}">
                            ET${employee.employeeId} - ${employee.user.name}
                        </option>
                    </c:if>
                    <c:if test="${employee.user.role == 'ROLE_STAFF'}">
                        <option value="ES${employee.employeeId}">
                            ES${employee.employeeId} - ${employee.user.name}
                        </option>
                    </c:if>
                </c:forEach>
            </select>
            <button class="btn btn-outline-success btn-sm ml-2" onclick="location.href='/${role}/payroll?employee=' + $('#employee').val()">Filter</a>
        </c:when>
        <c:otherwise>
            <a class="btn btn-primary" href="/${role}/payroll" role="button">View All Payroll</a>
        </c:otherwise>
    </c:choose>
    </div>
    <div class="div text-right mt-4">
        <a class="btn btn-primary" href="/${role}/payroll/add" role="button" >Add Payroll</a>
    </div>
    </sec:authorize>

    <div class="table-responsive mt-2">
        <table class="table table-hover mt-4 table-sort">
            <thead>
                <tr>
                    <th>Payment Ref No</th>
                    <th>Employee</th>
                    <th>Month</th>
                    <th>Year</th>
                    <th>Salary credited</th>
                    <th>Date credited</th>
                    <sec:authorize access='hasRole("ROLE_ADMIN")'><th>Action</th></sec:authorize>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${payrolls}" var="payroll">
                <tr>
                    <td>${payroll.paymentRefNo}</td>
                    <td>
                        <c:if test="${payroll.employee.user.role == 'ROLE_TEACHER'}">
                            ET${payroll.employee.employeeId} - ${payroll.employee.user.firstName} ${payroll.employee.user.middleName}
                            ${payroll.employee.user.lastName}
                        </c:if>
                        <c:if test="${payroll.employee.user.role == 'ROLE_STAFF'}">
                            ES${payroll.employee.employeeId} - ${payroll.employee.user.firstName} ${payroll.employee.user.middleName}
                            ${payroll.employee.user.lastName}
                        </c:if>
                    </td>
                    <td>${payroll.month}</td>
                    <td>${payroll.year}</td>
                    <td>${payroll.salaryCredited}</td>
                    <td><fmt:formatDate pattern="dd-MM-yyyy" value="${payroll.dateCredited}" /></td>
                    <sec:authorize access='hasRole("ROLE_ADMIN")'>
                    <td>
                        <a class="btn btn-outline-success btn-sm" href="/${role}/payroll/${payroll.paymentRefNo}"
                            role="button">View</a>
                        <a class="btn btn-outline-primary btn-sm" href="/${role}/payroll/${payroll.paymentRefNo}/edit"
                            role="button">Edit</a>
                        <a class="btn btn-outline-danger btn-sm" onclick="getRequestWithConfirmation('/${role}/payroll/${payroll.paymentRefNo}/delete',
                        'Do you want to delete this Payroll? \nWarning! This action is destructible!')"
                            role="button">Delete</a>
                    </td>
                    </sec:authorize>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
