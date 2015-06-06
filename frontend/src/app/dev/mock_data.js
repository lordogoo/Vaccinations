'use strict';

angular.module('mockData', [])
.value('mockObjects', {
    vaccinations:[
    {
      "_id": "54f88ccdfb16c75e35261900",
      "_vaccine_id": "550b46047fcba390ff335aaa",
      "scheduled": true,
      "name": "Bacillus Calmette-Guerin",
      "indication_name": "Birth to 1 yo",
      "dose": 0.05,
      "dosing_unit": "ml",
      "route": "intra-dermal",
      "administered": true,
      "adverse_reaction_observed": false,
      "administration_date": "2014-10-01",
      "scheduled_date": "2014-09-05",
      "body_site_administered": "left forearm",
      "dose_number": 1,
      "manufacturer": "P. Corp.",
      "lot_number": "1000",
      "manufacture_date": "2014-01-01",
      "expiry_date": "2016-01-01"
    },
    {
      "_vaccine_id": "550b46047fcba390ff335bba",
      "scheduled": true,
      "name": "Bacillus Calmette-Guerin",
      "indication_name": "Older than 1 yo",
      "dose": 0.1,
      "dosing_unit": "ml",
      "route": "intra-dermal",
      "administered": false,
      "adverse_reaction_observed": false,
      "administration_date": "",
      "scheduled_date": "2015-09-05",
      "body_site_administered": "left forearm",
      "dose_number": 2,
    },
    {
      "_id": "54f88ccdfb16c75e35261802",
      "_vaccine_id": "550b46047fcba390ff335cca",
      "scheduled": true,
      "name": "Polio",
      "indication_name": "Birth to 2 weeks",
      "dose": 2,
      "dosing_unit": "drops",
      "route": "PO",
      "administered": true,
      "adverse_reaction_observed": false,
      "administration_date": "2014-09-01",
      "scheduled_date": "2014-09-01",
      "body_site_administered": "Site",
      "dose_number": 1,
      "manufacturer": "P. Corp.",
      "lot_number": "1000",
      "manufacture_date": "2014-01-01",
      "expiry_date": "2016-01-01"
    },
    {
      "_id": "54f88ccd6bba27070e77d3dc",
      "_vaccine_id": "4aslkjlkjaslkfjlkah234ql",
      "scheduled": false,
      "provider_id": "AAAA",
      "name": "Rotavirus",
      "dose": 0.1,
      "dosing_unit": "ml",
      "route": "",
      "administered": true,
      "adverse_reaction_observed": false,
      "administration_date": "2014-10-01",
      "scheduled_date": "2014-10-01",
      "body_site_administered": "",
      "manufacturer": "P. Corp.",
      "lot_number": "1000",
      "manufacture_date": "2014-01-01",
      "expiry_date": "2016-01-01"
    },
    {
      "_vaccine_id": "550b46047fcba390ff335fff",
      "scheduled": true,
      "name": "Measles",
      "indication_name": "9 mo",
      "dose": .5,
      "dosing_unit": "ml",
      "route": "SC",
      "administered": false,
      "adverse_reaction_observed": false,
      "administration_date": "",
      "scheduled_date": "2015-03-01",
      "body_site_administered": "right outer thigh",
      "dose_number": 1,
    },
    {
      "_id": "54f88ccddce0415f38397d33",
      "_vaccine_id": "999",
      "scheduled": false,
      "provider_id": "BBBB",
      "name": "Hepatitis B",
      "dose": 2,
      "dosing_unit": "ml",
      "route": "",
      "administered": true,
      "adverse_reaction_observed": false,
      "administration_date": "2014-10-01",
      "scheduled_date": "2014-10-01",
      "body_site_administered": "",
      "lot_number": "1000",
      "manufacture_date": "2014-01-01",
      "expiry_date": "2016-01-01"
    },
    {
      "_id": "54f88ccddce0415f38397d34",
      "_vaccine_id": 60000,
      "scheduled": false,
      "name": "Hepatitis B",
      "dose": 2,
      "dosing_unit": "ml",
      "route": "intra dermal",
      "administered": false,
      "adverse_reaction_observed": false,
      "administration_date": "",
      "scheduled_date": "2014-11-01",
      "body_site_administered": "left deltoid",
    },
    {
      "_id": "54f88ccda57c4a6954ecfe19",
      "_vaccine_id": "550b46047fcba390ff33ddda",
      "scheduled": true,
      "provider_id": "BBBB",
      "name": "Polio",
      "indication_name": "6 weeks",
      "dose": 2,
      "dosing_unit": "drops",
      "route": "PO",
      "administered": true,
      "adverse_reaction_observed": false,
      "administration_date": "2014-10-01",
      "scheduled_date": "2014-10-01",
      "body_site_administered": "left thigh",
      "dose_number": 2,
      "manufacturer": "P. Corp.",
      "lot_number": "1000",
      "manufacture_date": "2014-01-01",
      "expiry_date": "2016-01-01"
    },
    {
      "_id": "54f88ccd2b5b5bbe9ad07c59",
      "_vaccine_id": 80000,
      "scheduled": false,
      "name": "Rotavirus",
      "dose": 0.05,
      "dosing_unit": "ml",
      "route": "intra dermal",
      "administered": false,
      "adverse_reaction_observed": false,
      "administration_date": "",
      "scheduled_date": "2014-11-01",
    },
    {
      "_id": "54f88ccdefe294237482eb5f",
      "_vaccine_id": "550b4604d6f88cbkkk3587ba",
      "scheduled": false,
      "provider_id": "BBBB",
      "name": "DPT",
      "dose": 0.1,
      "dosing_unit": "ml",
      "route": "intra dermal",
      "administered": true,
      "adverse_reaction_observed": true,
      "reaction_details": {
          "_id": "458d82kd",
          "_vaccination_id":"54f88ccdefe294237482eb5f",
          "date": "2014-10-01",
          "adverse_event_description": "Something bad, but not too bad.",
          "grade": "160754",
      },
      "administration_date": "2014-10-01",
      "scheduled_date": "2014-10-01",
      "body_site_administered": "outer thigh",
      "manufacturer": "P. Corp.",
      "lot_number": "1000",
      "manufacture_date": "2014-01-01",
      "expiry_date": "2016-01-01"
    }
  ],

  non_scheduled_vaccines: [

      {
        "_vaccine_id": "550b4604d6f88cbkkk3587ba",
        "name": "Rabies",
        "scheduled": false
      },
      {
        "_vaccine_id": "550b4604d6f88cbkkk3587ba",
        "name": "DPT",
        "scheduled": false
      },
      {
        "_vaccine_id": "550b460499dc11cccf4420bb",
        "name": "Hepatitis A",
        "scheduled": false
      },
      {
        "_vaccine_id": "550b4604a8298qqqb151205d",
        "name": "Hepatitis B",
        "scheduled": false
      },
      {
        "_vaccine_id": "4aslkjlkjaslkfjlkah234ql",
        "name": "Rotavirus",
        "scheduled": false
      },
      {
        "_vaccine_id": "550b4604a829wwweb1512ccc",
        "name": "Vitamin A",
        "dose": 100000,
        "dosing_unit": "IU",
        "route": "PO",
        "indication_name": "6 mo",
        "scheduled": true
      },
      {
        "_vaccine_id": "550b4604a829wwwxxx512ccc",
        "name": "Rabies",
        "scheduled": false
      },
      {
        "_vaccine_id": "5zzz4604a829wwwxxx512ccc",
        "name": "Cholera",
        "scheduled": false
      },
      {
        "_vaccine_id": "5zzz4gg4a829wwwxxx512ccc",
        "name": "Mumps",
        "scheduled": false
      },
      {
        "_vaccine_id": "xzzz4gg4a829wwwxxx512ccc",
        "name": "Rubella",
        "scheduled": false
      },
      {
        "_vaccine_id": "xzzz4gg4ls29wwwxxx512ccc",
        "name": "Tetanus toxoid",
        "scheduled": false
      },
      {
        "_vaccine_id": "CUSTOM",
        "custom": true,
        "name": "",
        "scheduled": false,
      }

    ],

    scheduled_vaccines: [
      {
        "_vaccine_id": "550b46047fcba390ff335aaa",
        "name": "Bacillus Calmette-Guerin",
        "dose": .05,
        "dosing_unit": "ml",
        "dose_number": 1,
        "route": "Intra-dermal left forearm",
        "indication_name": "Birth to 1 yo",
        "scheduled": true
      },
      {
        "_vaccine_id": "550b46047fcba390ff335bba",
        "name": "Bacillus Calmette-Guerin",
        "dose": .1,
        "dosing_unit": "ml",
        "dose_number": 2,
        "route": "Intra-dermal left forearm",
        "indication_name": "Older than 1 yo",
        "scheduled": true
      },
      {
        "_vaccine_id": "550b46047fcba390ff335cca",
        "name": "Polio",
        "dose": 2,
        "dosing_unit": "drops",
        "dose_number": 1,
        "route": "PO",
        "indication_name": "Birth to 2 weeks",
        "scheduled": true
      },
      {
        "_vaccine_id": "550b46047fcba390ff33ddda",
        "name": "Polio",
        "dose": 2,
        "dosing_unit": "drops",
        "dose_number": 2,
        "route": "PO",
        "indication_name": "6 weeks",
        "scheduled": true
      },
      {
        "_vaccine_id": "550b46047fcba390ff33xxxa",
        "name": "Polio",
        "dose": 2,
        "dosing_unit": "drops",
        "dose_number": 3,
        "route": "PO",
        "indication_name": "10 weeks",
        "scheduled": true
      },
      {
        "_vaccine_id": "550b46047fcba390ff335ccc",
        "name": "Polio",
        "dose": 2,
        "dosing_unit": "drops",
        "dose_number": 4,
        "route": "PO",
        "indication_name": "14 weeks",
        "scheduled": true
      },
      {
        "_vaccine_id": "550b46047fcba390ff335fff",
        "name": "Measles",
        "dose": .5,
        "dosing_unit": "SC",
        "dose_number": 1,
        "route": "right upper arm",
        "indication_name": "9 mo",
        "scheduled": true
      }
    ]
})