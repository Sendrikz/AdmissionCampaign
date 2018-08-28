<%@ page pageEncoding="UTF-8" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:bundle basename="pagecontent" prefix = "label." >
    <html><head>
    <meta charset="utf-8">
    <title><fmt:message key="title"/></title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
    </head>
<body>
<c:import url="/jsp/fragments/headerStudent.jsp" />
<table>
    <jsp:useBean id="listOfUni" scope="session" type="java.util.List"/>
    <c:forEach var="uni" items="${ listOfUni }" varStatus="status">
        <tr>
            <td>
                <form name="uniForm" method="POST" action="controller">
                    <input type="hidden" name="command" value="generateSpecialtiesByUni"/>
                    <input type="hidden" name="uniId" value=" ${ uni.id }"/>
                    <button class="custom-btn btn-light btn-sm text-bold" type="submit">${ uni.name }</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
</fmt:bundle>