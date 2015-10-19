<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!--
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.3.5
Version: 4.1.0
Author: KeenThemes
Website: http://www.keenthemes.com/
Contact: support@keenthemes.com
Follow: www.twitter.com/keenthemes
Like: www.facebook.com/keenthemes
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
License: You must have a valid license purchased only from themeforest(the above link) in order to legally use the theme for your project.
-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
  <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
  <title>Home Page</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
  <meta http-equiv="Content-type" content="text/html; charset=utf-8">
  <meta content="" name="description"/>
  <meta content="" name="author"/>
  <!-- BEGIN GLOBAL MANDATORY STYLES -->
  <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet"
        type="text/css">
  <link href="../../assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <link href="../../assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet"
        type="text/css">
  <link href="../../assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
  <link href="../../assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css">
  <link href="../../assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet"
        type="text/css"/>
  <!-- END GLOBAL MANDATORY STYLES -->
  <!-- BEGIN THEME STYLES -->
  <link href="../../assets/global/css/components.css" id="style_components" rel="stylesheet" type="text/css"/>
  <link href="../../assets/global/css/plugins.css" rel="stylesheet" type="text/css"/>
  <link href="../../assets/admin/layout2/css/layout.css" rel="stylesheet" type="text/css"/>
  <link id="style_color" href="../../assets/admin/layout2/css/themes/grey.css" rel="stylesheet" type="text/css"/>
  <link href="../../assets/admin/layout2/css/custom.css" rel="stylesheet" type="text/css"/>
  <!-- END THEME STYLES -->
  <link rel="shortcut icon" href="favicon.ico"/>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<!-- DOC: Apply "page-header-fixed-mobile" and "page-footer-fixed-mobile" class to body element to force fixed header or footer in mobile devices -->
