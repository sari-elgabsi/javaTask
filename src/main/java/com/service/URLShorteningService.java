package com.service;

import com.exception.NoDataFoundException;
import com.model.URL;
import com.model.URLStatistics;
import com.repository.URLRepository;
import com.repository.URLStatisticsRepository;
import com.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class URLShorteningService implements IURLShorteningService {

    @Autowired
    URLStatisticsRepository urlStatisticsRepository;

    @Autowired
    public URLRepository urlRepository;

    @Override
    public String toBase62(long id) {
        char map[] = Constants.CHARS_MAP.toCharArray();
        StringBuffer base62 = new StringBuffer();
        while (id > 0)
        {
            base62.append(map[(int) (id % 62)]);
            id = id / 62;
        }

        return base62.reverse().toString();
    }

    @Override
    public long fromBase62(String shortURL) {
        long id = 0;

        for (int i = 0; i < shortURL.length(); i++)
        {
            if ('a' <= shortURL.charAt(i) &&
                    shortURL.charAt(i) <= 'z') {
                id = id * 62 + shortURL.charAt(i) - 'a';
            }
            if ('A' <= shortURL.charAt(i) &&
                    shortURL.charAt(i) <= 'Z') {
                id = id * 62 + shortURL.charAt(i) - 'A' + 26;
            }
            if ('0' <= shortURL.charAt(i) &&
                    shortURL.charAt(i) <= '9') {
                id = id * 62 + shortURL.charAt(i) - '0' + 52;
            }
        }
        return id;
    }

    @Override
    public String generateShortenedUrl(URL requestURL) {
        URL url = new URL();
        url.setOriginalLongURL(requestURL.getOriginalLongURL());
        String shortenedUrl = Constants.DOMAIN.concat(toBase62(urlRepository.save(url).getId()));

        return shortenedUrl;
    }

    @Override
    public String getOriginalUrl(String shortendURL) {
        long id = fromBase62(shortendURL);
        Optional<URL> urlById = urlRepository.findById(id);
        urlById.orElseThrow(() -> new NoDataFoundException());
        handleShortenedUrlStatistics(shortendURL, urlById);

        return urlById.get().getOriginalLongURL();
    }

    private void handleShortenedUrlStatistics(String shortendURL, Optional<URL> urlById) {
        shortendURL = Constants.DOMAIN.concat(shortendURL);

        updateShortenedUrlStatistics(shortendURL);
    }

    private void updateShortenedUrlStatistics(String shortenedURL) {
        URLStatistics URLStatistics = urlStatisticsRepository.findByshortenedURLAndDate(shortenedURL, LocalDate.now());

        if(URLStatistics == null) {
            URLStatistics = new URLStatistics();
            URLStatistics.setDate(LocalDate.now());
            URLStatistics.setViewCounter(1);
            URLStatistics.setShortenedURL(shortenedURL);
        } else {
            URLStatistics.setViewCounter(URLStatistics.getViewCounter() + 1);
        }
        urlStatisticsRepository.save(URLStatistics);
    }

    @Override
    public URLStatistics getShortenedUrlStatistics(String shortendURL) {
        URLStatistics URLStatistics = urlStatisticsRepository.findByshortenedURLAndDate(Constants.DOMAIN.concat(shortendURL), LocalDate.now() );

        if(URLStatistics == null) {
            throw new NoDataFoundException();
        }
        return URLStatistics;
    }

    @Override
    public void validateUrl(URL requestURL) {
        if(requestURL == null || (requestURL != null && requestURL.getOriginalLongURL().isEmpty()) ||
                ((requestURL != null && !requestURL.getOriginalLongURL().isEmpty()) &&
                        !isValidUrl(requestURL.getOriginalLongURL()))) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isValidUrl(String url) {
            Pattern patt = Pattern.compile(Constants.REGEX);
            Matcher matcher = patt.matcher(url);

            return matcher.matches();
    }
}
