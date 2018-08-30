<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
        <style>
            /* ----- Inner ----- */
            .inner-main {
                background-image: url('/img/main.jpg');
                height: 100vh;
                background-repeat: no-repeat;
                background-position: center;
                background-size: cover;
                text-align: center;
            }
            .inner-main::before {
                content:'';
                display: inline-block;
                height: 100%;
                vertical-align: middle;
                margin-left: -4px;
            }
            .inner-content-main {
                vertical-align: middle;
                display: inline-block;
                position: absolute;
                top: 50%;
                left: 27%;
            }
            .inner-main h1 {
                color: #fff;
                font-size: 30px;
                font-family: 'Dosis', sans-serif;
                font-weight: 400;
                letter-spacing: 0.3em;
                text-indent: 1.7em;
            }
            /* ----- Inner end ----- */
        </style>
    </head>
    <body>
    <!-- HEADER START -->
    <div class="header">
        <div class="container-fluid">
            <ul class="header-nav text-uppercase">
                <li>
                <form name="redirectForm" method="POST" action="controller">
                    <button>
                        <input type="hidden" name="command" value="returnToMain" />
                        HOME
                    </button>
                </form>
                </li>
                <li>
                    <form name="logOutFrom" method="POST" action="controller">
                        <button>
                            <input type="hidden" name="command" value="logOut" />
                            LOGOUT
                        </button>
                    </form>
                    <!-- /logOutFrom -->
                </li>
            </ul>
        </div>
        <!-- /header-nav -->
    </div>
    <!-- HEADER END -->
    <div class="inner-main">
        <%--<div class="inner-content-main">--%>
            <%--<h1 class="text-uppercase" style="color: black;"> Admission campaign 2018 </h1>--%>
        <%--</div>--%>
        <!-- /inner-content-main -->
    </div>
    <!-- /inner-main -->
    </body>
    </html>
</fmt:bundle>