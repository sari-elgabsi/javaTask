package com.model;


import javax.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Data
@Entity
@Table(name = "shortened_url_statistics")
public class URLStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "shortenedURL")
    private String shortenedURL;

    @Column(name = "viewCounter")
    private int viewCounter;

    @Column(name = "date")
    private LocalDate date;

    public URLStatistics(long id, String shortenedURL, int viewCounter, LocalDate date) {
        this.id = id;
        this.shortenedURL = shortenedURL;
        this.viewCounter = viewCounter;
        this.date = date;
    }

    public URLStatistics() {}
}
