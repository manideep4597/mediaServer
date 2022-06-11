/**
 * Copyright (c) OCEANEERING INTERNATIONAL, Inc. 2021 - 2025 All Rights Reserved
 * <p>
 * This file contains confidential and proprietary information. Any use of this code, including 
 * reproduction, modification, distribution or republication, without the prior written consent
 * of OCEANEERING INTERNATIONAL, Inc., is strictly prohibited.
 */
package com.oi.oceanperception.offshorevideostreaming.repository;

import com.oi.oceanperception.offshorevideostreaming.entity.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Interface StreamRepository.
 */
@Repository
public interface StreamRepository extends JpaRepository<Stream, Long> {

    /**
     * Find by id.
     *
     * @param id the id
     * @return the stream
     */
    Stream findByStreamId(long id);

}
