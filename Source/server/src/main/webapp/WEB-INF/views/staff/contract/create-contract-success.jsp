<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../_shared/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="wrapper">

    <%@ include file="../_shared/navigation.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Thêm hợp đồng mới</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="well text-primary text-center bs-example">
                    <i class="fa fa-check"></i> Hợp đồng đã được khởi tạo thành công!
                </div>

                <c:set var="contract" value="${requestScope.CONTRACT}" scope="request"/>

                <form class="form-horizontal">
                    <jsp:include page="contract-detail-general.jsp" flush="true"/>
                </form>
                <br/>

                <div class="text-center">
                    <a href="${pageContext.request.contextPath}/staff/contract?action=detail&code=${contract.contractCode}"
                       type="button" class="btn btn-default">
                        <i class="fa fa-arrow-right"></i> <strong>Xem chi tiết hợp đồng này</strong>
                    </a>
                    <br/> <br/>
                    <a href="${pageContext.request.contextPath}/staff/contract" type="button" class="btn btn-default">
                        <i class="fa fa-arrow-left"></i> <strong>Danh sách hợp đồng</strong>
                    </a>
                </div>
                <br/>

                <div class="panel panel-success">
                    <div class="panel-heading">
                        Hướng dẫn cách phát hành thẻ cho khách hàng
                    </div>
                    <div class="panel-body">
                        Để cấp thẻ cho khách hàng, nhân viên cần sử dụng <b>Ứng dụng in thẻ</b> trên điện thoại để in
                        thông tin khách hàng lên thẻ.
                        <ol>
                            <li>Lựa chọn hoặc sử dụng chức năng tìm kiếm hợp đồng cần in thẻ trên ứng dụng.</li>
                            <li>Kiểm tra chính xác thông tin hợp đồng.</li>
                            <li>In thông tin ra thẻ và chuyển phát cho hợp đồng.</li>
                        </ol>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /#wrapper -->

<%@ include file="../_shared/footer.jsp" %>