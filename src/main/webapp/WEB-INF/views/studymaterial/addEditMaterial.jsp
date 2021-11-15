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
            <form:form class="form-horizontal" action="${submiturl}" method="post" enctype="multipart/form-data"
                modelAttribute="material">
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
                    <th style="width: 40%; text-align: center;">Material ID ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <c:choose>
                            <c:when test="${edit == true}">
                                ${material.materialId}
                            </c:when>
                            <c:otherwise>
                                <form:input type="text" path="materialId" class="form-control" required="true"></form:input>
                                <form:errors path="materialId" style="color: red;"></form:errors>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Subject ID ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        ${subjectId}
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Topic Name ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="topicName" class="form-control" required="true"></form:input>
                        <form:errors path="topicName" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Difficulty ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:select class="form-control" path="difficulty" required="true">
                            <form:option value="Easy">Easy</form:option>
                            <form:option value="Medium">Medium</form:option>
                            <form:option value="Hard">Hard</form:option>
                        </form:select>
                        <form:errors path="difficulty" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Description</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="description" class="form-control"></form:input>
                        <form:errors path="description" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">File ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <c:choose>
                            <c:when test="${edit == true}">
                                <a href="${url}" target="_blank">${material.filename}</a>
                            </c:when>
                            <c:otherwise>
                                <input type="file" name="file" class="form-control" required="true"></input>
                            </c:otherwise>
                        </c:choose>
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
