/**
 * Copyright (c) OCEANEERING INTERNATIONAL, Inc. 2021 - 2025 All Rights Reserved
 * <p>
 * This file contains confidential and proprietary information. Any use of this code, including 
 * reproduction, modification, distribution or republication, without the prior written consent
 * of OCEANEERING INTERNATIONAL, Inc., is strictly prohibited.
 */
package com.oi.oceanperception.offshorevideostreaming.data;

import java.util.Objects;

/**
 * The Class AuthRequest.
 */
public class AuthRequest {

    /** The password. */
    private String password;

    /** The username or email. */
    private String usernameOrEmail;

    /**
     * Gets the username or email.
     *
     * @return the username or email
     */
    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    /**
     * Sets the username or email.
     *
     * @param usernameOrEmail the new username or email
     */
    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Equals.
     *
     * @param o the o
     * @return true, if successful
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AuthRequest that = (AuthRequest) o;
        return Objects.equals(password, that.password) && Objects.equals(usernameOrEmail, that.usernameOrEmail);
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(password, usernameOrEmail);
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "{" + "password='" + password + '\'' + ", usernameOrEmail='" + usernameOrEmail + '\'' + '}';
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
