<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="container-fluid custom-container">
    <div class="div" style="text-align: center; color: red;">
        Result for Test Id: ${testId}<br>
        Maximum Marks: ${maximumMarks}
    </div>
    <sec:authorize access='hasRole("ROLE_STUDENT")'>
        <div class="div text-right">
            <a class="btn btn-outline-primary btn-sm" href="/${role}/academics/tests/${testId}/results-recheck" role="button">Apply for Recheck / Check Status</a>
        </div>
    </sec:authorize>
    <sec:authorize access='hasAnyRole("ROLE_STAFF", "ROLE_ADMIN")'>
    <div class="div text-right">
        <a class="btn btn-outline-primary btn-sm" href="/${role}/academics/tests/${testId}/results-recheck" role="button" >Filter Rechecks</a>
    </div>
    <div class="div text-right mt-3">
        <a class="btn btn-primary" href="/${role}/academics/tests/${testId}/results/add" role="button" >Add Result</a>
    </div>
    </sec:authorize>
    <div class="table-responsive mt-2">
        <table class="table table-hover mt-4 table-sort-default">
            <thead>
                <tr>
                    <th>Student ID</th>
                    <th>Student Name</th>
                    <th>Total Marks</th>
                    <th>Rank</th>
                    <sec:authorize access='hasAnyRole("ROLE_STAFF", "ROLE_ADMIN")'>
                    <th>Action</th>
                    </sec:authorize>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${results}" var="result">
                <tr <c:if test="${result.student.user.userId == pageContext.request.userPrincipal.principal.user.userId}">class="table-success"</c:if>>
                    <td>ST${result.student.studentId}</td>
                    <td>${result.student.user.firstName} ${result.student.user.middleName} ${result.student.user.lastName}</td>
                    <td>
                        ${result.marksScored}
                        (<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${result.marksScored / maximumMarks * 100}" /> %)
                    </td>
                    <td>${marksToRank[result.marksScored]}</td>
                    <sec:authorize access='hasAnyRole("ROLE_STAFF", "ROLE_ADMIN")'>
                    <td>
                        <a class="btn btn-outline-success btn-sm" href="/${role}/academics/tests/${testId}/results/ST${result.student.studentId}"
                            role="button">View</a>
                        <a class="btn btn-outline-primary btn-sm" href="/${role}/academics/tests/${testId}/results/ST${result.student.studentId}/edit"
                            role="button">Edit</a>
                        <a class="btn btn-outline-danger btn-sm" onclick="getRequestWithConfirmation('/${role}/academics/tests/${testId}/results/ST${result.student.studentId}/delete',
                        'Do you want to delete this Result? \nWarning! This action is destructible!')"
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
