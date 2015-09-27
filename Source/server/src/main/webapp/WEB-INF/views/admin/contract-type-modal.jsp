<%--
  Created by IntelliJ IDEA.
  User: TriPQM
  Date: 07/15/2015
  Time: 7:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- model for deactivate contract type -->
<form action="${pageContext.request.contextPath}/admin/contractType" method="post" class="form-horizontal">
<div class="modal fade" id="deactivate-contract-type">
  <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                  aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">Xác nhận</h4>
        </div>
        <div class="modal-body">
          <p class="text-center">Có <span id="numberContracts"></span> hợp đồng thuộc loại hợp đồng: <span id="contractName"></span></p>
          <p class="text-center">Bạn có chắc chắn muốn ngừng hoạt động loại hợp đồng này? </p>
        </div>
        <div class="modal-footer">
          <input id="contractTypeId" name="contractTypeId" type="hidden"/>
          <input id="page" name="page" type="hidden">
          <input id="action" type="hidden" name="action" />
          <button type="submit" class="btn btn-danger">
            <i class="fa fa-stop"></i> Ngừng hoạt động
          </button>
          <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>

        </div>
      </div>
      <!-- /.modal-content -->
  </div>
  <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
  <div class="modal fade" id="activate-contract-type">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                  aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">Xác nhận ${contractName}</h4>
        </div>
        <div class="modal-body">
          <p class="text-center">Tái hoạt động loại hợp đồng: <span id="contractName1"></span></p>
        </div>
        <div class="modal-footer">
          <button type="submit" class="btn btn-success">
            <i class="fa fa-play"></i> Tái hoạt động
          </button>
          <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>

        </div>
      </div>
      <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
  </div>

</form>