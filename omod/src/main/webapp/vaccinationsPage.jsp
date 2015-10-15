<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="Manage Vaccinations" otherwise="/login.htm" redirect="/index.htm" />

<c:choose>
	<c:when test="${retroactive != 'true'}">
		<a href="../vaccinations/vaccinationsPage.form?patientId=${patientId}&retroactive=true">Enable retroactive data entry</a>
	</c:when>
	<c:otherwise>
		<a href="../vaccinations/vaccinationsPage.form?patientId=${patientId}&retroactive=false">Disable retroactive data entry</a>
	</c:otherwise>
</c:choose>
<br/>
<h1 align="center">${patientName}</h1>
<openmrs:portlet id="Vaccinations" moduleId="vaccinations" url="vaccinationsPortlet" />

<%@ include file="/WEB-INF/template/footer.jsp"%>