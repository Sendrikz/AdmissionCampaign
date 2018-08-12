<%@ page pageEncoding="UTF-8" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}" />
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