<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table>
<tr><td>Month/Ticket type</td>
<c:forEach var="ticketType" items="${ticketTypes}">
<td>${ticketType}</td>
</c:forEach>
</tr>
<c:forEach var="entry" items="${ticketStatistics}">
<tr><td>${entry.key}</td>

<c:forEach var="ticketStatisticsItem" items="${entry.value}">
<td>${ticketStatisticsItem.value[0].ticketCount} / ${ticketStatisticsItem.value[0].ticketPrice}
</td>
</c:forEach>

</tr>
</c:forEach>
