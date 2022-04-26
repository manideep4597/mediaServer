/**
 * Copyright (c) OCEANEERING INTERNATIONAL, Inc. 2021 - 2025 All Rights Reserved
 * <p>
 * This file contains confidential and proprietary information. Any use of this code, including 
 * reproduction, modification, distribution or republication, without the prior written consent
 * of OCEANEERING INTERNATIONAL, Inc., is strictly prohibited.
 */
package com.oi.oceanperception.offshorevideostreaming.exceptions;

/**
 * The Class OpRestServiceException.
 */
public class OpRestServiceException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7292396996678999851L;

    /** The message. */
    private String message;

    /**
     * Instantiates a new OPAPI exception.
     *
     * @param message the message
     */
    public OpRestServiceException(String message) {
        this.message = message;
    }

    /**
     * Instantiates a new OPAPI exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public OpRestServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    @Override
    public String getMessage() {
        return message;
    }
}
