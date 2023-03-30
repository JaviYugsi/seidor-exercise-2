package com.seidor.exercise2.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.seidor.exercise2.models.Build;

import java.io.IOException;
import java.net.http.HttpResponse;

public interface JenkinsService {
    Build createNewBuild(Build newBuild) throws IOException, InterruptedException;
}
