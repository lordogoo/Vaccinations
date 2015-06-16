<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="Manage Vaccinations" otherwise="/login.htm" redirect="/index.htm" />

<openmrs:portlet id="Vaccinations" moduleId="vaccinations" url="vaccinationsPortlet" />

<%@ include file="/WEB-INF/template/footer.jsp"%>