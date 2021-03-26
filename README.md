# MILE-IISc OCR REST API

MILE-OCR-API has runtime dependency on [MILE-OCR-Engine](https://github.com/MILE-IISc/MILE-OCR-Engine.git) and compile-time dependency on [MILE-OCR-Model](https://github.com/MILE-IISc/MILE-OCR-Model.git). Hence it requires additional steps for build and deploy as shown below.

## Build MILE-OCR-Engine and MILE-OCR-API Docker images

### Install git lfs
Some of the files in MILE-OCR-Engine are big and requires Git LFS to be [installed](https://github.com/git-lfs/git-lfs/wiki/Installation)
```
$ curl -s https://packagecloud.io/install/repositories/github/git-lfs/script.deb.sh | sudo bash
$ sudo apt-get install git-lfs
$ git lfs install
```

### Clone the MILE-OCR-Engine repository and build its Docker image
```
$ cd ..
$ git clone https://github.com/MILE-IISc/MILE-OCR-Engine.git
$ cd MILE-OCR-Engine/
$ docker build -t mile-ocr-engine .
```

### Init sub-modules and pull latest changes
```
$ cd ../MILE-OCR-API
$ git submodule update --init
$ git submodule update --recursive
$ git submodule foreach git pull origin master
```

### Build MILE-OCR-API Docker image
```
$ docker build -t mile-ocr-api .
$ docker images
```

## Run MILE-OCR-Engine and MILE-OCR-API containers in the same network namespace

### Run MILE-OCR-API container
```
$ docker run -d --name ocr-api -p 9080:9080 -p 9443:9443 mile-ocr-api
$ docker ps
```

### Run MILE-OCR-Engine container in the same network namespace as MILE-OCR-API
```
$ docker run -d --name ocr-engine --network=container:ocr-api mile-ocr-engine
```

## Launch REST API Swagger UI

Open http://localhost:9080/openapi/ui/ in your browser


## Stop container and remove
```
$ docker stop ocr-api
$ docker rm ocr-api
```

## References
* [Sharing Network Namespaces in Docker](https://blog.mikesir87.io/2019/03/sharing-network-namespaces-in-docker/)
