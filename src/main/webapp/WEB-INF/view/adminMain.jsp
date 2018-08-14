<%@ page pageEncoding="UTF-8" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:bundle basename="pagecontent" prefix = "label." >
    <html><head><title><fmt:message key="title"/></title></head>
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

    </body></html>
</fmt:bundle>