<%@ page pageEncoding="UTF-8" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:bundle basename="pagecontent" prefix = "label." >
    <html><head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Sweet alert -->
        <script src='https://unpkg.com/sweetalert/dist/sweetalert.min.js'></script>
        <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>

        <!-- JQuery -->
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

        <!-- Custom style -->
        <link rel = "stylesheet" type="text/css" href = "/css/style.css"/>

        <script>
            $( function() {
                $("#tabs").tabs();
            } );
        </script>
    </head>
    <body>
    <c:import url="/WEB-INF/jsp/fragments/headerStudent.jsp" />

    <div class="container">
        <div class="university-section" style="margin-top: 40px">
            <div class="university-info">
                <h3> <fmt:message key="university"/> </h3>
                <hr/>
                <p> <fmt:message key="name"/>: ${ sessionScope.selectedUni.name }</p>
                <p> <fmt:message key="address"/>: ${ sessionScope.selectedUni.address }</p>
                <p> <fmt:message key="city"/>: ${ sessionScope.selectedUni.city }</p>
                <h3> <fmt:message key="faculties"/> </h3>
                <hr/>
            </div>
            <!-- /university-info -->

            <div class="university-content">
                <jsp:useBean id="facultySpecialtyMap" scope="session" type="java.util.HashMap"/>
                <jsp:useBean id="specialtySubjectMap" scope="session" type="java.util.HashMap"/>
                <div id="tabs">
                    <ul>
                        <c:forEach var="map" items="${ facultySpecialtyMap }" varStatus="status">
                            <li><a href="#tabs-${ map.key.id }">${ map.key.name }</a></li>
                        </c:forEach>
                    </ul>
                    <c:forEach var="map" items="${ facultySpecialtyMap }" varStatus="status">
                            <div id="tabs-${ map.key.id }">
                                <c:forEach var="specialtyMap" items="${map.value}" varStatus="status">
                                    <form name="specialtyForm" method="POST" action="vstup">
                                        <input type="hidden" name="command" value="registrationForSpecialty"/>
                                        <input type="hidden" name="specialtyToRegistrId" value="${ specialtyMap.id }"/>
                                        <h3>${ specialtyMap.name }</h3>
                                        <p> <fmt:message key="quantityOfStudents"/>: ${ specialtyMap.quantityOfStudents }</p>
                                        <c:if test="${specialtySubjectMap.containsKey(specialtyMap)}">
                                            <h5> <fmt:message key="subjects"/>: </h5>
                                            <dl class="row">
                                            <c:forEach var="subjects" items="${specialtySubjectMap.get(specialtyMap)}">
                                                <dd class="col-sm-4">
                                                <p> <fmt:message key="nameOfSubject"/>: ${subjects.key.name} </p>
                                                <p> <fmt:message key="durationOfTest"/>: ${subjects.key.duration}</p>
                                                <p> <fmt:message key="coef"/>: ${subjects.value}</p>
                                                </dd>
                                            </c:forEach>
                                            </dl>
                                        </c:if>
                                        <button class="custom-btn btn-light btn-sm text-bold" type="submit"><fmt:message key="submit"/></button>
                                        <hr/>
                                    </form>
                                </c:forEach>
                            </div>
                    </c:forEach>
                </div>
                <!-- /tabs -->
            </div>
            <!-- /university-content -->
        </div>
        <!-- /university-section -->
    </div>
    <!-- /container -->

    <c:import url="/WEB-INF/jsp/fragments/footer.jsp"/>

        <c:choose>
            <c:when test="${sessionScope.successfulSpecialty == 'yes'}">
                <script>
                    swal('Success!', 'You are successfully registrated', 'success');
                </script>
                <c:set var="successfulSpecialty" value="null" scope="session"/>
            </c:when>
            <c:when test="${sessionScope.notAllSubjects == 'yes'}">
                <script>
                    swal('Error!', 'You haven`t registrated on subject which is needed here', 'error');
                </script>
                <c:set var="notAllSubjects" value="null" scope="session"/>
            </c:when>
            <c:when test="${sessionScope.successfulSpecialty == 'no'}">
                <script>
                    swal('Error!', 'You have already registrated', 'error');
                </script>
                <c:set var="successfulSpecialty" value="null" scope="session"/>
            </c:when>
        </c:choose>
    </body>
    </html>
</fmt:bundle>