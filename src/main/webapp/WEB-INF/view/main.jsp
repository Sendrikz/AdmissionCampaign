<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="ru_RU" scope="session" />
<fmt:bundle basename="pagecontent" prefix = "label." >
<html><head><title><fmt:message key="title"/></title></head>
<body>
<h3><fmt:message key="title"/></h3>
<hr/>
<%= request.getAttribute("user") %>, hello!
<fmt:message key="email"/><%= request.getAttribute("name")%>
Your role is <%= request.getAttribute("user_role")%>
Your role is <%= session.getAttribute("admin")%>
<hr/>
</body></html>
</fmt:bundle>