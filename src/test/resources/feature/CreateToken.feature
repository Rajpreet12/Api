Feature: Dietician Project

Scenario Outline: Create a token for Dietician and Patient
Given User creates token as Dietician and patient 
When User sends HTTP request with request body from the excel "<sheetName>" and <rollNum>
Then User receives 200 status

Examples:
|sheetName|rollNum|
|userToken|0|
#|userToken|1|

Scenario: Create patient by dietician
Given User was authorized by "token" 
When User sends HTTP request with  request payload and under form-Data
Then User receives 200 status

Scenario: Get request to fetch all patients
Given User was authorized by "token"
When user sends HTTP request with valid endpoints "endpoints"
Then User receives 200 status