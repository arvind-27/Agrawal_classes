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
            <form:form class="form-horizontal" action="${submiturl}" method="post" modelAttribute="teacher">
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
                <c:if test="${edit == true}">
                <tr>
                    <th style="width: 40%; text-align: center;">Employee ID</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">ET${employeeId}</td>
                </tr>
                </c:if>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                <tr>
                    <th style="width: 40%; text-align: center;">Name ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="employee.user.firstName" class="form-control" required="true"
                            placeholder="First Name"></form:input>
                        <form:errors path="employee.user.firstName" style="color: red;"></form:errors>
                        <form:input type="text" path="employee.user.middleName" class="form-control" placeholder="Middle Name"></form:input>
                        <form:errors path="employee.user.middleName" style="color: red;"></form:errors>
                        <form:input type="text" path="employee.user.lastName" class="form-control" placeholder="Last Name"></form:input>
                        <form:errors path="employee.user.lastName" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Username ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="employee.user.username" class="form-control" required="true"></form:input>
                        <form:errors path="employee.user.username" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Email Address ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="email" path="employee.user.emailAddress" class="form-control" required="true"></form:input>
                        <form:errors path="employee.user.emailAddress" style="color: red;"></form:errors>
                    </td>
                </tr>
                </sec:authorize>
                <c:if test="${edit != true || (edit == true && role == 'admin')}">
                <tr>
                    <th style="width: 40%; text-align: center;">Subject ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:select class="form-control" path="subject.subjectId" required="true">
                            <c:forEach var="subject" items="${subjects}">
                                <form:option value="${subject.subjectId}">${subject.subjectName} - ${subject.subjectId}</form:option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="subject.subjectId" style="color: red;"></form:errors>
                    </td>
                </tr>
                </c:if>
                <tr>
                    <th style="width: 40%; text-align: center;">Gender ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:select class="form-control" path="gender" required="true">
                            <form:option value="Male">Male</form:option>
                            <form:option value="Female">Female</form:option>
                            <form:option value="Not Specified">Not Specified</form:option>
                        </form:select>
                        <form:errors path="gender" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Date of Birth ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="date" path="dateOfBirth" class="form-control" required="true" max="2005-01-01" min="1960-01-01"></form:input>
                        <form:errors path="dateOfBirth" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Address ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="houseNumber" class="form-control" placeholder="House Number"></form:input>
                        <form:errors path="houseNumber" style="color: red;"></form:errors>
                        <form:input type="text" path="street" class="form-control" required="true" placeholder="Street"></form:input>
                        <form:errors path="street" style="color: red;"></form:errors>
                        <form:input type="text" path="city" class="form-control" required="true" placeholder="City"></form:input>
                        <form:errors path="city" style="color: red;"></form:errors>
                        <form:input type="text" path="state" class="form-control" required="true" placeholder="State"></form:input>
                        <form:errors path="state" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Bachelors Degree</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="bachelorsDegree" class="form-control"></form:input>
                        <form:errors path="bachelorsDegree" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Masters Degree</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="mastersDegree" class="form-control"></form:input>
                        <form:errors path="mastersDegree" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Doctoral Degree</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="doctoralDegree" class="form-control"></form:input>
                        <form:errors path="doctoralDegree" style="color: red;"></form:errors>
                    </td>
                </tr>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                <tr>
                    <th style="width: 40%; text-align: center;">Basic Salary ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="number" path="employee.basicSalary" class="form-control"></form:input>
                        <form:errors path="employee.basicSalary" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Join Date</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="date" path="employee.joinDate" class="form-control"></form:input>
                        <form:errors path="employee.joinDate" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">End Date</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="date" path="employee.endDate" class="form-control"></form:input>
                        <form:errors path="employee.endDate" style="color: red;"></form:errors>
                    </td>
                </tr>
                </sec:authorize>
                <tr>
                    <th style="width: 40%; text-align: center;">PAN Number ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="text" path="employee.panNumber" class="form-control" required="true" placeholder="Format: AAAAA1234A"></form:input>
                        <form:errors path="employee.panNumber" style="color: red;"></form:errors>
                    </td>
                </tr>
                <tr>
                    <th style="width: 40%; text-align: center;">Account Number ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:input type="number" path="employee.accountNumber" class="form-control" required="true"></form:input>
                        <form:errors path="employee.accountNumber" style="color: red;"></form:errors>
                        <script>
                            var accountNumber = document.getElementById("employee.accountNumber").value;
                            if (accountNumber == 0) {
                                document.getElementById("employee.accountNumber").value = "";
                            }
                        </script>
                    </td>
                </tr>
                <c:if test="${edit == true}">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                <tr>
                    <th style="width: 40%; text-align: center;">Activated? ${mandatory}</th>
                    <th style="width: 10%;"></th>
                    <td style="width: 50%">
                        <form:select class="form-control" path="employee.user.isActive" required="true">
                            <form:option value="1">Yes</form:option>
                            <form:option value="0">No</form:option>
                        </form:select>
                        <form:errors path="employee.user.isActive" style="color: red;"></form:errors>
                    </td>
                </tr>
                </sec:authorize>
                </c:if>
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
