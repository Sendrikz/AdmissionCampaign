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
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">

        <script>
            $( function() {
                $( "#tabsSubject" ).tabs();
            } );
        </script>
    </head>
    <body>
    <c:import url="/jsp/fragments/headerAdmin.jsp" />
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
    <form name="gradeListForm" method="POST" action="controller">
        <button>
            <input type="hidden" name="command" value="gradeListRedirect" />
            Grade List
        </button>
    </form>
<div class="m-3">
    <div class="row col-md-6">
        <table class="table table-striped table-bordered table-sm">
            <tr>
                <th>Name</th>
                <th>Pass</th>
            </tr>
            <jsp:useBean id="paginationSpecialties" scope="session" type="java.util.ArrayList"/>
            <c:forEach items="${paginationSpecialties}" var="specialty">
                <tr>
                    <td>${specialty.name}</td>
                    <td>
                        <form name="RateForm" method="POST" action="controller">
                            <input type="hidden" name="command" value="rateSpecialty">
                            <input type="hidden" name="specialtyId" value="${specialty.id}"/>
                            <button>Count</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <nav aria-label="Navigation for countries">
        <ul class="pagination">
            <c:forEach begin="1" end="${sessionScope.noOfPages}" var="i">
                        <li class="page-item">
                            <form name="logOutFrom" method="POST" action="controller" class="page-link">
                                <button>
                                    <input type="hidden" name="command" value="paginationSpecialty"/>
                                    <input type="hidden" name="currentPage" value="${i}">
                                    ${i}
                                </button>
                            </form>
                        </li>
            </c:forEach>
        </ul>
    </nav>
</div>
    <form name="SendLetterForm" method="POST" action="controller">
        <input type="hidden" name="command" value="sendLetter">
        <button>Send letters</button>
    </form>
    </body></html>
</fmt:bundle>