FROM maven:3.6.3-jdk-8-slim AS build

COPY OCR_Model/src /home/OCR_Model/src
COPY OCR_Model/pom.xml /home/OCR_Model
RUN cd /home/OCR_Model && mvn clean install

COPY src /home/api/src
COPY pom.xml /home/api
RUN cd /home/api && mvn clean install

# https://docs.docker.com/samples/library/open-liberty/
FROM open-liberty:kernel-java8-openj9

# Add my app and config
COPY --chown=1001:0 --from=build /home/api/target/liberty/wlp/usr/servers/defaultServer/apps/mile-iisc-ocr-api.war /config/apps/
COPY --chown=1001:0 --from=build /home/api/target/liberty/wlp/usr/servers/defaultServer/server.xml /config/server.xml
COPY --chown=1001:0 --from=build /home/api/target/liberty/wlp/usr/servers/defaultServer/bootstrap.properties /config/bootstrap.properties
COPY --chown=1001:0 --from=build /home/api/target/liberty/wlp/usr/servers/defaultServer/mpopenapi/customization.css /config/mpopenapi/customization.css
COPY --chown=1001:0 --from=build /home/api/target/liberty/wlp/usr/servers/defaultServer/mpopenapi/images/custom-logo.png /config/mpopenapi/images/custom-logo.png
