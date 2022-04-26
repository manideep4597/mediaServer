/**
 * Copyright (c) OCEANEERING INTERNATIONAL, Inc. 2021 - 2025 All Rights Reserved
 * <p>
 * This file contains confidential and proprietary information. Any use of this code, including 
 * reproduction, modification, distribution or republication, without the prior written consent
 * of OCEANEERING INTERNATIONAL, Inc., is strictly prohibited.
 */
package com.oi.oceanperception.offshorevideostreaming.data;

/**
 * The Class StreamDetails.
 */
public class StreamDetails {
    /** The application name. */
    private String streamApplicationName;

    /** The relative path. */
    private String streamUrlRelativePath;

    /** The rtsp url. */
    private String streamRtspUrl;

    /** The status. */
    private String streamStatus;

    /** The stream id. */
    private Integer id;

    /** The stream name. */
    private String streamName;

    /**
     * Gets the stream application name.
     *
     * @return the stream application name
     */
    public String getStreamApplicationName() {
        return streamApplicationName;
    }

    /**
     * Sets the stream application name.
     *
     * @param streamApplicationName the new stream application name
     */
    public void setStreamApplicationName(String streamApplicationName) {
        this.streamApplicationName = streamApplicationName;
    }

    /**
     * Gets the stream url relative path.
     *
     * @return the stream url relative path
     */
    public String getStreamUrlRelativePath() {
        return streamUrlRelativePath;
    }

    /**
     * Sets the stream url relative path.
     *
     * @param streamUrlRelativePath the new stream url relative path
     */
    public void setStreamUrlRelativePath(String streamUrlRelativePath) {
        this.streamUrlRelativePath = streamUrlRelativePath;
    }

    /**
     * Gets the stream rtsp url.
     *
     * @return the stream rtsp url
     */
    public String getStreamRtspUrl() {
        return streamRtspUrl;
    }

    /**
     * Sets the stream rtsp url.
     *
     * @param streamRtspUrl the new stream rtsp url
     */
    public void setStreamRtspUrl(String streamRtspUrl) {
        this.streamRtspUrl = streamRtspUrl;
    }

    /**
     * Gets the stream status.
     *
     * @return the stream status
     */
    public String getStreamStatus() {
        return streamStatus;
    }

    /**
     * Sets the stream status.
     *
     * @param streamStatus the new stream status
     */
    public void setStreamStatus(String streamStatus) {
        this.streamStatus = streamStatus;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the stream name.
     *
     * @return the stream name
     */
    public String getStreamName() {
        return streamName;
    }

    /**
     * Sets the stream name.
     *
     * @param streamName the new stream name
     */
    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }
}
