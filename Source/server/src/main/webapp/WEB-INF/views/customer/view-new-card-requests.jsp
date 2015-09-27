<%--
  Created by IntelliJ IDEA.
  User: TriPQM
  Date: 07/04/2015
  Time: 6:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp"%>

<div id="wrapper">

  <%@ include file="_shared/navigation.jsp" %>
  <div id="page-wrapper">
    <div class="row">
      <div class="col-lg-12">
        <h1 class="page-header">Yêu cầu cấp thẻ mới</h1>
      </div>
      <!-- /.col-lg-12 -->
    </div>
    <c:if test="${param.info eq 'cancelNewCardRequestSuccess'}">
      <div class="text-success text-center">
        Hủy yêu cầu thẻ mới thành công
      </div>
    </c:if>
    <c:if test="${param.info eq 'fail'}">
      <div class="text-danger text-center">
        Có lỗi xảy ra. Xin thử lại
      </div>
    </c:if>
    <div class="row">
      <div class="col-lg-12">
        <div class="panel panel-default">
          <div class="panel-heading">
            <div class="pull-left center-dropdown-button">
              <!--<input type="checkbox" class="check-all"/>-->
              <b>Có ${unresolvedRequestCount} yêu cầu cấp thẻ mới chưa xử lý</b>
            </div>
            <div class="pull-right no-wrap">
              <form action="${pageContext.request.contextPath}/customer/card" method="get">
                <input type="hidden" name="action" value="viewNewCardRequestsSearch"/>
                <input type="text" class="form-control long-text-box" name="keyword"
                       placeholder="Tìm kiếm theo mã thẻ cũ" value="${param.keyword}"/>
                <input type="submit" class="btn btn-default" value="Tìm kiếm"/>
              </form>
            </div>
            <div class="clearfix"></div>
          </div>
          <!-- /.panel-heading -->
          <div class="panel-body">
            <div class="table-responsive">
              <table class="table table-hover table-striped">
                <thead>
                <tr>
                  <th>#</th>
                  <th>Thời gian</th>
                  <th>Ghi chú</th>
                  <th>Mã thẻ cũ</th>
                  <th>Hợp đồng</th>
                  <th>Ngày cấp mới</th>
                  <th>Thẻ mới cấp</th>
                  <th>Thực hiện</th>
                </tr>
                </thead>
                <c:set var="requests" value="${requestPaginator.getItemsOnCurrentPage(param.page)}"/>
                <tbody>
                <c:choose>
                  <c:when test="${requests.size() == 0}">
                    <tr>
                      <td colspan="7" style="vertical-align: middle; text-align: center;">
                        Không có yêu cầu thẻ mới nào
                      </td>
                    </tr>
                  </c:when>
                  <c:otherwise>
                    <c:forEach var="newRequest" items="${requests}" varStatus="counter">
                      <tr>
                        <td>${(requestPaginator.getCurrentPage(param.page) - 1) * requestPaginator.itemPerPage + counter.count}</td>
                        <td>
                          <fmt:formatDate value="${newRequest.requestDate}" pattern="dd/MM/yyyy"/>
                        </td>
                        <td><a tabindex="0" data-trigger="focus" data-toggle="popover" title="Ghi chú từ khách hàng"
                               role="button" data-content="${newRequest.note}"><i class="fa fa-file"></i></a></td>
                        <td>
                            ${newRequest.micCardInstanceByOldCardInstanceId.cardId}
                        </td>
                        <td>
                          <a href="${pageContext.request.contextPath}/customer/contract?action=detail&code=${mapCardContract[newRequest.micCardInstanceByOldCardInstanceId.cardId]}">
                              ${mapCardContract[newRequest.micCardInstanceByOldCardInstanceId.cardId]}
                          </a>
                        </td>
                        <td>
                          <c:if test="${empty newRequest.resolveDate}">
                            <span class="label label-danger">Chưa cấp</span>
                          </c:if>
                          <fmt:formatDate value="${newRequest.resolveDate}" pattern="dd/MM/yyyy"/>
                        </td>
                        <td>
                            ${map[newRequest.id]}
                        </td>
                        <td>
                          <c:if test="${newRequest.isPaid == 1}">
                            <span class="label label-info">Đã thanh toán</span>
                          </c:if>
                          <c:if test="${newRequest.isPaid == 0}">
                            <c:if test="${newRequest.micCardInstanceByOldCardInstanceId.micContractByContractCode.status.equalsIgnoreCase('Ready')}">
                              <button contractCode="${newRequest.micCardInstanceByOldCardInstanceId.contractCode}" type="button" class="btn btn-danger btn-xs"
                                      data-toggle="modal" data-target="#cancel-new-card-request" onclick="{
                                                                    var contractCode = $(this).attr('contractCode');
                                                                    $('#contractCodeModal').val(contractCode);
                                                                    $('#contractCodeModal1').text(contractCode);
                                                                 }">
                                <i class="fa fa-times"></i> Hủy
                              </button>
                            </c:if>
                          </c:if>
                        </td>
                      </tr>
                    </c:forEach>
                  </c:otherwise>
                </c:choose>

                </tbody>
              </table>
            </div>
            <!-- /.table-responsive -->
            <nav class="text-right">
              <ul class="pagination">
                <c:if test="${param.page != 1 && not empty param.page}">
                  <li>
                    <a href="?action=${param.action}&keyword=${param.keyword}&page=1" aria-label="Previous">
                      <span aria-hidden="true">Đầu</span>
                    </a>
                  </li>
                </c:if>
                <c:forEach begin="1" end="${requestPaginator.pageSize}" var="pageNumber">
                  <li ${param.page == pageNumber ||(pageNumber == 1 && empty param.page) ? "class='active'": ""} >
                    <a href="?action=${param.action}&keyword=${param.keyword}&page=${pageNumber}">${pageNumber}</a>
                  </li>
                </c:forEach>
                <c:if test="${param.page != requestPaginator.pageSize && requestPaginator.pageSize != 1}">
                  <li>
                    <a href="?action=${param.action}&keyword=${param.keyword}&page=${requestPaginator.pageSize}"
                       aria-label="Next">
                      <span aria-hidden="true">Cuối</span>
                    </a>
                  </li>
                </c:if>
              </ul>
            </nav>
          </div>
          <!-- /.panel-body -->
        </div>
        <!-- /.panel -->


      </div>
    </div>
  </div>
</div>
<!-- /#wrapper -->

<%@ include file="_shared/footer.jsp"%>
<jsp:include page="cancel-new-card-request-modal.jsp" flush="true"/>