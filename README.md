# Guides

1. Import the attached zip to Intelij/ide.

2. Run the application.

3. Use below link to install MySQL DB configured in application.properties file:

    https://dev.mysql.com/doc/mysql-installation-excerpt/5.7/en/
    
 # APIs invocation using postman
 
 1. for Shortening API use below curl:
 
 curl --location --request PUT 'http://localhost:8080/shortenUrl' \
 --header 'Content-Type: application/json' \
 --data-raw '{
    "originalLongURL": "https://www.tremorvideo.com/"
 }'
 
 originalLongURL value provided as reference.
 
 2. redirect:
 
 curl --location --request GET 'http://localhost:8080/redirectUrl/U'
 
 path variable provided as reference and can be replaced according to Shortening API response
 
 3. Analytics:
 
 curl --location --request GET 'http://localhost:8080/shortenedUrlStatistics/U' 
 
 path variable provided as reference and can be replaced according to Shortening API response
 
 