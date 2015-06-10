<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table>
<tr><td>Month/Ticket type</td>
<c:forEach var="ticketType" items="${ticketTypes}">
<td>${ticketType}</td>
</c:forEach>
<td>Totals</td>
</tr>

<c:forEach var="item" items="${ticketStatistics.items}">
<tr>
<td>${item.period}</td>
<td>${item.single15}</td>
<td>${item.single60}</td>
<td>${item.sms20}</td>
<td>${item.sms75}</td>
<td>${item.withoutTicket}</td>
<td>${item.totalPeriodSum}</td>
<tr>
</c:forEach>

<tr>
<c:forEach var="i" begin="0" end="6" step="1">
<c:choose>
    <c:when test="${i == 6}"><td align="center">${ticketStatistics.totalSum}</td></c:when>
    <c:otherwise><td>&nbsp;</td></c:otherwise>
</c:choose>
</c:forEach>
</tr>

</table>
