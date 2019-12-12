@echo off

echo detected java home @ %JAVA_HOME%
echo.
echo using maven wrapper to resolve depedencies
echo.

.\mvnw.cmd dependency:resolve -Dclassifier=javadoc
.\mvnw.cmd dependency:sources

echo.
echo dependency resolution complete
echo.
choice /C X /D X /T 5 /M "auto-exiting after 5 seconds. press X to exit now"

exit