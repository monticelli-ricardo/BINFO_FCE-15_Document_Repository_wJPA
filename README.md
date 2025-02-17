# Document Repository Application using JPA

## Overview
This application is a document repository system developed using Java and JPA, running on WildFly with a MariaDB database in Docker.

## Application functions:
- Search documents by the publication year, with user input validation
- List all documents available.
- List details from a selected document

## Architecture
- **Model:** 
   `Document.java` and `Author.java` represents the data and business logic of the application.
- **View:**
   `index.xhtml` is responsible for presenting the data to the user. Using PrimeFaces components to render the UI and some of CSS and JavasScript for styling and interactivity.
- **Controller:**
   `DocumentBean.java` serves as a controller, handling the user input and updates the **Model** and the **View** accordingly.
- **Data Access Object and Repository**
   `DocumentRepository.java` provides an abstract interface to the database, managing database operations (select document, list documents by publishing year, list all documents) for `Document.java` and `Authors.java` using JPA and MySQL.

## Setup Requirements:
- Docker
- Maven
- Java JDK 8 or above

## Application Setup

1. **Clone the respository or unzip the project file:**


2. **Build the Maven project:**

   ```
   mvn clean package wildfly:deploy
   ```

3. **Test the application:**

   ```
   http://localhost:8080/Exercise2/
   ```

4. **OPTIONAL - Load data into the Database:**

   If no data are yet present in the DB available in Docker, then load it. You have to options:
   
   **Option A:** 
   
      Load the `exercise2.dump` into the DB instance. Assume that a `mariadb` CLI program is available on the host, then just run:

      ```
      mysql -h localhost -u javaee -p javaee --protocol tcp < exercise2.dump
      ```

      with password "eeavaj". You can detect this problem if a search with id 1 outputs "No data found", and there is a corresponding error message in the logs.

   **Option B:**

      Run the following command to execute the "script" `db_dump.sql`:

      ```
      mysql -h localhost -u javaee -p javaee --protocol tcp < db_dump.sql
      ```

      with password "eeavaj". You can detect this problem if a search with id 1 outputs "No data found", and there is a corresponding error message in the logs.