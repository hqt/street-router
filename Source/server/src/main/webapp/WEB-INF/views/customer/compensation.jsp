<%@ include file="_shared/header.jsp" %>

<div id="wrapper">

    <%@ include file="_shared/navigation.jsp" %>


    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h2 class="page-header text-info fa-3x">Lịch Sử Bồi Thường
                          <span class="pull-right">
                              <a href="${pageContext.request.contextPath}/customer/compensation?action=create"
                                 class="btn btn-primary">Yêu Cầu Bồi Thường</a>
                          </span>
                </h2>

            </div>
        </div>
        <div class="row">

            <div class="panel panel-default">
                <div class="panel panel-heading">
                    <div class="pull-left">
                        <!--<input type="checkbox" class="check-all"/>-->
                        <b>Có 200 Vụ Bồi Thường</b>
                    </div>
                    <div class="pull-right ">
                        <input type="text" class="form-control long-text-box"
                               placeholder="Tìm kiếm theo tên, mã hợp đồng"/>
                        <input type="button" class="btn btn-default" value="Tìm kiếm"/>

                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-bordered">
                            <thead>
                            <tr class="success">
                                <th class="text-center ">#
                                </th>
                                <th class="text-center ">Mã yêu Cầu
                                </th>
                                <th class="text-center ">Mã Hợp Đồng
                                </th>
                                <th class="text-center ">Tên Khách Hàng
                                </th>
                                <th class="text-center ">Ngày Yêu Cầu
                                </th>
                                <th class="text-center ">Ngày Giải Quyết
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr class="active">
                                <td class="text-center">1</td>
                                <td class="text-center"><a
                                        href="${pageContext.request.contextPath}/customer/compensation?action=detail">COM736</a>
                                </td>
                                <td class="text-center"><a href="detail-contract.html">BHBB374</a></td>
                                <td class="text-center"><a href="detail-customer.html">Đinh Quang Trung</a></td>
                                <td class="text-center">04/05/2015</td>
                                <td class="text-center"><span class="label label-warning">Đang giải quyết</span></td>
                            </tr>
                            <tr class="">
                                <td class="text-center">2</td>
                                <td class="text-center"><a
                                        href="${pageContext.request.contextPath}/customer/compensation?action=detail">COM736</a>
                                </td>
                                <td class="text-center"><a href="detail-contract.html">BHBB374</a></td>
                                <td class="text-center"><a href="detail-customer.html">Đinh Quang Trung</a></td>
                                <td class="text-center">04/05/2015</td>
                                <td class="text-center"><span class="label label-warning">Đang giải quyết</span></td>
                            </tr>
                            <tr class="active">
                                <td class="text-center">3</td>
                                <td class="text-center"><a
                                        href="${pageContext.request.contextPath}/customer/compensation?action=detail">COM736</a>
                                </td>
                                <td class="text-center"><a href="detail-contract.html">BHBB374</a></td>
                                <td class="text-center"><a href="detail-customer.html">Đinh Quang Trung</a></td>
                                <td class="text-center">04/05/2015</td>
                                <td class="text-center"><span class="label label-warning">Đang giải quyết</span></td>
                            </tr>
                            <tr class="">
                                <td class="text-center">4</td>
                                <td class="text-center"><a
                                        href="${pageContext.request.contextPath}/customer/compensation?action=detail">COM736</a>
                                </td>
                                <td class="text-center"><a href="detail-contract.html">BHBB374</a></td>
                                <td class="text-center"><a href="detail-customer.html">Đinh Quang Trung</a></td>
                                <td class="text-center">04/05/2015</td>
                                <td class="text-center"><span class="label label-warning">Đang giải quyết</span></td>
                            </tr>
                            <tr class="active">
                                <td class="text-center">5</td>
                                <td class="text-center"><a
                                        href="${pageContext.request.contextPath}/customer/compensation?action=detail">COM736</a>
                                </td>
                                <td class="text-center"><a href="detail-contract.html">BHBB374</a></td>
                                <td class="text-center"><a href="detail-customer.html">Đinh Quang Trung</a></td>
                                <td class="text-center">04/05/2015</td>
                                <td class="text-center"><span>10/10/2015</span></td>
                            </tr>
                            <tr class="">
                                <td class="text-center">6</td>
                                <td class="text-center"><a
                                        href="${pageContext.request.contextPath}/customer/compensation?action=detail">COM736</a>
                                </td>
                                <td class="text-center"><a href="detail-contract.html">BHBB374</a></td>
                                <td class="text-center"><a href="detail-customer.html">Đinh Quang Trung</a></td>
                                <td class="text-center">04/05/2015</td>
                                <td class="text-center"><span>10/10/2015</span></td>
                            </tr>
                            <tr class="active">
                                <td class="text-center">7</td>
                                <td class="text-center"><a
                                        href="${pageContext.request.contextPath}/customer/compensation?action=detail">COM736</a>
                                </td>
                                <td class="text-center"><a href="detail-contract.html">BHBB374</a></td>
                                <td class="text-center"><a href="detail-customer.html">Đinh Quang Trung</a></td>
                                <td class="text-center">04/05/2015</td>
                                <td class="text-center"><span>10/10/2015</span></td>
                            </tr>
                            <tr class="">
                                <td class="text-center">8</td>
                                <td class="text-center"><a
                                        href="${pageContext.request.contextPath}/customer/compensation?action=detail">COM736</a>
                                </td>
                                <td class="text-center"><a href="detail-contract.html">BHBB374</a></td>
                                <td class="text-center"><a href="detail-customer.html">Đinh Quang Trung</a></td>
                                <td class="text-center">04/05/2015</td>
                                <td class="text-center"><span>10/10/2015</span></td>
                            </tr>
                            <tr class="active">
                                <td class="text-center">9</td>
                                <td class="text-center"><a
                                        href="${pageContext.request.contextPath}/customer/compensation?action=detail">COM736</a>
                                </td>
                                <td class="text-center"><a href="detail-contract.html">BHBB374</a></td>
                                <td class="text-center"><a href="detail-customer.html">Đinh Quang Trung</a></td>
                                <td class="text-center">04/05/2015</td>
                                <td class="text-center"><span>10/10/2015</span></td>
                            </tr>
                            <tr class="">
                                <td class="text-center">10</td>
                                <td class="text-center"><a
                                        href="${pageContext.request.contextPath}/customer/compensation?action=detail">COM736</a>
                                </td>
                                <td class="text-center"><a href="detail-contract.html">BHBB374</a></td>
                                <td class="text-center"><a href="detail-customer.html">Đinh Quang Trung</a></td>
                                <td class="text-center">04/05/2015</td>
                                <td class="text-center"><span>10/10/2015</span></td>
                            </tr>
                            </tbody>
                        </table>
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
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<%@ include file="_shared/footer.jsp" %>
