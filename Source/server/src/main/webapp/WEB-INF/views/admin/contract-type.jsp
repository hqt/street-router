<%--
  Created by IntelliJ IDEA.
  User: TriPQM
  Date: 07/15/2015
  Time: 10:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>

<div id="wrapper">

    <%@ include file="_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Quản lý loại hợp đồng</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <c:if test="${param.info eq 'deactivateSuccess'}">
            <div class="text-success">
                Ngừng hoạt động thành công
            </div>
        </c:if>
        <c:if test="${param.info eq 'addSuccess'}">
            <div class="text-success">
                Thêm loại hợp đồng thành công
            </div>
        </c:if>
        <c:if test="${param.info eq 'fail'}">
            <div class="text-danger">
                Có lỗi xảy ra. Xin thử lại
            </div>
        </c:if>
        <c:if test="${param.info eq 'activateSuccess'}">
            <div class="text-success">
                Tái hoạt động thành công
            </div>
        </c:if>

        <div class="row">
            <div class="col-lg-12">
                <br/>
                <c:if test="${not empty validateErrors}">
                    <div class="text-danger">
                        <ul>
                            <c:forEach var="error" items="${validateErrors}">
                                <li>${error}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>
                <form action="${pageContext.request.contextPath}/admin/contractType"
                      method="post" class="form-horizontal">
                    <fieldset>
                        <legend>Thêm loại hợp đồng mới</legend>
                        <div>
                            <label class="col-sm-4 control-label">Tên loại hợp đồng *</label>

                            <div class="col-sm-7">
                                <p class="text-value"><input name="contractType:name" type="text"
                                                             class="form-control input-md" required
                                                             placeholder="Ví dụ: Xe trên 50cc có BH cho người trên xe"
                                                             value="${submitted.name}"></p>

                            </div>
                        </div>
                        <div>
                            <label class="col-sm-4 control-label">Miêu tả *</label>

                            <div class="col-sm-7">
                                <p class="text-value"><textarea name="contractType:description" cols="2"
                                                                class="form-control input-md"
                                                                placeholder="Ví dụ: Bảo hiểm cho xe trên 50cc và có BH cho người ngồi trên xe"
                                                                required>${submitted.description}</textarea></p>

                            </div>
                        </div>
                        <div>
                            <label class="col-sm-4 control-label">Phí hằng năm (VNĐ) *</label>

                            <div class="col-sm-7">
                                <p class="text-value"><input name="contractType:pricePerYear" type="number"
                                                             class="form-control input-md"
                                                             required min="0" max="1000000000"
                                                             placeholder="Ví dụ: 86000"
                                                             value="${submitted.pricePerYear}"></p>

                            </div>
                        </div>

                    </fieldset>
                    <br>

                    <div class="text-center">
                        <input type="hidden" name="action" value="addContractType">
                        <button type="submit" class="btn btn-success">
                            Thêm
                        </button>

                    </div>
                    <fieldset>
                        <legend>Lịch sử</legend>
                        <div class="table-responsive">
                            <table class="table table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Tên</th>
                                    <th>Miêu tả</th>
                                    <th>Phí hằng năm</th>
                                    <th>Tình trạng</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:set var="contractTypes"
                                       value="${contractTypePaginator.getItemsOnCurrentPage(param.page)}"/>
                                <c:choose>
                                    <c:when test="${contractTypes.size() == 0}">
                                        <tr>
                                            <td colspan="6" style="vertical-align: middle; text-align: center;">
                                                Không có loại hợp đồng nào
                                            </td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="row" items="${contractTypes}" varStatus="counter">
                                            <tr>
                                                <td>${(contractTypePaginator.getCurrentPage(param.page) - 1) * contractTypePaginator.itemPerPage + counter.count}</td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/admin/contractType?action=viewEditContractType&contractTypeId=${row.id}">
                                                            ${row.name}
                                                    </a>

                                                </td>
                                                <td>
                                                    <a tabindex="0" data-trigger="focus" data-toggle="popover"
                                                       title="Miêu tả"
                                                       role="button" data-content="${row.description}"><i
                                                            class="fa fa-file"></i></a>
                                                </td>
                                                <td>
                                                    <fmt:setLocale value="vi_VN"/>
                                                    <fmt:formatNumber value="${row.pricePerYear}" type="currency"
                                                                      currencySymbol="" maxFractionDigits="0"/> VNĐ
                                                </td>
                                                <c:if test="${row.active eq '1'}">
                                                    <td>
                                                <span class="label label-success"
                                                      style="font-size: 12px">Đang hoạt động</span>
                                                    </td>
                                                </c:if>
                                                <c:if test="${row.active eq '0'}">
                                                    <td><span class="label label-dark"
                                                              style="font-size: 12px">Ngừng hoạt động</span>
                                                    </td>
                                                </c:if>
                                                <td>
                                                    <c:if test="${row.active eq 1}">
                                                        <button contractTypeId="${row.id}" page="${param.page}"
                                                                contractName="${row.name}"
                                                                numberContracts="${map[row.id]}"
                                                                actionName="deactivateContractType"
                                                                type="button" class="btn btn-danger btn-xs"
                                                                data-toggle="modal"
                                                                data-target="#deactivate-contract-type" onclick="{
                                      var contractTypeId = $(this).attr('contractTypeId');
                                      var page = $(this).attr('page');
                                      var contractName = $(this).attr('contractName');
                                      var action = $(this).attr('actionName');
                                      var numberContracts = $(this).attr('numberContracts');
                                      $('#action').val(action);
                                      $('#contractTypeId').val(contractTypeId);
                                      $('#page').val(page);
                                      $('#contractTypeId1').text(contractTypeId);
                                      $('#page1').text(page);
                                      $('#contractName').text(contractName);
                                      $('#numberContracts').text(numberContracts);
                                   }">
                                                            <i class="fa fa-stop"></i> Ngừng hoạt động
                                                        </button>
                                                    </c:if>
                                                    <c:if test="${row.active eq 0}">
                                                        <button contractTypeId="${row.id}" page="${param.page}"
                                                                contractName="${row.name}"
                                                                actionName="activateContractType"
                                                                type="button" class="btn btn-success btn-xs"
                                                                data-toggle="modal"
                                                                data-target="#activate-contract-type" onclick="{
                                      var contractTypeId = $(this).attr('contractTypeId');
                                      var page = $(this).attr('page');
                                      var contractName = $(this).attr('contractName');
                                      var action = $(this).attr('actionName');
                                      $('#contractTypeId').val(contractTypeId);
                                      $('#page').val(page);
                                      $('#action').val(action);
                                      $('#contractTypeId1').text(contractTypeId);
                                      $('#page1').text(page);
                                      $('#contractName1').text(contractName);

                                   }">
                                                            <i class="fa fa-play"></i> Tái hoạt động
                                                        </button>
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
                                        <a href="?action=view&keyword=${param.keyword}&page=1"
                                           aria-label="Previous">
                                            <span aria-hidden="true">Đầu</span>
                                        </a>
                                    </li>
                                </c:if>
                                <c:forEach begin="1" end="${contractTypePaginator.pageSize}" var="pageNumber">
                                    <li ${param.page == pageNumber ||(pageNumber == 1 && empty param.page) ? "class='active'": ""} >
                                        <a href="?action=view&keyword=${param.keyword}&page=${pageNumber}">${pageNumber}</a>
                                    </li>
                                </c:forEach>
                                <c:if test="${param.page != contractTypePaginator.pageSize && contractTypePaginator.pageSize != 1}">
                                    <li>
                                        <a href="?action=view&keyword=${param.keyword}&page=${contractTypePaginator.pageSize}"
                                           aria-label="Next">
                                            <span aria-hidden="true">Cuối</span>
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                        </nav>
                    </fieldset>
                </form>


            </div>
        </div>
    </div>
</div>
<!-- /#wrapper -->
<jsp:include page="contract-type-modal.jsp" flush="true"/>
<%@ include file="_shared/footer.jsp" %>
