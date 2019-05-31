FROM openjdk:8-jre
VOLUME /tmp
RUN echo "Asia/Shanghai" > /etc/timezone
RUN apt-get install -y libfontconfig
#ADD target/store.jar /run/store.jar
ENV PROFILE ""
ENV PASSWORD ""
CMD java $PROFILE $PASSWORD -Djava.security.egd=file:/dev/./urandom -Des.set.netty.runtime.available.processors=false -jar /run/store.jar

# mvn clean install -Dmaven.test.skip=true dockerfile:build
# docker run --net=host --name store --env PROFILE=-Dspring.profiles.active=test --env PASSWORD=-Djasypt.encryptor.password=5042d146667518a1a5017644946b8650aafca44c -v /etc/localtime:/etc/localtime:ro -v /data/store/target:/run -v /home/nas:/mnt -d d2c/store