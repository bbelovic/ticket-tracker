<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html><head>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css"/>" />
<title>Add new ticket information</title>
<head>
<body>
<div class="top">
Ticket tracking application
</div>
<div class="left">
Menu <br>
<a href="<c:url value="/newRideRecord"/>">New ride record</a><br>
<a href="<c:url value="/ticketStatistics"/>">Ticket statistics</a>
</div>
<div class="center">
<c:if test="${not empty  jspToInclude}">
<jsp:include page="${jspToInclude}.jsp"/>
</c:if>

</div>
</body>
</html>