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
</head>
<body>
<jsp:useBean id="subjectGradeList" scope="session" type="java.util.HashMap"/>
    <c:forEach var="subject" items="${subjectGradeList}" varStatus="status">
        Name: ${subject.key.name}
        Grade: ${subject.value}
    </c:forEach>
</body>
</html>
</fmt:bundle>