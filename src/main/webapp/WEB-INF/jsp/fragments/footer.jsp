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

        <!-- Font-awesome -->
        <link rel="stylesheet" href="../../../css/font-awesome.min.css">

        <!-- Custom style -->
        <link rel = "stylesheet" type="text/css" href = "/css/style.css"/>
</head>
<body>
    <div class="footer container-fluid">
        <div class="footer-title text-center">
            <h3>
                <fmt:message key="followUs"/>
            </h3>
        </div>
        <div class="footer-line">
            <img src="/img/line.jpg"/>
        </div>
        <!-- /footer-title -->
        <div class="footer-content text-center">
            <img src="/img/facebook.jpg"/>
            <img src="/img/twitter.jpg"/>
            <img src="/img/youtube.jpg"/>
            <img src="/img/pinterest.jpg"/>
            <img src="/img/in.jpg"/>
        </div>
        <div class="footer-text text-center">
            <span class="footer-copyright text-uppercase"><i class="fa fa-copyright"></i>
                Yuryeva Olha 2018
            </span>
        </div>
        <!-- /footer-text -->
    </div>
    <!-- /container-fluid  -->
</body>
</html>
</fmt:bundle>