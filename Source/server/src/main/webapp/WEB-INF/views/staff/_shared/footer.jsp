<%--
  Created by IntelliJ IDEA.
  User: dinhquangtrung
  Date: 5/23/15
  Time: 11:15 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="page-footer" class="footer-distributed">
    <div class="footer-left">
        <img src="${pageContext.request.contextPath}/img/logoCompany.png" height="70px"/>

        <p class="footer-links">
            <a href="${pageContext.request.contextPath}/">Trang chủ</a>
            ·
            <a href="#">Liên hệ</a>
            ·
            <a href="#">Hỗ trợ</a>
        </p>

        <p>
            <a href="${pageContext.request.contextPath}/user?action=login&refrole=staff&redirect=/staff">Nhân viên đăng nhập</a>
        </p>

        <p class="footer-company-name">MIC Group 2 &copy; 2015</p>
    </div>

    <div class="footer-center">
        <div>
            <i class="fa fa-map-marker"></i>

            <p>
        <span>Tòa nhà Innovation, Công viên phần mềm Quang Trung
        P. Tân Chánh Hiệp, Quận 12, TP. Hồ Chí Minh.</span>
            </p>
        </div>
        <div>
            <i class="fa fa-phone"></i>

            <p>+84 37337009</p>
        </div>
        <div>
            <i class="fa fa-envelope"></i>

            <p><a href="mailto:hydrangea8604@gmail.com">hydrangea8604@gmail.com</a></p>
        </div>
    </div>
    <div class="footer-right">
        <p class="footer-company-about">
            <span>Sơ lược về đề tài</span>
        </p>

        <p class="footer-company-name">Bản quyền thuộc về Group 2</p>
    </div>
</div>

<!-- Bootstrap Core JavaScript -->
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

<!-- User script -->
<script src="${pageContext.request.contextPath}/js/main.js"></script>

</body>
</html>