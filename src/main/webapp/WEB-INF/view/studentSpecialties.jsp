<%@ page pageEncoding="UTF-8" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:bundle basename="pagecontent" prefix = "label." >
    <html><head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>jQuery UI Tabs - Default functionality</title>
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script>
            $( function() {
                $( "#tabs" ).tabs();
            } );
        </script>
    </head>
    <body>
        <jsp:useBean id="facultySpecialtyMap" scope="session" type="java.util.HashMap"/>
        <div id="tabs">
            <ul>
                <c:forEach var="map" items="${ facultySpecialtyMap }" varStatus="status">
                    <li><a href="#tabs-${ map.key.id }">${ map.key.name }</a></li>
                </c:forEach>
            </ul>
            <c:forEach var="map" items="${ facultySpecialtyMap }" varStatus="status">
                    <div id="tabs-${ map.key.id }">
                        <p> ${ map.value }</p>
                    </div>
            </c:forEach>
        </div>
    </body>
    </html>
</fmt:bundle>