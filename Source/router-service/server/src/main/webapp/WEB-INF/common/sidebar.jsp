<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: datnt
  Date: 10/17/2015
  Time: 10:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
      <li>
        <a href="${pageContext.request.contextPath}/route/list">
          <i class="icon-map"></i>
          <span class="title">Route</span>
          <span class="arrow "></span>
        </a>
      </li>
      <li>
        <a href="${pageContext.request.contextPath}/station/list">
          <i class="icon-pointer"></i>
          <span class="title">Station</span>
          <span class="arrow "></span>
        </a>
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
            <a href=""> <!-- here, not yet to change link to station -->
              <i class="icon-pointer"></i>
              Station Notification</a>
          </li>
          <li>
            <a href=""> <!-- here, not yet to change link to station -->
              <i class="icon-clock"></i>
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
            <a href="${pageContext.request.contextPath}/configuration">
              <i class="icon-clock"></i>
              Time Configuration
            </a> <!-- here, not yet to change link to station -->
          </li>
          <li>
            <a href=""> <!-- here, not yet to change link to station -->
              <i class="icon-folder"></i>
              Source Configuration</a>
          </li>
        </ul>
      </li>
      <!-- END CONFIGURATION COMPONENT -->
      <!-- BEGIN STAFF COMPONENT -->
      <li>
        <a href="javascript:;">
          <i class="icon-user"></i>
          <span class="title">Staff</span>
          <span class="arrow "></span>
        </a>
        <ul class="sub-menu">
          <li>
            <a href="${pageContext.request.contextPath}/staff/list"> <!-- here, not yet to change link to station -->
              <i class="icon-list"></i>
              List Staff</a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/staff/add"> <!-- here, not yet to change link to station -->
              <i class="fa fa-user"></i>
              Create Staff</a>
          </li>
        </ul>
      </li>
      <!-- END STAFF COMPONENT -->
      <!-- END SIDEBAR MENU -->
    </ul>
  </div>
</div>
<!-- END SIDEBAR -->
