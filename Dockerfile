FROM mileiisc/maven-with-openliberty-dependencies AS build

COPY MILE-OCR-Model/src /home/mile/ocr-model/src
COPY MILE-OCR-Model/pom.xml /home/mile/ocr-model
RUN cd /home/mile/ocr-model && mvn clean install

COPY src /home/mile/api/src
COPY pom.xml /home/mile/api
RUN cd /home/mile/api && mvn clean install

# https://docs.docker.com/samples/library/open-liberty/
FROM open-liberty:full-java11-openj9

# Add my app and config
COPY --chown=1001:0 --from=build /home/mile/api/target/liberty/wlp/usr/servers/defaultServer/apps/MILE-OCR-API.war /config/apps/
COPY --chown=1001:0 --from=build /home/mile/api/target/liberty/wlp/usr/servers/defaultServer/server.xml /config/server.xml
COPY --chown=1001:0 --from=build /home/mile/api/target/liberty/wlp/usr/servers/defaultServer/bootstrap.properties /config/bootstrap.properties
COPY --chown=1001:0 --from=build /home/mile/api/target/liberty/wlp/usr/servers/defaultServer/mpopenapi/customization.css /config/mpopenapi/customization.css
COPY --chown=1001:0 --from=build /home/mile/api/target/liberty/wlp/usr/servers/defaultServer/mpopenapi/images/custom-logo.png /config/mpopenapi/images/custom-logo.png