<!-- DOC: Apply "page-sidebar-closed" class to the body and "page-sidebar-menu-closed" class to the sidebar menu element to hide the sidebar by default -->
<!-- DOC: Apply "page-sidebar-hide" class to the body to make the sidebar completely hidden on toggle -->
<!-- DOC: Apply "page-sidebar-closed-hide-logo" class to the body element to make the logo hidden on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-hide" class to body element to completely hide the sidebar on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-fixed" class to have fixed sidebar -->
<!-- DOC: Apply "page-footer-fixed" class to the body element to have fixed footer -->
<!-- DOC: Apply "page-sidebar-reversed" class to put the sidebar on the right side -->
<!-- DOC: Apply "page-full-width" class to the body element to have full width page without the sidebar menu -->
<body class="page-boxed page-header-fixed page-container-bg-solid page-sidebar-closed-hide-logo ">
<!-- BEGIN HEADER -->
<div class="page-header navbar navbar-fixed-top">
  <!-- BEGIN HEADER INNER -->
  <div class="page-header-inner container">
    <!-- BEGIN LOGO -->
    <div class="page-logo">
      <a href="index.html">
        <img src="../../assets/admin/layout2/img/logo-default.png" alt="logo" class="logo-default"/>
      </a>

      <div class="menu-toggler sidebar-toggler">
        <!-- DOC: Remove the above "hide" to enable the sidebar toggler button on header -->
      </div>
    </div>
    <!-- END LOGO -->
    <!-- BEGIN RESPONSIVE MENU TOGGLER -->
    <a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse"
       data-target=".navbar-collapse">
    </a>
    <!-- END RESPONSIVE MENU TOGGLER -->
    <!-- BEGIN PAGE ACTIONS -->
    <!-- DOC: Remove "hide" class to enable the page header actions -->
    <div class="page-actions hide">
      <div class="btn-group">
        <button type="button" class="btn btn-circle red-pink dropdown-toggle" data-toggle="dropdown">
          <i class="icon-bar-chart"></i>&nbsp;<span class="hidden-sm hidden-xs">New&nbsp;</span>&nbsp;<i
                class="fa fa-angle-down"></i>
        </button>
        <ul class="dropdown-menu" role="menu">
          <li>
            <a href="javascript:;">
              <i class="icon-user"></i> New User </a>
          </li>
          <li>
            <a href="javascript:;">
              <i class="icon-present"></i> New Event <span class="badge badge-success">4</span>
            </a>
          </li>
          <li>
            <a href="javascript:;">
              <i class="icon-basket"></i> New order </a>
          </li>
          <li class="divider">
          </li>
          <li>
            <a href="javascript:;">
              <i class="icon-flag"></i> Pending Orders <span class="badge badge-danger">4</span>
            </a>
          </li>
          <li>
            <a href="javascript:;">
              <i class="icon-users"></i> Pending Users <span class="badge badge-warning">12</span>
            </a>
          </li>
        </ul>
      </div>
      <div class="btn-group">
        <button type="button" class="btn btn-circle green-haze dropdown-toggle" data-toggle="dropdown">
          <i class="icon-bell"></i>&nbsp;<span class="hidden-sm hidden-xs">Post&nbsp;</span>&nbsp;<i
                class="fa fa-angle-down"></i>
        </button>
        <ul class="dropdown-menu" role="menu">
          <li>
            <a href="javascript:;">
              <i class="icon-docs"></i> New Post </a>
          </li>
          <li>
            <a href="javascript:;">
              <i class="icon-tag"></i> New Comment </a>
          </li>
          <li>
            <a href="javascript:;">
              <i class="icon-share"></i> Share </a>
          </li>
          <li class="divider">
          </li>
          <li>
            <a href="javascript:;">
              <i class="icon-flag"></i> Comments <span class="badge badge-success">4</span>
            </a>
          </li>
          <li>
            <a href="javascript:;">
              <i class="icon-users"></i> Feedbacks <span class="badge badge-danger">2</span>
            </a>
          </li>
        </ul>
      </div>
    </div>
    <!-- END PAGE ACTIONS -->
    <!-- BEGIN PAGE TOP -->
    <div class="page-top">
      <!-- BEGIN HEADER SEARCH BOX -->
      <!-- DOC: Apply "search-form-expanded" right after the "search-form" class to have half expanded search box -->
      <form class="search-form search-form-expanded" action="extra_search.html" method="GET">
        <div class="input-group">
          <input type="text" class="form-control" placeholder="Search..." name="query">
					<span class="input-group-btn">
					<a href="javascript:;" class="btn submit"><i class="icon-magnifier"></i></a>
					</span>
        </div>
      </form>
      <!-- END HEADER SEARCH BOX -->
      <!-- BEGIN TOP NAVIGATION MENU -->
      <div class="top-menu">
        <ul class="nav navbar-nav pull-right">
          <!-- BEGIN NOTIFICATION DROPDOWN -->
          <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
          <li class="dropdown dropdown-extended dropdown-notification" id="header_notification_bar">
            <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
               data-close-others="true">
              <i class="icon-bell"></i>
						<span class="badge badge-default">
						7 </span>
            </a>
            <ul class="dropdown-menu">
              <li class="external">
                <h3><span class="bold">12 pending</span> notifications</h3>
                <a href="extra_profile.html">view all</a>
              </li>
              <li>
                <ul class="dropdown-menu-list scroller" style="height: 250px;"
                    data-handle-color="#637283">
                  <li>
                    <a href="javascript:;">
                      <span class="time">just now</span>
										<span class="details">
										<span class="label label-sm label-icon label-success">
										<i class="fa fa-plus"></i>
										</span>
										New user registered. </span>
                    </a>
                  </li>
                  <li>
                    <a href="javascript:;">
                      <span class="time">3 mins</span>
										<span class="details">
										<span class="label label-sm label-icon label-danger">
										<i class="fa fa-bolt"></i>
										</span>
										Server #12 overloaded. </span>
                    </a>
                  </li>
                  <li>
                    <a href="javascript:;">
                      <span class="time">10 mins</span>
										<span class="details">
										<span class="label label-sm label-icon label-warning">
										<i class="fa fa-bell-o"></i>
										</span>
										Server #2 not responding. </span>
                    </a>
                  </li>
                  <li>
                    <a href="javascript:;">
                      <span class="time">14 hrs</span>
										<span class="details">
										<span class="label label-sm label-icon label-info">
										<i class="fa fa-bullhorn"></i>
										</span>
										Application error. </span>
                    </a>
                  </li>
                  <li>
                    <a href="javascript:;">
                      <span class="time">2 days</span>
										<span class="details">
										<span class="label label-sm label-icon label-danger">
										<i class="fa fa-bolt"></i>
										</span>
										Database overloaded 68%. </span>
                    </a>
                  </li>
                  <li>
                    <a href="javascript:;">
                      <span class="time">3 days</span>
										<span class="details">
										<span class="label label-sm label-icon label-danger">
										<i class="fa fa-bolt"></i>
										</span>
										A user IP blocked. </span>
                    </a>
                  </li>
                  <li>
                    <a href="javascript:;">
                      <span class="time">4 days</span>
										<span class="details">
										<span class="label label-sm label-icon label-warning">
										<i class="fa fa-bell-o"></i>
										</span>
										Storage Server #4 not responding dfdfdfd. </span>
                    </a>
                  </li>
                  <li>
                    <a href="javascript:;">
                      <span class="time">5 days</span>
										<span class="details">
										<span class="label label-sm label-icon label-info">
										<i class="fa fa-bullhorn"></i>
										</span>
										System Error. </span>
                    </a>
                  </li>
                  <li>
                    <a href="javascript:;">
                      <span class="time">9 days</span>
										<span class="details">
										<span class="label label-sm label-icon label-danger">
										<i class="fa fa-bolt"></i>
										</span>
										Storage server failed. </span>
                    </a>
                  </li>
                </ul>
              </li>
            </ul>
          </li>
          <!-- END NOTIFICATION DROPDOWN -->
          <!-- BEGIN INBOX DROPDOWN -->
          <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
          <li class="dropdown dropdown-extended dropdown-inbox" id="header_inbox_bar">
            <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
               data-close-others="true">
              <i class="icon-envelope-open"></i>
						<span class="badge badge-default">
						4 </span>
            </a>
            <ul class="dropdown-menu">
              <li class="external">
                <h3>You have <span class="bold">7 New</span> Messages</h3>
                <a href="page_inbox.html">view all</a>
              </li>
              <li>
                <ul class="dropdown-menu-list scroller" style="height: 275px;"
                    data-handle-color="#637283">
                  <li>
                    <a href="inbox.html?a=view">
										<span class="photo">
										<img src="../../assets/admin/layout3/img/avatar2.jpg" class="img-circle" alt="">
										</span>
										<span class="subject">
										<span class="from">
										Lisa Wong </span>
										<span class="time">Just Now </span>
										</span>
										<span class="message">
										Vivamus sed auctor nibh congue nibh. auctor nibh auctor nibh... </span>
                    </a>
                  </li>
                  <li>
                    <a href="inbox.html?a=view">
										<span class="photo">
										<img src="../../assets/admin/layout3/img/avatar3.jpg" class="img-circle" alt="">
										</span>
										<span class="subject">
										<span class="from">
										Richard Doe </span>
										<span class="time">16 mins </span>
										</span>
										<span class="message">
										Vivamus sed congue nibh auctor nibh congue nibh. auctor nibh auctor nibh... </span>
                    </a>
                  </li>
                  <li>
                    <a href="inbox.html?a=view">
										<span class="photo">
										<img src="../../assets/admin/layout3/img/avatar1.jpg" class="img-circle" alt="">
										</span>
										<span class="subject">
										<span class="from">
										Bob Nilson </span>
										<span class="time">2 hrs </span>
										</span>
										<span class="message">
										Vivamus sed nibh auctor nibh congue nibh. auctor nibh auctor nibh... </span>
                    </a>
                  </li>
                  <li>
                    <a href="inbox.html?a=view">
										<span class="photo">
										<img src="../../assets/admin/layout3/img/avatar2.jpg" class="img-circle" alt="">
										</span>
										<span class="subject">
										<span class="from">
										Lisa Wong </span>
										<span class="time">40 mins </span>
										</span>
										<span class="message">
										Vivamus sed auctor 40% nibh congue nibh... </span>
                    </a>
                  </li>
                  <li>
                    <a href="inbox.html?a=view">
										<span class="photo">
										<img src="../../assets/admin/layout3/img/avatar3.jpg" class="img-circle" alt="">
										</span>
										<span class="subject">
										<span class="from">
										Richard Doe </span>
										<span class="time">46 mins </span>
										</span>
										<span class="message">
										Vivamus sed congue nibh auctor nibh congue nibh. auctor nibh auctor nibh... </span>
                    </a>
                  </li>
                </ul>
              </li>
            </ul>
          </li>
          <!-- END INBOX DROPDOWN -->
          <!-- BEGIN TODO DROPDOWN -->
          <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
          <li class="dropdown dropdown-extended dropdown-tasks" id="header_task_bar">
            <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
               data-close-others="true">
              <i class="icon-calendar"></i>
						<span class="badge badge-default">
						3 </span>
            </a>
            <ul class="dropdown-menu extended tasks">
              <li class="external">
                <h3>You have <span class="bold">12 pending</span> tasks</h3>
                <a href="page_todo.html">view all</a>
              </li>
              <li>
                <ul class="dropdown-menu-list scroller" style="height: 275px;"
                    data-handle-color="#637283">
                  <li>
                    <a href="javascript:;">
										<span class="task">
										<span class="desc">New release v1.2 </span>
										<span class="percent">30%</span>
										</span>
										<span class="progress">
										<span style="width: 40%;" class="progress-bar progress-bar-success"
                                              aria-valuenow="40" aria-valuemin="0" aria-valuemax="100"><span
                                                class="sr-only">40% Complete</span></span>
										</span>
                    </a>
                  </li>
                  <li>
                    <a href="javascript:;">
										<span class="task">
										<span class="desc">Application deployment</span>
										<span class="percent">65%</span>
										</span>
										<span class="progress">
										<span style="width: 65%;" class="progress-bar progress-bar-danger"
                                              aria-valuenow="65" aria-valuemin="0" aria-valuemax="100"><span
                                                class="sr-only">65% Complete</span></span>
										</span>
                    </a>
                  </li>
                  <li>
                    <a href="javascript:;">
										<span class="task">
										<span class="desc">Mobile app release</span>
										<span class="percent">98%</span>
										</span>
										<span class="progress">
										<span style="width: 98%;" class="progress-bar progress-bar-success"
                                              aria-valuenow="98" aria-valuemin="0" aria-valuemax="100"><span
                                                class="sr-only">98% Complete</span></span>
										</span>
                    </a>
                  </li>
                  <li>
                    <a href="javascript:;">
										<span class="task">
										<span class="desc">Database migration</span>
										<span class="percent">10%</span>
										</span>
										<span class="progress">
										<span style="width: 10%;" class="progress-bar progress-bar-warning"
                                              aria-valuenow="10" aria-valuemin="0" aria-valuemax="100"><span
                                                class="sr-only">10% Complete</span></span>
										</span>
                    </a>
                  </li>
                  <li>
                    <a href="javascript:;">
										<span class="task">
										<span class="desc">Web server upgrade</span>
										<span class="percent">58%</span>
										</span>
										<span class="progress">
										<span style="width: 58%;" class="progress-bar progress-bar-info"
                                              aria-valuenow="58" aria-valuemin="0" aria-valuemax="100"><span
                                                class="sr-only">58% Complete</span></span>
										</span>
                    </a>
                  </li>
                  <li>
                    <a href="javascript:;">
										<span class="task">
										<span class="desc">Mobile development</span>
										<span class="percent">85%</span>
										</span>
										<span class="progress">
										<span style="width: 85%;" class="progress-bar progress-bar-success"
                                              aria-valuenow="85" aria-valuemin="0" aria-valuemax="100"><span
                                                class="sr-only">85% Complete</span></span>
										</span>
                    </a>
                  </li>
                  <li>
                    <a href="javascript:;">
										<span class="task">
										<span class="desc">New UI release</span>
										<span class="percent">38%</span>
										</span>
										<span class="progress progress-striped">
										<span style="width: 38%;" class="progress-bar progress-bar-important"
                                              aria-valuenow="18" aria-valuemin="0" aria-valuemax="100"><span
                                                class="sr-only">38% Complete</span></span>
										</span>
                    </a>
                  </li>
                </ul>
              </li>
            </ul>
          </li>
          <!-- END TODO DROPDOWN -->
          <!-- BEGIN USER LOGIN DROPDOWN -->
          <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
          <li class="dropdown dropdown-user">
            <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
               data-close-others="true">
              <img alt="" class="img-circle" src="../../assets/admin/layout2/img/avatar3_small.jpg"/>
						<span class="username username-hide-on-mobile">
						Nick </span>
              <i class="fa fa-angle-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-menu-default">
              <li>
                <a href="extra_profile.html">
                  <i class="icon-user"></i> My Profile </a>
              </li>
              <li>
                <a href="page_calendar.html">
                  <i class="icon-calendar"></i> My Calendar </a>
              </li>
              <li>
                <a href="inbox.html">
                  <i class="icon-envelope-open"></i> My Inbox <span class="badge badge-danger">
								3 </span>
                </a>
              </li>
              <li>
                <a href="page_todo.html">
                  <i class="icon-rocket"></i> My Tasks <span class="badge badge-success">
								7 </span>
                </a>
              </li>
              <li class="divider">
              </li>
              <li>
                <a href="extra_lock.html">
                  <i class="icon-lock"></i> Lock Screen </a>
              </li>
              <li>
                <a href="login.html">
                  <i class="icon-key"></i> Log Out </a>
              </li>
            </ul>
          </li>
          <!-- END USER LOGIN DROPDOWN -->
        </ul>
      </div>
      <!-- END TOP NAVIGATION MENU -->
    </div>
    <!-- END PAGE TOP -->
  </div>
  <!-- END HEADER INNER -->
