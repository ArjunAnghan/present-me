<%@ page import="com.presentme.utility.printAlert" %>
<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>
<div id="page-wrapper">
    <div class="container-fluid">


        <div class="col-md-4">
            <div class="row">
                <h1 class="page-header" style="display: inline">
                    <br>Add Group(s)
                </h1>
            </div>
            <br>

            <form action="addGroup" method="post">

                <div class="form-groups">
                    <label>GroupName (Separated by commas)</label>
                    <input type="text" name="groupname" value="" class="form-control" required>
                </div>
                <div class="form-groups ">
                    <input type="submit" class="btn btn-primary btn-outline" value="Add"/>
                </div>

            </form>
        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->


<%@include file="templates/footer.jsp" %>