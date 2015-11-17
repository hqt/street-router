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
                    <i class="fa fa-gift"></i>Add Route Actions
                </div>
                <div class="tools">
                    <a href="javascript:;" class="collapse">
                    </a>
                </div>
            </div>
            <div class="portlet-body form">
                <!-- BEGIN FORM-->
                <form action="" class="form-horizontal" enctype="multipart/form-data" method="post">
                    <div class="form-body">
                        <div class="form-group">
                            <label class="col-md-3 control-label">Route No</label>

                            <div class="col-md-4">
                                <input type="text" class="form-control input-circle" placeholder="Route No" name="routeNo">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Route Name</label>

                            <div class="col-md-4">
                                <input type="text" class="form-control input-circle" placeholder="Route Name" name="routeName">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Route Type</label>

                            <div class="col-md-4">
                                <select class="form-control" name="routeType">
                                    <option value="DEPART">DEPART</option>
                                    <option value="RETURN">RETURN</option>
                                </select>
                            <span class="help-block">
                            Select route type </span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Choose json source</label>
                            <div class="col-md-4">
                                <span class="btn btn-default btn-file">
                                    <input type="file" accept=".json" name="jsonFile"/>
                                </span>
                                <span class="help-block">
                                Extension file must be: '.json' </span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Choose excel source</label>
                            <div class="col-md-4">
                                <span class="btn btn-default btn-file">
                                    <input type="file" accept=".xls" name="excelFile"/>
                                </span>
                                <span class="help-block">
                                Extension file must be: '.xls' </span>
                            </div>
                        </div>
                    </div>

                    <div class="form-actions">
                        <div class="row">
                            <div class="col-md-offset-3 col-md-9">
                                <button type="submit" class="btn btn-circle blue" name="action" value="addRoute">Add</button>
                                <button type="reset" class="btn btn-circle default">Cancel</button>
                            </div>
                        </div>
                    </div>
                </form>
                <!-- END FORM-->
            </div>
        </div>
    </div>
</div>