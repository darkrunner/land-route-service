#Instructions
### *Prerequisites*
- [Java](https://www.oracle.com/java/technologies/downloads/#java17)

### *Build the application*
 To build the application execute the following command: `./mvnw clean install`

### *Run the application*
 To run the application execute the following command:`./mvnw spring-boot:run`

### *Build & Run the application*
To build and run the application on one go execute the following command:`./mvnw clean install spring-boot:run`

### *Example command line call*
   ```
   Request: 
        curl http://localhost:8080/routing/CZE/ITA
   Response: 
        {"route":["CZE","AUT","ITA"]}
  ```