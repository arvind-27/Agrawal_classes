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
                method="post" modelAttribute="complaint">
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
                <c:choose>
                    <c:when test="${edit == true}">
                        <tr>
                            <th style="width: 40%; text-align: center;">Complaint ID</th>
                            <th style="width: 10%;"></th>
                            <td style="width: 50%">${complaint.complaintId}</td>
                            <form:errors path="complaintId" style="color: red;"></form:errors>
                        </tr>
                        <tr>
                            <th style="width: 40%; text-align: center;">Date and Time</th>
                            <th style="width: 10%;"></th>
                            <td style="width: 50%">
                                <fmt:formatDate pattern="dd-MM-yyyy" value="${complaint.date}" />
                                <fmt:formatDate pattern="HH:mm:ss" value="${complaint.time}" />
                            </td>
                            <form:input type="hidden" path="date" class="form-control" required="true" value="${complaint.date}"></form:input>
                            <form:input type="hidden" path="time" class="form-control" required="true" value="${complaint.time}"></form:input>
                        </tr>
                    </c:when>
                </c:choose>
                <tr>
                    <th style="width: 40%; text-align: center;">Student ID</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">ST${complaint.studentId}</td>
                    <form:input type="hidden" path="studentId" class="form-control" required="true" value="${complaint.studentId}"></form:input>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Subject</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">${complaint.subject}<td>
                    <form:input type="hidden" path="subject" class="form-control" required="true" value="${complaint.subject}"></form:input>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Description</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">${complaint.description}<td>
                    <form:input type="hidden" path="description" class="form-control" required="true" value="${complaint.description}"></form:input>
                </tr>
                <c:if test="${edit == true}">
                <tr>
                    <th style="width: 40%; text-align: center;">Response</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="response" class="form-control"></form:input>
                        <form:errors path="response" style="color: red;"></form:errors>
                    <td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Resolved? ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:select class="form-control" path="isResolved" required="true">
                            <form:option value="1">Yes</form:option>
                            <form:option value="0">No</form:option>
                        </form:select>
                        <form:errors path="isResolved" style="color: red;"></form:errors>
                    <td>
                </tr>
                </c:if>
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
