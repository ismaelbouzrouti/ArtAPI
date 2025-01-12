# Project Overview

This project aims to develop a platform for students in the arts to reserve and rent various equipment for their projects and final works.

The materials range from large items like lamps, stage elements, and light panels to smaller accessories such as cables.

For the proof of concept, the application will keep track of a selection of diverse devices and accessories.

## Key Features

- **Catalog**: Students can browse products from different categories.
- **Filters**: Products can be filtered based on categories such as cables, lighting, and control panels.
- **Shopping Cart**: Students can add products to their cart and proceed to checkout for confirmation.
- **Secure Login**: The system includes a secure user registration and login system using JWT (JSON Web Tokens).
- **Admin Functionality**: Admin users can:
  - Create, edit, and delete products.
  - Check all reservations made by users.

## Technology Stack

- **Backend**: Built with Java Spring Boot.
- **Frontend**: Developed using React with TypeScript.

The core functionalities focus on renting products, with additional features to be added once the primary features are in place.

# Documentation

## Get Started

1. Clone the repo.
2. Open it in the IDE of your choice.
3. Go to `backend/art-api` and add a `.env` file with the following content:

   ```env
   MYSQL_DATABASE=yourdatabasename
   MYSQL_USER=yourdatabaseusername
   MYSQL_PASSWORD=yourdatabasepassword
   MYSQL_ROOT_PASSWORD=yourmysqlrootpassword
   PMA_HOST=MySQL # keep it like this
   PMA_USER=yourpmausername
   PMA_PASSWORD=yourpmapassword
   ```

4. In `backend/art-api/src/main/resources`, create an `application.properties` file with the following content:

   ```properties
   spring.application.name=art-api
   # Database Connection
   spring.datasource.url=jdbc:mysql://localhost:3306/yourdatabasename # (same as in your .env file)
   spring.datasource.username=yourdatabaseusername # (same as in your .env file)
   spring.datasource.password=yourdatabasepassword # (same as in your .env file)
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   # Hibernate/JPA Settings
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

   SECRET_KEY=yoursecretkey # more info below
   ```

5. To create your secret key, run the following code in Java:

   ```java
   import java.security.SecureRandom;
   import java.util.Base64;

   public class SecretKeyGenerator {
       public static void main(String[] args) {
           // Length of the secret key in bytes (32 bytes = 256 bits)
           int keyLength = 32;

           // Generate a random byte array
           byte[] secretKey = new byte[keyLength];
           SecureRandom secureRandom = new SecureRandom();
           secureRandom.nextBytes(secretKey);

           // Encode the byte array to a Base64 string
           String base64Key = Base64.getEncoder().encodeToString(secretKey);

           // Print the generated secret key
           System.out.println("Generated Secret Key: " + base64Key);
       }
   }
   ```

   Copy the output and paste it into your `application.properties` in `SECRET_KEY=`.

6. Now go back to `backend/art-api` and run the following command in the terminal:

   ```bash
   docker-compose up
   ```

   If you're using Windows, make sure you have Docker Desktop or WSL installed.

7. Run the Java application in your IDE.

