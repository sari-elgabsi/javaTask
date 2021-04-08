package com.controller;

import com.model.URL;
import com.model.URLResponse;
import com.model.URLStatistics;
import com.service.URLShorteningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class URLShorteningController {

    @Autowired
    URLShorteningService urlShorteningService;

    @PutMapping ("/shortenUrl")
    public ResponseEntity<URLResponse> createShortenedURL(@RequestBody URL requestURL) {
        urlShorteningService.validateUrl(requestURL);
        URLResponse urlResponse = new URLResponse();
        urlResponse.setShortenedURL(urlShorteningService.generateShortenedUrl(requestURL));

        return new ResponseEntity<>(urlResponse, HttpStatus.CREATED);
    }

    @GetMapping(path = "/redirectUrl/{shortenedURL}")
    public void redirectURL(@PathVariable String shortenedURL, HttpServletResponse response) throws IOException {

        response.sendRedirect(urlShorteningService.getOriginalUrl(shortenedURL));
    }

    @GetMapping(path = "/shortenedUrlStatistics/{shortenedURL}")
    public ResponseEntity<URLStatistics> getShortenedUrlStatistics (@PathVariable String shortenedURL) {

        return new ResponseEntity<>(urlShorteningService.getShortenedUrlStatistics(shortenedURL), HttpStatus.OK);
    }
}
