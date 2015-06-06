'use strict';

describe('Service: vaccinationsManager', function() {
    beforeEach(module('vaccinations', 'mockData'));

    var vaccinationsManager, httpBackend, mockVaccinationsData, mockVaccination, mockObjects, mockVaccine, vaccinations, appConstants;

    beforeEach(inject(function(_vaccinationsManager_, _mockObjects_, $httpBackend, _appConstants_){
        appConstants = _appConstants_;
        mockObjects = _mockObjects_;
        vaccinationsManager = _vaccinationsManager_;
        httpBackend = $httpBackend;

        // Create a mock responses and backend.
        mockVaccinationsData = mockObjects;
        mockVaccination = mockObjects.vaccinations[0];
        mockVaccine = mockObjects.non_scheduled_vaccines[0];
        httpBackend.whenGET(/\/?vaccinations\/patients\/1/).respond(mockVaccinationsData);
        httpBackend.whenPOST(/\/?vaccinations\/patients\/1/).respond('200 OK');
    }));

    beforeEach( function () {
        appConstants.setPatiendId('1');
    });

    beforeEach(function (done) {
        vaccinationsManager.getVaccinations().success( function (data) {
            vaccinations = data.vaccinations;
            done();
        });
        httpBackend.flush();
    });


    it('should automatically set an array of vaccination objects on instantiation', function () {
        expect(vaccinations.length).toBe(10);
    });

    it('should remove a vaccination from the array based on id', function(){
        var idOfVaccinationToRemove = mockVaccination._id;
        vaccinationsManager.removeVaccination(idOfVaccinationToRemove);
        expect(vaccinations.length).toBe(9);
        expect(vaccinationsManager.getVaccinationById(idOfVaccinationToRemove)).not.toBeDefined();
    });

    it('should add a new vaccination object to the array if its _id attribute is unique', function(){
        mockVaccination._id = mockVaccination._id + '1';
        vaccinationsManager.addVaccination(mockVaccination);
        expect(vaccinations.length).toBe(11);
    });

    it('should reject a new vaccination from being added if its _id attribute is not unique', function() {
        expect( function () {vaccinationsManager.addVaccination(mockVaccination); }).toThrow(
            new Error('Could not add vaccination to array, a vaccination with the _id attribute already exists.'));
        expect(vaccinations.length).toBe(10);
    });

    it('should create a POST request when submitting a vaccination without an _id attribute.', function () {
        //for (var i = 0; i < vaccinations.length; i++) {
        //    if (!vaccinations[i].hasOwnProperty('_id')) {
        //        var vaccs = vaccinations[i];
        //        var vaccsCopy = angular.copy(vaccinations[i]);
        //        break;
        //    }
        //}


        //httpBackend.expectPOST('/vaccinations/patients/' + appConstants.getPatientId());
        //console.log(vaccs);
        //vaccinationsManager.submitVaccination(vaccs, vaccsCopy);
        //httpBackend.flush();
    });

    it('should provide an array for staged vaccinations to be added.', function () {
        vaccinationsManager.addStagedVaccination(mockVaccine);
    })
});