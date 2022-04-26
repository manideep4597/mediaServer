/**
 * Copyright (c) OCEANEERING INTERNATIONAL, Inc. 2021 - 2025 All Rights Reserved
 * <p>
 * This file contains confidential and proprietary information. Any use of this code, including 
 * reproduction, modification, distribution or republication, without the prior written consent
 * of OCEANEERING INTERNATIONAL, Inc., is strictly prohibited.
 */

package com.oi.oceanperception.offshorevideostreaming.uitls;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oi.oceanperception.offshorevideostreaming.constants.OPConstants;
import com.oi.oceanperception.offshorevideostreaming.constants.SysConstants;
import com.oi.oceanperception.offshorevideostreaming.data.StreamDetails;
import com.oi.oceanperception.offshorevideostreaming.service.OceanPerception;

/**
 * The Class OpThread.
 */
public class OpThread implements Runnable {

    /** The Constant Log. */
    private static final Logger Log = LoggerFactory.getLogger(OpThread.class);

    /** The Constant properties. */
    private static final Properties properties;

    /** The ocean perception. */
    private OceanPerception oceanPerception;
    static {
        properties = new Properties();

        try {
            ClassLoader classLoader = OpThread.class.getClassLoader();
            InputStream applicationPropertiesStream = classLoader.getResourceAsStream(SysConstants.APP_PROPS);
            properties.load(applicationPropertiesStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** The stream details. */
    private StreamDetails streamDetails;

    /** The thread. */
    private Thread thread;

    /**
     * Instantiates a new op thread.
     *
     * @param streamDetails   the stream details
     * @param oceanPerception the ocean perception
     */
    public OpThread(StreamDetails streamDetails, OceanPerception oceanPerception) {
        this.oceanPerception = oceanPerception;
        this.streamDetails = streamDetails;
        Log.debug("Creating Thread for : {}", streamDetails.getStreamName());
    }

    /**
     * Run.
     */
    @Override
    public void run() {
        Log.debug("Running Thread for Stream: {}", streamDetails.getStreamName());
        String operSys = System.getProperty("os.name").toLowerCase();
        if (operSys.contains("win")) {
            runWindows();
        } else if (operSys.contains("nix") || operSys.contains("nux")
                || operSys.contains("aix")) {
            runLinux();
        }
        else {
            Log.debug("Running Thread for Stream: {}", streamDetails.getStreamName());
        }
    }
    private void runLinux(){
        Process process;
        String stream = streamDetails.getStreamApplicationName();

        String output = properties.getProperty(SysConstants.APP_PROPS_RTMP_LINK) + ":"
                + properties.getProperty(SysConstants.APP_PROPS_RTMP_PORT) + "/"
                + streamDetails.getStreamApplicationName() + "/" + streamDetails.getStreamName();

        ProcessBuilder processBuilder = new ProcessBuilder("ffmpeg",
                "-hide_banner", "-loglevel", properties.getProperty(SysConstants.FFMPEG_DEBUG_LEVEL, "warning"), "-i",
                streamDetails.getStreamRtspUrl(), "-codec:v", "h264", "-an","-tune", "zerolatency", "-b:v",
                "2M", "-maxrate", "4M", "-bufsize", "1M", "-f", "flv", output);
        try {
            long dateString = new Date().getTime();
            Path outPutFile = Paths.get(properties.getProperty(SysConstants.APP_PROPS_LOGS_FOLDER),
                    streamDetails.getStreamApplicationName() + "_" + streamDetails.getStreamName() + "_"
                            + SysConstants.OUTPUT + "_" + dateString + SysConstants.TXT_FORMAT);
            Path errorFile = Paths.get(properties.getProperty(SysConstants.APP_PROPS_LOGS_FOLDER), SysConstants.ERROR
                    + "_" + streamDetails.getStreamName() + "_" + dateString + SysConstants.TXT_FORMAT);
            Files.deleteIfExists(outPutFile);
            Files.deleteIfExists(errorFile);
            Files.createDirectories(Paths.get(properties.getProperty(SysConstants.APP_PROPS_LOGS_FOLDER)));
            processBuilder.redirectErrorStream(true);
            processBuilder.redirectError(errorFile.toFile());
            processBuilder.redirectOutput(outPutFile.toFile());
            process = processBuilder.start();
            streamDetails.setStreamStatus(OPConstants.OP_STREAM_STATUS_ACTIVE);
            streamDetails.setStreamUrlRelativePath(
                    streamDetails.getStreamApplicationName() + "/" + streamDetails.getStreamName());
            oceanPerception.updateStreams(streamDetails);
            Integer returnCode = process.waitFor();
            Log.debug("Stream id {} got : {} return code ", streamDetails.getId(), returnCode);
            if (returnCode > 0) {
                streamDetails.setStreamStatus(OPConstants.OP_STREAM_STATUS_EXCEPTION);
            } else {
                streamDetails.setStreamStatus(OPConstants.OP_STREAM_STATUS_INACTIVE);
            }
            oceanPerception.updateStreams(streamDetails);
        } catch (InterruptedException e) {
            Log.error("Interrupted while running Query for Stream: {}", streamDetails.getId());
            streamDetails.setStreamStatus(OPConstants.OP_STREAM_STATUS_INACTIVE);
            oceanPerception.updateStreams(streamDetails);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            streamDetails.setStreamStatus(OPConstants.OP_STREAM_STATUS_EXCEPTION);
            oceanPerception.updateStreams(streamDetails);
            Log.error("Error while running Query Stream: {}", streamDetails.getId());
        }
    }
    private void runWindows(){
        Process process;
        String stream = streamDetails.getStreamName();
        String output = properties.getProperty(SysConstants.APP_PROPS_RTMP_LINK) + ":"
                + properties.getProperty(SysConstants.APP_PROPS_RTMP_PORT) + "/"
                + streamDetails.getStreamApplicationName() + "/" + streamDetails.getStreamName();

        ProcessBuilder processBuilder = new ProcessBuilder("docker.exe", "run", "--rm", "--name", stream,
                properties.getProperty(SysConstants.APP_PROPS_FFMPEG_IMAGE, SysConstants.FFMPEG_DEFAULT_IMAGE),
                "-hide_banner", "-loglevel", properties.getProperty(SysConstants.FFMPEG_DEBUG_LEVEL, "warning"), "-i",
                streamDetails.getStreamRtspUrl(), "-codec:v", "h264", "-an","-tune", "zerolatency", "-b:v",
                "2M", "-maxrate", "4M", "-bufsize", "1M", "-f", "flv", output);
        try {
            long dateString = new Date().getTime();
            Path outPutFile = Paths.get(properties.getProperty(SysConstants.APP_PROPS_LOGS_FOLDER),
                    streamDetails.getStreamApplicationName() + "_" + streamDetails.getStreamName() + "_"
                            + SysConstants.OUTPUT + "_" + dateString + SysConstants.TXT_FORMAT);
            Path errorFile = Paths.get(properties.getProperty(SysConstants.APP_PROPS_LOGS_FOLDER), SysConstants.ERROR
                    + "_" + streamDetails.getStreamName() + "_" + dateString + SysConstants.TXT_FORMAT);
            Files.deleteIfExists(outPutFile);
            Files.deleteIfExists(errorFile);
            Files.createDirectories(Paths.get(properties.getProperty(SysConstants.APP_PROPS_LOGS_FOLDER)));
            processBuilder.redirectErrorStream(true);
            processBuilder.redirectError(errorFile.toFile());
            processBuilder.redirectOutput(outPutFile.toFile());
            process = processBuilder.start();
            streamDetails.setStreamStatus(OPConstants.OP_STREAM_STATUS_ACTIVE);
            streamDetails.setStreamUrlRelativePath(
                    streamDetails.getStreamApplicationName() + "/" + streamDetails.getStreamName());
            oceanPerception.updateStreams(streamDetails);
            Integer returnCode = process.waitFor();
            Log.debug("Stream id {} got : {} return code ", streamDetails.getId(), returnCode);
            if (returnCode > 0) {
                streamDetails.setStreamStatus(OPConstants.OP_STREAM_STATUS_EXCEPTION);
            } else {
                streamDetails.setStreamStatus(OPConstants.OP_STREAM_STATUS_INACTIVE);
            }
            oceanPerception.updateStreams(streamDetails);
        } catch (InterruptedException e) {
            Log.error("Interrupted while running Query for Stream: {}", streamDetails.getId());
            streamDetails.setStreamStatus(OPConstants.OP_STREAM_STATUS_INACTIVE);
            oceanPerception.updateStreams(streamDetails);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            streamDetails.setStreamStatus(OPConstants.OP_STREAM_STATUS_EXCEPTION);
            oceanPerception.updateStreams(streamDetails);
            Log.error("Error while running Query Stream: {}", streamDetails.getId());
        }
    }
    /**
     * Start.
     */
    public void start() {
        Log.debug("Starting Thread of Stream: {}", streamDetails.getStreamName());
        if (thread == null) {
            thread = new Thread(this, streamDetails.getStreamName());
            thread.start();
        }
    }

    /**
     * Stop.
     */
    public void stop() {
        Log.debug("Stopping Thread of Stream: {}", streamDetails.getStreamName());
        Thread.currentThread().interrupt();
    }

    public Long getThreadId() {

        return Thread.currentThread().getId();
    }

}
