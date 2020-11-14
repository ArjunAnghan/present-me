<%@ page import="com.presentme.utility.printAlert" %>
<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">All Faculties</h1>
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
                            <th>Faculty Code</th>
                            <th>Full Name</th>
                            <th>Email</th>
                            <th>Account Status</th>
                            <th>Lectures</th>
                            <th></th>
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
        <c:forEach var="tempFaculty" items="${allFaculties}">
        {
            id: "${tempFaculty.username}",
            email: "${tempFaculty.email}",
            name: "${tempFaculty.name}",
            status:"<c:choose><c:when test="${tempFaculty.leave == 1}">Deactivated</c:when><c:otherwise>Active</c:otherwise></c:choose>",
            lect: '<a class="btn btn-primary btn-outline btn-info" href="faculty?faculty=${tempFaculty.username}">Lectures</a>',
            act: '<c:choose><c:when test="${tempFaculty.leave == 1}"><a class="btn btn-success btn-outline" href="deactivate?faculty=${tempFaculty.username}">Activate</a></c:when><c:otherwise><a class="btn btn-danger btn-outline" href="activate?faculty=${tempFaculty.username}">Deactivate</a></c:otherwise></c:choose>'
        },
        </c:forEach>
    ];

    $(document).ready(function () {
        var table = $('#faculties').DataTable({
            "data": dataSet,
            "columns": [
                {"data": "id"},
                {"data": "name"},
                {"data": "email"},
                {"data": "status"},
                {
                    "data": "lect",
                    "orderable": false,
                    "searchable": false
                },
                {
                    "data": "act",
                    "orderable": false,
                    "searchable": false
                }

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
                "Faculties(s) registered successfully."));
    }
%>