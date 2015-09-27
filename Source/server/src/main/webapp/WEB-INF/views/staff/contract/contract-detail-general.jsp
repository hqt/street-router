<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fieldset>
    <legend>Thông tin về dịch vụ bảo hiểm</legend>
    <!-- Contract code & Contract status -->
    <div class="form-group">
        <label class="col-sm-4 control-label">Mã hợp đồng</label>

        <div class="col-sm-2">
            <div class="text-value text-primary">
                <b>${contract.contractCode}</b>
            </div>
        </div>
        <label class="col-sm-2 control-label">Trạng thái</label>

        <div class="col-sm-3">
            <div class="text-value">
                <c:set var="status" value="${contract.status}"/>
                <c:choose>
                    <c:when test="${status.equalsIgnoreCase('Pending')}">
                        <span class="label label-gray">Chưa kích hoạt</span>
                    </c:when>
                    <c:when test="${status.equalsIgnoreCase('No card')}">
                        <span class="label label-primary">Chưa có thẻ</span>
                    </c:when>
                    <c:when test="${status.equalsIgnoreCase('Ready')}">
                        <span class="label label-success">Sẵn sàng</span>
                    </c:when>
                    <c:when test="${status.equalsIgnoreCase('Request cancel')}">
                        <span class="label label-warning">Yêu cầu hủy</span>
                    </c:when>
                    <c:when test="${status.equalsIgnoreCase('Expired')}">
                        <span class="label label-danger">Hết hạn</span>
                    </c:when>
                    <c:when test="${status.equalsIgnoreCase('Cancelled')}">
                        <span class="label label-dark">Đã huỷ</span>
                    </c:when>
                    <c:otherwise>
                        <span class="label label-default">Không trạng thái</span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <!-- Contract type -->
    <div class="form-group">
        <label class="col-sm-4 control-label">Loại hợp đồng</label>

        <div class="col-sm-8">
            <div class="text-value">
                ${contract.micContractTypeByContractTypeId.name}
            </div>
        </div>
    </div>

    <c:if test="${!(contract.status eq 'Pending') or !(empty listPayment)}">
        <!-- Start date -->
        <div class="form-group">
            <label class="col-sm-4 control-label">Bắt đầu có hiệu lực từ</label>

            <div class="col-sm-5">
                <div class="text-value">
                ngày <fmt:formatDate value="${contract.startDate}" pattern="dd"/>
                tháng <fmt:formatDate value="${contract.startDate}" pattern="MM"/>
                năm <fmt:formatDate value="${contract.startDate}" pattern="yyyy"/>
                </div>
            </div>
        </div>
        <!-- Expired date -->
        <div class="form-group">
            <label class="col-sm-4 control-label">Thời điểm hết hiệu lực</label>

            <div class="col-sm-5">
                <div class="text-value">
                    ngày <fmt:formatDate value="${contract.expiredDate}" pattern="dd"/>
                    tháng <fmt:formatDate value="${contract.expiredDate}" pattern="MM"/>
                    năm <fmt:formatDate value="${contract.expiredDate}" pattern="yyyy"/>

                    <c:if test="${listRenew.size() > 0}">
                        <c:set var="lastDate" value="${listRenew.get(listRenew.size()-1).startDate}"/>
                        <p>(Đã gia hạn ${listRenew.size()} lần, lần cuối lúc <fmt:formatDate value="${lastDate}" pattern="dd/MM/yyyy"/>)</p>
                    </c:if>
                </div>
            </div>
        </div>

    </c:if>
</fieldset>