<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.presentme.utility.printAlert" %>
<!DOCTYPE html>
<html lang="en">

<%
    if (session != null)
        session.setMaxInactiveInterval(1);
%>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SAS</title>

    <!-- Bootstrap Core CSS -->
    <link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="<c:url value="/resources/vendor/metisMenu/metisMenu.min.css"/>" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<c:url value="/resources/dist/css/sb-admin-2.css"/>" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="<c:url value="/resources/vendor/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet"
          type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="<c:url value="/resources/js/html5shiv.js" />"></script>
    <
    script
    src = "<c:url value=" / resources / js / respond.min.js
    " />" ></script>
    <![endif]-->

</head>

<body>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">Please Sign In</h3>
                </div>
                <div class="panel-body">
                    <form role="form" method="post" action="/login" id="loginForm">
                        <fieldset>
                            <div class="form-groups">
                                <input class="form-control" placeholder="Username" name="username" type="text"
                                       title="Please Enter Username" autofocus required>
                            </div>
                            <div class="form-groups">
                                <input class="form-control" placeholder="Password" name="password" type="password"
                                       value="" title="Please Enter Password" required>
                            </div>
                            <!-- Change this to a button or input when using this as a form -->
                            <input type="submit" class="btn btn-md btn-success"
                                   style="width: 45%;float: left;margin-right: 30px" value="Login"/>
                            <a href="/forgot" class="btn btn-md btn-danger" style="width: 45%">Forgot
                                Password</a>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- jQuery -->
<script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>

<!-- Bootstrap Core JavaScript -->
<script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.min.js" />"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="<c:url value="/resources/vendor/metisMenu/metisMenu.min.js" />"></script>

<!-- Custom Theme JavaScript -->
<script src="<c:url value="/resources/dist/js/sb-admin-2.js" />"></script>

<script src="<c:url value="/resources/vendor/raphael/raphael.min.js" />"></script>
<script src="<c:url value="/resources/vendor/morrisjs/morris.min.js" />"></script>
<script src="<c:url value="/resources/data/morris-data.js" />"></script>
<%
    String message = (String) request.getAttribute("message");
    if (message != null && message.equals("Invalid Password")) {
        out.print(printAlert.getAlertBox("Invalid Username or Password",
                "The username or password you entered is incorrect, Please try again or use forget password"));
    } else if (message != null && message.equals("Invalid Login Method")) {
        out.print(printAlert.getAlertBox("Invalid Access",
                "The method you used to access content was harmful . Please try a valid method or use forgot password"));
    } else if (message != null && message.equals("Password Reset")) {
        out.print(printAlert.getAlertBox("Password Reset Successfully",
                "You Successfully reset your password , Please enter new password to login"));
    } else if (message != null && message.equals("Account Activation")) {
        out.print(printAlert.getAlertBox("Account Activation",
                "Your Account must be Activated In order to login , Please check your inbox of your registered account for activation link."));
    } else if (message != null && message.equals("Invalid Link")) {
        out.print(printAlert.getAlertBox("Invalid Activation Link",
                "The link you used to activate was invalid, Please try a valid Link that was sent to your email"));
    } else if (message != null && message.equals("Already Activated")) {
        out.print(printAlert.getAlertBox("Account Already Activated",
                "Your Account was already activated and password was set , you cannot use the activation link now."));
    } else if (message != null && message.equals("Activation Done")) {
        out.print(printAlert.getAlertBox("Account Activated",
                "Your Account was successfully activated and password was set."));
    }else if (message != null && message.equals("No Account")) {
        out.print(printAlert.getAlertBox("Account Not Available Or Deactivated",
                "Your Account was either deactivated or your username must be incorrect"));
    }else if (message != null && message.equals("Invalid OTP")) {
        out.print(printAlert.getAlertBox("OTP Invalid",
                "Your OTP was Invalid , Please try again."));
    }
    request.setAttribute("message", null);
%>

</body>

</html>
