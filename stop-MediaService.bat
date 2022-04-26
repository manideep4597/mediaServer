FOR /f "tokens=*" %%i IN ('docker ps -a -q --filter "ancestor=jrottenberg/ffmpeg"') DO docker stop %%i
echo "Stoped and Deleted RUNNING FFMPEG CONTAINERS"
FOR /f "tokens=1" %%i in ('jps -m ^| find "offshore-video-streaming-0.0.1-SNAPSHOT.jar"') do taskkill /F /PID %%i 
echo "Terminated Running Offshore Media Tool JAVA Service"
FOR /f "tokens=*" %%i IN ('docker ps -a -q --filter "ancestor=airensoft/ovenmediaengine"') DO docker stop %%i && docker rm %%i
echo "Stoped and Deleted RUNNING OvenMedia CONTAINERS"