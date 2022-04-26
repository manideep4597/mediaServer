/**
 * Copyright (c) OCEANEERING INTERNATIONAL, Inc. 2021 - 2025 All Rights Reserved
 * <p>
 * This file contains confidential and proprietary information. Any use of this code, including 
 * reproduction, modification, distribution or republication, without the prior written consent
 * of OCEANEERING INTERNATIONAL, Inc., is strictly prohibited.
 */
package com.oi.oceanperception.offshorevideostreaming.data;

/**
 * The Class AuthResponse.
 */
public class AuthResponse {

    /** The access token. */
    private String accessToken;

    /** The token type. */
    private String tokenType;

    /**
     * Gets the access token.
     *
     * @return the access token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets the access token.
     *
     * @param accessToken the new access token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Gets the token type.
     *
     * @return the token type
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * Sets the token type.
     *
     * @param tokenType the new token type
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
