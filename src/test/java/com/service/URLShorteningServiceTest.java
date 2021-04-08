package com.service;

import com.exception.NoDataFoundException;
import com.model.URL;
import com.model.URLStatistics;
import com.repository.URLRepository;
import com.repository.URLStatisticsRepository;
import com.util.ConstantsTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class URLShorteningServiceTest {

    @InjectMocks
    private URLShorteningService urlShorteningService;

    @Mock
    URLStatisticsRepository urlStatisticsRepository;

    @Mock
    URLRepository urlRepository;

    @Test (expected = IllegalArgumentException.class)
    public void validateUrlIllegalArgument() {
        urlShorteningService.validateUrl(null);
    }

    @Test (expected = NoDataFoundException.class)
    public void getShortenedUrlStatisticsNoDataFound() {
        when(urlStatisticsRepository.findByshortenedURLAndDate(any(String.class), any(LocalDate.class))).thenReturn(null);
        urlShorteningService.getShortenedUrlStatistics(ConstantsTest.MOCK_SHORTENED_URL);
    }

    @Test
    public void getOriginalUrlSuccess() {
        URL url = new URL(1,ConstantsTest.MOCK_ORIGINAL_URL);
        when(urlRepository.findById(any(long.class))).thenReturn(Optional.of(url));
        String originalUrl = urlShorteningService.getOriginalUrl(ConstantsTest.MOCK_SHORTENED_URL);
        Assert.assertNotNull(originalUrl);
        Assert.assertEquals(originalUrl, url.getOriginalLongURL());
    }

    @Test (expected = NoDataFoundException.class)
    public void getOriginalUrlNoDataFound() {
        when(urlRepository.findById(any(long.class))).thenReturn(Optional.empty());
        urlShorteningService.getOriginalUrl(ConstantsTest.MOCK_SHORTENED_URL);
    }

    @Test
    public void getShortenedUrlStatisticsSuccess() {
        URLStatistics urlStatisticsMock = new URLStatistics(1, ConstantsTest.MOCK_SHORTENED_URL, 1, LocalDate.now());
        when(urlStatisticsRepository.findByshortenedURLAndDate(any(String.class), any(LocalDate.class))).thenReturn(urlStatisticsMock);
        URLStatistics urlStatistics = urlShorteningService.getShortenedUrlStatistics(ConstantsTest.MOCK_SHORTENED_URL);
        Assert.assertEquals(urlStatisticsMock, urlStatistics);
        Assert.assertNotNull(urlStatistics);
    }
}
