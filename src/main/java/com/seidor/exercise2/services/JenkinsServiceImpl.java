package com.seidor.exercise2.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seidor.exercise2.dao.BuildDao;
import com.seidor.exercise2.models.Build;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class JenkinsServiceImpl implements JenkinsService {

    private final int SUCCESSFUL_CREATION_CODE = 201;

    @Value("${host}")
    private String host;

    @Value("${content-type}")
    private String contentType;

    private final String TAIL = "job/";
    private String urlAPI;

    @Autowired
    private BuildDao buildDao;

    @PostConstruct
    public void init() {
        this.urlAPI = this.host.concat(TAIL);
    }

    @Override
    public Build createNewBuild(Build newBuild) throws IOException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(newBuild);

        //Starting the HttpClient
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlAPI))
                .header("Content-Type", contentType)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != SUCCESSFUL_CREATION_CODE)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request was not successful");

        //We call to the persistence layer to store the data historic info
        return this.buildDao.save(newBuild);
    }
}
