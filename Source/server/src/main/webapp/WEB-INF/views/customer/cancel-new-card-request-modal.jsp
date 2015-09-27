<%--
  Created by IntelliJ IDEA.
  User: TriPQM
  Date: 07/19/2015
  Time: 12:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- model for deactivate contract type -->
<form action="${pageContext.request.contextPath}/customer/card" method="post" class="form-horizontal">
  <div class="modal fade" id="cancel-new-card-request">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                  aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">Xác nhận ${contract.contractCode}</h4>
        </div>
        <div class="modal-body">
          <p class="text-center">Hủy yêu cầu thẻ mới hợp đồng: <span id="contractCodeModal1"></span></p>
        </div>
        <div class="modal-footer">
          <input id="contractCodeModal" name="contractCode" type="hidden"/>
          <input type="hidden" name="action" value="cancelNewCardRequest" />
          <button type="submit" class="btn btn-danger">
            <i class="fa fa-times"></i> Hủy yêu cầu
          </button>
          <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>

        </div>
      </div>
      <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
  </div>
  <!-- /.modal -->

</form>
