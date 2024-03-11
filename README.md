# Rest Assured Cucumber Exercise
This project is an exercise about API testing with a mock API.
It is developed using Java, Cucumber, Rest Assured and JUnit. 
The purpose of this mini project is to put into practice these mentioned technologies. 

## Features

- Feature 1: Client testing (GET and POST)
- Feature 2: Resource testing (GET and PUT) 


## Installation

To run this project locally, follow these steps:

1. Clone the repository.
2. Install Java Development Kit (JDK) if not already installed.
3. Install a compatible IDE, such as IntelliJ or Eclipse.
4. Install Cucumber plugin for the IDE.
5. Install Maven as the build tool.
6. Run the following command to install project dependencies:

    `mvn install`

## Usage

To use this project, follow these steps:

1. Open the project in your preferred IDE.
2. Follow the Installation steps describe in the previous section.
3. Execute the _TestRunner_ class that is in _test/java/com/testing/api/runner/_ directory.
4. You can also execute a specific feature or scenario from the feature files and using the IDE. The path of these files is _test/resources/features/_
5. Verify the tests execution in the terminal.

## Configuration

The project can be configured by modifying the following files:

- `src/test/resources/log4j2.properties`: logger configuration.
- `src/main/com/testing/api/utils/Constants.java`: project configuration data such as: base url, endpoints and header configuration.
- `src/test/resources/schemas/*.json`: schemas of the HTTP responses defined using JSON. 
- `src/test/java/com/testing/api/stepDefinitions/Hooks.java`: printing in console of starting scenario message and configuration of the base URL.

## Testing

To run the tests for this project, execute the Maven lifecycle test phase or execute the _TestRunner_ file.
