<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="wrapper">

    <%@ include file="_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Thêm vi phạm cho hợp đồng ${contractCode}</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="text-center alert alert-danger alert-dismissible hide"
                 id="notifyPunishment"
                 role="alert">
                Nội dung vi phạm không được để trống
            </div>
            <div class="text-center alert alert-danger alert-dismissible hide"
                 id="notifyPunishment1"
                 role="alert">
                Văn bản đính kèm không được để trống
            </div>
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
                <form action="${pageContext.request.contextPath}/customer/punishment" method="post"
                      class="form-horizontal" >
                    <fieldset>

                        <!-- Title -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Nội dung vi phạm *</label>

                            <div class="col-sm-7">
                                 <textarea rows="5" cols="80" id="contentPunishment"
                                           class="form-control"
                                           placeholder="Ví dụ: Chạy quá tốc độ cho phép"
                                           autofocus="autofocus"
                                           name="punishment:title">
                                 </textarea>
                            </div>
                        </div>

                        <!-- Attachment -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="attachment">Văn bản đính kèm *</label>

                            <div class="col-sm-6">
                                <img id="imgAttachment" height="100px" src=""/>
                                <button class="btn btn-primary" type="button" id="pickFilePunishment">Chọn tập tin</button>
                                <input id="attachment" name="punishment:attachment"
                                       class="form-control "
                                       type="hidden">


                                <script type="text/javascript"
                                        src="//api.filepicker.io/v2/filepicker.js"></script>

                                <input type="filepicker" data-fp-apikey="AEbPPQfPfRHqODjEl5AZ2z"
                                       class="chooseFile hide"
                                       title="Đính kèm ảnh vi phạm không được trống"
                                       onchange="$('#imgAttachment').attr('src', event.fpfile.url);$('#attachment').val(event.fpfile.url);">
                            </div>
                        </div>
                    </fieldset>
                    <br/>
                    <!-- Create new customer button -->
                    <div class="text-center">
                        <input type="hidden" name="action" value="Create"/>
                        <input type="hidden" name="punishment:contractCode"
                               value="${contractCode}"/>
                        <input type="hidden" name="contractCode" value="${contractCode}">
                        <button id="confirmPunishment" type="submit" class="btn btn-success">
                            <i class="fa fa-arrow-right"></i> Gởi vi phạm
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

<script type="text/javascript">
    $(document).ready(function () {
        $('#pickFilePunishment').click(function () {
            $('.chooseFile').trigger("click");
        });
        $("#contentPunishment").keydown(function (event) {
            if ($.trim($('#notifyPunishment').val()) == 0) {
                $('#notifyPunishment').addClass('hide');
            }
        });
        $('#confirmPunishment').click(function () {

            if ($.trim($('#contentPunishment').val()) == 0) {
                $('#notifyPunishment').removeClass('hide');
                return false;
            }
            if ($('#attachment').val() == '') {
                $('#notifyPunishment1').removeClass('hide');
                return false;
            }
        });
    });
</script>

<%@ include file="_shared/footer.jsp" %>