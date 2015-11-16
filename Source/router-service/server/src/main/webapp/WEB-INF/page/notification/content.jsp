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
      Station Notification Management <small>manage station notification</small>
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
                <table class="table table-striped table-bordered table-hover text-center" id="nofActive">
                  <thead>
                  <tr>
                    <th>#</th>
                    <th>Code</th>
                    <th>Chang Name</th>
                    <th>Change Street</th>
                    <th>Change Latitude</th>
                    <th>Change Longitude</th>
                    <th>Approve</th>
                    <th>Block</th>
                    <th>Delete</th>
                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach var="nofActive" items="${nofListVM.listNofActive}" varStatus="count">
                    <tr>

                    </tr>
                  </c:forEach>
                  </tbody>
                </table>
              </div>
              <div class="tab-pane fade" id="tab2">
                <table class="table table-striped table-bordered table-hover text-center" id="nofInActive">
                  <thead>
                  <tr>
                    <th>#</th>
                    <th>Route No</th>
                    <th>Content</th>
                    <th>Approve</th>
                    <th>Unblock</th>
                    <th>Delete</th>
                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach var="nofInActive" items="${nofListVM.listNofInActive}" varStatus="count">
                    <tr>
                      <td>${count.count}</td>
                      <td>18(Hard Code)</td>
                      <td>${nofInActive.content}</td>
                      <td>
                        <a class="approve" href="javascript:;">
                          Approve
                        </a>
                      </td>
                      <td>
                        <a class="unblock" href="javascript:;">
                          Unblock
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


