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
                        <c:forEach var="tempAttendence" items="${theLecture.attendances}">
                            <tr>
                                <td>${tempAttendence.student.username}</td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                    <!-- /.table-responsive -->
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->

            <div class="col-md-4"></div>
            <div class="col-md-4">
                <form action="addStudentAttendence" method="post">

                    <div class="form-groups">
                        <label>Username(Case Insensitive, Multiple Usernames Separated By Commas) </label>
                        <input type="text" name="username" class="form-control" required>
                    </div>
                    <input type="hidden" value="${theLecture.id}" name="lectureID">
                    <div class="form-groups ">
                        <input type="submit" class="btn btn-primary btn-outline" value="Add"/>
                    </div>
                </form>

            </div>
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
    $(document).ready(function () {
        var table = $('#students').DataTable({});
    });

</script>