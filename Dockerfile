FROM amazoncorretto:17

WORKDIR /app

# 빌드된 JAR 파일을 이미지 내 app.jar로 복사
COPY build/libs/*.jar app.jar

# 실행 시 프로파일 활성화
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]
