# Document Repository Application using JPA

## Overview
This application is a document repository system developed using Java, JPA, running on WildFly with a MariaDB database in Docker.

# Application functions:
- List all documents available
- Search documents by the year of publication
- Navigation between the web pages

## Setup Requirements:
- Docker
- Maven
- Java JDK 8 or above

## Application Setup

1. **Clone or unzip the project file:**


2. **Build the Maven project:**

   ```
   mvn clean package wildfly:deploy
   ```

3. **Test the application:**

   ```
   http://localhost:8080/Exercise2
   ```

4. **OPTIONAL - Load data into the Database:**

   If no data are yet present in the DB available in Docker, then load it. You have to options:
   
   Option A: 
   
      Load the `DB.dump` into the DB instance. Assume that a `mariadb` CLI program is available on the host, then just run:

      ```
      mariadb -h localhost -u javaee -p javaee < DB.dump
      ```

      with password "eeavaj". You can detect this problem if a search with id 1 outputs "No data found", and there is a corresponding error message in the logs.

   Option B:

      Run the following script:
      ```
      mysql -h localhost -u javaee -p javaee --protocol tcp < db_dump.sql
      ```
      with password "eeavaj". You can detect this problem if a search with id 1 outputs "No data found", and there is a corresponding error message in the logs.