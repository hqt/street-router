<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.fpt.mic.micweb.utils.DateUtils" %>
<%@ page import="javax.rmi.CORBA.Util" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: PhucNguyen
  Date: 26/05/2015
  Time: 10:09 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>
<style type="text/css">
    .fixCheckbox {
        padding-left: 4%
    }

    .handleInput {
        border: none;
        background-color: white;
        width: 100%;
        padding-top: 6px;
    }

    .tooltip-inner {
        font-size: 13px;
        background-color: #3D6199;
        color: White;
        min-width: 400px;
        border-radius: 5px;
    }

    .tooltip:before {
        border-color: transparent #3D6199 transparent transparent;
        border-right: 6px solid #3D6199;
        border-style: solid;
        border-width: 6px 6px 6px 0px;
        content: "";
        display: block;
        height: 0;
        width: 0;
        line-height: 0;
        position: absolute;

    }

    .tooltip-arrow {
        display: none;
    }

    .fixWell {
        padding-top: 10px;
        height: 200px;
        margin-bottom: 20px;
        background-color: #f5f5f5;
        border: 1px solid #e3e3e3;
        border-radius: 5px;
        -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .05);
        box-shadow: inset 0 1px 1px rgba(0, 0, 0, .05);

    }

</style>
<div id="wrapper">
    <%@ include file="_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">

            <div class="col-lg-12">
                <h2 class="page-header ">Hợp Đồng ${contract.contractCode}
                     <span class="pull-right">
                           <input type="hidden" id="defaultRenew"
                                  value="${configUtils.contractDefaultTerm}">
                            <input type="hidden" id="countDateRemain" value="${countDateRemain}">
                            <input type="hidden" id="contractTypeStatus"
                                   value="${contract.getMicContractTypeByContractTypeId().getActive()}">
                            <input type="hidden" id="contractStatus1" value="${contract.status}">
                            <input type="hidden" id="contractRenewLimit" value="${configUtils.contractRenewLimit}">
                            <button type="submit" class="btn btn-primary hide "
                                    data-toggle="modal" id="renew"
                                    data-target=".renew-contract-modal"><i
                                    class="fa fa-refresh "></i> Gia Hạn
                            </button>
                           <button type="button" class="btn btn-primary ${handleShowingButton.checkRenew}"
                                   id="renewDefault">
                               <i class="fa fa-refresh"></i> Gia Hạn
                           </button>
                           <button type="button" class="btn btn-primary hide"
                                   data-toggle="modal" id="renewNotify"
                                   data-target=".notifyContractDeactive"><i
                                   class="fa fa-refresh hide"></i>
                           </button>

                           <button type="button" class="btn btn-danger ${handleShowingButton.checkCancelled}"
                                   data-toggle="modal" id="delete"
                                   data-target=".bs-example-modal-lg"><i class="fa fa-times"></i> Hủy Hợp Đồng
                           </button>
                     </span>
                </h2>
            </div>
            <form action="${pageContext.request.contextPath}/customer/contract" method="post">
                <div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
                     aria-labelledby="myLargeModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog modal-lg" style="width: 820px !important;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                        aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title">
                                    <label class="text-danger">Hủy Hợp Đồng</label></h4>

                            </div>
                            <div class="modal-body">
                                <div class="alert alert-danger alert-dismissible hide" id="notify" role="alert">
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                            aria-hidden="true">&times;</span></button>
                                    Vui lòng chọn lí do hủy hợp đồng trước khi xác nhận. Cảm ơn!
                                </div>
                                <div class="text-info fixCheckbox">
                                    <label>
                                        Quý khách vui lòng cung cấp lý do hoặc trường hợp hủy hợp đồng
                                    </label>
                                </div>
                                <div class="checkbox fixCheckbox">
                                    <label>
                                        <input type="radio" value="" name="rdbReason1" class="check" id="rdbReason1">
                                        Xe cơ giới bị thu hồi đăng ký và biển số theo quy định của pháp luật
                                    </label>
                                </div>
                                <div class="checkbox fixCheckbox">
                                    <label>
                                        <input type="radio" value="" name="rdbReason2" id="rdbReason2" class="check">
                                        Xe cơ giới hết niên hạn sử dụng theo quy định của pháp luật
                                    </label>
                                </div>
                                <div class="checkbox fixCheckbox">
                                    <label>
                                        <input type="radio" value="" name="rdbReason3" id="rdbReason3" class="check">
                                        Xe cơ giới bị mất được cơ quan công an xác nhận
                                    </label>
                                </div>
                                <div class="checkbox fixCheckbox">
                                    <label>
                                        <input type="radio" value="" name="rdbReason4" id="rdbReason4" class="check">
                                        Xe cơ giới hỏng không sử dụng được hoặc bị phá huỷ do tai nạn giao thông được cơ
                                        quan công an xác nhận
                                    </label>
                                </div>
                                <div class="checkbox fixCheckbox">
                                    <label>
                                        <input type="radio" value="" name="rdbAnother" id="rdbAnother" class="check">
                                        Lý do khác
                                    </label>
                                </div>
                                <div class="checkbox fixCheckbox">
                                    <label>
                                       <textarea name="txtAnotherReason" rows="3" cols="95" id="anotherReason"
                                                 class="hide"
                                                 autofocus="autofocus">
                                       </textarea>


                                    </label>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <%--Post to server (ContractController)--%>
                                <input type="hidden" name="action" value="CancelContract"/>
                                <input type="hidden" name="cancel:cancelReason" id="reason">
                                <input type="hidden" name="cancel:contractCode" id="contractId"
                                       value="${contract.contractCode}"/>
                                <%---------------------------------------%>
                                <input id="deleteContract" type="submit" class="btn btn-primary" name="Xác Nhận"
                                       value="Xác Nhận"/>
                                <input type="button" class="btn btn-default" id="cancelAction" data-dismiss="modal"
                                       value="Đóng"/>
                            </div>
                        </div>

                    </div>
                </div>
            </form>

            <form action="${pageContext.request.contextPath}/customer/contract" method="post">

                <div class="modal fade renew-contract-modal" tabindex="-1" role="dialog"
                     aria-labelledby="myLargeModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog modal-lg" style="width: 700px !important;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                        aria-hidden="true">&times;</span></button>
                                <h3 class="modal-title">Gia hạn hợp đồng</h3>
                            </div>
                            <div class="modal-body">
                                <div class="form-horizontal">
                                    <div class="alert alert-block alert-error fade
                                     in well well-lg text-info alertRenew hide">
                                        <h4 class="alert-heading text-center">KHÔNG THỂ GIA HẠN HỢP ĐỒNG CÒN GIÁ TRỊ
                                            TRÊN ${configUtils.contractRenewLimit} NGÀY</h4>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-5 text-right">Loại hợp đồng </label>

                                        <div class="col-sm-7">
                                            <input style="border:none ; background-color: white;width: 100% "
                                                   value="${contract.getMicContractTypeByContractTypeId().getName()}"
                                                   type="text" disabled="disabled"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-5 text-right">Thời điểm bắt đầu</label>

                                        <div class="col-sm-4">
                                            <input type="hidden" id="startDate"
                                                   value="${contract.startDate}"/>
                                            <input type="hidden" name="txtNewStartDate" id="newStartDate"
                                                   value="${contract.expiredDate}"/>
                                            <fmt:formatDate value="${contract.startDate}" pattern="dd/MM/yyyy"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-5 text-right">Thời điểm kết thúc </label>

                                        <div class="col-sm-4">
                                            <fmt:formatDate value="${contract.expiredDate}" pattern="dd/MM/yyyy"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-5 text-right">Gia hạn đến </label>

                                        <div class="col-sm-4">
                                            <input id="newExpiredDate" style="border:none; background-color: white"
                                                   type="hide" class="hide" disabled="disabled" value="${newDate}"/>
                                            <fmt:formatDate value="${newDate}"
                                                            pattern='dd/MM/yyyy'/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-5 text-right">Phí thanh toán </label>

                                        <div class="col-sm-4">
                                            <fmt:setLocale value="vi_VN"/>
                                            <input style="border:none; background-color: white" type="hidden"
                                                   id="payAmount" disabled="disabled"
                                                   value="${countFeeContract} VNĐ"/>
                                            <fmt:formatNumber value="${countFeeContract}" type="currency"
                                                              currencySymbol="" maxFractionDigits="0"/> VNĐ
                                        </div>

                                    </div>
                                </div>


                            </div>
                            <div class="modal-footer">
                                <input type="hidden" name="L_PAYMENTREQUEST_0_NAME0" id="content1" value="">
                                <input type="hidden" name="L_PAYMENTREQUEST_0_DESC0" id="content2">
                                <input type="hidden" name="L_PAYMENTREQUEST_0_QTY0" value="1">
                                <input type="hidden" name="PAYMENTREQUEST_0_ITEMAMT" id="payment">
                                <input type="hidden" name="PAYMENTREQUEST_0_TAXAMT" value="0">
                                <input type="hidden" name="PAYMENTREQUEST_0_AMT" id="paymentATM">
                                <input type="hidden" name="currencyCodeType" value="USD">
                                <input type="hidden" name="paymentType" value="Sale">
                                <input type="hidden" name="successUrl"
                                       value="/customer/contract?action=ActiveRenewContract">
                                <input type="hidden" name="txtContractCode" value="${contract.contractCode}"/>
                                <input type="hidden" name="action" value="RenewContract" id="actionId"/>
                                <input type="hidden" id="contractStatus" value="${contract.status}"/>
                                <input type="submit" class="btn btn-primary" value="Gia hạn hợp đồng" id="acceptRenew"/>

                                <button type="button" class="btn btn-danger" data-dismiss="modal">Đóng</button>
                            </div>
                        </div>
                    </div>
                </div>


                <!-- /.modal-content -->
            </form>
            <div class="modal fade notifyContractDeactive" tabindex="-1" role="dialog"
                 aria-labelledby="myLargeModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog modal-lg" style="width: 800px !important;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h3 class="modal-title text-danger">Không thể gia hạn hợp đồng</h3>
                        </div>
                        <div class="modal-body">
                            <div class="form-horizontal">
                                <div class="alert alert-block alert-error fade
                                     in well well-lg text-info alertRenew">
                                    <h4 class="alert-heading text-center">
                                        Hiện tại công ty đã ngừng cung cấp loại hợp đồng quý khách đang sử dụng
                                        (${contract.getMicContractTypeByContractTypeId().getName()}).
                                        Quý khách không thể gia hạn hợp đồng này. Vui lòng liên hệ nhân viên để biết thêm chi tiết
                                    </h4>
                                </div>
                            </div>

                        </div>
                        <div class="modal-footer">

                            <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <c:if test="${not empty validateErrors}">
                    <input type="hidden" id="modify-reason" value="${contract.modifyReason}"/>

                    <div class="well well-lg text-danger ">
                        <ul>
                            <c:forEach var="error" items="${validateErrors}">
                                <li>${error}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>
                <div class="text-info text-center">
                    ${message}
                </div>
                <c:if test="${param.info eq 'cancelNewCardRequestSuccess'}">
                    <div class="text-success text-center">
                        Hủy yêu cầu thẻ mới thành công
                    </div>
                </c:if>
                <c:if test="${param.info eq 'fail'}">
                    <div class="text-danger text-center">
                        Có lỗi xảy ra. Xin thử lại
                    </div>
                </c:if>
                <c:if test="${contract.status.equalsIgnoreCase('Request cancel')}">
                    <form action="${pageContext.request.contextPath}/customer/contract" method="post">
                        <div class="fixWell text-center text-danger " style="height: 65% !important;">
                            <label style="font-size: 18px">Hợp đồng đã được yêu cầu hủy vui lòng chờ xác nhận của nhân
                                viên</label> &nbsp;
                            <br/>
                            <input type="hidden" name="contractcode"
                                   value="${contract.contractCode}"/>
                            <input type="hidden" name="action" value="RejectRequestCancel"/>
                            <br/>

                            <div class="form-group">
                                <label class="col-md-3 text-right">Đã hủy vào: </label>

                                <div class="col-md-9 text-left">
                                    <fmt:formatDate value="${contract.cancelDate}" pattern="dd/MM/yyyy"/>
                                </div>
                            </div>
                            <br/>

                            <div class="form-group">
                                <label class="col-md-3 text-right">Lý do hủy: </label>

                                <div class="col-md-9 text-left">
                                        ${contract.cancelReason}
                                </div>
                                <br/>
                            </div>

                            <div style="padding-top: 1%">
                                <input type="submit" class="btn btn-danger small" value="Hủy yêu cầu"/>

                            </div>


                        </div>
                    </form>

                </c:if>
                <c:if test="${contract.status.equalsIgnoreCase('Cancelled')}">
                    <div class="text-center text-danger fixWell ">
                        <div class="form-group">
                            <label class="col-md-6 text-right" style="font-size: 18px"> Hợp đồng đã bị hủy </label>

                            <div class="col-md-6">

                            </div>
                        </div>
                        <br/>

                        <div class="form-group">
                            <label class="col-md-3 text-right">Đã hủy vào: </label>

                            <div class="col-md-9 text-left">
                                <fmt:formatDate value="${contract.cancelDate}" pattern="dd/MM/yyyy"/>
                            </div>
                        </div>
                        <br/>

                        <div class="form-group">
                            <label class="col-md-3 text-right">Lý do hủy: </label>

                            <div class="col-md-9 text-left">
                                    ${contract.cancelReason}
                            </div>
                        </div>
                        <br/>

                        <div class="form-group">
                            <label class="col-md-3 text-right">Ghi chú hủy: </label>

                            <div class="col-md-9 text-left">
                                <c:choose>
                                    <c:when test="${empty contract.cancelNote}">
                                        <label class="empty-value">Không có</label>
                                    </c:when>
                                    <c:otherwise>
                                        ${contract.cancelNote}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>


                </c:if>
                <c:if test="${contract.status.equalsIgnoreCase('Pending') && contract.startDate == contract.expiredDate}">

                    <div class="alert alert-block alert-error fade in well well-lg text-info">
                        <h4 class="alert-heading">Hợp đồng của quý khách chưa được thanh toán!</h4>

                        <p>Quý khách có thể thanh toán trực tiếp tại công ty
                            <button class="btn" data-toggle="modal" title="Hiện địa chỉ công ty"
                                    data-target=".map-modal"><i class="fa fa-map-marker"></i>
                            </button>
                        </p>
                        <button data-toggle="modal"
                                data-target=".payOnline" class="btn btn-primary">
                            Thanh toán online bằng Paypal
                        </button>
                    </div>
                    <form action="${pageContext.request.contextPath}/customer/contract" method="post">
                        <div class="modal fade payOnline" tabindex="-1" role="dialog"
                             aria-labelledby="myLargeModalLabel"
                             aria-hidden="true">
                            <div class="modal-dialog modal-lg" style="width: 600px !important;">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h4 class="modal-title">
                                            <label class="text-danger">Thông tin thanh toán</label></h4>

                                    </div>
                                    <div class="modal-body">
                                        <div class="form-horizontal">
                                            <div class="form-group">
                                                <label class="col-sm-5 text-right">Loại hợp đồng :</label>

                                                <div class="col-sm-7">
                                                        ${contract.getMicContractTypeByContractTypeId().getName()}
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-5 text-right">Thời điểm bắt đầu :</label>

                                                <div class="col-sm-4">
                                                    <fmt:formatDate value="${contract.startDate}"
                                                                    pattern='dd/MM/yyyy'/>
                                                    <input type="hidden" id="startDatePay"
                                                           value="${contract.startDate}">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-5 text-right">Thời điểm kết thúc :</label>

                                                <div class="col-sm-4">
                                                    <input type="hidden" id="configTime"
                                                           value=" ${configUtils.contractDefaultTerm}">
                                                        <%--<input type="text" style="padding-top: 0 !important;"--%>
                                                        <%--id="defaultDateExpired" disabled="disabled"--%>
                                                        <%--class="handleInput"/>--%>
                                                    <fmt:formatDate value="${newDate}"
                                                                    pattern='dd/MM/yyyy'/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-5 text-right">Phí thanh toán :</label>

                                                <div class="col-sm-4">
                                                    <input style="padding-top: 0 !important;" disabled="disabled"
                                                           type="text" id="textFree" class="handleInput"/>

                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <input id="payContract" type="submit" class="btn btn-primary"
                                               name="Thanh toán" value="Thanh toán"/>
                                        <input type="button" class="btn btn-default" data-dismiss="modal"
                                               value="Đóng"/>
                                        <!-- input hidden -->
                                        <input id="payAmount1" disabled="disabled" type="hidden"
                                               value="${countFeeContract} VNĐ"/>
                                        <input id="contractCode" disabled="disabled" type="hidden"
                                               value="${contract.contractCode}"/>
                                        <input type="hidden" name="L_PAYMENTREQUEST_0_NAME0" value="">
                                        <input type="hidden" name="L_PAYMENTREQUEST_0_DESC0" id="content3">
                                        <input type="hidden" name="L_PAYMENTREQUEST_0_QTY0" value="1">
                                        <input type="hidden" name="PAYMENTREQUEST_0_ITEMAMT" id="payment1">
                                        <input type="hidden" name="PAYMENTREQUEST_0_TAXAMT" value="0">
                                        <input type="hidden" name="PAYMENTREQUEST_0_AMT" id="paymentATM1">
                                        <input type="hidden" name="currencyCodeType" value="USD">
                                        <input type="hidden" name="paymentType" value="Sale">
                                        <input type="hidden" name="successUrl"
                                               value="/customer/contract?action=ActivePayContract">
                                        <input type="hidden" name="txtContractCode"
                                               value="${contract.contractCode}">
                                        <input type="hidden" name="action" value="PayContract">
                                    </div>
                                </div>

                            </div>
                        </div>

                    </form>
                    <div class="modal fade map-modal" tabindex="-1" role="dialog"
                         aria-labelledby="myLargeModalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-body">
                                    <iframe
                                            width="100%"
                                            height="500"
                                            frameborder="0" style="border:0"
                                            src="https://www.google.com/maps/embed/v1/place?key=AIzaSyBHWaWHbQJEFOvVmZw7tcR0qIGQQUoxsKM&q=Trường Đại Học FPT, tòa nhà Innovation, Công viên phần mềm Quang Trung, P.Tân Chánh Hiệp, Quận 12, TP. Hồ Chí Minh"
                                            >

                                    </iframe>
                                </div>

                            </div>
                        </div>
                    </div>

                </c:if>


                <div role="tabpanel">
                    <!-- Nav tabs -->
                    <ul class="nav nav-tabs" role="tablist">
                        <li role="presentation" class="active">
                            <a href="#commonInfo" aria-controls="profile" role="tab" data-toggle="tab">Thông tin
                                chung</a>
                        </li>
                        <li role="presentation">
                            <a href="#compensations" aria-controls="profile" role="tab" data-toggle="tab">Lịch sử bồi
                                thường</a>
                        </li>
                        <li role="presentation" id="showPunishment">
                            <a href="#punishments" aria-controls="messages" role="tab" data-toggle="tab">Lịch sử vi phạm
                                luật
                                GT</a>
                        </li>
                        <li role="presentation">
                            <a href="#accidents" aria-controls="messages" role="tab" data-toggle="tab">Lịch sử tai nạn
                            </a>
                        </li>

                        <%--<form action="${pageContext.request.contextPath}/customer/card" method="get">--%>
                        <c:if test="${empty newCardRequested }">
                            <c:if test="${contract.status.equalsIgnoreCase('Ready')}">
                                <div class="pull-right">
                                    <a href="${pageContext.request.contextPath}/customer/card?action=newCard&contractCode=${param.code}"
                                       class="btn btn-sm btn-primary">
                                        Yêu cầu thẻ mới
                                    </a>

                                </div>
                            </c:if>
                        </c:if>
                        <c:if test="${not empty newCardRequested}">
                            <div class="pull-right">
                                <p class="text-value">
                        <span class="label label-info"
                              style="font-size: 14px">Đang yêu cầu thẻ mới</span>
                                    <c:if test="${contract.status.equalsIgnoreCase('Ready')}">
                                        <c:if test="${newCardRequested.isPaid eq 0}">
                                            <button contractCode="${contract.contractCode}" type="button"
                                                    class="btn btn-danger btn-xs"
                                                    data-toggle="modal" data-target="#cancel-new-card-request" onclick="{
                                                                    var contractCode = $(this).attr('contractCode');
                                                                    $('#contractCodeModal').val(contractCode);
                                                                    $('#contractCodeModal1').text(contractCode);
                                                                 }">
                                                <i class="fa fa-times"></i> Hủy
                                            </button>
                                        </c:if>
                                    </c:if>
                                </p>
                            </div>

                        </c:if>


                        <%--</form>--%>
                    </ul>
                </div>
                <br/>

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="commonInfo">

                        <%--<div class="col-md-5">--%>
                        <%--<img src="http://finefrugality.files.wordpress.com/2012/06/handshake.jpg" width="100%" height="100%">--%>
                        <%--</div>--%>
                        <div class="form-horizontal">
                            <table class="table table-bordered">
                                <tr class="active">
                                    <td colspan="2" class="text-center" style="font-size: 15px"><label
                                            class="text-center">Thông tin
                                        hợp đồng</label></td>
                                </tr>
                                <tr>
                                    <td class="col-md-5">
                                        <table class="table table-bordered">
                                            <tr>
                                                <td class="col-md-4">
                                                    <label>Mã hợp đồng</label>
                                                </td>
                                                <td class="col-md-6 ">
                                                    <b>${contract.contractCode}</b>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="col-md-4">
                                                    <label class="text-center">Ngày bắt đầu</label>
                                                </td>
                                                <td class="col-md-6 ">

                                                    <c:choose>
                                                        <c:when test ="${contract.status.equalsIgnoreCase('Pending') && contract.startDate == contract.expiredDate}">
                                                            (Chưa thanh toán)
                                                        </c:when>
                                                        <c:otherwise>
                                                            ngày <fmt:formatDate value="${contract.startDate}" pattern="dd"/>
                                                            tháng <fmt:formatDate value="${contract.startDate}" pattern="MM"/>
                                                            năm <fmt:formatDate value="${contract.startDate}" pattern="yyyy"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="col-md-4">
                                                    <label>Ngày hết hạn</label>
                                                </td>
                                                <td class="col-md-6 ">

                                                    <c:choose>
                                                        <c:when test ="${contract.status.equalsIgnoreCase('Pending') && contract.startDate == contract.expiredDate}">
                                                            (Chưa thanh toán)
                                                        </c:when>
                                                        <c:otherwise>
                                                            ngày <fmt:formatDate value="${contract.expiredDate}" pattern="dd"/>
                                                            tháng <fmt:formatDate value="${contract.expiredDate}" pattern="MM"/>
                                                            năm <fmt:formatDate value="${contract.expiredDate}" pattern="yyyy"/>
                                                        </c:otherwise>

                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <c:if test="${listRenew.size() > 0}">
                                                <tr>
                                                    <td colspan="2">
                                                <c:set var="lastDate" value="${listRenew.get(listRenew.size()-1).startDate}"/>
                                                (Đã gia hạn ${listRenew.size()} lần, lần cuối lúc <fmt:formatDate value="${lastDate}" pattern="dd/MM/yyyy"/>)
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </table>
                                    </td>
                                    <td class="col-md-5">
                                        <table class="table table-bordered">

                                            <tr>
                                                <td class="text-center">
                                                    <label>Quyền lợi bảo hiểm</label>
                                                </td>
                                                <td class="">
                                                    ${contract.getMicContractTypeByContractTypeId().getDescription()}
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="text-center">
                                                    <label> Tình trạng hợp đồng</label>
                                                </td>
                                                <c:if test="${!contract.status.equalsIgnoreCase('Cancelled')}">
                                                    <td class="text-center " colspan="2">
                                                        <input type="hidden" name="txtNewStartDate" id="expiredDate"
                                                               value="${contract.expiredDate}"/>
                                                        <label>
                                                            <input value="${messageContract}" type="text"
                                                                   disabled="disabled"
                                                                   style="border:none ; background-color: white; width: 100%"/></label>
                                                    </td>
                                                </c:if>
                                                <c:if test="${contract.status.equalsIgnoreCase('Cancelled')}">
                                                    <td class="text-center" colspan="2">
                                                        <label>Hợp đồng đã bị hủy</label>
                                                    </td>
                                                </c:if>
                                            </tr>
                                            <tr>
                                                <td class="text-center">
                                                    <label>Trạng thái</label>
                                                </td>
                                                <c:if test="${contract.status.equalsIgnoreCase('Ready')}">
                                                    <td class="text-center">
                                                <span class="label label-success"
                                                      style="font-size: 16px">Sẵn sàng</span>
                                                    </td>
                                                </c:if>
                                                <c:if test="${contract.status.equalsIgnoreCase('Cancelled')}">
                                                    <td class="text-center"><span class="label label-dark"
                                                                                  style="font-size: 16px">Đã huỷ</span>
                                                    </td>
                                                </c:if>
                                                <c:if test="${contract.status.equalsIgnoreCase('No card')}">
                                                    <td class="text-center"><span class="label label-primary"
                                                                                  style="font-size: 16px">Chưa có thẻ</span>
                                                    </td>
                                                </c:if>
                                                <c:if test="${contract.status.equalsIgnoreCase('Expired')}">
                                                    <td class="text-center"><span class="label label-danger"
                                                                                  style="font-size: 16px"> Hết hạn</span>
                                                    </td>
                                                </c:if>
                                                <c:if test="${contract.status.equalsIgnoreCase('Pending')}">
                                                    <td class="text-center"><span
                                                            class="label label-default"
                                                            style="font-size: 16px">Chưa kích hoạt</span></td>
                                                </c:if>
                                                <c:if test="${contract.status.equalsIgnoreCase('Request cancel')}">
                                                    <td class="text-center"><span class="label label-warning"
                                                                                  style="font-size: 16px">Yêu cầu hủy</span>
                                                    </td>
                                                </c:if>
                                            </tr>

                                        </table>
                                    </td>
                                </tr>
                                <tr class="active">
                                    <td colspan="2" class="text-center" style="font-size: 15px"><label
                                            class="text-center">Thông
                                        tin xe</label></td>
                                </tr>
                                <tr>
                                    <td>
                                        <table class="table table-bordered">
                                            <tr>
                                                <td class="col-md-5">
                                                    <label>Biển số đăng ký</label>
                                                </td>
                                                <td class="col-md-5 ">
                                                    ${contract.plate}
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="col-md-5">
                                                    <label>Số khung</label>
                                                </td>
                                                <td class="col-md-5 ">
                                                    ${contract.chassis}
                                                </td>

                                            </tr>
                                        </table>
                                    </td>
                                    <td>
                                        <table class="table table-bordered">
                                            <tr>
                                                <td class="col-md-5">
                                                    <label class="text-center">Nhãn hiệu</label>
                                                </td>
                                                <td class="col-md-5 ">
                                                    ${contract.brand}
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="col-md-5">
                                                    <label>Số máy</label>
                                                </td>
                                                <td class="col-md-5 ">
                                                    ${contract.engine}
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <table class="table table-bordered">
                                            <tr>
                                                <td class="col-md-5">
                                                    <label>Dung tích</label>
                                                </td>
                                                <td class="col-md-5 ">
                                                    ${contract.capacity}
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="col-md-5">
                                                    <label>Số loại</label>
                                                </td>
                                                <td class="col-md-5 ">
                                                    <c:choose>
                                                        <c:when test="${empty contract.modelCode}">
                                                            <label class="empty-value">Không có</label>
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${contract.modelCode}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>

                                        </table>
                                    </td>
                                    <td>
                                        <table class="table table-bordered">
                                            <tr>
                                                <td class="col-md-5">
                                                    <label class="text-center">Màu sơn</label>
                                                </td>
                                                <td class="col-md-5 ">
                                                    <c:choose>
                                                        <c:when test="${empty contract.color}">
                                                            <label class="empty-value">Không có</label>
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${contract.color}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="col-md-5">
                                                    <label>Loại xe</label>
                                                </td>
                                                <td class="col-md-5 ">
                                                    <c:choose>
                                                        <c:when test="${empty contract.vehicleType}">
                                                            <label class="empty-value">Không có</label>
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${contract.vehicleType}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>

                                <tr>
                                    <td>
                                        <table class="table table-bordered">
                                            <tr>
                                                <td class="col-md-5">
                                                    <label>Năm sản xuất</label>
                                                </td>
                                                <td class="col-md-5 ">
                                                    <c:choose>
                                                        <c:when test="${empty contract.yearOfManufacture}">
                                                            <label class="empty-value">Không có</label>
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${contract.yearOfManufacture}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="col-md-5">
                                                    <label>Tự trọng</label>
                                                </td>
                                                <td class="col-md-5 ">
                                                    <c:choose>
                                                        <c:when test="${empty contract.weight}">
                                                            <label class="empty-value">Không có</label>
                                                        </c:when>
                                                        <c:otherwise>${contract.weight} kg</c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>

                                    <td>
                                        <table class="table table-bordered">
                                            <tr>
                                                <td class="col-md-5">
                                                    <label>Số người được chở</label>
                                                </td>
                                                <td class="col-md-5 ">
                                                    <c:choose>
                                                        <c:when test="${empty contract.seatCapacity}">
                                                            <label class="empty-value">Không có</label>
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${contract.seatCapacity}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr class="active">
                                    <td colspan="2" class="text-center" style="font-size: 15px">
                                        <label class="text-center">
                                            Thông tin thẻ đang hoạt động
                                        </label>

                                    </td>

                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <table class="table table-bordered">
                                            <thead>
                                            <tr class="success">

                                                <th class="text-center">
                                                    Mã thẻ
                                                </th>
                                                <th class="text-center">
                                                    Ngày kích hoạt
                                                </th>
                                                <th class=" text-center">
                                                    Trạng thái
                                                </th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:choose>
                                                <c:when test="${empty card.cardId}">
                                                    <tr>
                                                        <td colspan="5"
                                                            style="vertical-align: middle; text-align: center;">
                                                            Không có thẻ nào
                                                        </td>
                                                    </tr>
                                                </c:when>
                                                <c:otherwise>
                                                    <tr>
                                                        <td>
                                                            <a href="${pageContext.request.contextPath}/customer/card?action=detail&cardId=${card.cardId}"
                                                               target="_newtab">
                                                                    ${card.cardId}
                                                            </a>
                                                        </td>
                                                        <td>
                                                            <fmt:formatDate value="${card.activatedDate}"
                                                                            pattern="dd/MM/yyyy"/>
                                                            lúc
                                                            <fmt:formatDate value="${card.activatedDate}" type="time"/>
                                                        </td>
                                                        <td class="text-center">
                                                            <c:choose>
                                                                <c:when test="${empty card.deactivatedDate}">
                                                                    <span class="label label-success">Hoạt động</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="label label-danger">Ngưng hoạt động</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                </c:otherwise>

                                            </c:choose>

                                            </tbody>
                                        </table>
                                    </td>
                                </tr>

                            </table>
                        </div>
                    </div>
                    <%@include file="contract-detail/compensation.jsp" %>
                    <%@include file="contract-detail/punishment.jsp" %>
                    <%@include file="contract-detail/accident.jsp" %>

                    <%----------------------------------------------------------------------------------------------------%>

                </div>
            </div>
        </div>


    </div>

