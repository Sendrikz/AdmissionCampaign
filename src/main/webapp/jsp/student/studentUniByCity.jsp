<%@ page pageEncoding="UTF-8" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:bundle basename="pagecontent" prefix = "label." >
    <html><head>
    <meta charset="utf-8">
    <title><fmt:message key="title"/></title>
    </head>
<body>
<c:import url="/jsp/fragments/header.jsp" />
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