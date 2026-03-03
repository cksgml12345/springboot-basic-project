# springboot-basic-project

대학교 포트폴리오용 Spring Boot 프로젝트입니다.

## 기술 스택
- Java 17
- Spring Boot 3
- Gradle
- Spring Web
- Validation
- H2 Database

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

## 다음 확장 아이디어
- JPA Entity + Repository로 프로젝트/경력 데이터 DB 저장
- Swagger(OpenAPI) 문서화
- GitHub Actions CI 추가
