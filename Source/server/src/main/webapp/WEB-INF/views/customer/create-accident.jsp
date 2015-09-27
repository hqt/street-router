<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="wrapper">

    <%@ include file="_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Thông báo tai nạn cho hợp đồng ${param.code}</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="text-center alert alert-danger alert-dismissible hide"
                 id="notifyAccident"
                 role="alert">
                Nội dung thông báo không được để trống
            </div>
            <div class="text-center alert alert-danger alert-dismissible hide"
                 id="notifyAccident1"
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
                <form action="${pageContext.request.contextPath}/customer/accident" method="post"
                      class="form-horizontal">
                    <fieldset>
                        <!-- Contract code -->
                        <input type="hidden" name="accident:contractCode" value="${param.code}">

                        <!-- Created date -->
                        <div class="form-group hide">
                            <label class="col-sm-4 control-label" for="createdDate">Ngày gởi thông báo *</label>

                            <div class="col-sm-3">
                                <input id="createdDate" name="accident:createdDate"
                                       pattern="yyyy-MM-dd"
                                       class="form-control input-md" type="date" required/>
                            </div>
                        </div>

                        <!-- Title -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="title">Nội dung thông báo *</label>

                            <div class="col-sm-7">
                                <textarea id="title" name="accident:title" class="form-control"
                                          placeholder="Ví dụ: Gãy tay trái, chấn thương mô mềm"
                                          rows="4" maxlength="250"
                                          title="Vui lòng nhập nội dung thông báo"></textarea>
                            </div>
                        </div>

                        <!-- Attachment -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="attachment">Văn bản đính kèm *</label>

                            <div class="col-sm-6">

                                <img id="imgAttachment" height="100px" src=""/>
                                <button class="btn btn-primary" type="button" id="pickFile">Chọn tập tin</button>
                                <input id="attachment" name="accident:attachment" class="form-control"
                                       type="hidden" maxlength="255">

                                <script type="text/javascript" src="//api.filepicker.io/v2/filepicker.js"></script>

                                <input type="filepicker" data-fp-apikey="AEbPPQfPfRHqODjEl5AZ2z" class="hide pick"
                                       required id="attImage"
                                       title="Vui lòng tải lên văn bản đính kèm"
                                       onchange="$('#imgAttachment').attr('src', event.fpfile.url);$('#attachment').val(event.fpfile.url);">
                                <br/>


                            </div>
                        </div>
                    </fieldset>
                    <br/>
                    <!-- Create new customer button -->
                    <div class="text-center">
                        <input type="hidden" name="action" value="create"/>
                        <button id="create" type="submit" class="btn btn-success">
                            <i class="fa fa-arrow-right"></i> Gởi thông báo tai nạn
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
        //alert($('#imgAttachment').val());

        $('#pickFile').click(function () {
            $('.pick').trigger("click");
        });
        if ($('#createdDate').val() == "") {
            $('#createdDate').val(getCurrentDate());
        }
        document.getElementById("createdDate").max = getCurrentDate();
        $("#title").keydown(function (event) {
            if ($.trim($('#title').val()) == 0) {
                $('#notifyAccident').addClass('hide');
            }
        });
        $('#create').click(function () {
            if ($.trim($('#title').val()) == 0) {
                $('#notifyAccident').removeClass('hide');
                return false;
            }
            else {
                $('#notifyAccident').addClass('hide');
            }
            if ($('#attachment').val() == '') {
                $('#notifyAccident1').removeClass('hide');
                return false;
            }
            else {
                $('#notifyAccident1').addClass('hide');
            }
        });
    });
</script>

<%@ include file="_shared/footer.jsp" %>