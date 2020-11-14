<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.presentme.utility.printAlert" %>
<%@include file="templates/header.jsp" %>
<%@include file="templates/top-side-nav.jsp" %>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">All Groups</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">

                <!-- /.panel-heading -->
                <div class="panel-body">
                    <table width="50%" class="table table-striped table-bordered table-hover" id="groups">
                        <thead>
                        <tr>
                            <th>Group Name</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="tempGroup" items="${allGroups}">
                                <tr>
                                    <td>${tempGroup.groupname}</td>
                                    <td><a class="btn btn-primary btn-outline btn-danger" href="delGroup?groupId=${tempGroup.id}">Delete Group (Will delete Users falling under it)</a></td>
                                </tr>
                            </c:forEach>
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


<%
    String message = (String) request.getAttribute("message");
    if (message != null && message.equals("clashg")) {
        out.print(printAlert.getAlertBox("Group Already Exists",
                "The Groupname you entered already exists in database, Please check Again"));
    } else if (message != null && message.equals("addg")) {
        out.print(printAlert.getAlertBox("Added Successfully",
                "Group registered successfully."));
    }
%>