# S11P11E101 ground rules

### 커밋 메시지 구조

커밋 메시지는 제목, 본문, 그리고 꼬리말로 구성됩니다.

```
[<타입>](<스코프>): <제목>

<본문>

<꼬리말>

```

### 2. 타입(Type)

타입의 첫글자는 대문자로 작성합니다.

- **feat**: 새로운 기능 추가
- **fix**: 버그 수정
- **docs**: 문서 변경
- **style**: 코드 포맷팅, 세미콜론 누락 등 비즈니스 로직에 영향을 주지 않는 변경
- **remove**: 파일 삭제
- **refactor**: 코드 리팩토링, 기능 변경 없이 코드 개선
- **test**: 테스트 추가, 수정
- **chore**: 빌드 과정 또는 보조 도구 수정, 패키지 매니저 설정 등
- **perf**: 성능 향상 관련 변경
- **ci**: CI 구성 파일 및 스크립트 변경
- **wip:** 작업 진행 중 임시 저장

### 3. 스코프(Scope)

변경 사항의 범위를 나타냅니다. 예를 들어, 특정 모듈이나 기능의 이름을 사용할 수 있습니다.

- 예: `auth`, `payment`, `ui`, `backend`

### 4. 제목(Subject)

제목은 변경 사항을 간략하게 설명합니다. 첫 글자는 대문자로 작성하고, 명령문 형식으로 작성합니다.

- 50자를 넘지 않도록 하며, 마지막에 마침표를 찍지 않습니다.
- 예: `feat(auth): Add JWT authentication`

### 5. 본문(Body)

본문은 변경 사항의 이유와 주요 내용을 설명합니다. 필요 시 다음과 같은 규칙을 따릅니다:

- 한 줄에 72자를 넘지 않도록 합니다.
- "어떻게" 보다는 "무엇을", "왜" 변경했는지 설명합니다.
- 예:
    
    ```
    - Add JWT authentication to secure API endpoints
    - Update login method to issue JWT tokens
    - Modify user model to store JWT refresh tokens
    
    ```
   
### Git 브랜치 작성 컨벤션

Git 브랜치 작성 컨벤션은 브랜치 이름을 일관성 있게 관리하고, 각 브랜치의 목적을 명확히 하기 위해 필요합니다.

### 1. 브랜치 타입

브랜치 타입을 명확히 구분하여 브랜치 이름을 작성합니다:

- **feature**: 새로운 기능 개발
- **bugfix**: 버그 수정
- **hotfix**: 긴급 수정
- **release**: 릴리스 준비
- **refactor**: 코드 리팩토링
- **test**: 테스트 관련 작업
- **chore**: 기타 잡무
- **wip**: 작업 진행 중 임시 저장

### 2. 브랜치 네이밍 규칙

브랜치 이름은 `<타입>/<설명>` 형식을 따릅니다. 이슈 번호는 관련된 이슈 트래커의 번호를 사용하고, 설명은 변경 사항을 간략하게 나타냅니다.

- **feature** 브랜치: 새로운 기능 추가
    - 예: `feature/login-main`
    - feature의 main branch는 -main을 작성합니다.
    - feature의 하위 branch는 다음과 같이 작성합니다. (`feature/login/create`)
- **bugfix** 브랜치: 버그 수정
    - 예: `bugfix/fix-login-error`
- **hotfix** 브랜치: 긴급 수정
    - 예: `hotfix/critical-bug-fix`
- **release** 브랜치: 릴리스 준비
    - 예: `release/1.0.0`
- **refactor** 브랜치: 코드 리팩토링
    - 예: `refactor/optimize-auth-module`
- **test** 브랜치: 테스트 관련 작업
    - 예: `test/add-unit-tests`
- **chore** 브랜치: 기타 잡무
    - 예: `chore/update-dependencies`

### 3. 브랜치 관리

- **main**: 항상 배포 가능한 상태를 유지합니다.
- **develop**: 다음 릴리스에 포함될 기능이 합쳐지는 브랜치입니다.
- **feature, bugfix, refactor** 브랜치: 각 기능/수정/리팩토링 작업을 위한 브랜치입니다. 작업이 완료되면 `develop` 브랜치로 병합합니다.
- **release** 브랜치: 릴리스 준비를 위해 사용됩니다. `main`과 `develop` 브랜치로 병합됩니다.
- **hotfix** 브랜치: 긴급 수정이 필요할 때 사용합니다. `main`과 `develop` 브랜치로 병합됩니다.