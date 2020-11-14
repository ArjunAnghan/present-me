<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.presentme.utility.printAlert" %>
<%@ page import="com.presentme.entity.User" %>
<!DOCTYPE html>
<html lang="en">

<%
    session.setAttribute("otp", null);
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
                    <h3 class="panel-title">Forgot Password</h3>
                </div>
                <div class="panel-body">
                    <form role="form">
                        <fieldset>
                            <p>Username
                                <%
                                    User user = (User)session.getAttribute("userForgot");
                                    out.print(user.getUsername());
                            %> found</p>
                            <p>You will receive an OTP on the below email which is registered on this website , Continue
                                ?</p>
                            <a href="/password/sendOtp"
                               class="btn btn-md btn-success btn-block"><%=user.getEmail()%>
                            </a>
                            <a href="/" class="btn btn-md btn-block btn-info">Back to Login</a>
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
    if (request.getAttribute("message") != null && request.getAttribute("message").equals("Username Does not Exists")) {
        out.print(printAlert.getAlertBox("I nvalid Username",
                "The username you entered does not exists, Please try again or contact administrator"));
    } else if (request.getAttribute("message") != null && request.getAttribute("message").equals("Invalid Otp")) {
        out.print(printAlert.getAlertBox("Invalid Otp",
                "The OTP you entered was invalid, Please try sending again or contact administrator"));
    } else if (request.getAttribute("message") != null && request.getAttribute("message").equals("Invalid Login Method")) {
        out.print(printAlert.getAlertBox("Invalid Access",
                "The method you used to access content was harmful . Please try a valid method or use forgot password"));
    }
    request.setAttribute("message", null);
%>

</body>

</html>
