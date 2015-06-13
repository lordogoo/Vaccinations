<%@ include file="/WEB-INF/template/include.jsp"%>

<openmrs:require privilege="Manage Vaccinations" otherwise="/login.htm" redirect="/index.htm" />

<%@ include file="/WEB-INF/template/header.jsp"%>


<h1><openmrs:message code="formseparator.vaccinationsmenuitem"/></h1>
<h2><openmrs:message code="Patient.search"/></h2>	

<br />

<openmrs:portlet id="findPatient" url="findPatient" parameters="size=full|hideAddNewPatient=true|postURL=manage.form|showIncludeVoided=false|viewType=shortEdit" />

<%@ include file="/WEB-INF/template/footer.jsp"%>