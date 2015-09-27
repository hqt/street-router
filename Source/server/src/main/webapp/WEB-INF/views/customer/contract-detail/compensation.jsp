<%----------------------------------------------------------------------------------------------------%>
<%------------------------------------------COMPENSATION----------------------------------------------%>
<div role="tabpane1" class="tab-pane" id="compensations">
    <div class="row">
        <div class="col-lg-12">
            <div class="pull-right">
                <form action="${pageContext.request.contextPath}/customer/compensation" method="get">
                    <input type="hidden" name="action" value="Create">
                    <input type="hidden" name="contractCode" value="${contract.contractCode}">
                    <input type="hidden" id="cancelDateCompensation" value="${contract.cancelDate}">
                    <input type="hidden" id="ruleCancelCompensation" value="${configUtils.updateContractDueDate}">
                    <c:if test="${!contract.status.equalsIgnoreCase('Cancelled') &&
                                  !contract.status.equalsIgnoreCase('Pending')&&
                                  !contract.status.equalsIgnoreCase('Expired')
                                  }">
                        <button href="${pageContext.request.contextPath}/customer/compensation?action=create"
                                type="submit"
                                class="btn btn-success ">Yêu cầu bồi thường
                        </button>
                    </c:if>
                    <c:if test="${contract.status.equalsIgnoreCase('Cancelled')}">
                        <button href="${pageContext.request.contextPath}/customer/compensation?action=create"
                                type="submit"
                                class="btn btn-success addNewCompensation hide">Yêu cầu bồi thường
                        </button>
                    </c:if>
                    <c:if test="${contract.status.equalsIgnoreCase('Expired')}">
                        <button href="${pageContext.request.contextPath}/customer/compensation?action=create"
                                type="submit"
                                class="btn btn-success addCompensationCaseExpired hide">Yêu cầu bồi thường
                        </button>
                    </c:if>
                </form>
            </div>
        </div>
    </div>
    <br/>

    <!--<input type="checkbox" class="check-all"/>-->
    <b>Có ${compensationPaginator.itemSize} vụ bồi thường</b>
    <br/>
    <br/>

    <div class="table table-responsive">
        <table class="table table-bordered">
            <thead>
            <tr class="success">
                <th class="text-center ">#
                </th>
                <th class="text-center ">Mã yêu cầu
                </th>
                <th class="text-center ">Mã hợp đồng
                </th>
                <th class="text-center ">Ngày yêu cầu
                </th>
                <th class="text-center ">Ngày giải quyết
                </th>
            </tr>
            </thead>
            <tbody>
            <c:set var="compensations"
                   value="${compensationPaginator.getItemsOnCurrentPage(param.page, param.active)}"/>
            <c:choose>
                <c:when test="${compensations.size() == 0}">
                    <tr>
                        <td colspan="5" style="vertical-align: middle; text-align: center;">
                            Không có đền bù nào
                        </td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${compensations}"
                               var="compensation"
                               varStatus="counter">
                        <tr>
                            <td class="text-center">
                                    ${(compensationPaginator.getCurrentPage(param.page, param.active) - 1) * compensationPaginator.itemPerPage + counter.count}
                            </td>
                            <td class="text-center"><a
                                    href="${pageContext.request.contextPath}/customer/compensation?action=Detail&code=${compensation.compensationCode}">
                                    ${compensation.compensationCode}</a>
                            </td>
                            <td class="text-center"><a
                                    href="${pageContext.request.contextPath}/customer/contract?action=Detail&code=${compensation.contractCode}">
                                    ${compensation.contractCode}</a>
                            </td>

                            <td class="text-center">
                                <fmt:formatDate value='${compensation.createdDate}'
                                                pattern='dd/MM/yyyy'/>
                            </td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${empty compensation.resolveDate}">
                                        <span class="label label-warning">Đang giải quyết</span>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:formatDate value='${compensation.resolveDate}'
                                                        pattern='dd/MM/yyyy'/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>

            </tbody>
        </table>
        <nav class="text-right">
            <ul class="pagination">
                <c:set var="currentPage" value="${param.page}"/>

                <c:if test="${param.active != compensationPaginator.tag}">
                    <c:set var="currentPage" value="1"/>
                </c:if>

                <c:if test="${currentPage != 1 && not empty currentPage}">
                    <li>
                        <a href="?action=${param.action}&code=${contract.contractCode}&keyword=${param.keyword}&page=1&active=${compensationPaginator.tag}"
                           aria-label="Previous">
                            <span aria-hidden="true">Đầu</span>
                        </a>
                    </li>
                </c:if>
                <c:forEach begin="1" end="${compensationPaginator.pageSize}" var="pageNumber">
                    <li ${currentPage == pageNumber ||(pageNumber == 1 && empty currentPage) ? "class='active'": ""} >
                        <a href="?action=${param.action}&code=${contract.contractCode}&keyword=${param.keyword}&page=${pageNumber}&active=${compensationPaginator.tag}">${pageNumber}</a>
                    </li>
                </c:forEach>
                <c:if test="${currentPage != compensationPaginator.pageSize && compensationPaginator.pageSize != 1}">
                    <li>
                        <a href="?action=${param.action}&code=${contract.contractCode}&keyword=${param.keyword}&page=${compensationPaginator.pageSize}&active=${compensationPaginator.tag}"
                           aria-label="Next">
                            <span aria-hidden="true">Cuối</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        var cancelDate = $('#cancelDateCompensation').val();
        var temp = $('#ruleCancelCompensation').val();
        var countDateRemainCompensation = $('#countDateRemain').val();

        var count = Math.abs(DayDiff(cancelDate));
        if (Math.abs(countDateRemainCompensation) <= temp) {
            $('.addNewCompensation').removeClass('hide');
        }
        if (Math.abs(countDateRemainCompensation) <= temp) {
            $('.addCompensationCaseExpired').removeClass('hide');
        }

    });
    function DayDiff(date) {
        var oneDay = 24 * 60 * 60 * 1000;
        var timeNow = new Date();
        var expiredDate = new Date(date);
        return Math.round((expiredDate.getTime() - timeNow.getTime()) / (oneDay));
    }
</script>