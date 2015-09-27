<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form class="form-horizontal">
    <fieldset>
        <legend>Lịch sử thẻ</legend>
    </fieldset>
    <div class="table-responsive">
        <table class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th>#</th>
                <th>Mã thẻ</th>
                <th>Bắt đầu có hiệu lực từ</th>
                <th>Thời điểm hết hiệu lực</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="instance" items="${listCard}" varStatus="counter">
                <tr>
                    <td>${counter.count}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/staff/card?action=detail&cardId=${instance.cardId}">
                                ${instance.cardId}
                        </a>
                    </td>
                    <td>
                        <fmt:formatDate value="${instance.activatedDate}" pattern="dd/MM/yyyy"/> lúc
                        <fmt:formatDate value="${instance.activatedDate}" type="time"/>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${empty instance.deactivatedDate}">
                                <span class="label label-success">Đang hoạt động</span>
                            </c:when>
                            <c:otherwise>
                                <fmt:formatDate value="${instance.deactivatedDate}" pattern="dd/MM/yyyy"/> lúc
                                <fmt:formatDate value="${instance.deactivatedDate}" type="time"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</form>