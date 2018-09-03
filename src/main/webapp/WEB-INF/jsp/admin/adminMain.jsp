<%@ page pageEncoding="UTF-8" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:bundle basename="pagecontent" prefix = "label." >
    <html>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <title><fmt:message key="title"/></title>

        <!-- Sweet alert -->
        <script src='https://unpkg.com/sweetalert/dist/sweetalert.min.js'></script>
        <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>

        <!-- JQuery -->
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

        <!-- Custom style -->
        <link rel = "stylesheet" type="text/css" href = "/css/style.css"/>

        <script>
            $(function() {
                $("#tabsSubject").tabs();
            });
        </script>

    </head>
    <body>

    <c:import url="/WEB-INF/jsp/fragments/headerAdmin.jsp"/>

    <c:import url="/WEB-INF/jsp/fragments/infoSection.jsp"/>

    <div class="container">
        <div class="section students-grade-section">
            <h3> <fmt:message key="putGradesToStudents"/> </h3>
            <hr/>
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
                            <form name="userSubjectForm" method="POST" action="vstup">
                                <input type="hidden" name="command" value="setGrade">
                                <input type="hidden" name="userId" value="${user.id}">
                                <input type="hidden" name="subjectId" value="${subject.key.id}">
                                ${ user.lastName }
                                ${ user.firstName }
                                ${ user.patronymic }
                                Grade: <input class="custom-btn btn-light btn-sm text-bold" type="number" name="grade" placeholder=""
                                              min="0" max="200" step="0.1">
                                <button class="custom-btn btn-light btn-sm text-bold" type="submit">
                                    <fmt:message key="submit"/>
                                </button>
                            </form>
                        </c:forEach>
                    </div>
                </c:forEach>
            </div>
        </div>
        <!-- /students-grade-section -->

        <div class="section grade-list-section">
            <h3> <fmt:message key="showGradeList"/> </h3>
            <hr/>
            <form name="gradeListForm" method="POST" action="vstup">
                <button class="custom-btn btn-light btn-sm text-bold" type="submit">
                    <input type="hidden" name="command" value="gradeListRedirect" />
                    <fmt:message key="gradeList"/>
                </button>
            </form>
        </div>
        <!-- /grade-list-section -->

        <div class="section rating-specialties-table">
            <h3> <fmt:message key="ratingTable"/> </h3>
            <hr/>
            <div class="m-3">
                <div class="row col-md-6">
                    <table class="table table-striped table-bordered table-sm">
                        <tr>
                            <th><fmt:message key="name"/> </th>
                            <th><fmt:message key="pass"/> </th>
                        </tr>
                        <jsp:useBean id="paginationSpecialties" scope="session" type="java.util.ArrayList"/>
                        <c:forEach items="${paginationSpecialties}" var="specialty">
                            <tr>
                                <td>${specialty.name}</td>
                                <td>
                                    <form name="RateForm" method="POST" action="vstup">
                                        <input type="hidden" name="command" value="rateSpecialty">
                                        <input type="hidden" name="specialtyId" value="${specialty.id}"/>
                                        <button class="custom-btn btn-light btn-sm text-bold"><fmt:message key="count"/> </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <nav aria-label="Navigation for specialties">
                    <ul class="pagination">
                        <c:forEach begin="1" end="${sessionScope.noOfPages}" var="i">
                            <li class="page-item">
                                <form name="logOutFrom" method="POST" action="vstup">
                                    <button class="page-link">
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
        </div>
        <!-- /rating-specialties-table -->
    </div>

    <c:import url="/WEB-INF/jsp/fragments/footer.jsp"/>

<c:choose>
    <c:when test="${sessionScope.confirmRate == 'yes'}">
        <script>
            swal('<fmt:message key="success"/>', '<fmt:message key="buildRating"/>', 'success');
        </script>
        <c:set var="confirmRate" value="off" scope="session"/>
    </c:when>
    <c:when test="${sessionScope.confirmRate == 'no'}">
        <script>
            swal('<fmt:message key="error"/>', '<fmt:message key="alreadyBuildRating"/>', 'error');
        </script>
        <c:set var="confirmRate" value="off" scope="session"/>
    </c:when>
</c:choose>
    </body></html>
</fmt:bundle>
