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
      Configuration Management <small>configure parse data</small>
    </h3>
    <!-- END PAGE HEADER-->
    <!-- BEGIN PAGE CONTENT-->
    <div class="row">
      <div class="col-md-12">
        <div class="portlet box green">
          <div class="portlet-title">
            <div class="caption">
              <i class="fa fa-gift"></i>Configuration Form
            </div>
            <div class="tools">
              <a href="javascript:;" class="collapse">
              </a>
            </div>
          </div>
          <div class="portlet-body form">
            <!-- BEGIN FORM-->
            <form action="" class="form-horizontal" method="POST">
              <div class="form-body">
                <div class="form-group">
                  <label class="col-md-3 control-label">Configure Time</label>

                  <div class="col-md-6">
                    <div class="input-group">
                      <input id="configureTime" type="text"
                             class="form-control bootstrap-timepicker timepicker input-circle" name="timeConfigure">
                      <br/>
                      <div class="input-group">
                        <div class="checkbox-list">
                          Repeat day
                          <input type="checkbox" name="repeatDay" value="2"/> 2
                          <input type="checkbox" name="repeatDay" value="3"/> 3
                          <input type="checkbox" name="repeatDay" value="4"/> 4
                          <input type="checkbox" name="repeatDay" value="5"/> 5
                          <input type="checkbox" name="repeatDay" value="6"/> 6
                          <input type="checkbox" name="repeatDay" value="7"/> 7
                          <input type="checkbox" name="repeatDay" value="8"/> CN
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="form-actions">
                  <div class="row">
                    <div class="col-md-offset-3 col-md-9">
                      <button type="submit" class="btn btn-circle blue" name="action" value="configure">Configure</button>
                      <button type="button" class="btn btn-circle default">Cancel</button>
                    </div>
                  </div>
                </div>
              </div>
            </form>
            <!-- END FORM-->
          </div>
        </div>
      </div>
    </div>
    <!-- END PAGE CONTENT-->
  </div>
</div>
<!-- END CONTENT -->


