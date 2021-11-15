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
        <div class="container">

            <div class="row">
                <div class="col-sm-5" style="text-align: center;">
                    Staffs:
                </div>
                <div class="col-sm-7">
                    <c:forEach var="staff" items="${staffsPresent}">
                        <div>ES${staff.employee.employeeId} - ${staff.employee.user.firstName} ${staff.employee.user.middleName}
                            ${staff.employee.user.lastName} &emsp;
                            <a href="#" onclick="postRequest('/${role}/academics/courses/${courseId}/${batchId}/delete-staff',
                                {'staffId': '${staff.staffId}'})"> Remove
                            </a>
                        </div>
                    </c:forEach>

                    <c:if test="${not empty staffsNotPresent}">
                    <select id="staffId">
                        <c:forEach var="staff" items="${staffsNotPresent}">
                            <option value="${staff.staffId}">ES${staff.employee.employeeId} - ${staff.employee.user.firstName} ${staff.employee.user.middleName}
                            ${staff.employee.user.lastName}</option>
                        </c:forEach>
                    </select>
                    <a class="btn btn-outline-danger btn-sm" onclick="postRequest('/${role}/academics/courses/${courseId}/${batchId}/add-staff',
                    {'staffId': $('#staffId').val()})" role="button">Add</a>
                    </c:if>

                    <div id="error" style="color: red;"></div>
                </div>
            </div>
            <div class="row mt-4 mb-4">
                <div class="col-sm-7 offset-sm-5">
                    <a class="btn btn-primary" type="button"
                        href="/${role}/academics/courses/${courseId}/${batchId}">${buttonmessage}</a>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
