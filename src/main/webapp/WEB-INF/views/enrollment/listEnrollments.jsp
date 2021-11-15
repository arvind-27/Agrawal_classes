<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="container-fluid custom-container">
    <div class="row justify-content-center mb-3">
        <h2>${submessage1}</h2>
    </div>
    <c:if test="${fullenrollment == true}">
    <div class="div text-right">
        <select id="courseSelect">
            <c:forEach var="course" items="${courses}">
                <option value="${course.courseId}">
                    ${course.courseId} - ${course.courseName}
                </option>
            </c:forEach>
        </select>
        <select id="batchSelect">
            <option value="${course.courseId}">
                ${course.courseId} - ${course.courseName}
            </option>
        </select>
        <button id="enrollment" class="btn btn-primary btn ml-3">Add Enrollment</a>
    </div>
    </c:if>
    <c:if test="${fullenrollment != true && (role == 'admin' || role == 'staff')}">
        <div class="div text-right mt-2">
            <a class="btn btn-success" href="/${role}/academics/courses/${courseId}/${batchId}/enrollments/add" role="button">Enroll</a>
        </div>
    </c:if>
    <div class="table-responsive mt-2">
        <table class="table table-hover mt-4 table-sort">
            <thead>
                <tr>
                    <th>Enrollment ID</th>
                    <th>Student ID</th>
                    <th>Course ID</th>
                    <th>Batch ID</th>
                    <th>Join Date</th>
                    <th>End Date</th>
                    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF', 'ROLE_STUDENT')">
                    <th>Action</th>
                    </sec:authorize>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${enrollments}" var="enrollment">
                <tr>
                    <td>${enrollment.enrollmentId}</td>
                    <td><a href="/${role}/students/ST${enrollment.studentId}">ST${enrollment.studentId}</a></td>
                    <td><a href="/${role}/academics/courses/${enrollment.courseId}">${enrollment.courseId}</a></td>
                    <td><a href="/${role}/academics/courses/${enrollment.courseId}/${enrollment.batchId}">${enrollment.batchId}</a></td>
                    <td><fmt:formatDate pattern="dd-MM-yyyy" value="${enrollment.joinDate}" /></td>
                    <td><fmt:formatDate pattern="dd-MM-yyyy" value="${enrollment.endDate}" /></td>
                    <td>
                        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF', 'ROLE_STUDENT')">
                        <a class="btn btn-outline-success btn-sm" href="/${role}/academics/enrollments/${enrollment.enrollmentId}"
                            role="button">View</a>
                        </sec:authorize>
                        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')">
                        <a class="btn btn-outline-primary btn-sm" href="/${role}/academics/enrollments/${enrollment.enrollmentId}/edit"
                            role="button">Edit</a>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <a class="btn btn-outline-danger btn-sm" onclick="getRequestWithConfirmation('/${role}/academics/enrollments/${enrollment.enrollmentId}/delete',
                        'Do you want to delete this Enrollment? \nWarning! This action is destructible!')"
                            role="button">Delete</a>
                        </sec:authorize>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<c:if test="${fullenrollment == true}">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
    integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
    crossorigin="anonymous"></script>
<script>
    $("#enrollment").click(function () {
        location.href = "/${role}/academics/courses/" + $('#courseSelect').val() + "/" + $('#batchSelect').val() + "/enrollments/add";
    });
    function courseSelectOnChange() {
        var courseId = $("#courseSelect").val();
        var data = {};
        var url = "/${role}/academics/courses/" + courseId + "/get-all-batches";
        $.ajax({
            url: url,
            type: "post",
            data: data,
            success: function (data, status, xhr) {
                $('#batchSelect').find('option').remove();
                for (var i = 0; i < data.length; i++) {
                    $('#batchSelect').append('<option value = "' + data[i].batchId + '">' + data[i].batchId + ' - ' + data[i].batchName + '</option>');
                }
            },
            error: function (xhr, status, err) {
                alert(status);
            }
        });
    }
    $(function () {
        courseSelectOnChange();
        $("#courseSelect").change(courseSelectOnChange);
    });
</script>
</c:if>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
