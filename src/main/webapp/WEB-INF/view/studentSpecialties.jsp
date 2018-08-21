<%@ page pageEncoding="UTF-8" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:bundle basename="pagecontent" prefix = "label." >
    <html><head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

        <script src='https://unpkg.com/sweetalert/dist/sweetalert.min.js'></script>
        <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>
        <script>
            $( function() {
                $( "#tabs" ).tabs();
            } );
        </script>
    </head>
    <body>
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
                            <form name="specialtyForm" method="GET" action="controller">
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
        <c:if test="${sessionScope.successful == 'yes'}">
            <script>
                swal('Success!', 'You are successfully registrated', 'success');
            </script>
        </c:if>
        <c:if test="${sessionScope.successful == 'no'}">
            <script>
                swal('Error!', 'You have already registrated', 'error');
            </script>
        </c:if>
    </body>
    </html>
</fmt:bundle>