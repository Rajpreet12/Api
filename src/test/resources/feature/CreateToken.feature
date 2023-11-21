Feature: Dietician Project

@token
Scenario Outline: Create a token for Dietician and Patient
Given User creates token as Dietician and patient 
When User sends HTTP request with request body from the excel "<sheetName>" and <rollNum>
Then User receives 200 status

Examples:
|sheetName|rollNum|
|userToken|0|
|userToken|1|

Scenario: Create patient by dietician
Given User was authorized by "token" 
When User sends HTTP request with  request payload and under form-Data
Then User receives 200 status

Scenario: Get request to fetch all patients
Given User was authorized by "token"
When user sends HTTP request with valid endpoints "endpoints" for all patients
Then User receives 200 status

Scenario: Update patient by UserId

Given User was authorized by "token" 
When User sends HTTP request with  request payload and under form-Data with updated patient info
Then User receives 200 status



Scenario Outline: Get Patients Morbidity Details and Retrieve Patient file by FileId As Dietician
Given User was authorized by "token"
When user sends HTTP request with valid endpoints "<endpoints>" for morbidity "<morbidityType>" with "token"
Then User receives 200 status

Examples:
|endpoints|morbidityType|
|/patient/testReports/|patient_Id|
|patient/testReports/viewFile/|fileId|


Scenario Outline: Get Patients Morbidity Details and Retrieve Patient file by FileId As Patient
Given User was authorized by "token" as PAtient
When user sends HTTP request with valid endpoints "<endpoints>" for morbidity "<morbidityType>" with "token" as PAtient
Then User receives 200 status

Examples:
|endpoints|morbidityType|
|/patient/testReports/|patient_Id|
|patient/testReports/viewFile/|fileId|


Scenario: Delete Patient by UserId
Given User was authorized by "token"
When user sends HTTP request with valid endpoints "endpoints" for IserId to delete the patient
Then User receives 200 status


Scenario: Get all patients and delete all patients
Given User was authorized by "token"
When User fetch all patients and delete all patients
Then User receives 200 status

Scenario: User logout  
Given User was authorized by "token"
When user sends HTTP request with valid endpoints "endpoints"
Then User receives 200 status




