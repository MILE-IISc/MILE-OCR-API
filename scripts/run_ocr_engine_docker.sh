docker stop ocr-engine
docker run -d --rm --name ocr-engine --network=container:ocr-api mile-ocr-engine
