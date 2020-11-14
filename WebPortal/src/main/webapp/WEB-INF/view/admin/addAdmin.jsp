<%@ page import="com.presentme.utility.printAlert" %>
<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>
<div id="page-wrapper">
    <div class="container-fluid">


        <div class="col-md-4">
            <div class="row">
                <h1 class="page-header" style="display: inline">
                    <br>Add Admin
                </h1>
                <h6 class="text text-danger">(Be Careful when adding an admin as admin gives the user supreme
                    rights. Admins can
                    not be deleted by another admins, you must contact the database Administrator to delete the
                    admin.)</h6>
            </div>
            <br>

            <form action="addAdmin" method="post">

                <div class="form-groups">
                    <label>Username</label>
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
                <div class="form-groups">
                    <p style="font-size: 16px"><a href="#" data-toggle="modal"
                                                  data-target="#addAdmin"
                                                  class="btn btn-danger btn-outline">Add</a></p>
                </div>
                <%=printAlert.getSubmitBox("addAdmin", "Confirm Addition ?", "Select \"Confirm\" below to add the admin ")%>

            </form>
        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->


<%@include file="templates/footer.jsp" %>