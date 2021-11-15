<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="container-fluid custom-container">
    <sec:authorize access='hasRole("ROLE_ADMIN")'>
    <div class="div text-right">
        <a class="btn btn-primary" href="/${role}/academics/courses/add" role="button" >Add Course</a>
    </div>
    </sec:authorize>
    <div class="table-responsive mt-2">
        <table class="table table-hover mt-4 table-sort">
            <thead>
                <tr>
                    <th>Course ID</th>
                    <th>Course Name</th>
                    <th>Description</th>
                    <th>Subjects</th>
                    <sec:authorize access='hasRole("ROLE_ADMIN")'><th>Add Subject</th></sec:authorize>
                    <c:if test="${not empty pageContext.request.userPrincipal}">
                    <th>Action</th>
                    </c:if>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${courses}" var="course">
                <tr>
                    <td>${course.courseId}</td>
                    <td>${course.courseName}</td>
                    <td>${course.description}</td>
                    <td>
                        <c:forEach var="subject" items="${course.subjects}">
                            <div>${subject.subjectName} - ${subject.subjectId}</div>
                        </c:forEach>
                    </td>
                    <sec:authorize access='hasRole("ROLE_ADMIN")'>
                    <td>
                        <a class="btn btn-outline-success btn-sm" href="/${role}/academics/courses/${course.courseId}/add-subject"
                            role="button">Add Subject</a>
                    </td>
                    </sec:authorize>
                    <c:if test="${not empty pageContext.request.userPrincipal}">
                    <td>
                        <a class="btn btn-outline-success btn-sm" href="/${role}/academics/courses/${course.courseId}" role="button">View</a>
                        <sec:authorize access='hasRole("ROLE_ADMIN")'>
                        <a class="btn btn-outline-primary btn-sm" href="/${role}/academics/courses/${course.courseId}/edit" role="button">Edit</a>
                        <a class="btn btn-outline-danger btn-sm" onclick="getRequestWithConfirmation('/${role}/academics/courses/${course.courseId}/delete',
                        'Do you want to delete this Course? \nWarning! This action is destructible!')"
                            role="button">Delete</a>
                        </sec:authorize>
                    </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
