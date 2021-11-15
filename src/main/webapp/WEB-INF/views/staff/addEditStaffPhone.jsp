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
                    <a href="#" onclick="window.location.reload();">Reset <i class="fa fa-refresh" aria-hidden="true"></i></a>
                </td>
            </tr>
        </table>
        <div class="container">

            <div class="row">
                <div class="col-sm-5" style="text-align: center;">
                    Phone Numbers:
                </div>
                <div class="col-sm-7">
                    <c:forEach var="phoneNumber" items="${phoneNumbers}">
                        <div>${phoneNumber.phoneNumber} &emsp;
                            <a href="#" onclick="postRequest('/profile/users/${userId}/phoneNumber/delete',
                            {'phoneNumber': '${phoneNumber.phoneNumber}'})"> Remove
                            </a>
                        </div>
                    </c:forEach>

                    <input type="text" id="phoneNumber">
                    <a class="btn btn-outline-danger btn-sm" onclick="postRequest('/profile/users/${userId}/phoneNumber/add',
                    {'phoneNumber': $('#phoneNumber').val()})" role="button">Add</a>
                    <div id="error" style="color: red;"></div>
            </div>
        </div>
        <div class="row mt-4 mb-4">
            <div class="col-sm-7 offset-sm-5">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <a class="btn btn-primary" type="button" href="/${role}/staffs/ES${employeeId}">${buttonmessage}</a>
                </sec:authorize>
                <sec:authorize access="!hasRole('ROLE_ADMIN')">
                    <a class="btn btn-primary" type="button" href="/profile/staff">${buttonmessage}</a>
                </sec:authorize>
            </div>
        </div>
    </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
