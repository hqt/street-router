<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: datnt
  Date: 10/17/2015
  Time: 10:23 PM
  To change this template use File | Settings | File Templates.
--%>
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
          <div class="portlet-body" id="datatable_ajax">
            <div class="table-scrollable">
              <table class="table table-striped table-hover">
                <thead>
                <tr>
                  <th>
                    #
                  </th>
                  <th>
                    Route No
                  </th>
                  <th>
                    Route Name
                  </th>
                  <th>
                    Action
                  </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="route" items="${subRoute}" varStatus="count">
                  <tr>
                    <td>${count.count}</td>
                    <td>${route.routeNo}</td>
                    <td>
                      <form action="DispatcherServlet" method="post">
                        <input type="hidden" value="${route.routeNo}" name="routeNo"/>
                        <button type="submit" class="btn btn-link" name="action" value="detail">
                          <span>${route.routeName}</span>
                        </button>
                      </form>
                    </td>
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
                  </tr
                </c:forEach>
                </tbody>
              </table>
              <a title="Next" class="btn btn-sm default next" onclick="loadRoutesAtPage()"><i
                      class="fa fa-angle-right"></i></a>
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


