<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="wrapper">

    <%@ include file="_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Thêm vi phạm cho hợp đồng ${param.code}</h1>
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
                <form action="${pageContext.request.contextPath}/staff/punishment" method="post" class="form-horizontal">
                    <fieldset>
                        <!-- Contract code -->
                        <input type="hidden" name="punishment:contractCode"
                               value="${not empty param.code ? param.code : submitted.contractCode}">

                        <!-- Created date -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="createdDate">Ngày thêm vi phạm *</label>

                            <div class="col-sm-3">
                                <input id="createdDate" name="punishment:createdDate"
                                       value="<fmt:formatDate value="${submitted.createdDate}" pattern="yyyy-MM-dd" />"
                                       class="form-control input-md" type="date" required>
                            </div>
                        </div>

                        <!-- Title -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="title">Nội dung vi phạm *</label>

                            <div class="col-sm-7">
                                <textarea id="title" name="punishment:title" class="form-control input-md"
                                          placeholder="Ví dụ: Vượt đèn đỏ, lấn tuyến, vượt quá tốc độ 5%"
                                          rows="4" maxlength="250" required
                                          title="Vui lòng nhập nội dung thông báo">${submitted.title}</textarea>
                            </div>
                        </div>

                        <!-- Attachment -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="attachment">Văn bản đính kèm *</label>

                            <div class="col-sm-6">
                                <input id="attachment" name="punishment:attachment" type="hidden" required maxlength="255">
                                <img id="imgAttachment" height="100px" src=""/>
                                <button type="button" id="pickAttachment" class="btn btn-primary">Chọn tập tin</button>
                                <script type="text/javascript" src="//api.filepicker.io/v2/filepicker.js"></script>
                                <input type="filepicker" data-fp-apikey="AEbPPQfPfRHqODjEl5AZ2z" class="hide filePicker"
                                       onchange="$('#imgAttachment').attr('src', event.fpfile.url);$('#attachment').val(event.fpfile.url);">
                            </div>
                        </div>
                    </fieldset>
                    <br/>
                    <!-- Create new customer button -->
                    <div class="text-center">
                        <input type="hidden" name="action" value="create"/>
                        <button type="submit" class="btn btn-success">
                            <i class="fa fa-arrow-right"></i> Thêm vi phạm luật ATGT
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
        // Use Vietnamese button to open File Picker dialog
        $('#pickAttachment').click(function () {
            $('.filePicker').trigger("click");
        });
        // Set created date value and restriction
        if ($('#createdDate').val() == "") {
            $('#createdDate').val(getCurrentDate());
        }
        document.getElementById("createdDate").max = getCurrentDate();
    });
</script>

<%@ include file="_shared/footer.jsp" %>