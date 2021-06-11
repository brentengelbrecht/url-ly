# url-ly
A URL shortening service

## To run tests

`mvn test`

## To run locally

`mvn spring-boot:run`

URL home is at `http://localhost:8080`

## Usage

### To get a shortName

POST a request to the service: 

E.g. 

```
{
    "url": "http://www.google.com"
}
```

### To redirect to a URL

Request a GET passing the shortName: 

E.g. http://localhost:8080/g

### To get stats about a shortName

Request a GET passing shortName and `info` keyword

E.g. http://localhost:8080/g/info
