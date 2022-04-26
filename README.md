# Video Stream Server

This project implements the video stream server and related plugins/applications and scripts.

## Install Maven
 Download apache-maven-3.8.3-bin.zip from https://maven.apache.org/download.cgi and unzip to C:\Program Files.
 Add bin folder to path.

 ## Install Docker Windows
 Download Docker Desktop Installer.exe from https://docs.docker.com/desktop/windows/install/ and Install 

 ## Install Docker Linux

Commands to Install Docker in Linux

sudo apt-get update
 
sudo apt-get install \
    ca-certificates \
    curl \
    gnupg \
    lsb-release
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

sudo apt-get update

sudo apt-get install docker-ce docker-ce-cli containerd.io

## START Application

run-MediaServer-{profile}.bat for Windows
run-MediaServer-{profile}.sh for Linux


PORTS Used :
    - "1935/tcp" //RTMP
    - "3333/tcp" // WebRTC
    - "3478/tcp" // TCP Relay
    - "10006-10010/udp" // ICE Candidates
    - "8089/tcp"  // Java Plugin
