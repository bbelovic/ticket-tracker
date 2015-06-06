<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table>
<tr><td>Month/Ticket type</td>
<c:forEach var="ticketType" items="${ticketTypes}">
<td>${ticketType}</td>
</c:forEach>
<td>Totals</td>
</tr>

<c:forEach var="entry" items="${ticketStatistics}">
<tr><td>${entry.key}</td>

<c:forEach var="ticketStatisticsItem" items="${entry.value}">
<td align="center">
${ticketStatisticsItem.value[0].ticketCount}
</td>
</c:forEach>
<td align="center">${monthlyTotals[entry.key]}</td>
</tr>

</c:forEach>

<tr>
<c:forEach var="i" begin="0" end="6" step="1">
<c:choose>
    <c:when test="${i == 6}"><td align="center">${grandTotal}</td></c:when>
    <c:otherwise><td>&nbsp;</td></c:otherwise>
</c:choose>
</c:forEach>
</tr>
