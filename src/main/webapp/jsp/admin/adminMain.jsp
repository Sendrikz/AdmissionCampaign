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
                $( "#tabsSubject" ).tabs();
            } );
        </script>
    </head>
    <body>
        <h3><fmt:message key="account"/></h3>
        <hr/>
        <fmt:message key="lastName"/>: ${ sessionScope.loginedUser.lastName }
        <fmt:message key="firstName"/>: ${ sessionScope.loginedUser.firstName }
        <fmt:message key="patronymic"/>: ${ sessionScope.loginedUser.patronymic }
        <fmt:message key="birthday"/>: ${ sessionScope.loginedUser.birthday }
        <fmt:message key="city"/>: ${ sessionScope.loginedUser.city }
        <fmt:message key="email"/>: ${ sessionScope.loginedUser.email }
        <hr/>
        <h3> Put grades to students </h3>
        <div id="tabsSubject">
            <ul>
                <jsp:useBean id="subjectUserHashMap" scope="session" type="java.util.HashMap"/>
                <c:forEach var="elem" items="${ subjectUserHashMap }" varStatus="status">
                    <li><a href="#tabsSubject-${ elem.key.id }">${ elem.key.name }</a></li>
                </c:forEach>
            </ul>
            <c:forEach var="subject" items="${ subjectUserHashMap}" varStatus="status">
                <div id="tabsSubject-${subject.key.id}">
                    <c:forEach var="user" items="${subject.value}" varStatus="status">
                        <form name="userSubjectForm" method="POST" action="controller">
                            <input type="hidden" name="command" value="setGrade">
                            <input type="hidden" name="userId" value="${user.id}">
                            <input type="hidden" name="subjectId" value="${subject.key.id}">
                            ${ user.lastName }
                            ${ user.firstName }
                            ${ user.patronymic }
                            Grade: <input type="number" name="grade" placeholder=""
                                          min="0" max="200" step="0.1">
                            <button><fmt:message key="submit"/></button>
                        </form>
                    </c:forEach>
                </div>
            </c:forEach>
        </div>
    <h3> Get list of students who pass </h3>

    </body></html>
</fmt:bundle>