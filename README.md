# ocr-api
REST API for MILE IISc's OCR Engine

## Build Docker image
```
$ docker build -t ocr-api .
$ docker images
```

## Run container
```
$ docker run -d --name ocr-api -p 9080:9080 -p 9443:9443 ocr-api
$ docker ps
```

## Stop container and remove
```
$ docker stop ocr-api
$ docker rm ocr-api
```
