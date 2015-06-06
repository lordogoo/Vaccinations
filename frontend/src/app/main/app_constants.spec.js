'use strict';

describe('Service: appConstants', function() {
    beforeEach(module('vaccinations'));

    var appConstants;

    beforeEach(inject(function(_appConstants_){
        appConstants = _appConstants_;
    }));

    it('should extract the patient id from the url', function () {
        var patientId = appConstants.getPatientId('http://www.test.com/abc/1');
        expect(patientId).toBe('1');
    });

    it('should throw an error if no patient id can be parsed', function () {
        expect(function () { appConstants.getPatientId('http://www.test.com/abc/'); }).toThrow(
            new Error('The patient ID cannot be parsed from the URL'));
    });
});