# smec
Coding Challenge Attila Both for Smarter Ecommerce

Back-end runs on http://localhost:8088/

H2-Console is available: http://localhost:8088/h2-console/
(for user and pwd see application.properties)

Retrieve all Accounts: http://localhost:8088/api/accounts
Retrieve all Events: http://localhost:8088/api/events

Add Account: http://localhost:8088/api/addAccount?accountName=ATTILA

Add Event to account: http://localhost:8088/api/addEventToAccount

body example:
{
"accountName":"Attila",
"eventName":"Synchronization Started"
}