<%@ page import="java.util.Date" %>
<%@ include file="_shared/header.jsp" %>

<div id="wrapper">

    <%@ include file="_shared/navigation.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h2 class="page-header text-info">Lịch Sử Vi Phạm Luật ATGT
                </h2>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel panel-heading">
                <div class="pull-left">
                    <!--<input type="checkbox" class="check-all"/>-->
                    <b>Có 10 trường hợp vi phạm</b>
                </div>
                <div class="pull-right ">
                    <input type="text" class="form-control long-text-box"
                           placeholder="Tìm kiếm theo tên, mã hợp đồng"/>
                    <input type="button" class="btn btn-default" value="Tìm kiếm"/>

                </div>
                <div class="clearfix"></div>
            </div>
            <div class="panel panel-body">

                <div class="table table-responsive">
                    <table class="table table-bordered">
                        <tbody>
                        <tr>
                            <th class="col-md-2 text-center">
                                Ngày Vi Phạm
                            </th>
                            <th class="col-md-9 text-center">
                                Nội Dung Vi Phạm
                            </th>
                            <th class="col-md-1 text-center">
                                Biên Bản
                            </th>
                        </tr>
                        </tbody>
                        <tbody>
                        <% for (int i = 1; i <= 10; i++) {
                            out.print("   <tr>\n" +
                                    "                            <td class=\"text-center\">\n" +
                                    "                                12/02/2015\n" +
                                    "                            </td>\n" +
                                    "                            <td class=\"text-left\">\n" +
                                    "                                Sử dụng Giấy đăng ký xe bị tẩy xóa; Không đúng số khung, số máy hoặc không do cơ quan có\n" +
                                    "                                thẩm quyền cấp.\n" +
                                    "                                Phạt tiền từ 300.000 đến 400.000. Đồng thời tịch thu Giấy đăng ký không hợp lệ\n" +
                                    "                            </td>\n" +
                                    "                            <td class=\"text-center\">\n" +
                                    "                                <a href=\"#\" id=\"showPunishment_" + i + "\" class=\"fa-lg\"><i class=\"fa fa-file-image-o\"></i></a>\n" +
                                    "                            </td>\n" +

                                    "     </tr> ");
                        }

                        %>

                        </tbody>
                    </table>

                </div>
            </div>
            <div class="panel panel-footer">
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

<%@ include file="_shared/footer.jsp" %>
