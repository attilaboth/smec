# Coding Challenge Attila Both for Smarter Ecommerce

In case of questions please contact me atts.both@gmail.com

Java version: 9

After cloning the project to your IntelliJ IDE execute: 
1. mvn clean package
2. mvn spring-boot:run
3. alternatively open terminal cd into target dir, then execute: java -jar smec-0.0.1-SNAPSHOT.jar


Back-end runs on http://localhost:8088/

H2-Console is available: http://localhost:8088/h2-console/
(for user and pwd see application.properties)

Retrieve all Accounts: http://localhost:8088/api/accounts
Retrieve all Events: http://localhost:8088/api/events

Add Account: http://localhost:8088/api/addAccount?accountName=ACCOUNTNAME

Add Event to account: http://localhost:8088/api/addEventToAccount

body example:
{
"accountName":"ACCOUNTNAME",
"eventName":"Synchronization Started"
}

Get statistical data for an account: http://localhost:8088/api/statForAccount?accountName=ACCOUNTNAME

Get LAST_30_DAYS statistical data for an account: http://localhost:8088/api/statisticForAccountForLastDays?accountName=ACCOUNTNAME

# Challenge description
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

-The application should be as simple as possible, but production ready
-It can be built and tested on our CI server (our CI server can do command line calls and capture the log output)
-The API is tested and tests are executable by developers and the CI server
-The application is ready to be deployed to our Kubernetes Cluster
-No API security is required
-Take your own decisions on API design
-Deliver the full application code project in a remote repository or as .zip file
-Deliver the application in a structure so that other developers can easily run the application and execute the tests


# --- It is explicitly not required at the moment, but we would like to ask ---

A.How we need to change the API or architecture if there would be 1000 accounts with 10000 events per day ? If changes are required, please describe the changed architecture and implications. 
- If we increase the number of threads accessing the endpoints then I would implement a Caching mechanism and also use batch insert into DB. In this case I also have to change the Entity ID Generation from (strategy = GenerationType.IDENTITY) to something else, because this type is not supporting batch insert with hibernate. 

B.How would you scale-up the system  ?
- I would use Docker + Kubernetes. Definitely a cloud based environment for my Microservice. Then here are availbale autoscaling profiles in place already. When load is getting higher, Kubernetes recognizes this. Kubernetes knows about the application instances, monitors their loads, and automatically scale up and down.

C.How would you Monitor the application ?
- same answer as above.

==============================

# Evaluation: 
Among the positive aspects, I can mention the usage of general layering pattern, easy to ready and clean code, usage of Lombok and good usage of Spring/Spring Boot.

Among the less positive aspects, I can refer to a debatable usage of DTO pattern, no implementation of the db cleanup, usage of LocalDateTime, no Dockerfile, no K8s deployments, and all events are delivered with an account so that they are all loaded into memory. Those are just some exemplifying points.

