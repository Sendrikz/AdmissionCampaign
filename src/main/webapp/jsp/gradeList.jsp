<%@ page pageEncoding="UTF-8" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:bundle basename="pagecontent" prefix = "label." >
    <html><head>
    <meta charset="utf-8">
    <title><fmt:message key="title"/></title>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
    $( function() {
    $( "#accordion" ).accordion();
    } );
    </script>
    </head>
    <body>
    <h3> Get list of students </h3>

    <div id="accordion">
        <jsp:useBean id="specialtyUserGradeHashMap" scope="session" type="java.util.HashMap"/>
        <c:forEach var="elem" items="${ specialtyUserGradeHashMap }" varStatus="status">
            <h3> ${ elem.key.name }</h3>
            <div>
                <p>
                    <c:forEach var="user" items="${ elem.value }" varStatus="status">
                <div>
                        ${user.value.lastName}
                        ${user.value.firstName}
                        ${user.value.patronymic}
                        ${user.key}
                </div>
                </c:forEach>
                </p>
            </div>
        </c:forEach>
    </div>
</body>
</html>
</fmt:bundle>