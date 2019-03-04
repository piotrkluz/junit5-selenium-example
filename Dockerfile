FROM goyalzz/ubuntu-java-8-maven-docker-image

#Cache dependencies
COPY pom.xml /app/pom.xml
RUN cd app && mvn compile
#copy rest of files
COPY . /app

WORKDIR /app