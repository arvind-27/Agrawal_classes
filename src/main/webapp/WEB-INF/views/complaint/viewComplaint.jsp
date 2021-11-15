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
            <c:if test="${role == 'admin' || (complaint.isResolved == false && role == 'student')}">
            <tr>
                <th style="width: 40%;"></th>
                <th style="width: 10%;"></th>
                <td style="width: 50%; text-align: right;">
                    <a class="btn btn-primary" href="/${role}/complaints/${complaint.complaintId}/edit"
                        role="button">Edit Complaint</a>
                </td>
            </tr>
            </c:if>
            <tr>
                <th style="width: 40%; text-align: center;">Complaint ID</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${complaint.complaintId}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Date & Time</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">
                    <fmt:formatDate pattern="dd-MM-yyyy" value="${complaint.date}" />
                    <fmt:formatDate pattern="HH:mm:ss" value="${complaint.time}" />
                </td>
            </tr>
            <c:if test="${role == admin}">
            <tr>
                <th style="width: 40%; text-align: center;">Student ID</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%"><a href="/${role}/students/ST${complaint.studentId}">ST${complaint.studentId}</a></td>
            </tr>
            </c:if>
            <tr>
                <th style="width: 40%; text-align: center;">Subject</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${complaint.subject}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Description</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${complaint.description}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Response</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${complaint.response}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Resolved?</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">
                    <c:if test="${complaint.isResolved == true}"><span style="color: blue;">Yes</span></c:if>
                    <c:if test="${complaint.isResolved == false}"><span style="color: red;">No</span></c:if>
                </td>
            </tr>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
