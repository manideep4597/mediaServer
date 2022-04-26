/**
 * Copyright (c) OCEANEERING INTERNATIONAL, Inc. 2021 - 2025 All Rights Reserved
 * <p>
 * This file contains confidential and proprietary information. Any use of this code, including 
 * reproduction, modification, distribution or republication, without the prior written consent
 * of OCEANEERING INTERNATIONAL, Inc., is strictly prohibited.
 */

package com.oi.oceanperception.offshorevideostreaming.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class OvmResponseDto.
 */
public class OvmResponseDto {

    /** The allowed. */
    private Boolean allowed;

    /** The lifetime. */
    private Long lifetime;

    /** The new url. */
    @JsonProperty("new_url")
    private String newUrl;

    /** The reason. */
    private String reason;

    /**
     * Gets the allowed.
     *
     * @return the allowed
     */
    public Boolean getAllowed() {
        return allowed;
    }

    /**
     * Gets the lifetime.
     *
     * @return the lifetime
     */
    public Long getLifetime() {
        return lifetime;
    }

    /**
     * Gets the new url.
     *
     * @return the new url
     */
    public String getNewUrl() {
        return newUrl;
    }

    /**
     * Gets the reason.
     *
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the allowed.
     *
     * @param allowed the new allowed
     */
    public void setAllowed(Boolean allowed) {
        this.allowed = allowed;
    }

    /**
     * Sets the lifetime.
     *
     * @param lifetime the new lifetime
     */
    public void setLifetime(Long lifetime) {
        this.lifetime = lifetime;
    }

    /**
     * Sets the new url.
     *
     * @param newUrl the new new url
     */
    public void setNewUrl(String newUrl) {
        this.newUrl = newUrl;
    }

    /**
     * Sets the reason.
     *
     * @param reason the new reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }
}
