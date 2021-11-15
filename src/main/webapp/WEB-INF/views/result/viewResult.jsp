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
                    <a class="btn btn-primary" href="/${role}/academics/tests/${result.testId}/results/ST${result.student.studentId}/edit" role="button">Edit Result</a>
                </td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Test ID</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${result.testId}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Student ID</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">ST${result.student.studentId}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Student Name</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${result.student.user.firstName} ${result.student.user.middleName} ${result.student.user.lastName}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Marks Scored</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">
                    ${result.marksScored}
                    (<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${result.marksScored / maximumMarks * 100}" /> %)
                </td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Maximum Marks</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${maximumMarks}</td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Applied For Recheck?</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">
                    <c:if test="${result.hasAppliedRecheck == true}"><span style="color: blue;">Yes</span></c:if>
                    <c:if test="${result.hasAppliedRecheck == false}"><span style="color: red;">No</span></c:if>
                </td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Is Recheck Done?</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">
                    <c:if test="${result.isDoneRecheck == true}"><span style="color: blue;">Yes</span></c:if>
                    <c:if test="${result.isDoneRecheck == false}"><span style="color: red;">No</span></c:if>
                </td>
            </tr>
            <tr>
                <th style="width: 40%; text-align: center;">Recheck Comments</th>
                <th style="width: 10%;"></th>
                <td style="width: 50%">${result.recheckComments}</td>
            </tr>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>
