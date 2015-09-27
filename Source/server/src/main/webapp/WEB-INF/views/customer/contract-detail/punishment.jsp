<%----------------------------------------------------------------------------------------------------%>
<%------------------------------------------PUNISHMENT------------------------------------------------%>
<div role="tabpane1" class="tab-pane" id="punishments">

    <form action="${pageContext.request.contextPath}/customer/punishment" method="post">
        <div class="modal fade punishmentDialog" tabindex="-1" role="dialog"
             aria-labelledby="myLargeModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-body">
                        <div class="text-center alert alert-danger alert-dismissible hide"
                             id="notifyPunishment"
                             role="alert">
                            Nội dung vi phạm không được để trống
                        </div>
                        <div class="text-center alert alert-danger alert-dismissible hide"
                             id="notifyPunishment1"
                             role="alert">
                            Văn bản vi phạm không được để trống
                        </div>
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Nội dung vi phạm *</label>

                                <div class="col-sm-5">
                                                <textarea rows="8" cols="80" id="contentPunishment"
                                                          autofocus="autofocus">

                                                </textarea>
                                    <input type="hidden" id="titlePunishment"
                                           name="punishment:title">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Đính kèm *</label>

                                <div class="col-sm-5" style="padding-top: 5px">
                                    <img id="imgAttachment" height="100px" src=""/>
                                    <input id="attachment" name="punishment:attachment"
                                           class="form-control input-md"
                                           type="hidden" maxlength="255">


                                    <script type="text/javascript"
                                            src="//api.filepicker.io/v2/filepicker.js"></script>

                                    <input type="filepicker" data-fp-apikey="AEbPPQfPfRHqODjEl5AZ2z"
                                           required id="attImage"
                                           title="Đính kèm ảnh vi phạm không được trống"
                                           onchange="$('#imgAttachment').attr('src', event.fpfile.url);$('#attachment').val(event.fpfile.url);">
                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <%--Post to server (PersonalController)--%>
                        <input type="hidden" name="action" value="Create"/>
                        <input type="hidden" name="punishment:contractCode"
                               value="${contract.contractCode}"/>
                        <%---------------------------------------%>
                        <input id="confirmPunishment" type="submit" class="btn btn-primary" name="Xác Nhận"
                               value="Xác Nhận"/>
                        <input type="button" class="btn btn-danger" id="cancel" data-dismiss="modal"
                               value="Hủy Bỏ"/>
                    </div>
                </div>

            </div>
        </div>
    </form>
    <div class="row">
        <div class="col-lg-12">
            <div class="pull-right">
                <input type="hidden" id="cancelDatePunishment" value="${contract.cancelDate}">
                <input type="hidden" id="ruleCancelPunishment" value="${configUtils.updateContractDueDate}">
                <c:if test="${!contract.status.equalsIgnoreCase('Cancelled')&&
                              !contract.status.equalsIgnoreCase('Pending')&&
                              !contract.status.equalsIgnoreCase('Expired')
                              }">
                    <a href="${pageContext.request.contextPath}/customer/punishment?action=create&contractCode=${contract.contractCode}"
                       class="btn btn-success">
                        <i class="fa fa-plus"></i>Thêm vi phạm
                    </a>
                </c:if>
                <c:if test="${contract.status.equalsIgnoreCase('Cancelled')}">
                    <a href="${pageContext.request.contextPath}/customer/punishment?action=create&contractCode=${contract.contractCode}"
                       class="btn btn-success addNewPunishment hide">
                        <i class="fa fa-plus"></i>Thêm vi phạm
                    </a>
                </c:if>
                <c:if test="${contract.status.equalsIgnoreCase('Expired')}">
                    <a href="${pageContext.request.contextPath}/customer/punishment?action=create&contractCode=${contract.contractCode}"
                       class="btn btn-success addPunishmentCaseExpired hide">
                        <i class="fa fa-plus"></i>Thêm vi phạm
                    </a>
                </c:if>

            </div>
        </div>
    </div>
    <br/>

    <!--<input type="checkbox" class="check-all"/>-->
    <b>Có ${punishmentPaginator.itemSize} trường hợp vi phạm</b>
    <br/>
    <br/>

    <div class="table table-responsive">
        <table class="table table-bordered">
            <thead>
            <tr class="success">
                <th class="text-center " style="width: 10% !important;">Mã vi phạm
                </th>
                <th class=" text-center">
                    Nội dung vi phạm
                </th>
                <th class="text-center">
                    Ngày gởi đi
                </th>
                <th class="text-center col-md-1">
                    Biên bản
                </th>
            </tr>
            </thead>
            <tbody>
            <c:set var="punishments"
                   value="${punishmentPaginator.getItemsOnCurrentPage(param.page, param.active)}"/>
            <c:choose>
                <c:when test="${punishments.size() == 0}">
                    <tr>
                        <td colspan="5" style="vertical-align: middle; text-align: center;">
                            Không có vi phạm nào
                        </td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${punishments}"
                               var="punishment"
                               varStatus="counter">
                        <tr>
                            <td class="text-center">
                                    ${punishment.id}
                            </td>
                            <td>
                                    ${punishment.title}
                            </td>
                            <td class="text-center">
                                <fmt:formatDate value='${punishment.createdDate}'
                                                pattern='dd/MM/yyyy'/>
                            </td>
                            <td class="text-center">
                                <a href="${punishment.attachment}"
                                   target="_newtab"><i class="fa fa-file-text-o"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>

            </tbody>
        </table>
    </div>
    <nav class="text-right">
        <ul class="pagination">
            <c:set var="currentPage" value="${param.page}"/>

            <c:if test="${param.active != punishmentPaginator.tag}">
                <c:set var="currentPage" value="1"/>
            </c:if>

            <c:if test="${currentPage != 1 && not empty currentPage}">
                <li>
                    <a href="?action=${param.action}&code=${contract.contractCode}&keyword=${param.keyword}&page=1&active=${punishmentPaginator.tag}"
                       aria-label="Previous">
                        <span aria-hidden="true">Đầu</span>
                    </a>
                </li>
            </c:if>
            <c:forEach begin="1" end="${punishmentPaginator.pageSize}" var="pageNumber">
                <li ${currentPage == pageNumber ||(pageNumber == 1 && empty currentPage) ? "class='active'": ""} >
                    <a href="?action=${param.action}&code=${contract.contractCode}&keyword=${param.keyword}&page=${pageNumber}&active=${punishmentPaginator.tag}">${pageNumber}</a>
                </li>
            </c:forEach>
            <c:if test="${currentPage != punishmentPaginator.pageSize && punishmentPaginator.pageSize != 1}">
                <li>
                    <a href="?action=${param.action}&code=${contract.contractCode}&keyword=${param.keyword}&page=${punishmentPaginator.pageSize}&active=${punishmentPaginator.tag}"
                       aria-label="Next">
                        <span aria-hidden="true">Cuối</span>
                    </a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        var cancelDate = $('#cancelDatePunishment').val();
        var temp = $('#ruleCancelPunishment').val();
        var count = Math.abs(DayDiff(cancelDate));
        var countDateRemainPunishment = $('#countDateRemain').val();
        if (Math.abs(countDateRemainPunishment) <= temp) {
            $('.addNewPunishment').removeClass('hide');
        }
        if (Math.abs(countDateRemainPunishment) <= temp) {
            $('.addPunishmentCaseExpired').removeClass('hide');
        }

    });
    function DayDiff(date) {
        var oneDay = 24 * 60 * 60 * 1000;
        var timeNow = new Date();
        var expiredDate = new Date(date);
        return Math.round((expiredDate.getTime() - timeNow.getTime()) / (oneDay));
    }
</script>