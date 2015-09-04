<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="Manage Vaccinations" otherwise="/login.htm" redirect="/index.htm" />

<a href="../vaccinations/vaccinationsPage.form?patientId=${patientId}&retroactive=true">Retroactive data entry</a>
<br/>
<a href="../vaccinations/vaccinationsPage.form?patientId=${patientId}&retroactive=false">Non-retroactive data entry</a>
<openmrs:portlet id="Vaccinations" moduleId="vaccinations" url="vaccinationsPortlet" />

<%@ include file="/WEB-INF/template/footer.jsp"%>