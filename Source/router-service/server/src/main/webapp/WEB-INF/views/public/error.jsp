<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>
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
			<div style="max-width: 800px;margin: 0 auto;">

				<!--      Wizard container        -->

				<div class="wizard-container">
					<div class="card wizard-card ct-wizard-azzure" id="wizard">

						<!-- You can switch "ct-wizard-azzure"  with one of the next bright colors: "ct-wizard-blue",
                        "ct-wizard-green", "ct-wizard-orange", "ct-wizard-red" -->

						<div class="wizard-header">
							<h3>
								<%=request.getAttribute("error")%>
							</h3>
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
</body>
<%@ include file="_shared/footer.jsp" %>
</html>
