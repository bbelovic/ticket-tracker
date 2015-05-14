<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html><head>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css"/>" />
<title>Add new ticket information</title>
<head>
<body>
<div class="top">
Add new ride record
</div>
<form method="post" action="<c:url value="/ticketController"/>">
User ID:
<input type="text" name="userId"/>
<br>
Ticket type:
<select name="ticketType">
<option value="SMS_75">SMS 75</option>
<option value="SMS_20">SMS 20</option>
<option value="SINGLE_15">SINGLE 15</option>
<option value="SINGLE_60">SINGLE 60</option>
<option value="WITHOUT_TICKET">WITHOUT TICKET</option>
<option>UNIVERSAL</option>
</select>
<br>
Date:
<input type="datetime-local" name="rideDate" >
<br>
<input type="submit"/>
</form>
</body>
</html>