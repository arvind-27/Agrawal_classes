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
                method="post" modelAttribute="attendance">
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
                    <th style="width: 40%; text-align: center;">Date ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <c:choose>
                            <c:when test="${edit == true || role == 'staff'}">
                                <fmt:formatDate pattern="dd-MM-yyyy" value="${attendance.date}" />
                            </c:when>
                            <c:otherwise>
                                <form:input type="date" path="date" class="form-control" required="true" max="${todayFormatted}"></form:input>
                                <form:errors path="date" style="color: red;"></form:errors>
                            </c:otherwise>
                        </c:choose>
                    <td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Employee ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <c:choose>
                            <c:when test="${edit == true}">
                                <c:if test="${attendance.employee.user.role == 'ROLE_TEACHER'}">ET${attendance.employee.employeeId}</c:if>
                                <c:if test="${attendance.employee.user.role == 'ROLE_STAFF'}">ES${attendance.employee.employeeId}</c:if>
                                - ${attendance.employee.user.firstName} ${attendance.employee.user.middleName} ${attendance.employee.user.lastName}
                            </c:when>
                            <c:otherwise>
                                <form:select class="form-control" path="employee.employeeId" required="true">
                                    <c:forEach var="employee" items="${employees}">
                                        <form:option value="${employee.employeeId}">
                                            <c:if test="${employee.user.role == 'ROLE_TEACHER'}">ET${employee.employeeId}</c:if>
                                            <c:if test="${employee.user.role == 'ROLE_STAFF'}">ES${employee.employeeId}</c:if>
                                            - ${employee.user.firstName} ${employee.user.middleName} ${employee.user.lastName}
                                        </form:option>
                                    </c:forEach>
                                </form:select>
                                <form:errors path="employee.employeeId" style="color: red;"></form:errors>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Present? ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:select class="form-control" path="isPresent" required="true">
                            <form:option value="1">Yes</form:option>
                            <form:option value="0">No</form:option>
                        </form:select>
                        <form:errors path="isPresent" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Remarks</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="remarks" class="form-control"></form:input>
                        <form:errors path="remarks" style="color: red;"></form:errors>
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
