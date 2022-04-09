Feature: User wants to manage my todo list

  Background:
    Given User is on Landing Page
    When User enters item in todo list
      | Jogging             |
      | Reply to emails     |
      | Pay utility bills   |
      | Order grocery       |
      | Prepare PPT         |

Scenario: Adding todo item in input field
Given No Todo items are present
When User enters todo items
Then Entered Item appears in All and Active ToDo list
And No item should be present in Completed list
And Items left count should be equal to count of entered input


Scenario: Completing todo item from list
Given All active todo item present in todo list
When User clicks on checkbox to complete toDo item
Then Checked items should be completed in All and Completed tab
And Clear completed option is enabled


Scenario: Changing status from Completed to Active
Given User clicks on checkbox to complete toDo item
When User deselects a completed item
Then Deselected item should become active in All and Active tab


Scenario: Checking items left field
Given All active todo item present in todo list
And Items left count should be equal to count of entered input
When User clicks on checkbox to complete toDo item
Then Items left count should become 0


Scenario Outline: Checking clear completed functionality
Given user completes "<itemToComplete>" toDo item
When User clicks on Clear completed
Then Completed items should be removed from All and Completed list
And Count of active item left should remain same
Examples:
|itemToComplete|
|Jogging       |
|Prepare PPT   |
|Order grocery |


Scenario: Testing Select All and complete functionality
Given All active todo item present in todo list
When User clicks on toggleAll button
Then All items should be completed in All tab
And Items left count should become 0


Scenario: Testing DeSelect All functionality
Given User clicks on checkbox to complete toDo item
When User clicks on toggleAll button
Then All items should be active in All tab
And Items left count should be equal to count of entered input

