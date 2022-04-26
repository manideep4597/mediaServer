/**
 * Copyright (c) OCEANEERING INTERNATIONAL, Inc. 2021 - 2025 All Rights Reserved
 * <p>
 * This file contains confidential and proprietary information. Any use of this code, including 
 * reproduction, modification, distribution or republication, without the prior written consent
 * of OCEANEERING INTERNATIONAL, Inc., is strictly prohibited.
 */

package com.oi.oceanperception.offshorevideostreaming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The Class OffshoreVideoStreamingApplication.
 */
@SpringBootApplication
@EnableScheduling
public class OffshoreVideoStreamingApplication {

    /**
     * The main method.
     *
     * @param args the arguments
     */

    public static void main(String[] args) {

        SpringApplication.run(OffshoreVideoStreamingApplication.class, args);
    }

}
