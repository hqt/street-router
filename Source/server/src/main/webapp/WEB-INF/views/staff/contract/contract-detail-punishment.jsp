<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form class="form-horizontal">
    <fieldset>
        <legend>
            Lịch sử vi phạm luật ATGT
            <div class="pull-right" style="margin-top: -10px;">
                <a href="${pageContext.request.contextPath}/staff/punishment?action=create&code=${contract.contractCode}"
                   type="button" class="btn btn-success updateBtn">
                    <i class="fa fa-plus"></i> Thêm vi phạm luật ATGT
                </a>
            </div>
        </legend>
    </fieldset>


    <!--<input type="checkbox" class="check-all"/>-->
    <b>Có ${punishmentPaginator.itemSize} trường hợp vi phạm</b>

    <div class="table table-responsive">
        <table class="table table-bordered">
            <thead>
            <tr class="success">
                <th class="text-center ">Mã vi phạm
                </th>
                <th class=" text-center">
                    Nội dung vi phạm
                </th>
                <th class="text-center">
                    Ngày gởi đi
                </th>
                <th class="text-center">
                    Biên bản
                </th>

                <th>Chỉnh sửa</th>
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
                            <td>
                                <a href="${punishment.attachment}">
                                    Xem chi tiết
                                </a>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/staff/punishment?action=edit&id=${punishment.id}"
                                   type="button" class="btn btn-xs btn-primary updateBtn">
                                    <i class="fa fa-pencil"></i> Chỉnh sửa
                                </a>
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

</form>