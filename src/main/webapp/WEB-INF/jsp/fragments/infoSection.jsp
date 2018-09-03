<%@ page pageEncoding="UTF-8" %>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:bundle basename="pagecontent" prefix = "label." >
    <html>
    <head>
    <title>Title</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

    <!-- Custom style -->
    <link rel = "stylesheet" type="text/css" href = "/css/style.css"/>

</head>
<body>
<div class="container">
    <div class="section account-info-section" style="margin-top: 110px">
        <h3><fmt:message key="account"/></h3>
        <div class="account-info">
            <hr/>
            <dl class="row account-info-role">
                <dd class="col-sm-6 account-info-elem>">
                    <fmt:message key="lastName"/>: ${ sessionScope.loginedUser.lastName }
                </dd>
                <dd class="col-sm-6 account-info-elem>">
                    <fmt:message key="birthday"/>: ${ sessionScope.loginedUser.birthday }
                </dd>
                <dd class="col-sm-6 account-info-elem>">
                    <fmt:message key="firstName"/>: ${ sessionScope.loginedUser.firstName }
                </dd>
                <dd class="col-sm-6 account-info-elem>">
                    <fmt:message key="city"/>: ${ sessionScope.loginedUser.city }
                </dd>
                <dd class="col-sm-6 account-info-elem>">
                    <fmt:message key="patronymic"/>: ${ sessionScope.loginedUser.patronymic }
                </dd>
                <dd class="col-sm-6 account-info-elem>">
                    <fmt:message key="email"/>: ${ sessionScope.loginedUser.email }
                </dd>
            </dl>
            <!-- /account-info-role -->
        </div>
        <!-- /account-info -->
    </div>
    <!-- /account-info-section -->
</div>
</body>
</html>
</fmt:bundle>