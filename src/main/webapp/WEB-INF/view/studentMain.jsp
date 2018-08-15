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
    <h3><fmt:message key="account"/></h3>
    <hr/>
    <fmt:message key="lastName"/>: <%= session.getAttribute("LastName")%>
    <fmt:message key="firstName"/>: <%= session.getAttribute("FirstName")%>
    <fmt:message key="patronymic"/>: <%= session.getAttribute("Patronymic")%>
    <fmt:message key="city"/>: <%= session.getAttribute("City")%>
    <fmt:message key="email"/>: <%= session.getAttribute("Email")%>
    <hr/>

    <h3><fmt:message key="subjects"/></h3>
    <jsp:useBean id="subjectsList" scope="session" type="java.util.List"/>
    <c:forEach var="elem" items="${ subjectsList }" varStatus="status">
        <form name="subjectForm" method="GET" action="controller">
            <input type="hidden" name="command" value="registrateOnSubject" />
            <input type="hidden" name="subject" value="${ elem.name }" />
            <c:out value="${ elem.name }" />
            <button class="custom-btn btn-light btn-sm text-bold" type="submit"><fmt:message key="submit"/></button>
        </form>
    </c:forEach>

    <h3><fmt:message key="universities"/></h3>
    <button class="custom-btn btn-light btn-sm text-bold" type="submit" onclick="myFunction()"><fmt:message key="city"/></button>
    <%--<div id="myDIV" style="display: block;">--%>
        <%--Click the button!--%>
    <%--</div>--%>
    <table id="myDIV" style="display: none">
        <tr>
            <td>
                Kyiv
            </td>
        </tr>
        <tr>
            <td>
                Lviv
            </td>
        </tr>
    </table>
    <script>
        function myFunction() {
            var x = document.getElementById('myDIV');
            if (x.style.display === 'none') {
                x.style.display = 'block';
            } else {
                x.style.display = 'none';
            }
        }
    </script>
    </body></html>
</fmt:bundle>