# ocr-api
REST API for MILE IISc's OCR Engine

## Load sub-modules
```
$ git submodule update --init
```

## Build Docker image
```
$ docker build -t mile-ocr-api .
$ docker images
```

## Run container
```
$ docker run -d --name ocr-api -p 9080:9080 -p 9443:9443 mile-ocr-api
$ docker ps
```

## Launch REST API Swagger UI

Open http://localhost:9080/openapi/ui/ in your browser


## Stop container and remove
```
$ docker stop ocr-api
$ docker rm ocr-api
```
