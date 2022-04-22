Problem One - ATM
=================
This is a solution to the task "PROBLEM_1_ATM.md" - CLI  to simulate an interaction of an ATM with retail bank.

### Build jar
`mvn compile assembly:single`

Result: target/console_atm.jar

### Run program
`java -jar console_atm.jar` or using IDE - just run `com.okabanov.Application.main`

### Run unit tests
`mvn tests`

Solution Analysis
=================
### Libraries
Project **doesn't use any foreign libraries for runtime**. But will be good idea to use:
* spring-shell - for CLI suggestions and validations
* spring - for DI, if the application will have huge amount of services

### Improvements
* The `deposit` command must select the target account if there are two or more debts
* The `login` command should also perform authentication (with password and storing SESSION_ID)
* Services must send CRUD operations to the bank server
* Application must provide a unique ID for each request to avoid repeating the request over the network (mobile operators amy repeat the full request)