<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.presentme.utility.printAlert" %>
<!DOCTYPE html>
<html lang="en">
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
                    <h3 class="panel-title">Add Password</h3>
                </div>
                <div class="panel-body">
                    <form role="form" method="post" action="/addPassword">
                        <fieldset>
                            <div class="form-groups">
                                <input class="form-control" placeholder="Password" name="pass" type="password" autofocus
                                       minlength="6" required title="Please Enter password">
                            </div>
                            <div class="form-groups">
                                <input class="form-control" placeholder="Confirm Password" name="cpass" type="password"
                                       minlength="6" required title="Please Enter Password">
                            </div>
                            <!-- Change this to a button or input when using this as a form -->
                            <input type="submit" class="btn btn-md btn-success"
                                   style="width: 45%;float: left;margin-right: 30px" value="Add Password"/>
                            <a href="/" class="btn btn-md btn-info" style="width: 45%">Back to Login</a>
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
    if (request.getAttribute("message") != null && request.getAttribute("message").equals("Passwords do not match")) {
        out.print(printAlert.getAlertBox("Passwords Does not Match",
                "The passwords you entered do not match , Please try Again"));
    } else if (request.getAttribute("message") != null && request.getAttribute("message").equals("Passwords cannot be only white spaces")) {
        out.print(printAlert.getAlertBox("Passwords cannot be only white spaces",
                "Invalid passwords, You cannot use only white spaces in passwords"));
    }
    request.setAttribute("message", null);
%>

</body>

</html>
