<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>

<div id="wrapper">

    <%@ include file="_shared/navigation.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h4 class="page-header text-center text-danger">${result}</h4>
                <h4 class="page-header text-center text-info">${info}</h4>

                <form action="${pageContext.request.contextPath}/customer/contract" method="get">
                    <div class="well well-lg text-center text-danger form-inline">
                        <input type="hidden" name="code"
                               value="${contractCode}"/>
                        <input type="hidden" name="action" value="detail"/>
                       <button type="submit" class="btn btn-primary"><i class="fa fa-arrow-left"></i> Quay Về Trang Thông Tin Hợp Đồng </button>
                    </div>
                </form>
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