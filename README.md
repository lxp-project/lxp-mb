# 초기 설정
- 해당 repo git clone 후 각자 java project 생성
- git remote add [사용할 repogitory] 후 각자 push(해당 개인 프로젝트 시작점)
- git remote 참고사이트: https://inpa.tistory.com/entry/GIT-%E2%9A%A1%EF%B8%8F-%EA%B9%83%ED%97%99-%EC%9B%90%EA%B2%A9-%EC%A0%80%EC%9E%A5%EC%86%8C-%EA%B4%80%EB%A6%AC-git-remote
- checkstyle 설정은 

# 팀 공통 설정 파일

Java + Gradle 프로젝트의 코드 스타일 통일 및 GitHub 협업 워크플로우를 위한 공통 설정 모음입니다.
팀원은 이 repo의 파일을 본인 프로젝트에 복붙하여 동일한 환경을 구성합니다.

---

## 전체 파일 구조

```
.
├── .editorconfig                            # 에디터 기본 포맷 규칙 (들여쓰기, 인코딩 등)
├── build.gradle                             # Gradle + Checkstyle 설정 참고용
│
├── config/
│   └── checkstyle/
│       └── google_checks.xml               # Java 코드 스타일 규칙 (Google Java Style 기반)
│
└── .github/
    ├── workflows/
    │   └── ci.yml                          # PR 생성 시 자동 실행 (Checkstyle + 빌드 + 테스트)
    │
    ├── PULL_REQUEST_TEMPLATE.md            # PR 작성 양식 (PR 생성 시 자동으로 양식 적용)
    │
    └── ISSUE_TEMPLATE/
        ├── feat.md                         # [FEAT] 새 기능 구현 Issue
        ├── fix.md                          # [FIX] 버그 수정 Issue
        ├── refactor.md                     # [REFACTOR] 리팩토링 Issue
        ├── docs.md                         # [DOCS] 문서 수정 Issue
        ├── test.md                         # [TEST] 테스트 코드 작성 Issue
        └── code_review_request.md          # [REVIEW] 코드 리뷰 / 의견 요청
```

---

## 팀원 초기 세팅 방법 (처음 한 번만)

### 1단계: 파일 복사

이 repo의 파일을 본인 프로젝트(repo 최상단)에 복사합니다.

```
본인 프로젝트 repo 최상단/
│
├── .github/                     ← 여기에 복사 (GitHub가 repo 최상단에서만 인식)
│   ├── workflows/ci.yml         ← PR 시 CI 자동 실행
│   ├── PULL_REQUEST_TEMPLATE.md ← PR 양식 자동 적용
│   └── ISSUE_TEMPLATE/          ← Issue 양식 자동 적용
│
├── .editorconfig                ← 여기에 복사 (IntelliJ가 루트부터 탐색)
│
└── config/
    └── checkstyle/
        └── google_checks.xml    ← 여기에 복사
```

> `.github/` 폴더가 repo 최상단에 없으면 PR 템플릿, CI, Issue 템플릿이 모두 동작하지 않습니다.
> `.editorconfig` 가 repo 최상단에 없으면 IntelliJ 포맷 규칙이 적용되지 않습니다.

### 2단계: build.gradle에 Checkstyle 추가

기존 `build.gradle`에 아래 내용을 추가합니다.

```groovy
plugins {
    id 'java'
    id 'checkstyle'   // 추가
}

checkstyle {
    toolVersion = '10.12.17'
    configFile = file('config/checkstyle/google_checks.xml')
    ignoreFailures = false
    showViolations = true
}

// 테스트 코드는 Checkstyle 검사 제외
checkstyleTest {
    enabled = false
}
```

### 3단계: IntelliJ Checkstyle 플러그인 설치

```
Settings → Plugins → Marketplace
→ CheckStyle-IDEA 검색 후 Install → IntelliJ 재시작

Settings → Tools → Checkstyle
→ Checkstyle version: 10.12.17 선택  ← build.gradle 버전과 반드시 일치
→ [+] 클릭 → "Use a local Checkstyle file" 선택
→ Browse → config/checkstyle/google_checks.xml 선택
→ "Store relative to project location" 체크
→ Next → Finish → Active 체크
→ OK
```

### 4단계: GitHub 라벨 등록(선택)

Issue 템플릿에 지정된 라벨을 GitHub에 미리 만들어두어야 합니다.

```
Repository → Issues → Labels → New label

feature        색상: #1D9E75
bug            색상: #E24B4A
refactor       색상: #7F77DD
documentation  색상: #378ADD
test           색상: #BA7517
review         색상: #D4537E
```

### 5단계: 로컬에서 Checkstyle 확인

```bash
./gradlew checkstyleMain
```

---

## Issue 템플릿 종류

| 파일 | 제목 형식 | 사용 시점 |
| --- | --- | --- |
| `feat.md` | `[FEAT] 기능명` | 새로운 기능 구현 시작 전 |
| `fix.md` | `[FIX] 버그 내용` | 버그 발견 후 수정 시작 전 |
| `refactor.md` | `[REFACTOR] 대상` | 코드 구조 개선 시작 전 |
| `docs.md` | `[DOCS] 내용` | README 등 문서 수정 시작 전 |
| `test.md` | `[TEST] 대상` | 테스트 코드 작성 시작 전 |
| `code_review_request.md` | `[REVIEW] 내용` | 코드 구조나 방향에 대해 의견이 필요할 때 |

---

## PR 템플릿 항목

PR을 생성하면 `PULL_REQUEST_TEMPLATE.md` 양식이 자동으로 적용됩니다.

| 항목 | 설명 |
| --- | --- |
| 어떤 작업을 했나요 | 구현 내용 한 줄 요약 |
| 변경 사항 | 주요 변경 파일과 이유 |
| 스크린샷 | UI 변경 시 첨부 |
| 테스트 방법 | 리뷰어가 직접 확인할 수 있는 방법 |
| 리뷰 포인트 | 특히 봐줬으면 하는 부분 |
| 체크리스트 | Checkstyle, 주석, 예외 처리, 테스트 여부 |

PR 본문 맨 아래에 반드시 Issue 번호를 연결합니다.

```
Closes #1
```

---

## 브랜치 네이밍 규칙

```
feature/#이슈번호-작업내용    예: feature/#1-login-page
fix/#이슈번호-버그내용        예: fix/#7-null-pointer-exception
refactor/#이슈번호-대상       예: refactor/#12-user-service
docs/#이슈번호-내용           예: docs/#3-readme-update
test/#이슈번호-대상           예: test/#9-user-service-test
```

---

## 커밋 메시지 규칙

```
feat: 새로운 기능 추가
fix: 버그 수정
refactor: 코드 리팩토링 (기능 변경 없음)
docs: 문서 수정
test: 테스트 코드 추가 또는 수정
chore: 빌드, 설정 파일 수정
```

예시:
```
feat: 로그인 기능 구현 (#1)
fix: 회원가입 시 NPE 수정 (#7)
refactor: UserService 메서드 분리 (#12)
```

---

## 주요 코드 스타일 규칙

| 항목 | 규칙 |
| --- | --- |
| 최대 줄 길이 | 100자 |
| 클래스명 | PascalCase (예: `UserService`) |
| 메서드 / 변수명 | camelCase (예: `getUserById`) |
| 상수명 | UPPER_SNAKE_CASE (예: `MAX_RETRY`) |
| 패키지명 | 소문자 (예: `com.example.project`) |
| 중괄호 | 같은 줄에, 항상 필수 |
| `import *` | 금지 |
| 메서드 최대 길이 | 60줄 |
