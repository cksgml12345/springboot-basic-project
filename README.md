# springboot-basic-project

대학교 포트폴리오용 Spring Boot 프로젝트입니다.

## 기술 스택
- Java 17
- Spring Boot 3
- Gradle
- Spring Web
- Validation
- H2 Database
- Swagger(OpenAPI)

## 실행 방법
```bash
./gradlew bootRun
```

Wrapper 파일이 없으면 1회 생성:
```bash
gradle wrapper
```

## API 확인
- 홈: `GET http://localhost:8080/`
- 프로젝트 목록: `GET http://localhost:8080/api/projects`
- H2 콘솔: `http://localhost:8080/h2-console`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- 게시글 페이지 조회: `GET http://localhost:8080/api/posts/page?page=0&size=10`

## CI/CD
- GitHub Actions: [ci.yml](.github/workflows/ci.yml)
- `main` push / PR 시 자동으로 `./gradlew test`, `./gradlew bootJar` 실행

## Docker 배포
1. 빌드
```bash
./gradlew bootJar
```
2. 컨테이너 실행
```bash
docker compose up --build
```

## 성능 개선 포인트(적용됨)
- 게시글 상세/페이지 조회 캐시 적용
- 게시글 페이지네이션 API 추가
- 게시글 테이블 인덱스(`author_id`, `title`) 추가
- `spring.jpa.open-in-view=false` 적용
