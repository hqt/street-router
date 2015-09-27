<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form class="form-horizontal">
    <fieldset>
        <legend>
            Lịch sử tai nạn
            <div class="pull-right" style="margin-top: -10px;">
                <a href="${pageContext.request.contextPath}/staff/accident?action=create&code=${contract.contractCode}"
                   type="button" class="btn btn-success updateBtn">
                    <i class="fa fa-plus"></i> Thông báo tai nạn mới
                </a>
            </div>
        </legend>
    </fieldset>

    <!--<input type="checkbox" class="check-all"/>-->
    <b>Có ${accidentPaginator.itemSize} tai nạn</b>

    <c:set var="accidents"
           value="${accidentPaginator.getItemsOnCurrentPage(param.page, param.active)}"/>
    <div class="table table-responsive">
        <table class="table table-bordered">
            <thead>
            <tr class="success">
                <th class="text-center">
                    Mã tai nạn
                </th>
                <th class="text-center">
                    Nội dung
                </th>
                <th class=" text-center" style="width: 14% !important;">
                    Thời gian
                </th>
                <th class="text-center">
                    Mô tả
                </th>
                <th>Chỉnh sửa</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${accidents.size() == 0}">
                    <tr>
                        <td colspan="5" style="vertical-align: middle; text-align: center;">
                            Không có tai nạn nào
                        </td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${accidents}"
                               var="accident"
                               varStatus="counter">
                        <tr>
                            <td class="text-center">
                                    ${accident.id}
                            </td>
                            <td>
                                    ${accident.title}
                            </td>
                            <td class="text-center">
                                <fmt:formatDate value='${accident.createdDate}'
                                                pattern='dd/MM/yyyy'/>
                            </td>
                            <td class="text-center">
                                <a href="${accident.attachment}">
                                    Xem chi tiết
                                </a>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/staff/accident?action=edit&id=${accident.id}"
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

            <c:if test="${param.active != accidentPaginator.tag}">
                <c:set var="currentPage" value="1"/>
            </c:if>

            <c:if test="${currentPage != 1 && not empty currentPage}">
                <li>
                    <a href="?action=${param.action}&code=${contract.contractCode}&keyword=${param.keyword}&page=1&active=${accidentPaginator.tag}"
                       aria-label="Previous">
                        <span aria-hidden="true">Đầu</span>
                    </a>
                </li>
            </c:if>
            <c:forEach begin="1" end="${accidentPaginator.pageSize}" var="pageNumber">
                <li ${currentPage == pageNumber ||(pageNumber == 1 && empty currentPage) ? "class='active'": ""} >
                    <a href="?action=${param.action}&code=${contract.contractCode}&keyword=${param.keyword}&page=${pageNumber}&active=${accidentPaginator.tag}">${pageNumber}</a>
                </li>
            </c:forEach>
            <c:if test="${currentPage != accidentPaginator.pageSize && accidentPaginator.pageSize != 1}">
                <li>
                    <a href="?action=${param.action}&code=${contract.contractCode}&keyword=${param.keyword}&page=${accidentPaginator.pageSize}&active=${accidentPaginator.tag}"
                       aria-label="Next">
                        <span aria-hidden="true">Cuối</span>
                    </a>
                </li>
            </c:if>
        </ul>
    </nav>
</form>