</div>
<!-- /#page-wrapper -->
</div>
<!-- /#wrapper -->
<script src="//www.paypalobjects.com/api/checkout.js" async></script>
<script>
    $(document).ready(function () {
        var showTextFee = (parseFloat($('#payAmount1').val())).formatMoney(0, '.', '.');
        $('#textFree').val(showTextFee + ' VNĐ');

        var dateStartDefault = $('#startDatePay').val();
        var temp = $('#configTime').val();

//        if(dateStartDefault != '' && dateStartDefault!= null && dateStartDefault != undefined){
//            $('#defaultDateExpired').val(checkDefaultExpiredDetail(dateStartDefault, temp));
//        }
    });
    //    function countFeeContract(contractDefaultTerm, feeContract) {
    //        contractDefaultTerm = parseFloat(contractDefaultTerm);
    //        feeContract = parseFloat(feeContract);
    //        //tinh theo so nam config
    //        feeContract = feeContract * (contractDefaultTerm / 12);
    //        // lam tron 1000
    //        feeContract = feeContract - (feeContract % 1000);
    //        return feeContract;
    //    }
    function getQueryVariable(variable) {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i = 0; i < vars.length; i++) {
            var pair = vars[i].split("=");
            if (pair[0] == variable) {
                return pair[1];
            }
        }
        return false;
    }
    // Auto active tab after pagination
    $(function () {
        var active = getQueryVariable('active');
        if (active) {
            $(".nav-tabs a[href='#" + active + "s']").tab('show')
        }
    });
    function checkDefaultExpiredDetail(dateStart, temp) {
        //dateStart : start date of contract
        //temp : config rule default time for contract

        var dt = dateStart.split(/\-|\s/);
        var year = parseInt(dt[0]);
        var month = parseInt(parseInt(dt[1]) + parseInt(temp));
        var day = dt[2].toString();
        var tempYear = parseInt(month / 12).toFixed(0);
        year = parseInt(parseInt(tempYear) + parseInt(dt[0]));
        if (month > 12) {
            month = parseInt(parseInt(month + temp) % 12);
        }

        //nam nhuan
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            if (month == 2 && day == 29) {
                day = 29;
            }
        }
        else {
            if (month == 2 && day == 29) {
                day = 28;
            }
        }

        month = month.toString().length > 1 ? month : '0' + month;

        day = day.toString().length > 1 ? day : '0' + day;

        return day + '/' + month + '/' + year;
    }
</script>
<%@ include file="_shared/footer.jsp" %>
<jsp:include page="cancel-new-card-request-modal.jsp" flush="true"/>
