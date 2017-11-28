REM taskkill /IM cmd.exe /F /T
java -jar C:/JAR/selenium-server-standalone-2.53.0.jar  -Dwebdriver.chrome.driver="C:\JAR\chromedriver.exe" --port 4444
pause
REM java -jar C:/JAR/selenium-server-standalone-2.53.0.jar --port 4444 -role hub

