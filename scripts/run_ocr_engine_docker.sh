docker stop ocr-engine
docker run -d --restart unless-stopped --name ocr-engine --network=container:ocr-api mile-ocr-engine
