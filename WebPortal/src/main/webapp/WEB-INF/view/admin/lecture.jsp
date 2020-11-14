<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>


<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h3 class="page-header">All Present Students</h3>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default col-md-6">

                <!-- /.panel-heading -->
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="students">
                        <thead>
                        <tr>
                            <th>Student ID</th>
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
        <c:forEach var="tempAttendence" items="${theLecture.attendances}">
        {
            id: "${tempAttendence.student.username}"
        },
        </c:forEach>
    ];

    $(document).ready(function () {
        var table = $('#students').DataTable({
            "data": dataSet,
            "columns": [
                {"data": "id"}
            ]
        });
    });

</script>