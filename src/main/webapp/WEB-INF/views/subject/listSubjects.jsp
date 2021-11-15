<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="container-fluid custom-container">
    <sec:authorize access="hasRole('ROLE_ADMIN')">
    <div class="div text-right">
        <a class="btn btn-primary" href="/${role}/academics/subjects/add" role="button" >Add Subject</a>
    </div>
    </sec:authorize>
    <div class="table-responsive mt-2">
        <table class="table table-hover mt-4 table-sort">
            <c:if test="${role == 'student'}">
                <div style="text-align: center; color: red;">You can only view the study materials for the subjects in the courses you are enrolled.</div>
            </c:if>
            <c:if test="${role == 'teacher'}">
                <div style="text-align: center; color: red;">You can only view or add the study materials for the subject you are a teacher of.</div>
            </c:if>
            <thead>
                <tr>
                    <th>Subject ID</th>
                    <th>Subject Name</th>
                    <th>Description</th>
                    <c:if test="${not empty pageContext.request.userPrincipal}">
                    <sec:authorize access="!hasRole('ROLE_STAFF')"><th>Study Materials</th></sec:authorize>
                    </c:if>
                    <sec:authorize access="hasRole('ROLE_ADMIN')"><th>Action</th></sec:authorize>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${subjects}" var="subject">
                <tr>
                    <td>${subject.subjectId}</td>
                    <td>${subject.subjectName}</td>
                    <td>${subject.description}</td>
                    <c:if test="${not empty pageContext.request.userPrincipal}">
                    <sec:authorize access="!hasRole('ROLE_STAFF')">
                        <c:if test="${role == 'student'}">
                            <c:set var="contains" value="false" />
                            <c:forEach var="studentSubjectId" items="${studentSubjectsId}">
                                <c:if test="${studentSubjectId eq subject.subjectId}">
                                    <c:set var="contains" value="true" />
                                </c:if>
                            </c:forEach>
                            <c:if test="${contains == true}"></c:if>
                        </c:if>
                        <td>
                        <c:choose>
                            <c:when test="${role == 'teacher' && teacherSubjectId != subject.subjectId}">
                                <div style="color: red;">Can't View</div>
                            </c:when>
                            <c:when test="${role == 'student' && contains == false}">
                                <div style="color: red;">Not Enrolled</div>
                            </c:when>
                            <c:otherwise>
                                <a class="btn btn-success" href="/${role}/academics/subjects/${subject.subjectId}/materials" role="button">View</a>
                            </c:otherwise>
                        </c:choose>
                        </td>
                    </sec:authorize>
                    </c:if>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <td>
                        <a class="btn btn-outline-success btn-sm" href="/${role}/academics/subjects/${subject.subjectId}"
                            role="button">View</a>
                        <a class="btn btn-outline-primary btn-sm" href="/${role}/academics/subjects/${subject.subjectId}/edit"
                            role="button">Edit</a>
                        <a class="btn btn-outline-danger btn-sm" onclick="getRequestWithConfirmation('/${role}/academics/subjects/${subject.subjectId}/delete',
                        'Do you want to delete this Subject? \nWarning! This action is destructible!')"
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
