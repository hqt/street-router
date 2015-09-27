<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="wrapper">

    <c:set var="accident" value="${requestScope.ACCIDENT}"/>

    <%@ include file="_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Chỉnh sửa thông báo tai nạn ${accident.id}</h1>
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
                <form action="${pageContext.request.contextPath}/staff/accident" method="post" class="form-horizontal">
                    <fieldset>
                        <!-- Contract code -->
                        <input type="hidden" name="edit:contractCode" value="${accident.contractCode}">

                        <!-- Created date -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="createdDate">Ngày gởi thông báo *</label>

                            <div class="col-sm-3">
                                <input id="createdDate" name="edit:createdDate"
                                       value="<fmt:formatDate value="${not empty submitted.createdDate ? submitted.createdDate : accident.createdDate}" pattern="yyyy-MM-dd" />"
                                       class="form-control input-md" type="date" required>
                            </div>
                        </div>

                        <!-- Title -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="title">Nội dung thông báo *</label>

                            <div class="col-sm-7">
                                <textarea id="title" name="edit:title" class="form-control input-md"
                                          placeholder="Ví dụ: Gãy tay trái, chấn thương mô mềm"
                                          rows="4" maxlength="250"
                                          title="Vui lòng nhập nội dung thông báo">${not empty submitted.title ? submitted.title : accident.title}</textarea>
                            </div>
                        </div>

                        <!-- Attachment -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="attachment">Văn bản đính kèm *</label>

                            <div class="col-sm-6">
                                <input id="attachment" name="edit:attachment" type="hidden" required maxlength="255"
                                       value="${accident.attachment}">
                                <img id="imgAttachment" height="100px" src="${accident.attachment}"/>
                                <button type="button" id="pickAttachment" class="btn btn-primary">Chọn tập tin</button>
                                <script type="text/javascript" src="//api.filepicker.io/v2/filepicker.js"></script>
                                <input type="filepicker" data-fp-apikey="AEbPPQfPfRHqODjEl5AZ2z" class="hide filePicker"
                                       onchange="$('#imgAttachment').attr('src', event.fpfile.url);$('#attachment').val(event.fpfile.url);">
                            </div>
                        </div>
                    </fieldset>
                    <br/>
                    <!-- Create new accident button -->
                    <div class="text-center">
                        <input type="hidden" name="edit:id" value="${accident.id}"/>
                        <input type="hidden" name="action" value="edit"/>
                        <button type="submit" id="btnEdit" class="btn btn-primary">
                            <i class="fa fa-pencil"></i> Cập nhật thông báo tai nạn
                        </button>
                        <br/><br/>
                        <a href="${pageContext.request.contextPath}/staff/contract?action=detail&code=${accident.contractCode}"
                           type="button" class="btn btn-default">
                            <i class="fa fa-arrow-left"></i> <strong>Xem chi tiết hợp đồng hiện tại</strong>
                        </a>
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