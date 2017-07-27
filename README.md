# Royalty Manager

## Frameworks and tools used:
- IntelliJ IDEA
- Maven 3.5.0
- Spring Boot
- Dozer Mapping
- H2 in memory DB
- Jersey
- Spring Security (2 users created: user/user and admin/admin)
- Testing: JUnit4, Mockito, Hamcrest
- Angular CLI for setting up and building the UI project
- Angular 2 for the UI project

## Running the app:
### API Server
- By using Spring Boot Maven plugin: mvn spring-boot:run

## UI App
- By angular-cli: ng sever (Make sure to install by npm install -g angular-cli)
- Access to http://localhost:4200


## Improvements:
### Backend
- Use JPA and hibernate for transactionality and Database Mapping

### FrontEnd
- Packaging for the royalty-manager-ui module
- Test FE by Jasmine framework
- Styling SCSS or SASS

### Testing
- API tests and E2E by any framkework such as JBehave
