FROM openjdk:11-jre-slim
## 위에 타임좀 설정해서 작성함
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
## 유저 로컬폴더에 jar 생성하겠다
COPY ./build/libs/final-aurora-springboot-0.0.1-SNAPSHOT.jar /usr/local/final-aurora-springboot-0.0.1-SNAPSHOT.jar
RUN chmod +x /usr/local/final-aurora-springboot-0.0.1-SNAPSHOT.jar
## 워크 디렉토리 잡아줌
WORKDIR /usr/local
ENTRYPOINT ["java", "-jar", "final-aurora-springboot-0.0.1-SNAPSHOT.jar"]