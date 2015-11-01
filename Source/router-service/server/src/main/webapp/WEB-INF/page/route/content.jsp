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
    <div class="modal fade" id="portlet-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
    <!-- BEGIN PAGE HEADER-->
    <h3 class="page-title">
      Route Management <small>manage route of city map</small>
    </h3>
    <%--<div class="page-bar">
      <ul class="page-breadcrumb">
        <li>
          <i class="fa fa-home"></i>
          <a href="">Home</a>
          <i class="fa fa-angle-right"></i>
        </li>
        <li>
          <a href="#">Data Tables</a>
          <i class="fa fa-angle-right"></i>
        </li>
        <li>
          <a href="#">Managed Datatables</a>
        </li>
      </ul>
      <div class="page-toolbar">
        <div class="btn-group pull-right">
          <button type="button" class="btn btn-fit-height grey-salt dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-delay="1000" data-close-others="true">
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
    </div>--%>
    <!-- END PAGE HEADER-->
    <!-- BEGIN PAGE CONTENT-->
    <div class="row">
      <div class="col-md-12">
        <!-- BEGIN EXAMPLE TABLE PORTLET-->
        <div class="portlet box grey-cascade">
          <div class="portlet-title">
            <div class="caption">
              <i class="fa fa-globe"></i>Route Managed Table
            </div>
          </div>
          <div class="portlet-body">
            <div class="table-toolbar">
              <div class="row">
                <div class="col-md-6">
                  <div class="btn-group">
                    <button id="sample_editable_1_new" class="btn green">
                      Add New <i class="fa fa-plus"></i>
                    </button>
                  </div>
                </div>
              </div>
            </div>
            <table class="table table-striped table-bordered table-hover text-center" id="route">
              <thead>
                <tr>
                  <th>Route No</th>
                  <th>Route Name</th>
                  <th>Route Type</th>
                  <th>Update</th>
                  <th>Delete</th>
                </tr>
              </thead>
              <tbody>
              <form action=""
                <c:forEach var="route" items="${routes}">
                  <tr>
                    <td>
                      ${route.routeNo}
                    </td>
                    <td>
                      <a href="${pageContext.request.contextPath}/route/detail?routeId=${route.routeId}">
                          ${route.routeName}
                      </a>
                    </td>
                    <td>${route.routeType}</td>
                    <td>
                      <button class="btn btn-link">Update</button>
                    </td>
                    <td>
                      <button class="btn btn-link">Delete</button>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
        <!-- END EXAMPLE TABLE PORTLET-->
      </div>
    </div>
    <!-- END PAGE CONTENT-->
  </div>
</div>
<!-- END CONTENT -->


