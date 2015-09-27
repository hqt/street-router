<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="wrapper">

    <%@ include file="_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Thêm khách hàng mới</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">

                <c:if test="${not empty validateErrors}">
                    <div class="text-danger">
                        <ul>
                            <c:forEach var="error" items="${validateErrors}">
                                <li>${error}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>

                <p class="text-right"><b>Các ô có dấu * là bắt buộc</b></p>
                <br/>

                <!-- Form to create new customer -->
                <form action="${pageContext.request.contextPath}/staff/customer" method="post" class="form-horizontal">
                    <fieldset>
                        <!-- Customer full name input-->
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="name">Tên khách hàng *</label>
                            <div class="col-sm-4">
                                <input id="name" name="customer:name" class="form-control input-md"
                                       type="text" required minlength="3" maxlength="80"
                                       pattern="^([^0-9`~!@#$%^&*,.<>;':/|{}()=_+-]+)$"
                                       value="${submitted.name}" title="Vui lòng nhập họ tên"
                                       placeholder="Ví dụ: Trần Văn A">
                            </div>
                        </div>

                        <!-- Address input-->
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="txtAddress">Địa chỉ *</label>
                            <div class="col-sm-7">
                                <input id="txtAddress" name="customer:address" class="form-control input-md"
                                       type="text" required minlength="3" maxlength="250"
                                       value="${submitted.address}" onfocus="geolocate()"
                                       title="Vui lòng nhập địa chỉ"
                                       placeholder="Ví dụ: 123A, Điện Biên Phủ, Quận 1, TP.HCM">
                            </div>
                        </div>

                        <!-- Email address input-->
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="email">Email *</label>
                            <div class="col-sm-5">
                                <input id="email" name="customer:email" class="form-control input-md"
                                       type="text" required minlength="3" maxlength="250"
                                       pattern="^([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,3})$"
                                       value="${submitted.email}" title="Vui lòng nhập một email đúng"
                                       placeholder="Ví dụ: aido@tenmien.com">
                            </div>
                        </div>

                        <!-- Phone number input-->
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="phone">Số điện thoại *</label>
                            <div class="col-sm-3">
                                <input id="phone" name="customer:phone" class="form-control input-md"
                                       type="tel" required minlength="8" maxlength="15"
                                       pattern="[0-9]+" title="Vui lòng chỉ nhập số"
                                       placeholder="Ví dụ: 0933270393"
                                       value="${submitted.phone}">
                            </div>
                        </div>

                        <!-- Personal ID input-->
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="personalID">Số CMND / Hộ chiếu</label>
                            <div class="col-sm-3">
                                <input id="personalID" name="customer:personalID"class="form-control input-md"
                                       type="text" minlength="8" maxlength="15"
                                       pattern="[0-9]+" title="Vui lòng chỉ nhập số"
                                       placeholder="Ví dụ: 272185738"
                                       value="${submitted.personalID}">
                            </div>
                        </div>
                    </fieldset>
                    <br/>
                    <!-- Create new customer button -->
                    <div class="text-center">
                        <input type="hidden" name="action" value="create"/>
                        <button type="submit" class="btn btn-success">
                            <i class="fa fa-arrow-right"></i>
                            Thêm khách hàng
                        </button>
                    </div>
                </form>
            </div>
            <br/> <br/>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
    </div>
    <!-- /.page-wrapper -->
</div>
<!-- /#wrapper -->

<script src="${pageContext.request.contextPath}/js/geolocation.js" type="text/javascript"></script>
<!-- Google API Autocomplete for address-->
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>

<script type="text/javascript">
    initialize();
</script>

<%@ include file="_shared/footer.jsp" %>