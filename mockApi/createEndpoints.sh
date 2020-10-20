curl -v -X PUT "http://localhost:1080/mockserver/expectation" -d '{
  "httpRequest" : {
    "method" : "POST",
    "path" : "/auth/signin"
  },
  "httpResponse" : {
    "body" : "{
  \"credentials\": {
    \"site\": {
      \"id\": \"9a8b7c6d5-e4f3-a2b1-c0d9-e8f7a6b5c4d\",
      \"contentUrl\": \"teste\"
    },
    \"user\": {
      \"id\": \"9f9e9d9c-8b8a-8f8e-7d7c-7b7a6f6d6e6d\"
      },
    \"token\": \"12ab34cd56ef78ab90cd12ef34ab56cd\"
    }
}",
    "statusCode": 200
  }}'


  curl -v -X PUT "http://localhost:1080/mockserver/expectation" -d '{
  "httpRequest" : {
    "method" : "GET",
    "path" : "/data"
  },
  "httpResponse" : {
  "body" : "cluster,domain_name,intent,locale,Measure Names,Time Level,Max. utterance_id_datetime_utc,Measure Values,Min. utterance_id_datetime_utc
na,Domain,Intent,en_US,# Turns,Date Range A: 6/25 - 6/25,6/25/2020,3,6/25/2020
na,Domain,Intent,en_US,Metric,Date Range A: 6/25 - 6/25,6/25/2020,0.5,6/25/2020
na,Domain,Intent,en_US,%GSR,Date Range A: 6/25 - 6/25,6/25/2020,0.5,6/25/2020
na,Domain,Intent,en_US,%ASRER,Date Range A: 6/25 - 6/25,6/25/2020,0,6/25/2020",
    "statusCode": 200
  }}'


