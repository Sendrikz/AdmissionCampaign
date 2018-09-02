<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
        <style>
            .footer {
                background-color: #313f47;
            }
            .footer-title {
                 padding-top: 115px;
             }
            .footer-title h3 {
                color: #ffffff;
                margin-bottom: 15px;
            }
            .footer-line img {
                margin-left: 646px;
                margin-bottom: 28px;
            }
            .footer-line img + img {
                margin-left: 8px;
            }
            .footer-content {
                margin: 22px 0;
            }
            .footer-text {
                margin-top: 112px;
                padding-bottom: 30px;
            }
            .footer-text span {
                color: #ffffff;
            }
        </style>
</head>
<body>
    <div class="footer container-fluid">
        <div class="footer-title text-center">
            <h3>
                Follow Us On
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