</div>
<!-- END HEADER -->
<div class="clearfix">
</div>
<div class="container">
  <!-- BEGIN CONTAINER -->
  <div class="page-container">
    <!-- BEGIN SIDEBAR -->
    <div class="page-sidebar-wrapper">
      <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
      <!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
      <div class="page-sidebar navbar-collapse collapse">
        <!-- BEGIN SIDEBAR MENU -->
        <!-- DOC: Apply "page-sidebar-menu-light" class right after "page-sidebar-menu" to enable light sidebar menu style(without borders) -->
        <!-- DOC: Apply "page-sidebar-menu-hover-submenu" class right after "page-sidebar-menu" to enable hoverable(hover vs accordion) sub menu mode -->
        <!-- DOC: Apply "page-sidebar-menu-closed" class right after "page-sidebar-menu" to collapse("page-sidebar-closed" class must be applied to the body element) the sidebar sub menu mode -->
        <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
        <!-- DOC: Set data-keep-expand="true" to keep the submenues expanded -->
        <!-- DOC: Set data-auto-speed="200" to adjust the sub menu slide up/down speed -->
        <ul class="page-sidebar-menu page-sidebar-menu-hover-submenu " data-keep-expanded="false"
            data-auto-scroll="true" data-slide-speed="200">
          <li class="start ">
            <a href="index.html">
              <i class="icon-home"></i>
              <span class="title">Staff Dashboard</span>
            </a>
          </li>
          <li>
            <a href="javascript:;">
              <i class="icon-map"></i>
              <span class="title">Route</span>
              <span class="arrow "></span>
            </a>
            <ul class="sub-menu">
              <li>
                <a href="ecommerce_index.html"> <!-- here, not yet to change link to route -->
                  <i class="icon-home"></i>
                  Dashboard</a>
              </li>
              <li>
                <a href="ecommerce_orders.html"> <!-- here, not yet to change link to route -->
                  <i class="icon-basket"></i>
                  Route Information</a>
              </li>
              <li>
                <a href="ecommerce_orders.html"> <!-- here, not yet to change link to route -->
                  <i class="icon-basket"></i>
                  Route Map</a>
              </li>
            </ul>
          </li>
          <li>
            <a href="javascript:;">
              <i class="icon-star"></i>
              <span class="title">Station</span>
              <span class="arrow "></span>
            </a>
            <ul class="sub-menu">
              <li>
                <a href="ecommerce_index.html"> <!-- here, not yet to change link to station -->
                  <i class="icon-home"></i>
                  Dashboard</a>
              </li>
              <li>
                <a href="ecommerce_orders.html"> <!-- here, not yet to change link to station -->
                  <i class="icon-basket"></i>
                  Station Information</a>
              </li>
              <li>
                <a href="ecommerce_orders.html"> <!-- here, not yet to change link to station -->
                  <i class="icon-basket"></i>
                  Station Map</a>
              </li>
            </ul>
          </li>
          <!-- BEGIN NOTIFICATION COMPONENT -->
          <li>
            <a href="javascript:;">
              <i class="icon-bell"></i>
              <span class="title">Notification</span>
              <span class="arrow "></span>
            </a>
            <ul class="sub-menu">
              <li>
                <a href="ecommerce_index.html"> <!-- here, not yet to change link to station -->
                  <i class="icon-home"></i>
                  Dashboard</a>
              </li>
              <li>
                <a href="ecommerce_orders.html"> <!-- here, not yet to change link to station -->
                  <i class="icon-basket"></i>
                  Station Notification</a>
              </li>
              <li>
                <a href="ecommerce_orders.html"> <!-- here, not yet to change link to station -->
                  <i class="icon-basket"></i>
                  Timetable notification</a>
              </li>
            </ul>
          </li>
          <!-- END NOTIFICATION COMPONENT -->
          <!-- BEGIN CONFIGURATION COMPONENT -->
          <li>
            <a href="javascript:;">
              <i class="icon-wrench"></i>
              <span class="title">Configuration</span>
              <span class="arrow "></span>
            </a>
            <ul class="sub-menu">
              <li>
                <a href="ecommerce_index.html"> <!-- here, not yet to change link to station -->
                  <i class="icon-home"></i>
                  Dashboard</a>
              </li>
              <li>
                <a href="ecommerce_orders.html"> <!-- here, not yet to change link to station -->
                  <i class="icon-basket"></i>
                  Time Configuration</a>
              </li>
              <li>
                <a href="ecommerce_orders.html"> <!-- here, not yet to change link to station -->
                  <i class="icon-basket"></i>
                  Source Configuration</a>
              </li>
            </ul>
          </li>
          <!-- END CONFIGURATION COMPONENT -->
          <!-- END SIDEBAR MENU -->
      </div>
    </div>
    <!-- END SIDEBAR -->
    <!-- BEGIN CONTENT -->
    <div class="page-content-wrapper">
      <div class="page-content">
        <!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
        <div class="modal fade" id="portlet-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">Modal title</h4>
              </div>
              <div class="modal-body">
                Widget settings form goes here
              </div>
              <div class="modal-footer">
                <button type="button" class="btn blue">Save changes</button>
                <button type="button" class="btn default" data-dismiss="modal">Close</button>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->
        <!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
        <!-- BEGIN STYLE CUSTOMIZER -->
        <div class="theme-panel hidden-xs hidden-sm">
          <div class="toggler tooltips" data-container="body" data-placement="left" data-html="true"
               data-original-title="Click to open advance theme customizer panel">
            <i class="icon-settings"></i>
          </div>
          <div class="toggler-close">
            <i class="icon-close"></i>
          </div>
          <div class="theme-options">
            <div class="theme-option theme-colors clearfix">
							<span>
							THEME COLOR </span>
              <ul>
                <li class="color-black current color-default" data-style="default">
                </li>
                <li class="color-blue" data-style="blue">
                </li>
                <li class="color-brown" data-style="brown">
                </li>
                <li class="color-purple" data-style="purple">
                </li>
                <li class="color-grey" data-style="grey">
                </li>
                <li class="color-white color-light" data-style="light">
                </li>
              </ul>
            </div>
            <div class="theme-option">
							<span>
							Layout </span>
              <select class="layout-option form-control input-small">
                <option value="fluid" selected="selected">Fluid</option>
                <option value="boxed">Boxed</option>
              </select>
            </div>
            <div class="theme-option">
							<span>
							Header </span>
              <select class="page-header-option form-control input-small">
                <option value="fixed" selected="selected">Fixed</option>
                <option value="default">Default</option>
              </select>
            </div>
            <div class="theme-option">
							<span>
							Sidebar </span>
              <select class="sidebar-option form-control input-small">
                <option value="fixed">Fixed</option>
                <option value="default" selected="selected">Default</option>
              </select>
            </div>
            <div class="theme-option">
							<span>
							Footer </span>
              <select class="page-footer-option form-control input-small">
                <option value="fixed">Fixed</option>
                <option value="default" selected="selected">Default</option>
              </select>
            </div>
          </div>
        </div>
        <!-- END BEGIN STYLE CUSTOMIZER -->
        <!-- BEGIN PAGE HEADER-->
        <h3 class="page-title">
          Route
          <small>showing route information</small>
        </h3>
        <div class="page-bar">
          <ul class="page-breadcrumb">
            <li>
              <i class="fa fa-home"></i>
              <a href="index.html">Home</a>
              <i class="fa fa-angle-right"></i>
            </li>
            <li>
              <a href="#">Route</a>
              <i class="fa fa-angle-right"></i>
            </li>
            <li>
              <a href="#">Route Information</a>
            </li>
          </ul>
          <div class="page-toolbar">
            <div class="btn-group pull-right">
              <button type="button" class="btn btn-fit-height grey-salt dropdown-toggle"
                      data-toggle="dropdown" data-hover="dropdown" data-delay="1000"
                      data-close-others="true">
                Actions <i class="fa fa-angle-down"></i>
              </button>
              <ul class="dropdown-menu pull-right" role="menu">
                <li>
                  <a href="#">Action</a>
                </li>
                <li>
                  <a href="#">Another action</a>
                </li>
                <li>
                  <a href="#">Something else here</a>
                </li>
                <li class="divider">
                </li>
                <li>
                  <a href="#">Separated link</a>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <!-- END PAGE HEADER-->
        <!-- BEGIN PAGE CONTENT-->
        <div class="row">
          <div class="col-md-12">
            <!-- BEGIN SAMPLE TABLE PORTLET-->
            <div class="portlet box purple">
              <div class="portlet-title">
                <div class="caption">
                  <i class="fa fa-comments"></i>Striped Table
                </div>
                <div class="tools">
                  <a href="javascript:;" class="collapse">
                  </a>
                  <a href="#portlet-config" data-toggle="modal" class="config">
                  </a>
                  <a href="javascript:;" class="reload">
                  </a>
                  <a href="javascript:;" class="remove">
                  </a>
                </div>
              </div>
              <%--<div class="table-scrollable">
                &lt;%&ndash;table class="table table-striped table-bordered table-hover dataTable no-footer"
                                       id="datatable_ajax" role="grid" aria-describedby="datatable_ajax_info">
                                    <thead>
                                    <tr class="heading" role="row">
                                        <th width="2%" class="sorting_disabled" rowspan="1" colspan="1">
                                            <div class="checker"><span><input class="group-checkable"
                                                                              type="checkbox"></span></div>
                                        </th>
                                        <th width="5%" tabindex="0" class="sorting" aria-controls="datatable_ajax"
                                            rowspan="1" colspan="1">
                                            Record&nbsp;#
                                        </th>
                                        <th width="15%" tabindex="0" class="sorting" aria-controls="datatable_ajax"
                                            rowspan="1" colspan="1">
                                            Date
                                        </th>
                                        <th width="15%" tabindex="0" class="sorting" aria-controls="datatable_ajax"
                                            rowspan="1" colspan="1">
                                            Customer
                                        </th>
                                        <th width="10%" tabindex="0" class="sorting" aria-controls="datatable_ajax"
                                            rowspan="1" colspan="1">
                                            Ship&nbsp;To
                                        </th>
                                        <th width="10%" tabindex="0" class="sorting" aria-controls="datatable_ajax"
                                            rowspan="1" colspan="1">
                                            Price
                                        </th>
                                        <th width="10%" tabindex="0" class="sorting" aria-controls="datatable_ajax"
                                            rowspan="1" colspan="1">
                                            Amount
                                        </th>
                                        <th width="10%" tabindex="0" class="sorting" aria-controls="datatable_ajax"
                                            rowspan="1" colspan="1">
                                            Status
                                        </th>
                                        <th width="10%" tabindex="0" class="sorting" aria-controls="datatable_ajax"
                                            rowspan="1" colspan="1">
                                            Actions
                                        </th>
                                    </tr>
                                    <tr class="filter" role="row">
                                        <td rowspan="1" colspan="1">
                                        </td>
                                        <td rowspan="1" colspan="1">
                                            <input name="order_id" class="form-control form-filter input-sm"
                                                   type="text">
                                        </td>
                                        <td rowspan="1" colspan="1">
                                            <div class="input-group date date-picker margin-bottom-5"
                                                 data-date-format="dd/mm/yyyy">
                                                <input name="order_date_from" class="form-control form-filter input-sm"
                                                       type="text" readonly="" placeholder="From">
												<span class="input-group-btn">
												<button class="btn btn-sm default" type="button"><i
                                                        class="fa fa-calendar"></i></button>
												</span>
                                            </div>
                                            <div class="input-group date date-picker" data-date-format="dd/mm/yyyy">
                                                <input name="order_date_to" class="form-control form-filter input-sm"
                                                       type="text" readonly="" placeholder="To">
												<span class="input-group-btn">
												<button class="btn btn-sm default" type="button"><i
                                                        class="fa fa-calendar"></i></button>
												</span>
                                            </div>
                                        </td>
                                        <td rowspan="1" colspan="1">
                                            <input name="order_customer_name" class="form-control form-filter input-sm"
                                                   type="text">
                                        </td>
                                        <td rowspan="1" colspan="1">
                                            <input name="order_ship_to" class="form-control form-filter input-sm"
                                                   type="text">
                                        </td>
                                        <td rowspan="1" colspan="1">
                                            <div class="margin-bottom-5">
                                                <input name="order_price_from" class="form-control form-filter input-sm"
                                                       type="text" placeholder="From">
                                            </div>
                                            <input name="order_price_to" class="form-control form-filter input-sm"
                                                   type="text" placeholder="To">
                                        </td>
                                        <td rowspan="1" colspan="1">
                                            <div class="margin-bottom-5">
                                                <input name="order_quantity_from"
                                                       class="form-control form-filter input-sm margin-bottom-5 clearfix"
                                                       type="text" placeholder="From">
                                            </div>
                                            <input name="order_quantity_to" class="form-control form-filter input-sm"
                                                   type="text" placeholder="To">
                                        </td>
                                        <td rowspan="1" colspan="1">
                                            <select name="order_status" class="form-control form-filter input-sm">
                                                <option value="">Select...</option>
                                                <option value="pending">Pending</option>
                                                <option value="closed">Closed</option>
                                                <option value="hold">On Hold</option>
                                                <option value="fraud">Fraud</option>
                                            </select>
                                        </td>
                                        <td rowspan="1" colspan="1">
                                            <div class="margin-bottom-5">
                                                <button class="btn btn-sm yellow filter-submit margin-bottom"><i
                                                        class="fa fa-search"></i> Search
                                                </button>
                                            </div>
                                            <button class="btn btn-sm red filter-cancel"><i class="fa fa-times"></i>
                                                Reset
                                            </button>
                                        </td>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>&ndash;%&gt;
              </div>--%>
              <div class="portlet-body" id="datatable_ajax">
                <div class="table-scrollable">
                  <table class="table table-striped table-hover">
                    <thead>
                      <tr>
                        <th>
                          #
                        </th>
                        <th>
                          Station Name
                        </th>
                        <th>
                          Action
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="station" items="${requestScope.stations}" varStatus="count">
                      <tr>
                        <td>${count.count}</td>
                        <td>${station.stationName}</td>
                        <td>
                          <div class="btn-group">
                            <button type="button" class="btn blue">
                              <i class="fa fa-edit"></i>
                              Edit
                            </button>
                            <button type="button" class="btn red">
                              <i class="fa fa-times"></i>
                              Delete
                            </button>
                          </div>
                        </td>
                      </tr>
                    </c:forEach>
                    </tbody>
                  </table>
                  <%--<a title="Next" class="btn btn-sm default next" onclick="loadRoutesAtPage()"><i class="fa fa-angle-right"></i></a>--%>
                </div>
              </div>
            </div>
            <!-- END SAMPLE TABLE PORTLET-->
          </div>
        </div>


        <!-- END PAGE CONTENT-->
      </div>
    </div>
    <!-- END CONTENT -->
    <!-- BEGIN QUICK SIDEBAR -->
    <!--Cooming Soon...-->
    <!-- END QUICK SIDEBAR -->
  </div>
  <!-- END CONTAINER -->
  <!-- BEGIN FOOTER -->
  <div class="page-footer">
    <div class="page-footer-inner">
      2014 &copy; Metronic by keenthemes. <a
            href="http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes"
            title="Purchase Metronic just for 27$ and get lifetime updates for free" target="_blank">Purchase
      Metronic!</a>
    </div>
    <div class="scroll-to-top">
      <i class="icon-arrow-up"></i>
    </div>
  </div>
  <!-- END FOOTER -->
