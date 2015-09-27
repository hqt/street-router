<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>

<div id="wrapper">

  <%@ include file="_shared/navigation.jsp" %>

  <div id="page-wrapper">
    <div class="row">
      <div class="col-lg-12">
        <h4 class="page-header text-center text-danger">${danger}</h4>
        <h4 class="page-header text-center text-info">${info}</h4>

        <div class="text-center">
          <input type="hidden" name="action" value="detail"/>
          <a href="${pageContext.request.contextPath}/admin/contractType" type="button"
             class="btn btn-default">
            <i class="fa fa-arrow-left"></i>
            Trở về trang loại hợp đồng
          </a>
        </div>
      </div>
      <!-- /.col-lg-12 -->
    </div>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
  </div>
</div>
</div>
</div>
<!-- /#wrapper -->

<%@ include file="_shared/footer.jsp" %>