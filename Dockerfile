FROM adoptopenjdk:11-jre-hotspot
WORKDIR /opt/app
COPY ./data /opt/app
CMD ["java", "-jar", "MerlyBot-1.0.jar"]