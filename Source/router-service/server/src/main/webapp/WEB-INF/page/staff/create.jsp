<%--
  Created by IntelliJ IDEA.
  User: datnt
  Date: 10/20/2015
  Time: 2:47 PM
  To change this template use File | Settings | File Templates.
--%>
<div class="page-content-wrapper">
    <div class="page-content">
        <div class="portlet box green">
            <div class="portlet-title">
                <div class="caption">
                    <i class="fa fa-gift"></i>Staff Add Actions
                </div>
                <div class="tools">
                    <a href="javascript:;" class="collapse">
                    </a>
                </div>
            </div>
            <div class="portlet-body form">
                <!-- BEGIN FORM-->
                <form action="#" class="form-horizontal" method="POST">
                    <div class="form-body">
                        <div class="form-group">
                            <label class="col-md-3 control-label">Full Name</label>

                            <div class="col-md-4">
                                <input type="text" class="form-control input-circle" placeholder="Enter staff name">
															<span class="help-block">
															@Example: Ngô Tiến Đạt. </span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Account Name</label>

                            <div class="col-md-4">
                                <input type="text" class="form-control input-circle" placeholder="Enter Account name">
															<span class="help-block">
															@Example: datntse60980. </span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Email Address</label>

                            <div class="col-md-4">
                                <div class="input-group">
																<span class="input-group-addon input-circle-left">
																<i class="fa fa-envelope"></i>
																</span>
                                    <input type="email" class="form-control input-circle-right"
                                           placeholder="Email Address">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Password</label>

                            <div class="col-md-4">
                                <div class="input-group">
                                    <input type="password" class="form-control input-circle-left"
                                           placeholder="Password">
																<span class="input-group-addon input-circle-right">
																<i class="fa fa-user"></i>
																</span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Phone Number</label>

                            <div class="col-md-4">
                                <div class="input-icon">
                                    <input type="text" class="form-control input-circle" placeholder="Phone number">
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