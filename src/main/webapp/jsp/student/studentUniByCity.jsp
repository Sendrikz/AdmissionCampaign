<%@ page pageEncoding="UTF-8" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:bundle basename="pagecontent" prefix = "label." >
    <html><head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <title><fmt:message key="title"/></title>

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">

        <!-- Custom style -->
        <link rel = "stylesheet" type="text/css" href = "/css/style.css"/>
    </head>
<body>
    <c:import url="/jsp/fragments/headerStudent.jsp" />
    <div class="container-fluid">
        <div class="section universities-section" style="padding-left: 100px;margin-top: 40px">
            <dl class="row">
                <jsp:useBean id="listOfUni" scope="session" type="java.util.List"/>
                <c:forEach var="uni" items="${ listOfUni }" varStatus="status">
                <dd class="col-sm-3 account-info-elem>">
                    <form name="uniForm" method="POST" action="controller">
                        <input type="hidden" name="command" value="generateSpecialtiesByUni"/>
                        <input type="hidden" name="uniId" value=" ${ uni.id }"/>
                        <button class="custom-btn btn-light btn-lg text-bold" type="submit">${ uni.name }</button>
                    </form>
                </dd>
                </c:forEach>
            </dl>
        </div>
        <!-- /universities section -->
    </div>
    <!-- /container -->
    <c:import url="/jsp/fragments/footer.jsp"/>
</body>
</html>
</fmt:bundle>