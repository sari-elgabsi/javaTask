package com.service;

import com.model.URL;
import com.model.URLResponse;
import com.model.URLStatistics;
import org.springframework.stereotype.Service;

@Service
public interface IURLShorteningService {

    String toBase62(long id);

    long fromBase62(String shortURL);

    String generateShortenedUrl(URL requestURL);

    String getOriginalUrl(String shortendURL);

    URLStatistics getShortenedUrlStatistics(String shortendURL);

    void validateUrl(URL requestURL);

}
