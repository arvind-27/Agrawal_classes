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
            <tr>
                <th style="width: 40%;"></th>
                <th style="width: 10%;"></th>
                <td style="width: 50%; text-align: right;">
                    <a class="btn btn-success" href="/${role}/academics/subjects/${subject.subjectId}/materials" role="button">Study Materials</a>
                    <a class="btn btn-primary" href="/${role}/academics/subjects/${subject.subjectId}/edit" role="button">Edit Subject</a>
                </td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Subject ID</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${subject.subjectId}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Subject Name</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${subject.subjectName}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Description</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${subject.description}</td>
            </tr>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
