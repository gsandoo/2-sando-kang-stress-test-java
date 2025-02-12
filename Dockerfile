# 베이스 이미지
FROM openjdk:17-jdk-alpine

# 컨테이너 내 작업 디렉토리 설정
WORKDIR /app

# JAR 파일을 컨테이너의 /app 디렉토리에 복사
COPY ./build/libs/stress-test-0.0.1-SNAPSHOT.jar .

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "stress-test-0.0.1-SNAPSHOT.jar"]

# 포트 노출
EXPOSE 8080