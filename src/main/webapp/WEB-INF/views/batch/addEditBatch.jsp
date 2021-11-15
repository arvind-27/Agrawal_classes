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
            <tr>
                <th style="width: 60%;">
                    <h4>${submessage2}</h4>
                </th>
                <td style="width: 40%; text-align: right;">
                    <a href="#" onclick="window.location.reload();">Reset <i class="fa fa-refresh"
                            aria-hidden="true"></i></a>
                </td>
            </tr>
        </table>
        <table class="table table-borderless">
            <form:form class="form-horizontal" action="${submiturl}"
                method="post" modelAttribute="batch">
                <tr>
                    <th style="width: 40%; text-align: center;">Batch ID ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <c:choose>
                            <c:when test="${edit == true}">
                                ${batch.batchId}
                            </c:when>
                            <c:otherwise>
                                <form:input type="text" path="batchId" class="form-control" required="true"></form:input>
                                <form:errors path="batchId" style="color: red;"></form:errors>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Batch Name ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="batchName" class="form-control"></form:input>
                        <form:errors path="batchName" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Course ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <c:choose>
                            <c:when test="${edit == true}">
                                ${courseId} - ${batch.course.courseName}
                            </c:when>
                            <c:when test="${not empty courseId}">
                                ${courseId}
                            </c:when>
                            <c:otherwise>
                                <form:select class="form-control" path="course.courseId" required="true">
                                    <c:forEach var="course" items="${courses}">
                                        <form:option value="${course.courseId}">${course.courseId} - ${course.courseName}</form:option>
                                    </c:forEach>
                                </form:select>
                            </c:otherwise>
                        </c:choose>
                        <form:errors path="course.courseId" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Fee ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="number" path="fee" class="form-control" required="true"></form:input>
                        <form:errors path="fee" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Room Number ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="number" path="roomNumber" class="form-control" required="true"></form:input>
                        <form:errors path="roomNumber" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Start Time</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="time" path="startTime" class="form-control"></form:input>
                        <form:errors path="startTime" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">End Time</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="time" path="endTime" class="form-control"></form:input>
                        <form:errors path="endTime" style="color: red;"></form:errors>
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