</div>
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="../../assets/global/plugins/respond.min.js"></script>
<script src="../../assets/global/plugins/excanvas.min.js"></script>
<![endif]-->
<script src="../../assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<!-- IMPORTANT! Load jquery-ui.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="../../assets/global/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
        type="text/javascript"></script>
<script src="../../assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="../../assets/global/plugins/amcharts/amcharts/amcharts.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/amcharts/amcharts/serial.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/amcharts/amcharts/pie.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/amcharts/amcharts/radar.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/amcharts/amcharts/themes/light.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/amcharts/amcharts/themes/patterns.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/amcharts/amcharts/themes/chalk.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/amcharts/ammap/ammap.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/amcharts/ammap/maps/js/worldLow.js" type="text/javascript"></script>
<script src="../../assets/global/plugins/amcharts/amstockcharts/amstock.js" type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="../../assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="../../assets/admin/layout2/scripts/layout.js" type="text/javascript"></script>
<script src="../../assets/admin/layout2/scripts/demo.js" type="text/javascript"></script>
<script src="../../assets/admin/pages/scripts/charts-amcharts.js"></script>
<script src="../../assets/global/scripts/datatable.js"></script>
<script src="../../assets/admin/pages/scripts/table-ajax.js"></script>
<script src="../../static/js/index.js"></script>
<script>
  jQuery(document).ready(function () {
    // initiate layout and plugins
    Metronic.init(); // init metronic core components
    Layout.init(); // init current layout
    Demo.init(); // init demo features
    ChartsAmcharts.init();
  });
</script>
<!-- END PAGE LEVEL SCRIPTS -->
</body>
<!-- END BODY -->
</html>