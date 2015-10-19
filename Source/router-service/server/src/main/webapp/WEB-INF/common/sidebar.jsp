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
    </ul>
  </div>
</div>
<!-- END SIDEBAR -->
