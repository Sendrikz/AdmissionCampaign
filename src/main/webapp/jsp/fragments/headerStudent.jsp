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
        <link rel = "stylesheet" type="text/css" href = "../../css/style.css"/>
        <style>
            .container-fluid {
                padding: 0 15px;
            }
            .header {
                overflow: hidden;
                padding-top: 26px;
                position: fixed;
                width: 100%;
                top: 0;
                z-index: 999;
                background-color: #313f47;
            }
            .header .container-fluid {
                max-width: 1576px;
                margin: auto;
            }
            .header-nav {
                float: right;
                color: rgba(255, 255, 255, 0.75);
            }
            .header .header-nav li {
                float:left;
                line-height: 22px;
            }
            .header .header-nav li + li {
                margin-left: 35px;
            }
            /* Header end */
            ul, ol{
                list-style: none;
                padding: 0;
                margin: 0;
            }
            button {
                outline: none;
                border: none;
                cursor: pointer;
                padding: 0;
                background-color: inherit;
                color: inherit;
            }
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
    </body>
    </html>
</fmt:bundle>