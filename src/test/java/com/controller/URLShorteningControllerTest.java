package com.controller;


import com.model.URL;
import com.model.URLResponse;
import com.model.URLStatistics;
import com.service.URLShorteningService;
import com.util.ConstantsTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
public class URLShorteningControllerTest {

    private URL url;

    @Mock
    private URLShorteningService urlShorteningService;

    @Mock
    private HttpServletResponse httpServletResponse;

    @InjectMocks
    private URLShorteningController urlShorteningController;

    @Before
    public void init () {
        url = new URL();
        url.setOriginalLongURL(ConstantsTest.MOCK_ORIGINAL_URL);
    }

    @Test
    public void createShortenedURLSuccess() {
        when(urlShorteningService.generateShortenedUrl(Mockito.any(URL.class))).thenReturn(ConstantsTest.MOCK_SHORTENED_URL);
        ResponseEntity<URLResponse>  response = urlShorteningController.createShortenedURL(url);
        Assert.assertTrue(response.getStatusCode() == HttpStatus.CREATED);
        Assert.assertEquals(response.getBody().getShortenedURL(), ConstantsTest.MOCK_SHORTENED_URL );
    }

    @Test
    public void createShortenedURLValidateNotNull() {
        when(urlShorteningService.generateShortenedUrl(Mockito.any(URL.class))).thenReturn(ConstantsTest.MOCK_SHORTENED_URL);
        ResponseEntity<URLResponse>  response = urlShorteningController.createShortenedURL(url);
        Assert.assertNotNull(response.getBody().getShortenedURL());
    }

    @Test
    public void getShortenedUrlStatisticsSuccess() {
        URLStatistics urlStatistics = new URLStatistics(1, ConstantsTest.MOCK_SHORTENED_URL, 1, LocalDate.now());
        when(urlShorteningService.getShortenedUrlStatistics(ConstantsTest.MOCK_SHORTENED_URL)).thenReturn(urlStatistics);
        ResponseEntity<URLStatistics>  response = urlShorteningController.getShortenedUrlStatistics(ConstantsTest.MOCK_SHORTENED_URL);
        Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
        Assert.assertEquals(response.getBody().getShortenedURL(), urlStatistics.getShortenedURL());
        Assert.assertEquals(response.getBody().getDate(), urlStatistics.getDate());
        Assert.assertEquals(response.getBody().getId(), urlStatistics.getId());
        Assert.assertEquals(response.getBody().getViewCounter(), urlStatistics.getViewCounter());
    }
}
