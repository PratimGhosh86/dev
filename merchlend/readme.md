# generated using <https://start.spring.io/>

## build dependencies

- java 8u191
- maven 3.5

## Endpoint

- Swagger: <https://localhost:8443/swagger-ui.html>
- H2 console: <https://localhost:8443/h2-console>

For H2 connection, use the following JDBC URL: `jdbc:h2:mem:testdb`

## self-signed certificate generation

keytool -genkeypair -alias localhost -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 36500

## run the aplication

./mvnw spring-boot:run -DskipTests
