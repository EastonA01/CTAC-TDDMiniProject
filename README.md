# CTAC-SpringBoot TDD Project

## Running the project

- Clone the repository onto your local machine
  - This can be done by either downloading the repo as a .zip file and unpacking it or if using IntelliJ/VSCode
    into your desired directory via command line: ```gh repo clone EastonA01/CTAC-TDDMiniProject```
- Navigate to the Main class (named CtacTddMiniProjectApplication)
  - (If running through IntelliJ) you can right click the class in the project files and select "Run".
  - (Command Line Option) in the root of our project folder you can run ```mvn spring-boot:run``` if all dependencies
    are properly installed

## Testing Routes
- /orders
  - POST Method:
    - ```http://localhost:8080/orders```
      - Enter in postman the following data in RAW JSON:
        - ```json
          {
          "customerName" : "John Doe",
          "orderDate" : "2024-11-09",
          "shippingAddress" : "123 Test Lane",
          "total" : "14.99"
          }
          ``` 
      - You should receive a 200 response with the object with it's corresponding id.
  - GET Method:
    - ```http://localhost:8080/orders/{id}```
    - Response will be the requested object or an error if none found
      - PUT Method:
        - ```http://localhost:8080/orders/{id}``` Where {id} is the ID of the order we generated
        - Place the following RAW JSON:
          - ```json
            {
            "customerName" : "Jane Doe",
            "orderDate" : "2024-11-09",
            "shippingAddress" : "1234 Test Drive",
            "total" : "20.00"
            }
            ```
    - DELETE Method:
        - ```http://localhost:8080/orders/{id}``` Where {id} is the ID of the order we generated
        - A 204 Response is received if no content (success) or 404 if not found.