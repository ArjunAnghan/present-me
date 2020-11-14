<%@ page import="com.presentme.utility.printAlert" %>
<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">All Admins</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">

                <!-- /.panel-heading -->
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="faculties">
                        <thead>
                        <tr>
                            <th>Username</th>
                            <th>Full Name</th>
                            <th>Email</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                    <!-- /.table-responsive -->
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-8 -->
        <!-- /.row -->
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
</div>

<%@include file="templates/footer.jsp" %>

<script>


    var dataSet = [
        <c:forEach var="tempAdmin" items="${allAdmins}">
        {
            id: "${tempAdmin.username}",
            email: "${tempAdmin.email}",
            name: "${tempAdmin.name}"
        },
        </c:forEach>
    ];

    $(document).ready(function () {
        var table = $('#faculties').DataTable({
            "data": dataSet,
            "columns": [
                {"data": "id"},
                {"data": "name"},
                {"data": "email"}
            ]
        });
    });

</script>

<%
    String message = (String) request.getAttribute("message");
    if (message != null && message.equals("clashg")) {
        out.print(printAlert.getAlertBox("User Already Exists",
                "The Username you entered already exists in database, Please check Again"));
    } else if (message != null && message.equals("addg")) {
        out.print(printAlert.getAlertBox("Added Successfully",
                "Admin registered successfully."));
    }
%>