package edu.example.demoentra.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {
    private static final String QUESTION = "Which sports do you like most?";
    private final Map<LocalDateTime, String> surveys = new LinkedHashMap<>();


    @GetMapping(value = "/question", produces = MediaType.APPLICATION_JSON_VALUE)
    public String question(){
        return QUESTION;
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('SCOPE_Survey.User')")
    public ResponseEntity<String> addSurvey(@RequestBody String answer){
        if(StringUtils.hasText(answer)){
            surveys.put(LocalDateTime.now(), answer);
            return ResponseEntity.ok("The response has been noted");
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_Survey.Admin')")
    public Map<LocalDateTime, String> getSurveys(){
        return surveys;
    }
}
