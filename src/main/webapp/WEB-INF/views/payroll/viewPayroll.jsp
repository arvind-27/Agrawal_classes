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
        <table class="table table-borderless mt-4">
            <tr>
                <th style="width: 40%;"></th>
                <th style="width: 10%;"></th>
                <td style="width: 50%; text-align: right;">
                    <a class="btn btn-primary" href="/${role}/payroll/${payroll.paymentRefNo}/edit"
                        role="button">Edit Payroll</a>
                </td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Payment Ref No</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${payroll.paymentRefNo}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Employee</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">
                    <c:if test="${payroll.employee.user.role == 'ROLE_TEACHER'}">
                        <a href="/${role}/teachers/ET${payroll.employee.employeeId}">
                            ET${payroll.employee.employeeId} - ${payroll.employee.user.firstName} ${payroll.employee.user.middleName}
                            ${payroll.employee.user.lastName}
                        </a>
                    </c:if>
                    <c:if test="${payroll.employee.user.role == 'ROLE_STAFF'}">
                        <a href="/${role}/staffs/ES${payroll.employee.employeeId}">
                            ES${payroll.employee.employeeId} - ${payroll.employee.user.firstName} ${payroll.employee.user.middleName}
                            ${payroll.employee.user.lastName}
                        </a>
                    </c:if>
                </td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Month</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">ET${payroll.month}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Year</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${payroll.year}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Salary credited</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${payroll.salaryCredited}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Date credited</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%"><fmt:formatDate pattern="dd-MM-yyyy" value="${payroll.dateCredited}" /></td>
            </tr>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
