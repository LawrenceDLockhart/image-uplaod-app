# Image Upload App

This project is a simple CRUD application using Vaadin Flow. The application allows users to upload and image with a description and delete it later.

## Getting Started

### Prerequisites

Ensure you have the following installed:
- Java Development Kit (JDK) 11 or later
- Maven

### Running the Application

This is a standard Maven project. To run it from the command line:

1. **Build and Run**:
   ```sh
   ./mvnw clean install
   ./mvnw spring-boot:run
2. **Import to IDE:**
    - You can also import the project to your IDE of choice as you would with any Maven project.

### Deploying to Production
To create a production build:
1. **Build:**
    ```sh
   ./mvnw clean package -Pproduction
   ```
    - This will build a JAR file with all dependencies and front-end resources, ready to be deployed. The file can be found in the target folder after the build completes.
2. **Run**
   ```sh
   java -jar target/your-app-1.0-SNAPSHOT.jar


### Useful Links
- [Vaadin Documentation](https://vaadin.com/docs)
- [Vaadin Start Page](https://start.vaadin.com/)
- [Vaadin GitHub Repository](https://github.com/vaadin)
