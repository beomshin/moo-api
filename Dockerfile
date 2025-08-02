# 1단계: 빌드
FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /app

# gradle 빌드 결과 복사
COPY target/*.jar app.jar

# 2단계: 실제 실행 이미지
FROM eclipse-temurin:21-jre-alpine

# JVM 환경 설정 (옵션)
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=80.0 -Xms256m -Xmx1024m"

WORKDIR /app

COPY --from=builder /app/app.jar .

# 실행
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]