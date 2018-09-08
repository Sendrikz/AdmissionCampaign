<%@ page pageEncoding="UTF-8" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:bundle basename="pagecontent" prefix = "label." >
<html>
<head>
    <title>Title</title>
    <style>
        .student-grade-title {
            border: 1px solid;
            background-color: lightblue;
            padding: 10px;
            margin: 10px 0;
        }
    </style>
</head>
<body>
    <div class="student-grade-title">
        <table class="table table-striped table-bordered table-sm">
            <tr>
                <th><fmt:message key="name"/> </th>
                <th><fmt:message key="grade"/> </th>
            </tr>
        <jsp:useBean id="subjectGradeList" scope="session" type="java.util.HashMap"/>
        <c:forEach var="subject" items="${subjectGradeList}" varStatus="status">
            <tr>
                <td>
                ${subject.key.name}
                </td>
                <td>
                ${subject.value}
                </td>
            </tr>
        </c:forEach>
        </table>
    </div>
</body>
</html>
</fmt:bundle>