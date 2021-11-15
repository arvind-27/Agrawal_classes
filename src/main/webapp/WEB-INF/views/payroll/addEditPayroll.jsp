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
                method="post" modelAttribute="payroll">
                <tr>
                    <th style="width: 40%;">
                        <h4>${submessage2}</h4>
                    </th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%; text-align: right;">
                        <a href="#" onclick="window.location.reload();">Reset <i class="fa fa-refresh"
                                aria-hidden="true"></i></a>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Payment Ref No ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <c:choose>
                            <c:when test="${edit == true}">
                                ${payroll.paymentRefNo}
                            </c:when>
                            <c:otherwise>
                                <form:input type="text" path="paymentRefNo" class="form-control" required="true"></form:input>
                                <form:errors path="paymentRefNo" style="color: red;"></form:errors>
                            </c:otherwise>
                        </c:choose>
                    <td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Employee ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:select class="form-control" path="employee.employeeId" required="true">
                            <c:forEach var="employee" items="${employees}">
                                <form:option value="${employee.employeeId}">
                                    <c:if test="${employee.user.role == 'ROLE_TEACHER'}">ET${employee.employeeId}</c:if>
                                    <c:if test="${employee.user.role == 'ROLE_STAFF'}">ES${employee.employeeId}</c:if>
                                    - ${employee.user.firstName} ${employee.user.middleName} ${employee.user.lastName}
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Month ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="number" path="month" class="form-control" required="true"></form:input>
                        <form:errors path="month" style="color: red;"></form:errors>
                    <td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Year ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="number" path="year" class="form-control" required="true"></form:input>
                        <form:errors path="year" style="color: red;"></form:errors>
                    <td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Salary Credited ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="number" path="salaryCredited" class="form-control" required="true" step=".01"></form:input>
                        <form:errors path="salaryCredited" style="color: red;"></form:errors>
                    <td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Date Credited ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="date" path="dateCredited" class="form-control" required="true" max='${todayFormatted}'></form:input>
                        <form:errors path="dateCredited" style="color: red;"></form:errors>
                    <td>
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
