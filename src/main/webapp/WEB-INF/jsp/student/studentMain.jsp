<%@ page pageEncoding="UTF-8" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:bundle basename="pagecontent" prefix = "label." >
    <html><head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <title><fmt:message key="title"/></title>

        <!-- Sweet alert -->
        <script src='https://unpkg.com/sweetalert/dist/sweetalert.min.js'></script>
        <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

        <!-- Custom style -->
        <link rel = "stylesheet" type="text/css" href = "/css/style.css"/>

        <style>
            .subject-item img{
                width: 500px;
                height: 500px;
            }
            .grade-list-section {
                margin-bottom: 70px;
            }
            .section + .section {
                margin-top: 70px;
            }
        </style>
    </head>
    <body>
    <c:import url="/WEB-INF/jsp/fragments/headerStudent.jsp"/>
    <c:import url="/WEB-INF/jsp/fragments/infoSection.jsp"/>
    <div class="container">

        <c:if test="${sessionScope.isPassed == 'yes'}">
            <c:import url="/WEB-INF/jsp/fragments/studentAdmission.jsp"/>
        </c:if>
        <c:if test="${sessionScope.isChecked == 'yes'}">
            <c:import url="/WEB-INF/jsp/fragments/studentGrades.jsp"/>
        </c:if>

        <div class="section subject-section">
            <h3><fmt:message key="subjects"/></h3>
            <hr/>
            <div class="subject-content">
                <dl class="row subject-content-row">
                    <jsp:useBean id="subjectsList" scope="session" type="java.util.List"/>
                    <c:forEach var="elem" items="${ subjectsList }" varStatus="status">
                    <dd class="col-sm-6">
                        <div class="subject-item">
                            <img alt="" src="/img/${elem.id}.jpg" style="width: 500px; height: 500px"/>
                        </div>
                        <form name="subjectForm" method="POST" action="vstup" style="text-align: center">
                            <input type="hidden" name="command" value="registrateOnSubject" />
                            <input type="hidden" name="subject" value="${ elem.name }" />
                            <c:out value="${ elem.name }" />
                            <button class="custom-btn btn-light btn-sm text-bold" type="submit"><fmt:message key="submit"/></button>
                        </form>
                    </dd>
                    </c:forEach>
                </dl>
            </div>
            <!-- /subject-content -->
        </div>
        <!-- /subject-section -->

        <div class="section university-section">
            <h3><fmt:message key="universities"/></h3>
            <hr/>
            <button class="custom-btn btn-light btn-sm text-bold" type="submit" onclick="myFunction()"><fmt:message key="city"/></button>
            <div class="university-content" style="margin-left: 50px; margin-top: 15px;">
                <dl class="row" id="myCity" style="display: none;">
                    <jsp:useBean id="citiesList" scope="session" type="java.util.List"/>
                    <c:forEach var="city" items=" ${ citiesList }" varStatus="status">
                        <dd class="col-sm-3">
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
                            <form name="citiesForm" method="POST" action="vstup">
                                <input type="hidden" name="command" value="generateUniversitiesByCity"/>
                                <input type="hidden" name="city" value=" ${ correctCity }"/>
                                <button class="custom-btn btn-light btn-sm text-bold" type="submit">${ correctCity }</button>
                            </form>
                        </dd>
                    </c:forEach>
                </dl>
            </div>
            <!-- /university-content -->
        </div>
        <!-- /university-section -->

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
    </div>
    <!-- /container -->

    <c:import url="/WEB-INF/jsp/fragments/footer.jsp"/>

    <c:choose>
        <c:when test="${sessionScope.successfulSubject == 'yes'}">
            <script>
                swal('<fmt:message key="success"/>', '<fmt:message key="successfulRegistration"/>' , 'success');
            </script>
            <c:set var="successfulSubject" value="off" scope="session"/>
        </c:when>
        <c:when test="${sessionScope.successfulSubject == 'no'}">
            <script>
                swal('<fmt:message key="error"/>', '<fmt:message key="alreadyRegistrated"/>', 'error');
            </script>
            <c:set var="successfulSubject" value="off" scope="session"/>
        </c:when>
    </c:choose>

    <script>
        function myFunction() {
            var x = document.getElementById('myCity');
            if (x.style.display === 'none') {
                x.style.display = '';
            } else {
                x.style.display = 'none';
            }
        }
    </script>

    </body></html>
</fmt:bundle>