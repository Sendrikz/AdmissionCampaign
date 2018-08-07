<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel = "stylesheet" href = "../../css/style.css">
    <title>Login</title>

    <!-- Font-awesome -->
    <link rel="stylesheet" href="../../css/font-awesome.min.css">

</head>
<body>
<div class="inner">
    <div class="login-content">
        <h4 class="login-title text-center">LOGIN</h4>
        <form name="loginForm" method="POST" action="controller">
            <input type="hidden" name="command" value="login" />
            <div class="login-form-group">
                <i class="fa fa-user"></i>
                <input type="text" class="login-form-control" id="inputEmail" name="login" placeholder="Email address"
                       pattern="^[_A-Za-zа-яА-ЯёЁ0-9-\\+]+([.][_A-Za-zа-яА-ЯёЁ0-9-]+)*@[A-Za-zа-яА-ЯёЁ0-9-]+([.][A-Za-zа-яА-ЯёЁ0-9]+)*([.][A-Za-zа-яА-ЯёЁ]{2,})$"
                       required>
            </div>
            <!-- /login-form-group -->
            <div class="login-form-group">
                <i class="fa fa-lock"></i>
                <input type="password" class="login-form-control" id="inputPassword" name="password"
                       placeholder="Password" required pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$">
            </div>
            <label>Example of password: Password1 (min 8 symbols)</label>
            <!-- /login-form-control -->
            <div class="login-btn-group">
                <button class="custom-btn btn-light btn-sm text-bold" type="submit">Submit</button>
                <button type="button" class="custom-btn btn-green btn-lg" data-toggle="modal" data-target="#registrationModal">
                    <i class="fa fa-pencil-square-o"></i>
                    Create An Account
                </button>
            </div>
            <!-- /login-btn-group -->
        </form>
    </div>
    <!-- /login-content -->
</div>
<!-- /inner -->

<!-- Modal start -->
<div class="modal fade" id="registrationModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title text-white text-center" id="loginModalLabel">Registration</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <!-- /modal-header -->
            <form name="login-form" action="/hello" method="GET">
                <div class="modal-body">
                    <div class="row registration-form-group text-white">
                        <label class="col-2">FirstName</label>
                        <div class="col-6">
                            <input type="text" class="registration-form-control" name="firstName" placeholder=""
                                   required pattern="^[а-яА-ЯёЁa-zA-Z]+$">
                        </div>
                        <!-- /col-6 -->
                    </div>
                    <!-- /registration-form-group -->
                    <div class="row registration-form-group text-white">
                        <label class="col-2">LastName</label>
                        <div class="col-6">
                            <input type="text" class="registration-form-control" name="lastName" placeholder=""
                                   required pattern="^[а-яА-ЯёЁa-zA-Z]+$">
                        </div>
                        <!-- /col-6 -->
                    </div>
                    <!-- /registration-form-group -->
                    <div class="row registration-form-group text-white">
                        <label class="col-2">Patronymic</label>
                        <div class="col-6">
                            <input type="text" class="registration-form-control" name="patronymic" placeholder=""
                                   required pattern="^[а-яА-ЯёЁa-zA-Z]+$">
                        </div>
                        <!-- /col-6 -->
                    </div>
                    <!-- /registration-form-group -->
                    <div class="row registration-form-group text-white">
                        <label class="col-2">Birthday</label>
                        <div class="col-6">
                            <input type="date" class="registration-form-control" name="birthday" placeholder=""
                                   required min="1948-12-31" max="2001-12-31">
                        </div>
                        <!-- /col-6 -->
                    </div>
                    <!-- /registration-form-group -->
                    <div class="row registration-form-group text-white">
                        <label class="col-2">City</label>
                        <div class="col-6">
                            <input type="text" class="registration-form-control" name="city" placeholder=""
                                   required pattern="^[а-яА-ЯёЁa-zA-Z]+$">
                        </div>
                        <!-- /col-6 -->
                    </div>
                    <!-- /registration-form-group -->
                    <div class="row registration-form-group text-white">
                        <label class="col-2">Email</label>
                        <div class="col-6">
                            <input type="text" class="registration-form-control" name="email" placeholder=""
                                   pattern="^[_A-Za-zА-ЯёЁ0-9-\\+]+([.][_A-Za-zА-ЯёЁ0-9-]+)*@[A-Za-zА-ЯёЁ0-9-]+([.][A-Za-zА-ЯёЁ0-9]+)*([.][A-Za-zА-ЯёЁ]{2,})$"
                                   required>
                        </div>
                        <!-- /col-6 -->
                    </div>
                    <!-- /registration-form-group -->
                    <div class="row registration-form-group text-white">
                        <label class="col-2">Password</label>
                        <div class="col-6">
                            <input type="password" class="registration-form-control" name="password" placeholder=""
                                   required pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$">
                        </div>
                        <!-- /col-6 -->
                    </div>
                    <!-- /registration-form-group -->
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Save changes</button>
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
