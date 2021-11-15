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
                method="post" modelAttribute="enrollment">
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
                <c:if test="${edit == true}">
                    <tr>
                        <th style="width: 40%; text-align: center;">Enrollment ID</th>
                        <th style="width: 10%;"></th>
                        <td style="width: 50%">
                            ${enrollment.enrollmentId}
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <th style="width: 40%; text-align: center;">Student ID ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <c:choose>
                            <c:when test="${edit == true || role == 'student'}">
                                ST${enrollment.studentId}
                            </c:when>
                            <c:otherwise>
                                <form:select class="form-control" path="studentId" required="true">
                                    <c:forEach var="student" items="${students}">
                                        <form:option value="${student.studentId}">
                                            ST${student.studentId} - ${student.user.firstName} ${student.user.middleName}
                                            ${student.user.lastName}
                                        </form:option>
                                    </c:forEach>
                                </form:select>
                            </c:otherwise>
                        </c:choose>
                        <div><form:errors path="studentId" style="color: red;"></form:errors></div>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Course ID ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        ${enrollment.courseId}
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Batch ID ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        ${enrollment.batchId}
                    </td>
                </tr>
                <c:if test="${role != 'student'}">
                <tr>
                    <th style="width: 40%; text-align: center;">Join Date</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="date" path="joinDate" class="form-control"></form:input>
                        <form:errors path="joinDate" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">End Date</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="date" path="endDate" class="form-control"></form:input>
                        <form:errors path="endDate" style="color: red;"></form:errors>
                    </td>
                </tr>
                </c:if>
                <tr>
                    <th style="width: 40%; text-align: center;">Amount Payable ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <c:if test="${edit == 'true'}">Rs ${enrollment.transaction.amount}</c:if>
                        <c:if test="${edit != 'true'}">Rs ${amount}</c:if>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Transaction Mode ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <c:if test="${edit == 'true'}">${enrollment.transaction.transactionMode}</c:if>
                        <c:if test="${edit != 'true'}">${transactionMode}</c:if>
                    </td>
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
