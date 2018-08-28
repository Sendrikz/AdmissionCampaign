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

        <script src='https://unpkg.com/sweetalert/dist/sweetalert.min.js'></script>
        <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
    </head>
    <body>
    <c:import url="/jsp/fragments/headerStudent.jsp"/>
    <c:if test="${sessionScope.isChecked == 'yes'}">
        <c:import url="/jsp/fragments/studentGrades.jsp"/>
    </c:if>
    <h3><fmt:message key="account"/></h3>
    <hr/>
    <fmt:message key="lastName"/>: ${ sessionScope.loginedUser.lastName }
    <fmt:message key="firstName"/>: ${ sessionScope.loginedUser.firstName }
    <fmt:message key="patronymic"/>: ${ sessionScope.loginedUser.patronymic }
    <fmt:message key="birthday"/>: ${ sessionScope.loginedUser.birthday }
    <fmt:message key="city"/>: ${ sessionScope.loginedUser.city }
    <fmt:message key="email"/>: ${ sessionScope.loginedUser.email }
    <hr/>

    <h3><fmt:message key="subjects"/></h3>
    <jsp:useBean id="subjectsList" scope="session" type="java.util.List"/>
    <c:forEach var="elem" items="${ subjectsList }" varStatus="status">
        <form name="subjectForm" method="POST" action="controller">
            <input type="hidden" name="command" value="registrateOnSubject" />
            <input type="hidden" name="subject" value="${ elem.name }" />
            <c:out value="${ elem.name }" />
            <button class="custom-btn btn-light btn-sm text-bold" type="submit"><fmt:message key="submit"/></button>
        </form>
    </c:forEach>

    <h3><fmt:message key="universities"/></h3>
    <button class="custom-btn btn-light btn-sm text-bold" type="submit" onclick="myFunction()"><fmt:message key="city"/></button>
    <table id="myCity" style="display: none">
        <jsp:useBean id="citiesList" scope="session" type="java.util.List"/>
        <c:forEach var="city" items=" ${ citiesList }" varStatus="status">
            <tr>
                <td>
                    <c:choose>
                        <c:when test="${fn:contains(city, '[')}" >
                            <c:set var="correctCity" value="${fn:substringAfter(city, '[')}"/>
                        </c:when>
                        <c:when test="${fn:contains(city, ']')}" >
                            <c:set var="correctCity" value="${fn:substringBefore(city, ']')}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="correctCity" value="${city}"/>
                        </c:otherwise>
                    </c:choose>
                    <form name="citiesForm" method="POST" action="controller">
                        <input type="hidden" name="command" value="generateUniversitiesByCity"/>
                        <input type="hidden" name="city" value=" ${ correctCity }"/>
                        <button class="custom-btn btn-light btn-sm text-bold" type="submit">${ correctCity }</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <form name="gradeListForm" method="POST" action="controller">
        <button>
            <input type="hidden" name="command" value="gradeListRedirect" />
            Grade List
        </button>
    </form>

    <c:choose>
        <c:when test="${sessionScope.successfulSubject == 'yes'}">
            <script>
                swal('Success!', 'You are successfully registrated', 'success');
            </script>
            <c:set var="successfulSubject" value="off" scope="session"/>
        </c:when>
        <c:when test="${sessionScope.successfulSubject == 'no'}">
            <script>
                swal('Error!', 'You have already registrated', 'error');
            </script>
            <c:set var="successfulSubject" value="off" scope="session"/>
        </c:when>
    </c:choose>
    <script>
        function myFunction() {
            var x = document.getElementById('myCity');
            if (x.style.display === 'none') {
                x.style.display = 'block';
            } else {
                x.style.display = 'none';
            }
        }
    </script>

    </body></html>
</fmt:bundle>