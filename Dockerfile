# Amazon Corretto 17을 기반 이미지로 설정 (JDK 17 포함)
FROM amazoncorretto:17

# 애플리케이션을 빌드하기 위한 Maven 명령어 설정
CMD ["./mvnw", "clean", "package"]

# 빌드 결과 JAR 파일의 경로를 ARG로 설정
ARG JAR_FILE=target/*.jar

# 빌드된 JAR 파일을 이미지 내 app.jar로 복사
COPY ./build/libs/*.jar app.jar

# 컨테이너 시작 시 JAR 파일을 실행하는 명령어 설정
ENTRYPOINT ["java", "-jar", "app.jar"]