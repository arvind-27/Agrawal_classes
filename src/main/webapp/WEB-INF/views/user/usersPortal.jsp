<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="container-fluid custom-container">
    <div class="table-responsive mt-2">
        <table class="table table-hover mt-4 table-sort">
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Email Address</th>
                    <th>Name</th>
                    <th>Date Created</th>
                    <th>Last Login</th>
                    <th>Role</th>
                    <th>Email Verified?</th>
                    <th>Active?</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.username}</td>
                    <td>${user.emailAddress}</td>
                    <td>${user.firstName} ${user.middleName} ${user.lastName}</td>
                    <td>
                        <fmt:formatDate pattern="dd-MM-yyyy" value="${user.dateCreated}" />
                    </td>
                    <td>
                        <fmt:formatDate pattern="dd-MM-yyyy" value="${user.lastLoginDate}" />
                        <fmt:formatDate pattern="HH:mm:ss" value="${user.lastLoginTime}" />
                    </td>
                    <td>${user.role}</td>
                    <td>
                        <c:if test="${user.isEmailVerified == true}"><span style="color: blue;">Yes</span></c:if>
                        <c:if test="${user.isEmailVerified == false}"><span style="color: red;">No</span></c:if>
                    </td>
                    <td>
                        <c:if test="${user.isActive == true}"><span style="color: blue;">Yes</span></c:if>
                        <c:if test="${user.isActive == false}"><span style="color: red;">No</span></c:if>
                    </td>
                    <td>
                        <a class="btn btn-outline-danger btn-sm" onclick="getRequestWithConfirmation('/${role}/users/${user.userId}/delete',
                                            'Do you want to delete this User? \nWarning! This action is destructible!')"
                        role="button">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
