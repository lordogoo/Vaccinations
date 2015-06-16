
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="Manage Vaccinations" otherwise="/login.htm" redirect="/module/vaccinations/vaccinationsPage.jsp">
<openmrs:portlet id="Vaccinations" moduleId="vaccinations" url="vaccinationsPortlet" />
</openmrs:require>