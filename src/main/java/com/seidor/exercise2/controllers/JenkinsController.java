package com.seidor.exercise2.controllers;

import com.seidor.exercise2.models.Build;
import com.seidor.exercise2.services.JenkinsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@Api
@RequestMapping("/api")
@Validated
public class JenkinsController {

    @Autowired
    private JenkinsService jenkinsService;

    @PostMapping(value = "/builds/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Creates new build",
                    notes = "User should provide a valid Build object",
                    response = ResponseEntity.class)
    @ApiResponse(responseCode = "201", description = "Build created succesfully")
    public @ResponseBody ResponseEntity<Build> createNewBuild(@Valid @RequestBody Build newBuild) {

        Build build;
        try {

          build = this.jenkinsService.createNewBuild(newBuild);

        } catch (ResponseStatusException statusException) {
            throw statusException;
        } catch (IOException ioException) {
            return ResponseEntity.internalServerError().body(null);
        } catch (InterruptedException interruptedException){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(build);
    }

}
