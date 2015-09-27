<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="_shared/header.jsp" %>
<style>
  label, input {
    width: 100%;
  }
</style>
<body>
<div class="image-container set-full-height"
     style="background-image: url('${pageContext.request.contextPath}/img/wizard-city.jpg')">
  <!--   MIC Branding   -->
  <a href="home">
    <div class="logo-container">
      <img src="${pageContext.request.contextPath}/img/logoCompany.png"
           style="height: 70px;">
    </div>
  </a>

  <div class="container">
    <div class="row">
      <div style="max-width: 400px;margin: 0 auto;">

        <!--      Wizard container        -->

        <div class="wizard-container">
          <div class="card wizard-card ct-wizard-azzure" id="wizard">


            <!-- You can switch "ct-wizard-azzure"  with one of the next bright colors: "ct-wizard-blue",
            "ct-wizard-green", "ct-wizard-orange", "ct-wizard-red" -->

            <div class="wizard-header">
              <h3>
                <b>KHÔNG TÌM THẤY (404)</b><br>
              </h3>
            </div>
            <div style="padding: 30px; text-align: center">
                <img src="/img/404.png" width="300px"/>
                <br/>
              Trang bạn yêu cầu không tồn tại.<br/>
                <a href="${pageContext.request.contextPath}/public/home">Quay về trang chủ</a>
            </div>


          </div>
        </div>
        <!-- wizard container -->
      </div>
    </div>
    <!-- row -->
  </div>
  <!--  big container -->
</div>

<!-- jQuery -->
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</body>

<%@ include file="_shared/footer.jsp" %>