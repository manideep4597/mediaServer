FOR /f "tokens=*" %%i IN ('docker ps -a -q --filter "ancestor=jrottenberg/ffmpeg"') DO docker stop %%i
echo "Stoping and deleting the Existing Containers Completed"