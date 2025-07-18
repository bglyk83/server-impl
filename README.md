# server-impl

Spring Boot 기반의 간단한 게시판 프로젝트입니다.

## 주요 기능
- 회원가입, 로그인 (JWT 인증)
- 게시글 작성, 조회, 수정, 삭제
- 마이페이지(회원 정보 조회 및 수정)
- MyBatis Mapper 사용

## 폴더 구조
```
server-impl/
├── src/main/java/com/koreait/jpa/
│   ├── controller/        # REST API 컨트롤러
│   ├── service/           # 비즈니스 로직
│   ├── entity/            # JPA 엔티티
│   ├── dto/               # 데이터 전송 객체
│   ├── component/         # JWT 등 컴포넌트
│   ├── config/            # 시큐리티 설정
│   └── mapper/            # MyBatis 매퍼 인터페이스
├── src/main/resources/
│   ├── static/            # 정적 리소스(HTML, JS)
│   ├── mapper/            # MyBatis XML 매퍼
│   └── application.properties # 환경설정
└── build.gradle           # Gradle 빌드 파일
```

## 실행 방법

1. **DB 준비**
   - MySQL 등 RDBMS에 `member`, `post` 테이블을 생성하세요.
   - `src/main/resources/database.sql` 참고

2. **설정 파일 수정**
   - `src/main/resources/application.properties`에서 DB 접속 정보 등 환경설정

3. **빌드 및 실행**
   ```bash
   ./gradlew bootRun
   ```
   또는
   ```bash
   ./gradlew build
   java -jar build/libs/server-impl-0.0.1-SNAPSHOT.jar
   ```

4. **접속**
   - [http://localhost:8080/login.html](http://localhost:8080/login.html)
  

- 최초 실행 시 DB 테이블이 없으면 에러가 발생할 수 있습니다. `database.sql` 참고하여 테이블을 생성하세요.
