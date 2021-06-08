docker stop ocr-api
docker run -d --rm --name ocr-api -p 9080:9080 -p 9443:9443 mile-ocr-api
