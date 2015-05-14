<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<title>vaccinations</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width">
</head>
<body>
	<div class="container" ng-controller="MainController">
		<loader></loader>
		<div class="add-vaccination-wrapper">
			<h5 class="add-vaccination-header">Add New Vaccination</h5>
			<div class="add-vaccination-select-wrapper">
				<select class="form-control add-vaccination" ng-model="newVaccine"
					ng-options="vaccines as formatVaccine(vaccines) for vaccines in vaccines"></select>
			</div>
			<div class="form-button-wrapper">
				<button ng-if="newVaccine" class="btn btn-info"
					ng-click="stageVaccination(newVaccine, false)">
					<strong>Administer</strong>
				</button>
				<button ng-if="newVaccine" class="btn btn-primary"
					ng-click="stageVaccination(newVaccine, true)">
					<strong>Schedule</strong>
				</button>
			</div>
			<div class="staged-wrapper" ng-if="stagedVaccinations.length > 0">
				<vaccination ng-if="stagedVaccinations"
					get-vaccination="stagedVaccinations[0]"></vaccination>
			</div>
		</div>
		<div class="vaccinations-wrapper">
			<div class="scheduled-header">
				<span class="label label-default section-label">Scheduled</span>
			</div>
			<div
				ng-repeat="(name, vaccinationsGroup) in vaccinations | filter: {scheduled: 'true'} | groupBy: 'name'">
				<div class="vaccination-group-header">{{ ::name }}</div>
				<vaccination get-vaccination="vaccination"
					ng-repeat="vaccination in vaccinationsGroup | orderBy: ['dose_number']"></vaccination>
			</div>
			<div class="not-scheduled-header">
				<span class="label label-default section-label">Unscheduled</span>
			</div>
			<div
				ng-repeat="(name, vaccinationsGroup) in vaccinations | filter: {scheduled: 'false'} | groupBy: 'name'">
				<div class="vaccination-group-header">{{ ::name }}</div>
				<vaccination get-vaccination="vaccination"
					ng-repeat="vaccination in vaccinationsGroup | orderBy: ['scheduled_date']"></vaccination>
			</div>
		</div>
	</div>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/moduleResources/vaccinations/scripts/vendor-34de396c.js"></script>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/moduleResources/vaccinations/scripts/app-9c7e6386.js"></script>
		
	<link rel="stylesheet" href="${pageContext.request.contextPath}/moduleResources/vaccinations/styles/vendor-60509a3f.css">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/moduleResources/vaccinations/styles/app-51c872b4.css">
	<!-- 
		<link rel="stylesheet" href="styles/vendor-60509a3f.css">
		<link rel="stylesheet" href="styles/app-51c872b4.css">
 	-->
</body>
</html>

<%@ include file="/WEB-INF/template/footer.jsp"%>