<%--
  Created by IntelliJ IDEA.
  User: datnt
  Date: 11/1/2015
  Time: 9:58 PM
  To change this template use File | Settings | File Templates.
--%>
<div class="page-content-wrapper">
  <div class="page-content">
    <div class="portlet box green">
      <div class="portlet-title">
        <div class="caption">
          <i class="fa fa-gift"></i>Add Trip Actions
        </div>
        <div class="tools">
          <a href="javascript:;" class="collapse">
          </a>
        </div>
      </div>
      <div class="portlet-body form">
        <!-- BEGIN FORM-->
        <form action="#" class="form-horizontal">
          <div class="form-body">
            <div class="form-group">
              <label class="col-md-3 control-label">Route No</label>

              <div class="col-md-4">
                <input type="text" class="form-control input-circle" value="Tuyến số 50" disabled>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3 control-label">Route Name</label>

              <div class="col-md-4">
                <input type="text" class="form-control input-circle" value="Bến Thành - Chợ lớn" disabled>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3 control-label">Route Type</label>
              <div class="col-md-4">
                <input type="text" class="form-control input-circle" value="DEPART" disabled>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3 control-label">Start Time</label>

              <div class="col-md-3">
                <div class="input-group">
                  <input type="text" class="form-control timepicker-24">
													<span class="input-group-btn">
													<button class="btn default" type="button"><i class="fa fa-clock-o"></i></button>
													</span>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3 control-label">End Time</label>

              <div class="col-md-3">
                <div class="input-group">
                  <input type="text" class="form-control timepicker timepicker-24">
													<span class="input-group-btn">
													<button class="btn default" type="button"><i class="fa fa-clock-o"></i></button>
													</span>
                </div>
              </div>
            </div>
          </div>
          <div class="form-actions">
            <div class="row">
              <div class="col-md-offset-3 col-md-9">
                <button type="submit" class="btn btn-circle blue">Submit</button>
                <button type="button" class="btn btn-circle default">Cancel</button>
              </div>
            </div>
          </div>
        </form>
        <!-- END FORM-->
      </div>
    </div>
  </div>
</div>