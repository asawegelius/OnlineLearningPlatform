<%-- 
    Document   : loginRegisterContent
    Created on : Apr 20, 2016, 1:47:05 PM
    Author     : asawe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script>
function myFunction() {
    document.getElementById('loginForm').reset();
    document.getElementById('registerForm').reset();
    document.getElementById('forgotForm').reset();
}
</script>
<!-- -Login Modal -->

<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content login-modal">
            <div class="modal-header login-modal-header">
                <button onclick="myFunction()" type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title text-center" id="loginModalLabel">USER AUTHENTICATION</h4>
            </div>
            <div class="modal-body">
                <div class="text-center">
                    <div role="tabpanel" class="login-tab">
                        <!-- Nav tabs -->
                        <ul class="nav nav-tabs" role="tablist">
                            <li role="presentation" class="active"><a id="signin-taba" href="#home" aria-controls="home" role="tab" data-toggle="tab">Sign In</a></li>
                            <li role="presentation"><a id="signup-taba" href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Sign Up</a></li>
                            <li role="presentation"><a id="forgetpass-taba" href="#forget_password" aria-controls="forget_password" role="tab" data-toggle="tab">Forget Password</a></li>
                        </ul>

                        <!-- Tab panes -->
                        <div class="tab-content">
                            <div role="tabpanel" class="tab-pane active text-center" id="home">
                                &nbsp;&nbsp;
                                <span id="login_fail" class="response_error" style="display: none;">Loggin failed, please try again.</span>
                                <div class="clearfix"></div>
                                <form action="" method="post" id="loginForm">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <div class="input-group-addon"><i class="fa fa-at"></i></div>
                                            <input type="email" class="form-control" id="login_email" placeholder="Email" required>
                                        </div>
                                        <span class="help-block has-error" id="email-error"></span>
                                    </div>
                                    <div class="form-group">
                                        <div class="input-group">
                                            <div class="input-group-addon"><i class="fa fa-lock"></i></div>
                                            <input type="password" class="form-control" id="login_password" placeholder="Password" required>
                                        </div>
                                        <span class="help-block has-error" id="password-error"></span>
                                    </div>
                                    <button  type="submit" id="login_btn" class="btn btn-block bt-login" data-loading-text="Signing In....">Login</button>
                                    <div class="clearfix"></div>

                                </form>
                            </div>
                            <div role="tabpanel" class="tab-pane" id="profile">
                                &nbsp;&nbsp;
                                <span id="registration_fail" class="response_error" style="display: none;">Registration failed, please try again.</span>
                                <div class="clearfix"></div>
                                <form action="" method="post" id="registerForm">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <div class="input-group-addon"><i class="fa fa-at"></i></div>
                                            <input type="email" class="form-control email" id="register_email" name="remail" placeholder="Email" required>
                                        </div>
                                        <span class="help-block has-error" data-error='0' id="remail-error"></span>
                                    </div>
                                    <div class="form-group">
                                        <div class="input-group">
                                            <div class="input-group-addon"><i class="fa fa-lock"></i></div>
                                            <input type="password" class="form-control" id="rpassword" name="rpassword"  placeholder="Password" required>
                                        </div>
                                        <span class="help-block has-error" data-error='0' id="rpassword-error"></span>
                                    </div>
                                    <div class="form-group">
                                        <div class="input-group">
                                            <div class="input-group-addon"><i class="fa fa-lock"></i></div>
                                            <input type="password" class="form-control" id="confirm_password" placeholder="Repeat password" required>
                                        </div>
                                        <span class="help-block has-error" data-error='0' id="confirm_password-error"></span>
                                    </div>
                                    <button type="submit" id="register_btn" class="btn btn-block bt-login" data-loading-text="Registering....">Register</button>
                                    <div class="clearfix"></div>

                                </form>
                            </div>
                            <div role="tabpanel" class="tab-pane text-center" id="forget_password">
                                &nbsp;&nbsp;
                                <span id="reset_fail" class="response_error" style="display: none;"></span>
                                <div class="clearfix"></div>
                                <form action="" method="post" id="forgotForm">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <div class="input-group-addon"><i class="fa fa-at"></i></div>
                                            <input type="email" class="form-control" id="femail"  placeholder="Email" required>
                                        </div>
                                        <span class="help-block has-error" data-error='0' id="femail-error"></span>
                                    </div>

                                    <button  type="submit" id="reset_btn" class="btn btn-block bt-login" data-loading-text="Please wait....">Forget Password</button>
                                    <div class="clearfix"></div>

                                </form>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

        </div>
    </div>
</div>
<!-- - Login Model Ends Here -->

<script>
    $(document).ready(function () {
        $(document).on('click', '.signup-tab', function (e) {
            e.preventDefault();
            $('#signup-taba').tab('show');
        });

        $(document).on('click', '.signin-tab', function (e) {
            e.preventDefault();
            $('#signin-taba').tab('show');
        });

        $(document).on('click', '.forgetpass-tab', function (e) {
            e.preventDefault();
            $('#forgetpass-taba').tab('show');
        });
    });


</script>
<script>
    var password = document.getElementById("rpassword")
            , confirm_password = document.getElementById("confirm_password");

    function validatePassword() {
        if (password.value != confirm_password.value) {
            confirm_password.setCustomValidity("Passwords Don't Match");
        } else {
            confirm_password.setCustomValidity('');
        }
    }

    password.onchange = validatePassword;
    confirm_password.onkeyup = validatePassword;
</script>
