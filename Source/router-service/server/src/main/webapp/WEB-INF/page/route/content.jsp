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
                    <form action="">
                      <button class="btn green" name="action" value="redirectAddRoute">
                        Add New <i class="fa fa-plus"></i>
                      </button>
                    </form>
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
                <c:forEach var="route" items="${routes}">
                  <tr>
                    <input type="hidden" value="${route.routeId}" />
                    <td>
                      ${route.routeNo}
                    </td>
                    <td>
                      <a href="${pageContext.request.contextPath}/route/detail?routeId=${route.routeId}" class="href">
                        <span>${route.routeName}</span>
                      </a>
                    </td>
                    <td>${route.routeType}</td>
                    <td>
                      <a class="edit" href="javascript:;">
                        Edit </a>
                    </td>
                    <td>
                      <a class="delete" href="javascript:;">
                        Delete </a>
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


