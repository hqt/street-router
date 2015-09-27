<%@ include file="_shared/header.jsp" %>

<div id="wrapper">

    <%@ include file="_shared/navigation.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Thẻ AC7-37F-E8E-DDC
                        <span class="pull-right">
                            <a href="${pageContext.request.contextPath}/customer/card?action=newCard"
                               class="btn btn-primary">Yêu Cầu Thẻ Mới</a>
                        </span>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <form class="form-horizontal">
                    <fieldset>
                        <legend>Thông tin

                        </legend>
                        <br/>

                        <div class="form-group">
                            <label class="col-sm-4 control-label">Mã thẻ</label>

                            <div class="col-sm-6">
                                <div class="text-value text-primary">
                                    <a href="#"><b>AC7-37F-E8E-DDC</b></a>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-4 control-label">Khách hàng sở hữu</label>

                            <div class="col-sm-6">
                                <div class="text-value text-primary">
                                    <a href="detail-customer.html"><b>Đinh Quang Trung</b></a>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-4 control-label">Trạng thái</label>

                            <div class="col-sm-6">
                                <div class="text-value">
                                    <span class="label label-success">Hoạt động</span>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-4 control-label">Ngày bắt đầu có hiệu lực</label>

                            <div class="col-sm-6">
                                <div class="text-value">
                                    Ngày 2 tháng 3 năm 2014, lúc 09:23:22

                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-4 control-label">Lần cuối truy cập</label>

                            <div class="col-sm-8">
                                <div class="text-value">
                                    Ngày 7 tháng 4 năm 2015, lúc 17:20:21

                                    <i class="fa fa-question-circle"
                                       data-toggle="tooltip" data-placement="bottom"
                                       title="Ghi nhận lần cuối cùng thẻ được đọc bởi thiết bị từ Cảnh Sát Giao Thông."></i>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                </form>

                <legend>Lịch sử truy cập</legend>

                <p>
                    Có tổng số <b>201 Thẻ</b>
                </p>

                <div class="panel panel-default">
                    <div class="panel panel-heading">
                        <div class="pull-left">
                            <!--<input type="checkbox" class="check-all"/>-->
                        </div>
                        <div class="pull-right">
                            <form class="form-inline">
                                <div class="form-group">
                                    <label>Từ</label>
                                    <input type="datetime" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label>Đến</label>
                                    <input type="datetime" class="form-control">
                                </div>
                                <button type="submit" class="btn btn-primary">Tìm Kiếm</button>
                            </form>


                        </div>
                        <div class="clearfix"></div>
                    </div>

                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-bordered ">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Thời gian</th>
                                    <th>Thiết bị</th>
                                    <th>Dịch vụ</th>
                                    <th>Kết quả trả về</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>3/4/2015 10:02:34</td>
                                    <td>30579182353904</td>
                                    <td>Xác thực thẻ bảo hiểm</td>
                                    <td><span class="label label-success">Thẻ hợp lệ</span></td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>3/4/2015 10:02:34</td>
                                    <td>30579182353904</td>
                                    <td>Xác thực thẻ bảo hiểm</td>
                                    <td><span class="label label-warning">Thẻ sắp hết hạn</span></td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>3/4/2015 10:02:34</td>
                                    <td>30579182353904</td>
                                    <td>Gửi thông tin vi phạm</td>
                                    <td><span class="label label-success">Nhận dữ liệu thành công</span></td>
                                </tr>

                                <tr>
                                    <td>1</td>
                                    <td>3/4/2015 10:02:34</td>
                                    <td>30579182353904</td>
                                    <td>Xác thực thẻ bảo hiểm</td>
                                    <td><span class="label label-success">Thẻ hợp lệ</span></td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>3/4/2015 10:02:34</td>
                                    <td>30579182353904</td>
                                    <td>Xác thực thẻ bảo hiểm</td>
                                    <td><span class="label label-success">Thẻ hợp lệ</span></td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>3/4/2015 10:02:34</td>
                                    <td>30579182353904</td>
                                    <td>Xác thực thẻ bảo hiểm</td>
                                    <td><span class="label label-success">Thẻ hợp lệ</span></td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>3/4/2015 10:02:34</td>
                                    <td>30579182353904</td>
                                    <td>Xác thực thẻ bảo hiểm</td>
                                    <td><span class="label label-success">Thẻ hợp lệ</span></td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>3/4/2015 10:02:34</td>
                                    <td>30579182353904</td>
                                    <td>Xác thực thẻ bảo hiểm</td>
                                    <td><span class="label label-success">Thẻ hợp lệ</span></td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>3/4/2015 10:02:34</td>
                                    <td>30579182353904</td>
                                    <td>Xác thực thẻ bảo hiểm</td>
                                    <td><span class="label label-success">Thẻ hợp lệ</span></td>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td>3/4/2015 10:02:34</td>
                                    <td>30579182353904</td>
                                    <td>Xác thực thẻ bảo hiểm</td>
                                    <td><span class="label label-success">Thẻ hợp lệ</span></td>
                                </tr>
                                </tbody>
                            </table>
                            <!-- /.table-responsive -->
                        </div>

                        <nav class="text-right">
                            <ul class="pagination">
                                <li>
                                    <a href="#" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                                <li class="active"><a href="#">1</a></li>
                                <li><a href="#">2</a></li>
                                <li><a href="#">3</a></li>
                                <li><a href="#">4</a></li>
                                <li><a href="#">5</a></li>
                                <li>
                                    <a href="#" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
            <!-- col -->
        </div>
    </div>
</div>
<!-- /#page-wrapper -->

<%@ include file="_shared/footer.jsp" %>
