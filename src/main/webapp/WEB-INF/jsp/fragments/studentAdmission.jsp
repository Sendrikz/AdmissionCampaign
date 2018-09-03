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
        .congratulation-title {
            border: 1px solid;
            background-color: lightgreen;
            padding: 10px;
        }
    </style>
</head>
<body>
<div class="congratulation-title">
   <fmt:message key="congratulations"/> ${sessionScope.userSpecialty.name}
</div>
</body>
</html>
</fmt:bundle>
