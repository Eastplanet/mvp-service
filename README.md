# 🚗 [자율 주행 보드를 활용한 무인 주차] 서비스 - MVP(Management for Valet Parking)

## 목차

- [프로젝트 소개](#프로젝트-소개)
    - [프로젝트 설명](#1-프로젝트-설명)
    - [개발 스택 및 개발 툴](#2-개발-스택-및-개발-툴)
    - [팀원 구성 및 역할 분담](#3-팀원-구성-및-역할-분담)
- [프로젝트 정보](#프로젝트-정보)
    - [프로젝트 구조](#1-프로젝트-구조)
    - [포팅 메뉴얼](#2-포팅-메뉴얼)
- [프로젝트 결과물](#프로젝트-결과물)
    - [핵심 알고리즘 소개](#1-핵심-알고리즘-소개)
    - [목표 서비스 구현 및 실제 구현 정도](#2-목표-서비스-구현-및-실제-구현-정도)
- [프로젝트 규칙](#프로젝트-규칙)
    - [커밋 메시지 구조](#1-커밋-메시지-구조)
    - [Git 브랜치 작성 컨벤션](#2-git-브랜치-작성-컨벤션)
    - [브랜치 관리](#3-브랜치-관리)
- [프로젝트 후기](#프로젝트-후기)
- [License](#license)

## 프로젝트 소개

### 1. 프로젝트 설명

**프로젝트 로고**:

**프로젝트 이름**: MVP(Management for Valet Parking)

**프로젝트 개요**:
 이 프로젝트는 자율 주행 보드를 활용해 차량의 무인 주차 시스템을 제공하는 서비스입니다. 주차장 사용자는 주차장 입구에서 간단한 키오스크 조작만으로 주차장을 이용할 수 있으며, 자율 주행 보드가 차량의 입차와 출차를 자동으로 처리합니다. 주차장 관리자는 관리 기능과 이용 통계를 통해 주차장을 효율적으로 운영할 수 있습니다.

**프로젝트 기간**: 2024.07.08 ~ 2024.08.16

**프로젝트 상세 설명**:

1. **Ai를 활용한 번호판 자동 인식**: LPR(License Plate Recognition) 기술을 사용하여 차량 번호판을 자동으로 인식.
2. **3단계 자율 주행 보드**: 라인트레이싱과 강화학습한 Ai모델을 통한 자율 주행 보드를 사용하여 차량의 무인 주차 및 출차를 구현.
3. **장애물 감지 및 대처**: LiDAR 센서를 활용하여 주차장 내 장애물을 감지하고 대처하는 로직 구현.
4. **키오스크 기반 입차/출차**: 간단한 키오스크 조작만으로 입차 및 출차를 진행하여 사용자 편의를 극대화.
5. **관리자 페이지**: 주차장 관리, 정기 주차회원 관리, 주차장 이용 통계 시각화, 시스템 설정 등 다양한 관리 기능 제공.

---

### 2. 개발 스택 및 개발 툴

![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black)
![SpringBoot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Python](https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Jenkins](https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white)
![Nginx](https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white)

- **프론트엔드(FE):** React 18.3.1, JavaScript, TypeScript, CSS3, HTML5, PyQT
- **백엔드(BE):** Java 17, SpringBoot, RabbitMQ 3.13.6
- **데이터베이스(DB):** MySQL
- **인프라(Infrastructure):** Ubuntu, Docker, Jenkins, Nginx
- **임베디드 시스템(EM):** Python 3.11.2, C++, ROS noetic, Mosquitto 2.0.11
- **AI:** Python 3.9.13, Gymnasium, stable-baselines3 2.3.2, highway-env 1.8.2, CUDA 12.1

---

### 3. 팀원 구성 및 역할 분담

- **팀장**: 김동현
  - 강화학습 환경 제작
  - 핵심 알고리즘 설계
  - 실제 환경 조성

- **팀원**: 김세진
  - Backend 개발
  - 통신 시스템 설계
  - 키오스크 앱 개발

- **팀원**: 손우혁
  - 차량 HW 제작
  - 임베디드 개발

- **팀원**: 손원륜
  - 차량 HW 제작
  - 웹 디자인
  - Frontend 개발

- **팀원**: 오동규
  - 인프라 구성
  - Backend 개발

- **팀원**: 조성인
  - 웹 디자인
  - 번호판 인식
  - Frontend 개발


<div align="right">
  
[목차로](#목차)

</div>

## 프로젝트 정보

### 1. 프로젝트 구조

- **Component Structure**

    ![Component Structure]
---

- **ERD(Entity Relationship Diagram)**

    ![ERD]

---

### 2. 포팅 메뉴얼

**Gitlab 내 'exec'폴더 참조**

<div align="right">
  
[목차로](#목차)

</div>

## 프로젝트 결과물

### 1. 핵심 알고리즘 소개

#### **자율 주차를 위한 모델 및 알고리즘**

1. **강화 학습 기반 자율 주차 모델**
    - 심층 강화 학습을 이용한 자율 주차 모델 개발.
    - Soft Actor-Critic (SAC) 알고리즘과 Hindsight Experience Replay(HER) 알고리즘을 통해 학습 효율성 극대화.
    - Mish 활성화 함수를 사용하여 Dying Relu 문제 해결.

2. **환경 이식 및 SLAM 알고리즘**
    - Hector SLAM을 이용한 2D LiDAR 기반 맵 생성.
    - AMCL과 Particle Filter 기반의 Localization 기술을 사용하여 차량의 위치와 헤딩 값을 정확히 도출.

3. **LPR 기술 활용**
    - 차량 번호판을 실시간으로 인식하고, 텍스트로 변환하여 주차 시스템에 반영.

### 2. 목표 서비스 구현 및 실제 구현 정도

- **서비스 목표**:

    - 무인 자율주차 시스템을 통해 주차 공간의 효율성을 높이고, 인명사고를 줄이며 사용자 편의를 증대시키는 서비스 제공.

- **실제 구현 정도**:

    - 무인 주차 시스템의 모든 주요 기능이 계획대로 구현됨.
    - 관리자 페이지를 통해 실시간으로 주차장 상태 및 데이터 관리 가능.
    - 자율 주행 보드와 LPR 시스템의 통합이 완료되어 무인 주차 및 출차 과정이 원활히 작동.


<div align="right">
  
[목차로](#목차)

</div>

## 프로젝트 규칙

### 1. 커밋 메시지 구조

커밋 메시지는 제목, 본문, 그리고 꼬리말로 구성됩니다.

```
[<타입>](<스코프>): <제목>

<본문>

<꼬리말>

```

#### **타입(Type)**

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
- **wip**: 작업 진행 중 임시 저장

#### **스코프(Scope)**

변경 사항의 범위를 나타냅니다. 예를 들어, 특정 모듈이나 기능의 이름을 사용할 수 있습니다.

- 예: `auth`, `payment`, `ui`, `backend`

#### **제목(Subject)**

제목은 변경 사항을 간략하게 설명합니다. 첫 글자는 대문자로 작성하고, 명령문 형식으로 작성합니다.

- 50자를 넘지 않도록 하며, 마지막에 마침표를 찍지 않습니다.
- 예: `feat(auth): Add JWT authentication`

#### **본문(Body)**

본문은 변경 사항의 이유와 주요 내용을 설명합니다. 필요 시 다음과 같은 규칙을 따릅니다:

- 한 줄에 72자를 넘지 않도록 합니다.
- "어떻게" 보다는 "무엇을", "왜" 변경했는지 설명합니다.
- 예:
    
    ```
    - Add JWT authentication to secure API endpoints
    - Update login method to issue JWT tokens
    - Modify user model to store JWT refresh tokens
    
    ```

---

### 2. Git 브랜치 작성 컨벤션

Git 브랜치 작성 컨벤션은 브랜치 이름을 일관성 있게 관리하고, 각 브랜치의 목적을 명확히 하기 위해 필요합니다.

#### **브랜치 타입**

브랜치 타입을 명확히 구분하여 브랜치 이름을 작성합니다:

- **feature**: 새로운 기능 개발
- **bugfix**: 버그 수정
- **hotfix**: 긴급 수정
- **release**: 릴리스 준비
- **refactor**: 코드 리팩토링
- **test**: 테스트 관련 작업
- **chore**: 기타 잡무
- **wip**: 작업 진행 중 임시 저장

#### **브랜치 네이밍 규칙**

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

---

### 3. 브랜치 관리

- **main**: 항상 배포 가능한 상태를 유지합니다.
- **develop**: 다음 릴리스에 포함될 기능이 합쳐지는 브랜치입니다.
- **feature, bugfix, refactor** 브랜치: 각 기능/수정/리팩토링 작업을 위한 브랜치입니다. 작업이 완료되면 `develop` 브랜치로 병합합니다.
- **release** 브랜치: 릴리스 준비를 위해 사용됩니다. `main`과 `develop` 브랜치로 병합됩니다.
- **hotfix** 브랜치: 긴급 수정이 필요할 때 사용합니다. `main`과 `develop` 브랜치로 병합됩니다.

<div align="right">
  
[목차로](#목차)

</div>

## 프로젝트 후기

(프로젝트 후기에 대한 내용 추가)