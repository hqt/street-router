<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${pageContext.request.contextPath}/js/jquery.min.js" type="text/javascript"></script>

<html>
<head>
    <input type="hidden" id="url" value="${url1}">
    <title>Mic- Thẻ Bảo Hiểm</title>
    <!--Including Bootstrap style files-->
    <c:set var="url1" value="${url1}"></c:set>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span4">
        </div>
        <div class="span5">

        </div>
    </div>
</div>
<script type="text/javascript">
    if (window != top) {
        top.location.href = '${url}';
    } //lightbox return
    else
        window.location.href = '${url}'; //return from full page paypal flow
</script>
</body>
</html>