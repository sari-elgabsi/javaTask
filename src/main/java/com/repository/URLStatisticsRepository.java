package com.repository;

import com.model.URLStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface URLStatisticsRepository extends JpaRepository<URLStatistics, Long> {

    URLStatistics findByshortenedURLAndDate (String shortenedURL, LocalDate localDate );

}
