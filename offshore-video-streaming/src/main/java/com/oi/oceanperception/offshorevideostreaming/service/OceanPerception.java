/**
 * Copyright (c) OCEANEERING INTERNATIONAL, Inc. 2021 - 2025 All Rights Reserved
 * <p>
 * This file contains confidential and proprietary information. Any use of this code, including 
 * reproduction, modification, distribution or republication, without the prior written consent
 * of OCEANEERING INTERNATIONAL, Inc., is strictly prohibited.
 */
package com.oi.oceanperception.offshorevideostreaming.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oi.oceanperception.offshorevideostreaming.constants.OPConstants;
import com.oi.oceanperception.offshorevideostreaming.data.AuthRequest;
import com.oi.oceanperception.offshorevideostreaming.data.AuthResponse;
import com.oi.oceanperception.offshorevideostreaming.data.StreamDetails;
import com.oi.oceanperception.offshorevideostreaming.exceptions.OpRestServiceException;
import com.oi.oceanperception.offshorevideostreaming.uitls.OpThread;

/**
 * The Class OceanPerception.
 */
@Component
public class OceanPerception {

    /** The Constant Log. */
    private static final Logger Log = LoggerFactory.getLogger(OceanPerception.class);

    /** The auth response. */
    private AuthResponse authResponse;

    /** The Instance url. */
    @Value("${op.rest.services.instance.url}")
    private String InstanceUrl;

    /** The username. */
    @Value("${op.rest.services.username}")
    private String username;

    /** The password. */
    @Value("${op.rest.services.password}")
    private String password;

    /** The rest template. */
    private RestTemplate restTemplate;

    /** The stream thread map. */
    private ConcurrentHashMap<Integer, Long> streamThreadMap = new ConcurrentHashMap<>();

    /** The accepted status. */
    private final HashSet<String> acceptedStatus = new HashSet<String>() {
        {
            add("ACTIVE");
            add("INACTIVE");
            add("EXCEPTION");
        }
    };

    @EventListener(ContextClosedEvent.class)
    public void onContextClosedEvent(ContextClosedEvent contextClosedEvent) {
        Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
        for (Thread thread : setOfThread) {
            if (streamThreadMap.containsValue(thread.getId())) {
                thread.interrupt();

            }
        }
    }

    /**
     * Creates the object mapper.
     *
     * @return the object mapper
     */
    private ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

        return objectMapper;
    }

    /**
     * Gets the rest template.
     *
     * @return the rest template
     */
    private RestTemplate getRestTemplate() {
        if (Objects.isNull(restTemplate)) {
            restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(0, createMappingJacksonHttpMessageConverter());
        }
        return restTemplate;
    }

    /**
     * Creates the mapping jackson http message converter.
     *
     * @return the mapping jackson 2 http message converter
     */
    private MappingJackson2HttpMessageConverter createMappingJacksonHttpMessageConverter() {

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(createObjectMapper());
        return converter;
    }

    /**
     * Authorize.
     *
     * @return the auth response
     */
    public AuthResponse authorize() {
        restTemplate = new RestTemplate();
        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword(password);
        authRequest.setUsernameOrEmail(username);
        String url = InstanceUrl + OPConstants.OP_AUTH_PATH;
        HttpEntity<AuthRequest> entity = new HttpEntity<>(authRequest, getHttpHeaders());
        authResponse = restTemplate.exchange(url, HttpMethod.POST, entity, AuthResponse.class).getBody();
        return authResponse;
    }

    /**
     * Gets the http headers with token.
     *
     * @return the http headers with token
     */
    public HttpHeaders getHttpHeadersWithToken() {
        HttpHeaders headers = new HttpHeaders();
        if (Objects.isNull(authResponse)) {
            authorize();
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(authResponse.getAccessToken());
        return headers;
    }

    /**
     * Gets the http headers.
     *
     * @return the http headers
     */
    public HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    /**
     * Update streams.
     *
     * @param streamDetails the stream details
     */
    public void updateStreams(StreamDetails streamDetails) {
        HttpEntity<StreamDetails> entity = new HttpEntity<>(streamDetails, getHttpHeadersWithToken());
        String url = InstanceUrl + OPConstants.OP_UPDATE_STREAM + streamDetails.getId();
        ResponseEntity<StreamDetails> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.PUT, entity, StreamDetails.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                Log.debug("Stream with Id {} got Updated with Status {}", streamDetails.getId(),
                        streamDetails.getStreamStatus());
            } else {
                Log.error("Stream with Id {} got Exception while updating", streamDetails.getId());
                throw new OpRestServiceException(
                        String.format("Stream with Id %s got Exception while updating", streamDetails.getId()));
            }
        } catch (RestClientResponseException e) {
            if (e.getRawStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
                authorize();
                entity = new HttpEntity<>(getHttpHeadersWithToken());
                response = restTemplate.exchange(url, HttpMethod.PUT, entity, StreamDetails.class);
                if (response.getStatusCode().equals(HttpStatus.OK)) {
                    Log.debug("Stream with Id {} got Updated with Status {}", streamDetails.getId(),
                            streamDetails.getStreamStatus());
                } else {
                    Log.error("Stream with Id {} got Exception while updating", streamDetails.getId());
                    throw new OpRestServiceException(
                            String.format("Stream with Id %s got Exception while updating", streamDetails.getId()));
                }
            } else {
                Log.error(e.toString());
                throw new RuntimeException(e.toString());
            }
        }

    }

    /**
     * Do streaming frequently.
     *
     * @throws Exception the exception
     */
    @Scheduled(fixedDelayString = "${op.streams.refresh.milliseconds}")
    public void doStreamingFrequently() throws Exception {
        HttpEntity<Void> entity = new HttpEntity<>(getHttpHeadersWithToken());
        String url = InstanceUrl + OPConstants.OP_GET_ALL_STREAMS;
        ResponseEntity<StreamDetails[]> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, StreamDetails[].class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                Log.debug("Get All Streams from OP Rest Service");
            } else {
                Log.error(" Exception while fetching Streams from OP Rest Service");
                throw new OpRestServiceException(" Exception while fetching Streams from OP Rest Service");
            }
        } catch (RestClientResponseException e) {
            if (e.getRawStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
                authorize();
                entity = new HttpEntity<>(getHttpHeadersWithToken());
                response = this.restTemplate.postForEntity(url, entity, StreamDetails[].class);
                if (response.getStatusCode().equals(HttpStatus.OK)) {
                    Log.debug("Get All Streams from OP Rest Service");
                } else {
                    Log.error(" Exception while fetching Streams from OP Rest Service");
                    throw new OpRestServiceException(" Exception while fetching Streams from OP Rest Service");
                }
            } else {
                throw new OpRestServiceException(e.toString());
            }
        }

        List<StreamDetails> streamDetailsList = Arrays.asList(response.getBody());
        streamDetailsList.forEach(streamDetails -> {
            if (acceptedStatus.contains(streamDetails.getStreamStatus())) {
                if (!streamDetails.getStreamStatus().equals("ACTIVE")) {
                    OpThread opThread = new OpThread(streamDetails, this);
                    opThread.start();
                    streamThreadMap.put(streamDetails.getId(), opThread.getThreadId());
                }
            } else {
                if (streamThreadMap.containsKey(streamDetails.getId())) {
                    Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
                    for (Thread thread : setOfThread) {
                        if (thread.getId() == streamThreadMap.get(streamDetails.getId())) {
                            thread.interrupt();
                            streamThreadMap.remove(streamDetails.getId());
                        }
                    }
                }
            }
        });

    }

}