8. Open another terminal, navigate to `frontend/Art-catalogue`, and run the following command:

   ```bash
   npm run dev
   ```

   Click on the link that is given or copy-paste this URL in your browser: [http://localhost:5173/](http://localhost:5173/)

## How It Works

There are 2 users you can use to log in:

- **Username**: `user`, **Password**: `12345678`
- **Username**: `admin`, **Password**: `12345678`

You can also create your own account.

### Admin

When logged in as the admin, you can:

- Create, edit, and delete products.
- View all reservations made by users.

### User

When logged in as a user (or your own account), you can:

- Browse multiple products and add them to your shopping cart.
- Experiment with the shopping cart.
- Once satisfied with your cart, you can proceed to checkout and view your reservation.

# Security Process

## Authentication Flow

1. **Login Request:**

   - The client sends a POST request to the `/login` endpoint with a username and password.
   - A `UsernamePasswordAuthenticationToken` is created using these credentials.

2. **Authentication Manager:**

   - The `UsernamePasswordAuthenticationToken` is passed to the `AuthenticationManager` for verification.
   - The `AuthenticationManager` checks the database to confirm:
     - The user exists.
     - The provided password matches the stored one.

3. **Security Context:**

   - If authentication is successful:
     - A fully populated `Authentication` object is returned.
     - This `Authentication` object is stored in the `SecurityContext` to make the user available throughout the app.

4. **JWT Generation:**
   - A JWT (JSON Web Token) is generated and sent back to the client.
   - The client includes this token in subsequent requests for authentication.

---

## Authorization Flow for Protected Endpoints

1. **Incoming Request:**

   - The client sends a request (e.g., `GET /products`) with the JWT included in the `Authorization` header.

2. **JWT Filter:**

   - The filter intercepts the request and checks for the presence of a token:
     - If the token is missing or invalid:
       - The request is considered unauthenticated and unauthorized.
     - If the token is valid:
       - The filter extracts user information from the token.

3. **Security Context Update:**

   - The user details extracted from the JWT are added back to the `SecurityContext`, ensuring that user information is available for processing the request.

4. **Controller Handling:**
   - The request is passed to the corresponding controller, where the application logic is executed to generate a response.

---

## Key Components

- **`AuthenticationManager`**

  - Responsible for verifying credentials and authenticating the user.

- **`UsernamePasswordAuthenticationToken`**

  - Represents the credentials (username and password) during authentication.

- **`SecurityContext`**

  - Holds the authenticated user's details, making them available throughout the application.

- **JWT (JSON Web Token)**

  - A token that carries user identity and claims, enabling stateless authentication.

  ## Sources

- [Data Mapper Pattern - Java Design Patterns](https://java-design-patterns.com/patterns/data-mapper/#detailed-explanation-of-data-mapper-pattern-with-real-world-examples)
- [Spring Boot Global Exception Handler](https://medium.com/@aedemirsen/spring-boot-global-exception-handler-842d7143cf2a)
- [Single Responsibility Principle - Java Design Patterns](https://java-design-patterns.com/principles/#single-responsibility-principle)
- [Best Way to Structure API Responses in Spring Boot](https://medium.com/@dulanjayasandaruwan1998/the-best-way-to-structure-api-responses-in-spring-boot-23eb6892daab)
- [JWT Resource Server - Spring Security Documentation](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html)
- [Java Bean Validation: Not Null, Empty, Blank - Baeldung](https://www.baeldung.com/java-bean-validation-not-null-empty-blank)
- [Rest-Assured Response - Baeldung](https://www.baeldung.com/rest-assured-response)
- [Response Entity in Spring - Baeldung](https://www.baeldung.com/spring-response-entity)
- [Spring Security Reference Documentation](https://docs.spring.io/spring-security/reference/)
- [Spring Security Tutorial - Toptal](https://www.toptal.com/spring/spring-security-tutorial)
- [Axios Tokens, Cookies, and Authentication - RapidAPI](https://rapidapi.com/guides/axios-tokens-cookies-auth)
- [Spring Security OAuth with JWT - Baeldung](https://www.baeldung.com/spring-security-oauth-jwt)
- [Best Way to Structure API Responses in Spring Boot (Medium)](https://medium.com/@dulanjayasandaruwan1998/the-best-way-to-structure-api-responses-in-spring-boot-23eb6892daab)
- [Develop Shopping Cart for E-Commerce App - Dev.to](https://dev.to/nilmadhabmondal/let-s-develop-shopping-cart-for-ecommerce-app-5f0h)
- [Spring Boot Global Exception Handler (Medium)](https://medium.com/@aedemirsen/spring-boot-global-exception-handler-842d7143cf2a)
- [How to Use Java DTOs to Stay Secure - Dev.to](https://dev.to/snyk/how-to-use-java-dtos-to-stay-secure-31gg)
- [How to Create a REST API Using Java Spring Boot - GeeksforGeeks](https://www.geeksforgeeks.org/how-to-create-a-rest-api-using-java-spring-boot/)
- [Spring REST Tutorials](https://spring.io/guides/tutorials/rest)
- [Video Tutorial on Spring Boot - The Dev World](https://www.youtube.com/watch?v=phs90_s0Mjk&ab_channel=TheDevWorld-bySergioLema)
- [Spring Boot Tutorial - Dan Vega](https://www.youtube.com/watch?v=KYNR5js2cXE&t=1s&ab_channel=DanVega)
- [Spring Boot Tutorial - Dan Vega](https://www.youtube.com/watch?v=66DtzkhBlSA&ab_channel=DanVega)
- [React Learn Documentation](https://react.dev/learn)
- [useEffect Hook - React Documentation](https://react.dev/reference/react/useEffect)
- [useState Hook - React Documentation](https://react.dev/reference/react/useState)
- [Learn TypeScript with React](https://react.dev/learn/typescript)
- [TypeScript Handbook - React Integration](https://www.typescriptlang.org/docs/handbook/react.html)

## AI Tools Used

- [ChatGPT](https://chatgpt.com/share/6783e2cd-4200-800e-8526-2cac28c752f3)
