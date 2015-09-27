<%@page import="java.util.HashMap"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>MIC - Bảo hiểm trực tuyến</title>
	<!--Including Bootstrap style files-->
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/bootstrap-responsive.min.css" rel="stylesheet">
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="https://code.jquery.com/jquery.js"></script>
</head>
<body>
<div class="container-fluid">
	<div class="well">

		<h2 class="text-center">MIC - Bảo hiểm trực tuyến</h2>
	</div>
	<div class="row-fluid">

<div class="span4">
	</div>
	<div class="span5">
		<table>
			<tbody>
				<tr><td><h4>Shipping Address</h5></td><td><h4>Billing Address</h4></td></tr>
				<% HashMap result = (HashMap) request.getAttribute("result");  %>
				<%--<tr><td><%=result.get("PAYMENTREQUEST_0_SHIPTONAME")%></td><td><%=result.get("PAYMENTREQUEST_0_SHIPTONAME")%></td></tr>--%>
				<%--<tr><td><%=result.get("PAYMENTREQUEST_0_SHIPTOSTREET")%></td><td><%=result.get("PAYMENTREQUEST_0_SHIPTOSTREET")%></td></tr>--%>
				<%--<tr><td><%=result.get("PAYMENTREQUEST_0_SHIPTOCITY")%></td><td><%=result.get("PAYMENTREQUEST_0_SHIPTOCITY")%></td></tr>--%>
				<%--<tr><td><%=result.get("PAYMENTREQUEST_0_SHIPTOSTATE")%></td><td><%=result.get("PAYMENTREQUEST_0_SHIPTOSTATE")%></td></tr>--%>
				<%--<tr><td><%=result.get("PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE")%></td><td><%=result.get("PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE")%></td></tr>--%>
				<%--<tr><td><%=result.get("PAYMENTREQUEST_0_SHIPTOZIP")%></td><td><%=result.get("PAYMENTREQUEST_0_SHIPTOZIP")%></td></tr>--%>
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				
				<tr><td>Total Amount:</td><td id='amount'><%=result.get("PAYMENTREQUEST_0_AMT")%></td></tr>
				<tr><td>Currency Code:</td><td><%=result.get("CURRENCYCODE")%></td></tr>
				<tr><td>&nbsp;</td></tr>
				<tr><td><h3>Shipping Method</h3></td></tr>
				<form action="public/return?action=return&page=return" name="order_confirm" method="POST">
					<%--<tr><td>Shipping methods: </td><td>--%>
					<%--<select onchange="updateAmount();" name="shipping_method" id="shipping_method" style="width: 250px;" class="required-entry">--%>
						<%--<optgroup label="United Parcel Service" style="font-style:normal;">--%>
						<%--<option value="2.00">--%>
						<%--Worldwide Expedited - $2.00</option>--%>
						<%--<option value="3.00">--%>
						<%--Worldwide Express Saver - $3.00</option>--%>
						<%--</optgroup>--%>
						<%--<optgroup label="Flat Rate" style="font-style:normal;">--%>
						<%--<option selected value="0.00">--%>
						<%--Fixed - $0.00</option>--%>
						<%--</optgroup>--%>
					<%--</select><br></td></tr>--%>
					<tr><td><input type="Submit" name="confirm" alt="Check out with PayPal" class="btn btn-primary btn-large" value="Confirm Order"></td></tr>
				</form>
			</tbody>
		</table>
	</div>
	<div class="span3">
	</div>
		<%--<script >--%>
				<%--var origAmt=<%=result.get("PAYMENTREQUEST_0_AMT")%>;--%>
				<%--var oldshipAmt=<%=result.get("PAYMENTREQUEST_0_SHIPPINGAMT")%>;--%>
				<%--function updateAmount(){--%>
					<%--var e = document.getElementById("shipping_method");--%>
					<%--var shipAmt = parseInt(e.options[e.selectedIndex].value);--%>
					<%--var newAmt = shipAmt+origAmt-oldshipAmt;--%>
					<%--document.getElementById("amount").innerHTML=newAmt+'.00';--%>
				<%--}--%>
				<%--$(document).ready(function() {--%>
				    <%--// any code goes here--%>
				   <%--updateAmount();--%>
				<%--});--%>
				<%--</script>--%>
	</div> <!-- Row-Fluid ends here -->
</div>  <!--Container-Fluid ends here -->
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>
</body>
</html>