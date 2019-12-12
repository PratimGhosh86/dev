@ECHO %JAVA_HOME%
@ECHO Compiling code
@"%JAVA_HOME%\bin\javac" *.java
@ECHO Executing code
@"%JAVA_HOME%\bin\java" NameFormatter
@PAUSE
