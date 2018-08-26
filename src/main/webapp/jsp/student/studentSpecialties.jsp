<%@ page pageEncoding="UTF-8" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:bundle basename="pagecontent" prefix = "label." >
    <html><head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src='https://unpkg.com/sweetalert/dist/sweetalert.min.js'></script>
        <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>

        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script>
            $( function() {
                $("#tabs").tabs();
            } );
        </script>
    </head>
    <body>

    <c:import url="/jsp/fragments/headerStudent.jsp" />
        <p> Name: ${ sessionScope.selectedUni.name }</p>
        <p> Address: ${ sessionScope.selectedUni.address }</p>
        <p> City: ${ sessionScope.selectedUni.city }</p>
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
                            <form name="specialtyForm" method="POST" action="controller">
                                <input type="hidden" name="command" value="registrationForSpecialty"/>
                                <input type="hidden" name="specialtyToRegistrId" value="${ specialtyMap.id }"/>
                                <p> Name: ${ specialtyMap.name }</p>
                                <p> Quantity of students: ${ specialtyMap.quantityOfStudents }</p>
                                <c:if test="${specialtySubjectMap.containsKey(specialtyMap)}">
                                    <p> Subjects: </p>
                                    <c:forEach var="subjects" items="${specialtySubjectMap.get(specialtyMap)}">
                                        <p> Name of subject: ${subjects.key.name} </p>
                                        <p> Duration of test: ${subjects.key.duration}</p>
                                        <p> Coef: ${subjects.value}</p>
                                    </c:forEach>
                                </c:if>
                                <button><fmt:message key="submit"/></button>
                            </form>
                        </c:forEach>
                    </div>
            </c:forEach>
        </div>
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