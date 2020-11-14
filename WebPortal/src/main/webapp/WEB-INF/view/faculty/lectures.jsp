<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.presentme.entity.Lecture" %>
<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h3 class="page-header">All Lectures For ${theFaculty.name}</h3>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">

                <!-- /.panel-heading -->
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="lectures">
                        <thead>
                        <tr>
                            <th>Date & Time</th>
                            <th>Slot No</th>
                            <th>Subject</th>
                            <th>Present Students</th>
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


    <%SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");%>
    var dataSet = [
        <c:forEach var="tempLecture" items="${theFaculty.lectureList}">
        {
            <%Lecture temp = (Lecture)pageContext.findAttribute("tempLecture");%>
            id: "<%=format.format(temp.getTime())%>",
            email: "${tempLecture.slotno}",
            name: "${tempLecture.subject}",
            lect: '<a class="btn btn-primary btn-outline btn-info" href="attendence?lecture=${tempLecture.id}">Attendance</a>'
        },
        </c:forEach>
    ];

    $(document).ready(function () {
        var table = $('#lectures').DataTable({
            "data": dataSet,
            "columns": [
                {"data": "id"},
                {"data": "email"},
                {"data": "name"},
                {
                    "data": "lect",
                    "orderable": false,
                    "searchable": false
                }

            ]
        });
    });

</script>