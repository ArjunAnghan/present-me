<%@ page import="com.presentme.entity.Admin" %>
<body>

<div id="wrapper">

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="faculties">Smart Attendance System</a>
        </div>
        <!-- /.navbar-header -->

        <ul class="nav navbar-top-links navbar-right">
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-user fa-fw"></i>
                    <%
                        Admin admin = (Admin)session.getAttribute("user");
                        out.print(admin.getName());
                    %>
                    <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                    </li>
                    <li class="divider"></li>
                    <li><a href="#" class="dropdown-item" data-toggle="modal" data-target="#logoutModal"><i
                            class="fa fa-sign-out fa-fw"></i> Logout</a>
                    </li>
                </ul>
                <!-- /.dropdown-user -->
            </li>
            <!-- /.dropdown -->
        </ul>
        <!-- /.navbar-top-links -->
        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">

                    <li>
                        <a href="faculties"><i class="fa fa-user-md fa-fw"></i> Faculties</a>

                    </li>
                    <li>
                        <a href="students"><i class="fa fa-users fa-fw"></i> Students</a>

                    </li>
                    <li>
                        <a href="admins"><i class="fa fa-user-secret fa-fw"></i> Admins</a>

                    </li>
                    <li>
                        <a href="groups"><i class="fa fa-user-secret fa-fw"></i> Groups</a>

                    </li>
                    <li>
                        <a href="#"><i class="fa fa-user-plus fa-fw"></i> Add Users<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="addStudentPage"><span class="fa fa-plus-circle"></span> Add Students</a>
                            </li>
                            <li>
                                <a href="addFacultyPage"><span class="fa fa-plus-circle"></span> Add Faculty</a>
                            </li>
                            <li>
                                <a href="addAdminPage"><span class="fa fa-plus-circle"></span> Add Admin</a>
                            </li>
                            <li>
                                <a href="addGroupPage"><span class="fa fa-plus-circle"></span> Add Group</a>
                            </li>

                        </ul>

                    </li>

                </ul>
            </div>
            <!-- /.sidebar-collapse -->
        </div>
        <!-- /.navbar-static-side -->
    </nav>