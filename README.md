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



===== CODING CHALLENGE =====
Please, build an application based on Spring Boot that allows to manage accounts and collect events for an account. 

The application clients are other applications that manage accounts and events through an API. We expect to have around 100 accounts and roughly 1000 events per account and day.

Accounts can be listed, created and updated. Clients should only have to provide an account name. Events can be listed and added, but neither updated, nor deleted.

Example data:

Account(d: 4711, name: Account #1)
Event(happenedAt: 2019-09-23 15:03:01, type: Randomly chosen by client,

e.g. "Synchronization Started", "Data Imported")
Event(happenedAt: 2019-09-23 15:03:02, type: Randomly chosen by client,

e.g. "Synchronization Started", "Data Imported") ...

Account statistics for a single account can be requested through the API. The statistics show the number of events aggregated per day and type, e.g.

day, type, count
2019-09-23, Synchronization Started, 123 2019-09-23, Data Imported, 18
...

Events should be deleted after 30 days, but not vanish from the statistical data.

Non-Functional Requirements

The application should be as simple as possible, but production ready
It can be built and tested on our CI server (our CI server can do command line calls and capture the log output)
The API is tested and tests are executable by developers and the CI server
The application is ready to be deployed to our Kubernetes Cluster
No API security is required
Take your own decisions on API design
Deliver the full application code project in a remote repository or as .zip file
Deliver the application in a structure so that other developers can easily run the application and execute the tests
It is explicitly not required at the moment, but we would like to ask

 How we need to change the API or architecture if there would be 1000 accounts with 10000 events per day ? If changes are required, please describe the changed architecture and implications. 
 How would you scale-up the system  ?
 How would you Monitor the application ?
==============================
