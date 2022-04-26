FOR /f "tokens=*" %%i IN ('docker ps -a -q --filter "ancestor=jrottenberg/ffmpeg"') DO docker stop %%i
echo "Deleted RUNNING FFMPEG CONTAINERS"
docker pull jrottenberg/ffmpeg
echo "PULL FFMPEG LATEST IMAGE Done"
docker-compose -f ./OvenMediaEngine/docker-compose.yml up -d prod
echo "Oven Media Server is UP and Running"
FOR /f "tokens=1" %%i in ('jps -m ^| find "offshore-video-streaming-0.0.1-SNAPSHOT.jar"') do taskkill /F /PID %%i 
echo "Terminated Running Offshore Media Tool JAVA Service"
call mvn -f offshore-video-streaming/pom.xml clean package
echo "Offshore Media Tool Packaging done"
javaw -jar -Dspring.profiles.active=prod offshore-video-streaming/target/offshore-video-streaming-0.0.1-SNAPSHOT.jar