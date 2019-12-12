@echo off

echo detected java home @ %JAVA_HOME%
echo.
echo using maven wrapper to run application (remote debugging enabled at port 8005)
echo.

./mvnw.cmd spring-boot:run -DskipTests -Drun.arguments=--spring.output.ansi.enabled=detect
