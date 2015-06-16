<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require privilege="Manage Vaccinations" otherwise="/login.htm" redirect="/module/vaccinations/manage.jsp"/>
<%@ include file="template/localHeader.jsp"%>
<%@ include file="/WEB-INF/template/footer.jsp"%>
