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
                        </tr>
                        <tr>
                            <th style="width: 40%; text-align: center;">Date and Time</th>
                            <th style="width: 10%;"></th>
                            <td style="width: 50%">
                                <fmt:formatDate pattern="dd-MM-yyyy" value="${complaint.date}" />
                                <fmt:formatDate pattern="HH:mm:ss" value="${complaint.time}" />
                            </td>
                        </tr>
                    </c:when>
                </c:choose>
                <tr>
                    <th style="width: 40%; text-align: center;">Student ID ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">ST${studentId}</td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Subject ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="subject" class="form-control" required="true"></form:input>
                        <form:errors path="subject" style="color: red;"></form:errors>
                    <td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Description ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="description" class="form-control" required="true"></form:input>
                        <form:errors path="description" style="color: red;"></form:errors>
                    <td>
                </tr>
                <c:if test="${edit == true}">
                <tr>
                    <th style="width: 40%; text-align: center;">Response</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">${complaint.response}<td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Resolved?</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <c:if test="${complaint.isResolved == true}"><span style="color: blue;">Yes</span></c:if>
                        <c:if test="${complaint.isResolved == false}"><span style="color: red;">No</span></c:if>
                    </td>
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
