<%@ page import="com.presentme.utility.printAlert" %>
<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>
<div id="page-wrapper">
    <div class="container-fluid">


        <div class="col-md-12">
            <div class="row">
                <h1 class="page-header" style="display: inline">
                    <br>Add Faculty
                </h1>
            </div>
            <br>

            <form action="addFaculty" method="post">


                <div class="col-md-4">
                    <div class="form-groups">
                        <label>Faculty Code(Username)</label>
                        <input type="text" name="username" value="" class="form-control" required>
                    </div>
                    <div class="form-groups">
                        <label>Email</label>
                        <input type="email" name="email" value="" class="form-control" required>
                    </div>
                    <div class="form-groups">
                        <label>Full Name </label>
                        <input type="text" name="name" value="" class="form-control" required>
                    </div>
                    <div class="form-groups ">
                        <input type="submit" class="btn btn-primary btn-outline" value="Add"/>
                    </div>

                </div><!--Main Content-->

            </form>
            <!-- SIDEBAR-->

            <aside id="asd" class="col-md-2">
                <strong>
                    <div style="text-align: center;">OR</div>
                </strong>

            </aside>

            <form action="addFacultyCsv" method="post" enctype="multipart/form-data">
                <aside id="admin_sidebar" class="col-md-4">
                    <h3><label>Upload CSV</label></h3>
                    <h6>(There should be no heading and the fields should be in same order as on the left side. Max Size
                        16MB .Duplicates and records already stored in database will be discarded. In case of error at
                        any record, the execution will be stopped at that record number. CSV file will not be stored on
                        server.)</h6>
                    <input type="file" name="facultycsv" class="form-control" accept=".csv"
                           required>

                    <br>
                    <div class="form-groups ">
                        <input type="submit" class="btn btn-primary btn-outline" value="Add Faculties"/>
                    </div>


                </aside><!--SIDEBAR-->
            </form>

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->


<%@include file="templates/footer.jsp" %>
<%
    String message = (String) request.getAttribute("message");
    if (message != null && message.equals("Invalid Extension")) {
        out.print(printAlert.getAlertBox("File Extension Invalid",
                "The File you submitted has invalid Extension."));
    } else if (message != null && message.equals("Invalid Contents")) {
        out.print(printAlert.getAlertBox("Invalid Contents",
                "The Data should be of only 3 columns, FacultyCode-Email-FullName without any column title or labels"));
    }
    request.setAttribute("message", null);
%>