package com.exercise.api.data.controllers;

import com.exercise.api.data.models.Teacher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Entity;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest
@ActiveProfiles("test")
public abstract class ControllerTest {
    @Value("${server.protocol}://${server.address}:${server.port}")
    String hostAddress;

    protected URI buildURI(String ...path) throws URISyntaxException {
        return new URI(hostAddress + String.join("/", path));
    }
}
