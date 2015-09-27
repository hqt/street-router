<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: TriPQM
  Date: 07/02/2015
  Time: 11:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>

<div id="wrapper">

    <%@ include file="_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Thông tin cấu hình</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <br/>
                <form action="${pageContext.request.contextPath}/admin/config"
                      method="get" class="form-horizontal">
                    <fieldset>
                        <legend>Thời gian hoạt động của cấu hình</legend>
                    <label class="col-sm-7 control-label">Thời điểm bắt đầu cấu hình:</label>
                    <div class="col-sm-3">
                        <p class="text-value"><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${submitted.startDate}" /></p>
                    </div>
                    <label class="col-sm-7 control-label">Thời điểm kết thúc cấu hình:</label>
                    <div class="col-sm-3">
                        <c:if test="${empty expiredDate}">
                            <div class="text-value">
                                <span class="label label-success">Đang có hiệu lực</span>
                            </div>

                        </c:if>
                        <p class="text-value"><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${expiredDate}" /></p>
                    </div>
                    </fieldset>
                    <fieldset>
                        <legend>Các giới hạn ngày theo chính sách của công ty</legend>

                        <label class="col-sm-7 control-label">Thời hạn thanh toán hợp đồng mới:</label>

                        <div class="col-sm-2">
                            <p class="text-value">${submitted.paymentDueDate}</p>

                        </div>
                        <div class="col-sm-2">
                            <p class="text-value"> ngày</p>
                        </div>
                        <label class="col-sm-7 control-label">Thời gian thông báo hợp đồng sắp hết hạn lần 1:
                            Trước</label>

                        <div class="col-sm-2">
                            <p class="text-value">${submitted.nearlyExceedExpiredOne}</p>

                        </div>
                        <div class="col-sm-2">
                            <p class="text-value"> ngày</p>
                        </div>
                        <label class="col-sm-7 control-label">Thời gian thông báo hợp đồng sắp hết hạn lần 2:
                            Trước</label>

                        <div class="col-sm-2">
                            <p class="text-value">${submitted.nearlyExceedExpiredTwo}</p>

                        </div>
                        <div class="col-sm-2">
                            <p class="text-value"> ngày</p>
                        </div>
                        <label class="col-sm-7 control-label">Thời gian thông báo hợp đồng sắp hết hạn lần 3:
                            Trước</label>

                        <div class="col-sm-2">
                            <p class="text-value">${submitted.nearlyExceedExpiredThree}</p>

                        </div>
                        <div class="col-sm-2">
                            <p class="text-value"> ngày</p>
                        </div>
                        <label class="col-sm-7 control-label">Thời hạn mặc định của hợp đồng:</label>

                        <div class="col-sm-2">
                            <p class="text-value">${submitted.contractDefaultTerm}</p>

                        </div>
                        <div class="col-sm-2">
                            <p class="text-value"> tháng</p>
                        </div>
                        <label class="col-sm-7 control-label">Thời hạn tối thiểu của hợp đồng:</label>

                        <div class="col-sm-2">
                            <p class="text-value">${submitted.contractMinTerm}</p>

                        </div>
                        <div class="col-sm-2">
                            <p class="text-value"> tháng</p>
                        </div>
                        <label class="col-sm-7 control-label">Thời hạn tối đa còn lại để gia hạn hợp đồng:
                        </label>

                        <div class="col-sm-2">
                            <p class="text-value">${submitted.contractRenewLimit}</p>

                        </div>
                        <div class="col-sm-2">
                            <p class="text-value"> ngày</p>
                        </div>
                    </fieldset>
                    <fieldset>
                        <legend>Giới hạn chỉnh sửa ngày cho nhân viên</legend>
                        <label class="col-sm-7 control-label">Thời gian thêm thông tin sau khi hủy hợp đồng:</label>

                        <div class="col-sm-2">
                            <p class="text-value">${submitted.updateContractDueDate}</p>

                        </div>
                        <div class="col-sm-2">
                            <p class="text-value"> ngày</p>
                        </div>

                        <label class="col-sm-7 control-label">Ngày bắt đầu hợp đồng trước ngày hiện tại:</label>

                        <div class="col-sm-2">
                            <p class="text-value">${submitted.startDateBefore}</p>

                        </div>
                        <div class="col-sm-2">
                            <p class="text-value"> ngày</p>
                        </div>
                        <label class="col-sm-7 control-label">Ngày bắt đầu hợp đồng sau ngày hiện tại:</label>

                        <div class="col-sm-2">
                            <p class="text-value">${submitted.startDateAfter}</p>

                        </div>
                        <div class="col-sm-2">
                            <p class="text-value"> ngày</p>
                        </div>
                        <label class="col-sm-7 control-label">Ngày thanh toán hợp đồng trước ngày hiện tại:</label>

                        <div class="col-sm-2">
                            <p class="text-value">${submitted.paidDaterBefore}</p>

                        </div>
                        <div class="col-sm-2">
                            <p class="text-value"> ngày</p>
                        </div>
                        <label class="col-sm-7 control-label">Ngày thanh toán hợp đồng sau ngày hiện tại:</label>

                        <div class="col-sm-2">
                            <p class="text-value">${submitted.paidDateAfter}</p>

                        </div>
                        <div class="col-sm-2">
                            <p class="text-value"> ngày</p>
                        </div>
                        <label class="col-sm-7 control-label">Ngày huỷ hợp đồng trước ngày hiện tại:</label>

                        <div class="col-sm-2">
                            <p class="text-value">${submitted.cancelDateBefore}</p>

                        </div>
                        <div class="col-sm-2">
                            <p class="text-value"> ngày</p>
                        </div>
                        <label class="col-sm-7 control-label">Ngày huỷ hợp đồng sau ngày hiện tại:</label>

                        <div class="col-sm-2">
                            <p class="text-value">${submitted.cancelDateAfter}</p>

                        </div>
                        <div class="col-sm-2">
                            <p class="text-value"> ngày</p>

                        </div>
                    </fieldset>
                    <fieldset>
                        <legend>Các loại chi phí</legend>

                        <label class="col-sm-7 control-label">Phí làm thẻ mới:</label>

                        <div class="col-sm-2">
                            <p class="text-value"><span id="newCardRequestFee">${submitted.newCardRequestFee}</span></p>

                        </div>
                        <div class="col-sm-2">
                            <p class="text-value"> VNĐ</p>
                        </div>
                        <label class="col-sm-7 control-label">Phí vận chuyển thẻ:</label>

                        <div class="col-sm-2">
                            <p class="text-value"><span id="deliveryFee">${submitted.deliveryFee}</span></p>
                        </div>
                        <div class="col-sm-2">
                            <p class="text-value"> VNĐ</p>
                        </div>
                    </fieldset>
                    <br>

                    <div class="pull-left">
                        <a href="${pageContext.request.contextPath}/admin/config" type="button"
                           class="btn btn-default">
                            <i class="fa fa-arrow-left"></i>
                            Trở lại
                        </a>
                    </div>
                    <br>


                </form>

            </div>
        </div>
    </div>
</div>
<!-- /#wrapper -->


<%@ include file="_shared/footer.jsp" %>
<script language="javascript">
    Number.prototype.formatMoney = function(c, d, t){
        var n = this,
                c = isNaN(c = Math.abs(c)) ? 2 : c,
                d = d == undefined ? "." : d,
                t = t == undefined ? "," : t,
                s = n < 0 ? "-" : "",
                i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "",
                j = (j = i.length) > 3 ? j % 3 : 0;
        return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
    };

    var newCardRequestFee = '${submitted.newCardRequestFee}';
    var deliveryFee = '${submitted.deliveryFee}';

    $('#newCardRequestFee').text(parseFloat(newCardRequestFee).formatMoney(0,'.','.'));
    $('#deliveryFee').text(parseFloat(deliveryFee).formatMoney(0,'.','.'));

</script>