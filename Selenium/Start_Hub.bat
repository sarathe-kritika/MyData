cd "C:\Selenium"

start cmd /k java -Dwebdriver.chrome.driver=chromedriver.exe -jar selenium-server-standalone-3.5.3.jar -role node -hub http://localhost:4444/grid/register

java -jar selenium-server-standalone-3.5.3.jar -role hub -hubConfig hub.json