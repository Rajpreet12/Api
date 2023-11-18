Feature: Dietician Project

Scenario Outline: Create a token for Dietician and Patient
Given User creates token as Dietician and patient 
When User sends HTTP request with request body from the excel "<sheetName>" and <rollNum>
Then User receives 201 created status

Examples:
|sheetName|rollNum|
|userToken|0|
|userToken|1|