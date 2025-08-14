# 1단계: 빌드
FROM gradle:8.9-jdk21 AS build
WORKDIR /src
COPY . .
RUN gradle --no-daemon clean installDist

# 2단계: 실행
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /src/build/install/test_postgresql/ /app/

# 기본 DB 설정 (실행 시 덮어쓸 수 있음)
ENV DB_URL=jdbc:postgresql://host.docker.internal:5432/postgres \
    DB_USER=postgres \
    DB_PASS=1234

ENTRYPOINT ["./bin/test_postgresql"]