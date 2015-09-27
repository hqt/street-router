<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- model for add new payment -->
<div class="modal fade" id="add-payment-modal">
    <div class="modal-dialog">
        <form action="${pageContext.request.contextPath}/staff/contract" method="post" class="form-horizontal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Thêm thông tin thanh toán</h4>
                </div>
                <div class="modal-body">
                    <jsp:include page="contract-detail-general.jsp" flush="true"/>
                    <%--/Contract information--%>
                    <br/>
                    <fieldset>
                        <legend>Thông tin thanh toán</legend>
                        <!-- Paid date -->
                        <div class=" form-group">
                            <label class="col-sm-4 control-label" for="addPaidDate">Ngày nộp phí *</label>

                            <div class="col-sm-4">
                                <input id="addPaidDate" name="createPayment:paidDate" class="form-control input-md"
                                       type="date" required>
                            </div>
                        </div>

                        <!-- Content -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="addContent">Dịch vụ *</label>

                            <div class="col-sm-7">
                                <input id="addContent" name="createPayment:content" class="form-control input-md"
                                       type="text" required maxlength="250"
                                       title="Vui lòng nhập dịch vụ cần thanh toán"
                                       placeholder="Ví dụ: Đăng ký hợp đồng mới KH0001">
                            </div>
                        </div>

                        <!-- Amount -->
                        <div class=" form-group">
                            <label class="col-sm-4 control-label" for="addAmount">Số tiền *</label>

                            <div class="col-sm-5">
                                <div class="input-group">
                                    <input id="addAmount" name="createPayment:amount" class="form-control input-md"
                                           type="number" required min="0" max="1000000000"
                                           title="Vui lòng nhập số tiền" placeholder="Ví dụ: 80000">

                                    <div class="input-group-addon">VNĐ</div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <%--/Payment information--%>
                </div>
                <div class="modal-footer">
                    <input type="hidden" name="createPayment:contractCode" value="${contract.contractCode}"/>
                    <input type="hidden" name="action" value="createPayment"/>
                    <button type="submit" class="btn btn-success">
                        <i class="fa fa-arrow-right"></i> Thêm thông tin thanh toán
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </form>
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- model for complete payment -->
<div class="modal fade" id="complete-payment-modal">
    <div class="modal-dialog">
        <form action="${pageContext.request.contextPath}/staff/contract" method="post" class="form-horizontal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Hoàn tất thông tin thanh toán</h4>
                </div>
                <div class="modal-body">
                    <jsp:include page="contract-detail-general.jsp" flush="true"/>
                    <%--/Contract information--%>
                    <br/>
                    <fieldset>
                        <legend>Thông tin thanh toán</legend>

                        <!-- Paid date -->
                        <div class=" form-group">
                            <label class="col-sm-5 control-label" for="paidDate">Ngày nộp phí *</label>

                            <div class="col-sm-4">
                                <input id="completePaidDate" name="completePayment:paidDate"
                                       class="form-control input-md"
                                       type="date" required>
                                <input type="hidden" name="completePayment:amount" value="${contract.contractFee}"/>
                            </div>
                        </div>
                    </fieldset>
                    <%--/Payment information--%>
                </div>
                <div class="modal-footer">
                    <input type="hidden" name="completePayment:contractCode" value="${contract.contractCode}"/>
                    <input type="hidden" name="action" value="completePayment"/>
                    <button type="submit" class="btn btn-success">
                        <i class="fa fa-arrow-right"></i> Hoàn tất thanh toán
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </form>
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- model for detail payment -->
<div class="modal fade" id="detail-payment-modal">
    <div class="modal-dialog">
        <form class="form-horizontal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Chi tiết thông tin thanh toán</h4>
                </div>
                <div class="modal-body">
                    <fieldset>
                        <!-- Payment ID & Paid date -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Mã giao dịch</label>

                            <div class="col-sm-2">
                                <div class="text-value">
                                    <strong id="payment-id-value"></strong>
                                </div>
                            </div>

                            <label class="col-sm-3 control-label">Mã hợp đồng</label>

                            <div class="col-sm-2">
                                <div class="text-value" id="contract-code-value"></div>
                            </div>
                        </div>

                        <!-- Amount & Content -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Dịch vụ</label>

                            <div class="col-sm-7">
                                <div class="text-value" id="content-value"></div>
                            </div>
                        </div>

                        <!-- Amount -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Số tiền</label>

                            <div class="col-sm-4">
                                <div class="text-value" id="amount-value"></div>
                            </div>
                        </div>

                        <!-- Payment method & Paid date -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Hình thức</label>

                            <div class="col-sm-2">
                                <div class="text-value" id="payment-method-value"></div>
                            </div>

                            <label class="col-sm-2 control-label">Thời gian</label>

                            <div class="col-sm-4">
                                <div class="text-value" id="paid-date-value"></div>
                            </div>
                        </div>

                        <!-- Paypal ID -->
                        <div id="ppIDCtrl" class="form-group hide">
                            <label class="col-sm-3 control-label">Mã Paypal</label>

                            <div class="col-sm-4">
                                <div class="text-value" id="paypal-id-value"></div>
                            </div>
                        </div>

                        <!-- Receiver -->
                        <div id="receiverCtrl" class="form-group hide">
                            <label class="col-sm-3 control-label">Người nhận</label>

                            <div class="col-sm-4">
                                <div class="text-value" id="receiver-value"></div>
                            </div>
                        </div>

                        <!-- Start date -->
                        <div id="stDateCtrl" class="form-group hide">
                            <label class="col-sm-5 control-label">Ngày bắt đầu gia hạn</label>

                            <div class="col-sm-4">
                                <div class="text-value" id="start-date-value"></div>
                            </div>
                        </div>

                        <!-- Expired date -->
                        <div id="expDateCtrl" class="form-group hide">
                            <label class="col-sm-5 control-label">Ngày hết hạn hợp đồng</label>

                            <div class="col-sm-4">
                                <div class="text-value" id="expired-date-value"></div>
                            </div>
                        </div>
                    </fieldset>
                    <%--/Payment information--%>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </form>
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- model for renew contract -->
<div class="modal fade" id="renew-contract-modal">
    <div class="modal-dialog">
        <form action="${pageContext.request.contextPath}/staff/contract" method="post" class="form-horizontal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Gia hạn hợp đồng</h4>
                </div>
                <div class="modal-body">
                    <c:choose>
                        <c:when test="${contract.micContractTypeByContractTypeId.active eq 1}">
                            <jsp:include page="contract-detail-general.jsp" flush="true"/>
                            <legend>Thông tin gia hạn hợp đồng</legend>
                            <!-- New expired date -->
                            <div class="form-group">
                                <label class="col-sm-4 control-label" for="expiredDate">Gia hạn đến *</label>

                                <div class="col-sm-4">
                                    <input id="expiredDate" name="renew:expiredDate" class="form-control input-md"
                                           type="date" required>
                                </div>
                            </div>
                            <!-- Renew fee -->
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Phí gia hạn</label>

                                <div class="col-sm-3">
                                    <div class="text-value">
                                    <span id="renewFee"
                                          style="color:deepskyblue; font-weight: bolder; font-size: large"></span> VNĐ
                                        <input type="hidden" id="contractFee" name="renew:contractFee"/>
                                    </div>
                                </div>

                                <label class="col-sm-3 control-label control-newcard" for="newCard">Cấp thẻ mới

                                    <i class="fa fa-question-circle" id="newCardTooltip" data-toggle="tooltip"
                                       data-placement="bottom" title=""
                                       data-original-title="Thẻ cũ sẽ bị vô hiệu hóa, yêu cầu cấp thẻ mới sẽ được tạo."></i>
                                </label>

                                <div class="col-sm-1">
                                    <div class="text-value">
                                        <input type="checkbox" id="newCard" class="control-newcard" name="renew:newCard"
                                               value="true">
                                    </div>
                                </div>
                            </div>

                            <div id="collapseNewCard" class="panel-collapse collapse in control-delivery">
                                <!-- New card fee -->
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">Phí thẻ mới</label>

                                    <div class="col-sm-3">
                                        <div class="text-value">
                                    <span id="newCardFee"
                                          style="color:navy; font-weight: bolder; font-size: large"></span> VNĐ
                                        </div>
                                    </div>

                                    <label class="col-sm-3 control-label control-delivery" for="deliveryNewCard">
                                        Vận chuyển thẻ
                                    </label>

                                    <div class="col-sm-1">
                                        <div class="text-value">
                                            <input type="checkbox" id="deliveryNewCard" class="control-delivery"
                                                   name="renew:deliveryNewCard" value="true">

                                        </div>
                                    </div>
                                </div>

                                <div id="collapseDelivery" class="panel-collapse collapse in ">
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">Phí vận chuyển</label>

                                        <div class="col-sm-3">
                                            <div class="text-value">
                                    <span id="deliveryFee"
                                          style="font-weight: bolder; font-size: large"></span> VNĐ
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Total fee -->
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">Tổng chi phí</label>

                                    <div class="col-sm-3">
                                        <div class="text-value">
                                    <span id="totalFee"
                                          style="color:red; font-weight: bolder; font-size: large"></span> VNĐ
                                            <input type="hidden" id="amount" name="renew:amount"/>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Paid date -->
                            <div class=" form-group">
                                <label class="col-sm-4 control-label" for="paidDate">Ngày nộp phí *</label>

                                <div class="col-sm-4">
                                    <input id="paidDate" name="renew:paidDate" class="form-control input-md"
                                           type="date" required>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-warning">
                                <p class="bs-example text-center text-uppercase">
                                    Không thể gia hạn hợp đồng này
                                </p>
                            </div>
                            <p class="text-center text-primary">
                                Loại hợp đồng <strong>${contract.micContractTypeByContractTypeId.name}</strong>
                                đã bị ngưng hoạt động theo chính sách của công ty
                            </p>
                        </c:otherwise>
                    </c:choose>

                </div>
                <div class="modal-footer">
                    <input type="hidden" name="renew:contractCode" value="${contract.contractCode}"/>
                    <input type="hidden" name="action" value="renew"/>
                    <c:if test="${contract.micContractTypeByContractTypeId.active eq 1}">
                        <button type="submit" class="btn btn-primary">
                            <i class="fa fa-arrow-right"></i> Gia hạn hợp đồng
                        </button>
                    </c:if>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </form>
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- model for handle request cancel contract -->
<div class="modal fade" id="handle-request-cancel-modal">
    <div class="modal-dialog">
        <form action="${pageContext.request.contextPath}/staff/contract" method="post" class="form-horizontal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Giải quyết yêu cầu hủy hợp đồng</h4>
                </div>
                <div class="modal-body">
                    <fieldset>
                        <!-- Cancel date -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Thời điểm gởi yêu cầu</label>

                            <div class="col-sm-4">
                                <div class="text-value">
                                    <fmt:formatDate value="${contract.cancelDate}" pattern="dd/MM/yyyy"/> lúc
                                    <fmt:formatDate value="${contract.cancelDate}" type="time"/>
                                </div>
                            </div>
                        </div>

                        <!-- Cancel reason -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Lý do hủy hợp đồng</label>

                            <div class="col-sm-7">
                                <div class="text-value">${contract.cancelReason}</div>
                            </div>
                        </div>

                        <!-- Decision -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="decision">Quyết định của công ty *</label>

                            <div class="col-sm-5">
                                <select class="form-control" id="decision" name="handleRequest:decision" required>
                                    <option value="cancelContract">Hủy hợp đồng này</option>
                                    <option value="rejectRequest">Tiếp tục duy trì hợp đồng</option>
                                </select>
                            </div>
                        </div>

                        <!-- Cancel note -->
                        <div class="form-group control-cancel-note">
                            <label class="col-sm-4 control-label" for="handleNote">Ghi chú</label>

                            <div class="col-sm-7">
                                <textarea id="handleNote" name="handleRequest:cancelNote" rows="2" maxlength="2000"
                                          class="form-control input-md"></textarea>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="modal-footer">
                    <input type="hidden" name="handleRequest:contractCode" value="${contract.contractCode}"/>
                    <input type="hidden" name="action" value="handleCancelRequest"/>
                    <button type="submit" class="btn btn-primary">
                        <i class="fa fa-check"></i> Xác nhận
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </form>
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- model for cancel contract -->
<div class="modal fade" id="cancel-contract-modal">
    <div class="modal-dialog">
        <form action="${pageContext.request.contextPath}/staff/contract" method="post" class="form-horizontal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Hủy hợp đồng</h4>
                </div>
                <div class="modal-body">
                    <fieldset>
                        <!-- Contract code -->
                        <input type="hidden" name="cancel:contractCode" value="${contract.contractCode}"/>

                        <!-- Cancel date -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="cancelDate">Ngày hủy hợp đồng *</label>

                            <div class="col-sm-4">
                                <input id="cancelDate" name="cancel:cancelDate" type="date" required
                                       class="form-control input-md"
                                       value="<fmt:formatDate value="${contract.cancelDate}" pattern="yyyy-MM-dd"/>">
                            </div>
                        </div>

                        <!-- Cancel reason -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="cancelReason">Lý do hủy hợp đồng *</label>

                            <div class="col-sm-7">
                                <textarea id="cancelReason" name="cancel:cancelReason" class="form-control input-md"
                                          rows="2" required maxlength="250">${contract.cancelReason}</textarea>
                            </div>
                        </div>

                        <!-- Cancel note -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="cancelNote">Ghi chú</label>

                            <div class="col-sm-7">
                                <textarea id="cancelNote" name="cancel:cancelNote" rows="2" maxlength="2000"
                                          class="form-control input-md"></textarea>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="modal-footer">
                    <input type="hidden" name="action" value="cancel"/>
                    <button type="submit" class="btn btn-primary">
                        <i class="fa fa-check"></i> Đồng ý hủy
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </form>
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->