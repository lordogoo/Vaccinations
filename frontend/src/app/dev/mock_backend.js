// 'use strict';

// var mockBackend = angular.module('mockBackend', ['vaccinations', 'ngMockE2E', 'mockData']);
// mockBackend.run(function($httpBackend, $timeout, mockObjects, helperFunctions, appConstants){
//     // Get the mock json data from the mockData module.
//     var vaccinations = mockObjects.vaccinations;
//     var loaderDelay = 10000;
//     appConstants.setPatiendId(1);

//     $httpBackend.whenGET(/^\/?vaccinations\/patients\/1/).respond(mockObjects);
//     $httpBackend.whenGET(/^\/?vaccines/).respond(mockObjects);
//     $httpBackend.whenGET(/^\/?vaccines\/non_scheduled$/).respond(mockObjects);

//     $httpBackend.whenPOST(/^\/vaccinations\/patients\/1/).respond(function(method, url, data){
//         var vaccination = angular.fromJson(data).vaccination;
//         // Add a vaccination id field and remove the
//         // staged marker.
//         var time = new Date().getTime() + loaderDelay;
//         while (new Date() < time) {}

//         vaccination._id = "NEWLYADDED" + Math.floor(Math.random() * 10000000);
//         delete vaccination._staged;
//         delete vaccination._administering;
//         delete vaccination._scheduling;
//         if (vaccination.administration_date) {
//             vaccination.administered = true;
//         } else {
//             vaccination.administered = false;
//         }
//         vaccinations.push(vaccination);
//         return [200, {vaccination:vaccination}, {}];

//     });

//     $httpBackend.whenPUT(/^\/vaccinations\/[a-zA-Z0-9_]+\/patients\/[a-zA-Z0-9]+$/)
//         .respond( function (method, url, data) {
//             var time = new Date().getTime() + loaderDelay;
//             while (new Date() < time) {}

//             var vaccination = angular.fromJson(data).vaccination;
//             var index = helperFunctions.findObjectIndexByAttribute('_id', vaccination._id, vaccinations);
//             if (vaccination.administration_date) {
//                 vaccination.administered = true;
//             }

//             // Get old reaction details since they aren't sent
//             var reaction_details = vaccinations[index].reaction_details;
//             vaccination.reaction_details = reaction_details;
//             vaccinations.splice(index, 1);
//             vaccinations.push(vaccination);
//             return [200, {vaccination: vaccination}, {}];
//     });

//     $httpBackend.whenDELETE(/^\/vaccinations\/[a-zA-Z0-9]+\/patients\/[a-zA-Z0-9]+$/)
//         .respond( function (method, url, data) {
//             var time = new Date().getTime() + loaderDelay;
//             while (new Date() < time) {}

//             var vaccinationId = /[a-zA-Z0-9]+(?=\/patients\/)/.exec(url)[0];
//             var index = helperFunctions.findObjectIndexByAttribute('_id', vaccinationId, vaccinations);
//             vaccinations.splice(index, 1);

//             return [200, {}, {}];
//     });

//     $httpBackend.whenPOST(/^\/vaccinations\/[a-zA-Z0-9]+\/patients\/[a-zA-Z0-9]+\/adverse_reactions$/)
//         .respond(function (method, url, data) {
//             var time = new Date().getTime() + loaderDelay;
//             while (new Date() < time) {}

//             var reaction = angular.fromJson(data).reaction;

//             var vaccinationId = /[a-zA-Z0-9]+(?=\/patients\/)/.exec(url)[0];
//             var index = helperFunctions.findObjectIndexByAttribute('_id', vaccinationId, vaccinations);

//             var vaccination = vaccinations[index];
//             vaccination.reaction_details = reaction;
//             vaccination.reaction_details._id = "NEWLYADDED" + Math.floor(Math.random() * 10000000);
//             vaccination.adverse_reaction_observed = true;
//             vaccination.reaction_details._vaccination_id = vaccination._id;

//             return [200, {vaccination: vaccination}, {}];
//     });

//     $httpBackend.whenPUT(/^\/vaccinations\/[a-zA-Z0-9]+\/patients\/[a-zA-Z0-9]+\/adverse_reactions\/[0-9a-zA-Z]+$/)
//         .respond(function (method, url, data) {
//             var time = new Date().getTime() + loaderDelay;
//             while (new Date() < time) {}

//             var reaction = angular.fromJson(data).reaction;

//             var vaccinationId = /[a-zA-Z0-9]+(?=\/patients\/)/.exec(url)[0];
//             var index = helperFunctions.findObjectIndexByAttribute('_id', vaccinationId, vaccinations);
//             var vaccination = vaccinations[index];
//             vaccination.reaction_details = reaction;
//             vaccination.adverse_reaction_observed = true;

//             return [200, {vaccination: vaccination}, {}];
//     });

//     $httpBackend.whenDELETE(/^\/vaccinations\/[a-zA-Z0-9]+\/patients\/[a-zA-Z0-9]+\/adverse_reactions\/[0-9a-zA-Z]+$/)
//         .respond( function (method, url, data) {

//             var time = new Date().getTime() + loaderDelay;
//             while (new Date() < time) {}

//             var vaccinationId = /[a-zA-Z0-9]+(?=\/patients\/)/.exec(url)[0];
//             var index = helperFunctions.findObjectIndexByAttribute('_id', vaccinationId, vaccinations);
//             var vaccination = vaccinations[index];
//             vaccination.adverse_reaction_observed = false;
//             delete vaccination.reaction_details;
//             return [201, {vaccination: vaccination}, {}];
//         })

//     // Do not serve anything from the mock server on these routes.
//     $httpBackend.whenGET(/\/?mock_data\/.+/).passThrough();
//     $httpBackend.whenGET(/\/?app\/.+/).passThrough();
// });
// // Manually bootstrap the backend.
// angular.element(document).ready(function () {
//     angular.bootstrap(document, ['mockBackend']);
// });
