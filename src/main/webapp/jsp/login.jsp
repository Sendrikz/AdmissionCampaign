<!doctype html>
<%@ page pageEncoding="UTF-8" %>
<%@ page isELIgnored ="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : 'en_US'}" scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:bundle basename="pagecontent" prefix = "label." >
    <html lang="${language}">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel = "stylesheet" href = "../../css/style.css">
    <title><fmt:message key="title"/></title>

    <!-- Font-awesome -->
    <link rel="stylesheet" href="../../css/font-awesome.min.css">

    <script src='https://unpkg.com/sweetalert/dist/sweetalert.min.js'></script>
    <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>

    <style type="text/css">
        .select-language {
            overflow: hidden;
            position: fixed;
            right: 20px;
            top: 10px;
            z-index: 999;
        }
    </style>
</head>
<body>
<div class="select-language">
    <form>
        <select id="language" name="language" onchange="submit()">
            <option value="en_US" ${language == 'en_US' ? 'selected' : ''}>English</option>
            <option value="uk_UA" ${language == 'uk_UA' ? 'selected' : ''}>Ukrainian</option>
        </select>
    </form>
</div>
<div class="inner">
    <div class="login-content">
        <h4 class="login-title text-center"><fmt:message key="title"/></h4>
        <form name="loginForm" method="POST" action="controller">
            <input type="hidden" name="command" value="login" />
            <div class="login-form-group">
                <i class="fa fa-user"></i>
                <input type="text" class="login-form-control" id="inputEmail" name="login" placeholder="<fmt:message key="email"/>"
                       pattern="^[_A-Za-zа-яА-ЯёЁ0-9-\\+]+([.][_A-Za-zа-яА-ЯёЁ0-9-]+)*@[A-Za-zа-яА-ЯёЁ0-9-]+([.][A-Za-zа-яА-ЯёЁ0-9]+)*([.][A-Za-zа-яА-ЯёЁ]{2,})$"
                       required>
            </div>
            <!-- /login-form-group -->
            <div class="login-form-group">
                <i class="fa fa-lock"></i>
                <input type="password" class="login-form-control" id="inputPassword" name="password"
                       placeholder="<fmt:message key="password"/>" required pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$">
            </div>
            <label><fmt:message key="exampleOfPassword"/></label>
            <!-- /login-form-control -->
            <div class="login-btn-group">
                <button class="custom-btn btn-light btn-sm text-bold" type="submit"><fmt:message key="submit"/></button>
                <button type="button" class="custom-btn btn-green btn-lg" data-toggle="modal" data-target="#registrationModal">
                    <i class="fa fa-pencil-square-o"></i>
                    <fmt:message key="createAccount"/>
                </button>
            </div>
            <!-- /login-btn-group -->
        </form>
    </div>
    <!-- /login-content -->
</div>
<!-- /inner -->

<c:choose>
    <c:when test="${sessionScope.successfulRegistrated == 'yes'}">
        <script>
            swal('Good!', 'Your profile is created. Login to start', 'success');
        </script>
        <c:set var="successfulRegistrated" value="null" scope="session"/>
    </c:when>
    <c:when test="${sessionScope.successfulRegistrated == 'no'}">
        <script>
            swal('Try again!', 'Such user already created', 'error');
        </script>
        <c:set var="successfulRegistrated" value="null" scope="session"/>
    </c:when>
    <c:when test="${sessionScope.successfulLogin == 'no'}">
        <script>
            swal('Try again!', 'There is no such user', 'error');
        </script>
        <c:set var="successfulLogin" value="null" scope="session"/>
    </c:when>
</c:choose>

<!-- Modal start -->
<div class="modal fade" id="registrationModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title text-white text-center" id="loginModalLabel"><fmt:message key="registration"/></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <!-- /modal-header -->
            <form name="registerForm" method="POST" action="controller">
                <input type="hidden" name="command" value="registration" />
                <div class="modal-body">
                    <div class="row registration-form-group text-white">
                        <label class="col-2"><fmt:message key="firstName"/></label>
                        <div class="col-6">
                            <input type="text" class="registration-form-control" name="firstName" placeholder=""
                                   required pattern="^[а-яА-ЯёЁa-zA-Z]+$">
                        </div>
                        <!-- /col-6 -->
                    </div>
                    <!-- /registration-form-group -->
                    <div class="row registration-form-group text-white">
                        <label class="col-2"><fmt:message key="lastName"/></label>
                        <div class="col-6">
                            <input type="text" class="registration-form-control" name="lastName" placeholder=""
                                   required pattern="^[а-яА-ЯёЁa-zA-Z]+$">
                        </div>
                        <!-- /col-6 -->
                    </div>
                    <!-- /registration-form-group -->
                    <div class="row registration-form-group text-white">
                        <label class="col-2"><fmt:message key="patronymic"/></label>
                        <div class="col-6">
                            <input type="text" class="registration-form-control" name="patronymic" placeholder=""
                                   required pattern="^[а-яА-ЯёЁa-zA-Z]+$">
                        </div>
                        <!-- /col-6 -->
                    </div>
                    <!-- /registration-form-group -->
                    <div class="row registration-form-group text-white">
                        <label class="col-2"><fmt:message key="birthday"/></label>
                        <div class="col-6">
                            <input type="date" class="registration-form-control" name="birthday" placeholder=""
                                   required min="1948-12-31" max="2001-12-31">
                        </div>
                        <!-- /col-6 -->
                    </div>
                    <!-- /registration-form-group -->
                    <div class="row registration-form-group text-white">
                        <label class="col-2"><fmt:message key="city"/></label>
                        <div class="col-6">
                            <input type="text" class="registration-form-control" name="city" placeholder=""
                                   required pattern="^[а-яА-ЯёЁa-zA-Z]+$">
                        </div>
                        <!-- /col-6 -->
                    </div>
                    <!-- /registration-form-group -->
                    <div class="row registration-form-group text-white">
                        <label class="col-2"><fmt:message key="email"/></label>
                        <div class="col-6">
                            <input type="text" class="registration-form-control" name="email" placeholder=""
                                   pattern="^[_A-Za-zА-ЯёЁ0-9-\\+]+([.][_A-Za-zА-ЯёЁ0-9-]+)*@[A-Za-zА-ЯёЁ0-9-]+([.][A-Za-zА-ЯёЁ0-9]+)*([.][A-Za-zА-ЯёЁ]{2,})$"
                                   required>
                        </div>
                        <!-- /col-6 -->
                    </div>
                    <!-- /registration-form-group -->
                    <div class="row registration-form-group text-white">
                        <label class="col-2"><fmt:message key="password"/></label>
                        <div class="col-6">
                            <input type="password" class="registration-form-control" name="password" placeholder=""
                                   required pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$">
                        </div>
                        <!-- /col-6 -->
                    </div>
                    <!-- /registration-form-group -->
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="close"/></button>
                    <button type="submit" class="btn btn-primary"><fmt:message key="saveChanges"/></button>
                </div>
                <!-- /modal-footer -->
            </form>
        </div>
        <!-- /modal-content -->
    </div>
    <!-- /modal-dialog -->
</div>
<!-- Modal end -->

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
</fmt:bundle>