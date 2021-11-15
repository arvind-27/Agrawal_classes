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
                method="post" modelAttribute="feedback">
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
                    <th style="width: 40%; text-align: center;">Teacher ID ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:select class="form-control" path="employeeId" required="true">
                            <c:forEach var="teacher" items="${teachers}">
                                <form:option value="${teacher.employee.employeeId}">ET${teacher.employee.employeeId} - ${teacher.employee.user.name}</form:option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="employeeId" style="color: red;"></form:errors>
                    </td>
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
                    <th style="width: 40%; text-align: center;">Message ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="message" class="form-control" required="true"></form:input>
                        <form:errors path="message" style="color: red;"></form:errors>
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
