<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">All Students</h1>
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
                            <th>Student ID</th>
                            <th>Email</th>
                            <th>Full Name</th>
                            <th>Group Name</th>
                            <th>Clear IMEI</th>
                            <th>Clear Password</th>
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
        <c:forEach var="tempStudent" items="${allStudents}">
        {
            id: "${tempStudent.username}",
            email: "${tempStudent.email}",
            name: "${tempStudent.name}",
            gname:"${tempStudent.groupsEntity.groupname}",
            clc: '<c:if test="${tempStudent.imei != null && !tempStudent.imei.trim().equals(\"\")}"><a class="btn btn-primary btn-outline btn-danger" href="clearImei?student=${tempStudent.username}">Clear IMEI</a></c:if>',
            clp: '<c:if test="${tempStudent.password != null}"><a class="btn btn-primary btn-outline btn-danger" href="clearpassword?student=${tempStudent.username}">Clear password</a></c:if>'
        },
        </c:forEach>
    ];

    $(document).ready(function () {
        var table = $('#faculties').DataTable({
            "data": dataSet,
            "columns": [
                {"data": "id"},
                {"data": "email"},
                {"data": "name"},
                {"data":"gname"},
                {
                    "data": "clc",
                    "orderable": false,
                    "searchable": false
                },
                {
                    "data": "clp",
                    "orderable": false,
                    "searchable": false
                }

            ]
        });
    });

</script>