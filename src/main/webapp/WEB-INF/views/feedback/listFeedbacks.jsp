<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="container-fluid custom-container">
    <sec:authorize access='hasRole("ROLE_STUDENT")'>
    <div class="div text-right">
        <a class="btn btn-primary" href="/${role}/feedbacks/add" role="button" >Add Feedback</a>
    </div>
    </sec:authorize>
    <div class="table-responsive mt-2">
        <table class="table table-hover mt-4 table-sort">
            <thead>
                <tr>
                    <th>Feedback ID</th>
                    <th>Student ID</th>
                    <th>Teacher ID</th>
                    <th>Date & Time</th>
                    <th>Subject</th>
                    <th>Message</th>
                    <th>Response</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${feedbacks}" var="feedback">
                <tr>
                    <td>${feedback.feedbackId}</td>
                    <td>ST${feedback.studentId}</td>
                    <td>ET${feedback.employeeId}</td>
                    <td>
                        <fmt:formatDate pattern="dd-MM-yyyy" value="${feedback.date}" />
                        <fmt:formatDate pattern="HH:mm:ss" value="${feedback.time}" />
                    </td>
                    <td>${feedback.subject}</td>
                    <td>${feedback.message}</td>
                    <td>
                        <c:choose>
                            <c:when test="${feedback.response != null || role != 'teacher'}">${feedback.response}</c:when>
                            <c:otherwise>
                                <input type="text" class="form-control" id="response"></input>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${role == 'admin' || (role == 'student' && feedback.response == null)}">
                                <a class="btn btn-outline-danger btn-sm" onclick="getRequestWithConfirmation('/${role}/feedbacks/${feedback.feedbackId}/delete',
                                                    'Do you want to delete this Feedback? \nWarning! This action is destructible!')"
                                    role="button">Delete</a>
                            </c:when>
                            <c:when test="${role == 'teacher' && feedback.response == null}">
                                <a class="btn btn-outline-success btn-sm" onclick="postRequest('/${role}/feedbacks/${feedback.feedbackId}/respond',
                                                            {'response': $('#response').val()})" role="button">Respond</a>
                            </c:when>
                            <c:otherwise>-</c:otherwise>
                        </c:choose>

                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
