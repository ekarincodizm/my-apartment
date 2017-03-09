<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<aside class="main-sidebar">
    <section class="sidebar">
        <div class="user-panel">
            <div class="pull-left image">
                <img src="" 
                    class="img-circle user_profile_image" 
                    alt="User Image" id="aside_user_profile_image" 
                    style="width:45px;height:45px;">
            </div>
            <div class="pull-left info">
                <p>
                    <span class="session-user-firstname">Firstname</span> 
                    <span class="session_user_lastname">Lastname</span>
                </p>
                <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
        </div>
        <!-- search form -->
        <!--<form action="#" method="get" class="sidebar-form">
            <div class="input-group">
                <input type="text" name="q" class="form-control" placeholder="Search...">
              <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i></button>
              </span>
            </div>
        </form>-->
        <!-- /.search form -->
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="header">
                xxx
            </li>
            <li class="treeview ">
                <a href="#">
                    <i class="fa fa-file-o"></i> Test 1
                </a>
            </li>
            <li class="treeview ">
                <a href="#">
                    <i class="fa fa-file-o"></i> Test 2
                </a>
            </li>
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-gears"></i>
                    <span>Setting</span> <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li>
                        <a href="#">
                            <i class="fa fa-file-o"></i> Test 3
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    </section>
</aside>