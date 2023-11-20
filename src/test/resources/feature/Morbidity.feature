@Morbidity
Feature: Morbidity Controller

Scenario Outline: Create a token for Dietician and Patient
Given User creates token as Dietician and patient 
When User sends HTTP request with request body from the excel "<sheetName>" and <rollNum>
Then User receives 200 status

Examples:
|sheetName|rollNum|
|userToken|0|

Scenario: Verify user able to retrieve all morbidities with valid endpoint
Given User was authorized by token to make dietician request
When User sends HTTP request to get all morbidity
Then Verify user receives 200 status

Scenario Outline: Get all morbility with TestName <morbidityTestName>
Given User was authorized by token to make dietician request
When User sends HTTP request for morbidity with "<morbidityTestName>"
Then Verify user receives 200 status
	And Verify morbidity response data with TestName

Examples:
|morbidityTestName|
|TSH|
|Blood Pressure Levels|
|T4|
|T3|
|HbA1c|
|Plasma Glucose|
|Average Glucose|
|Fasting Glucose|

Scenario: Verify user unable to retrieve the morbidity condition by invalid Test name
Given User was authorized by token to make dietician request
When User sends HTTP request for morbidity with "TSH67678"
Then Verify user receives 404 status
	And Verify morbidity response data with invalid TestName