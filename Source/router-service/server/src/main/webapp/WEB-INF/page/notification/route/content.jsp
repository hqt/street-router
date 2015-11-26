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
      Route Notification Management <small>manage route notification</small>
    </h3>
    <!-- END PAGE HEADER-->
    <!-- BEGIN PAGE CONTENT-->
    <div class="row">
      <div class="col-md-12">
        <div class="portlet box green">
          <div class="portlet-title">
            <div class="caption">
              <i class="fa fa-gift"></i>Station Notification Table
            </div>
            <div class="tools">
              <a href="javascript:;" class="collapse"></a>
            </div>
          </div>
          <div class="portlet-body">
            <ul class="nav nav-tabs">
              <li class="active">
                <a href="#tab1" data-toggle="tab">
                  Active Notification
                </a>
              </li>
              <li>
                <a href="#tab2" data-toggle="tab">
                  Block Notification
                </a>
              </li>
            </ul>
            <div class="tab-content">
              <div class="tab-pane fade active in" id="tab1">
                <table class="table table-striped table-bordered table-hover text-center" id="routeNofActive">
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>Route No</th>
                      <th>Route Type</th>
                      <th>Notification</th>
                      <th>Approve</th>
                      <th>Reject</th>
                    </tr>
                  </thead>
                  <tbody>
                  <c:forEach var="item" items="${routeListNofActive.nofRouteVMs}" varStatus="count">
                    <tr>
                      <input type="hidden" value="${item.notificationId}"/>
                      <td>${count.count}</td>
                      <td>${item.routeNo}</td>
                      <td>${item.routeType}</td>
                      <td>
                          ${item.notification}
                      </td>
                      <td>
                        <a class="approve" href="javascript:;">
                          Approve
                        </a>
                      </td>
                      <td>
                        <a class="delete" href="javascript:;">
                          Reject
                        </a>
                      </td>
                    </tr>
                  </c:forEach>
                  </tbody>
                </table>
              </div>
              <div class="tab-pane fade" id="tab2">
                <table class="table table-striped table-bordered table-hover text-center" id="routeNofInActive">
                  <thead>
                  <tr>
                    <th>#</th>
                    <th>Route No</th>
                    <th>Notification</th>
                    <th>Unblock</th>
                    <th>Delete</th>
                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach var="item" items="${itemsInActive.modelL}" varStatus="count">
                    <tr>
                      <input type="hidden" value="${item.nofId}"/>
                      <input type="hidden" value="${item.tripId}"/>
                      <input type="hidden" value="${item.tripNo}"/>
                      <input type="hidden" value="${item.routeType}"/>
                      <td>${count.count}</td>
                      <td>${item.routeNo}</td>
                      <td>
                          ${item.notification}
                      </td>
                      <td>
                        <a href="${pageContext.request.contextPath}/notification/trip/unblock?nofId=${item.nofId}">
                          UnBlock
                        </a>
                      </td>
                      <td>
                        <a class="delete" href="javascript:;">
                          Delete
                        </a>
                      </td>
                    </tr>
                  </c:forEach>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- END PAGE CONTENT-->
  </div>
</div>
<!-- END CONTENT -->


