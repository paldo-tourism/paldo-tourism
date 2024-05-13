# 8DO-TOURISM
2024년 상반기 오르미 4기 파이널 프로젝트

- 스프링 부트 _ 8도관광(고속버스 예매 사이트)
- [배포 URL](http://43.202.23.27:8080)

## 🖥️ 1. 프로젝트 소개
이 프로젝트는 Spring 기반의 고속버스 예매 웹 애플리케이션입니다. <br>
국토교통부(TAGO) API를 활용해 고속버스의 노선 조회 및 예매를 할 수 있습니다. <br>
또한, ESTsoft에서 제공하는 Alan AI를 통해 목적지를 추천받을 수 있습니다.
<br>

## 🕰️ 2. 개발 기간

* 프로젝트 일정: 04/24(수) ~ 05/16(목)
* 프로젝트 발표: 05/17(금)

## 🧑‍🤝‍🧑 3. 멤버 구성
- 김용준 (팀장) : 
- 강한주 (팀원) : 
- 김정용 (팀원) : 
- 박지수 (팀원) : 
- 안태규 (팀원) : 

## ⚙️ 4. 개발 환경
- **Java** : <img src = "https://img.shields.io/badge/Java 17-007396?&logo=java&logoColor=white">
- **IDE** : <img src = "https://img.shields.io/badge/Intellij Idea-000000?&logo=intellijidea&logoColor=white">
- **Framework** : <img src = "https://img.shields.io/badge/Springboot 3.2.5-6DB33F?&logo=springboot&logoColor=white">
- **Database** :  <img src = "https://img.shields.io/badge/Amazon RDS-527FFF?&logo=Amazon RDS&logoColor=white">, <img src = "https://img.shields.io/badge/MySQL-4479A1?&logo=MySQL&logoColor=white">
- **Server** : <img src = "https://img.shields.io/badge/Amazon EC2-FF9900?&logo=amazonec2&logoColor=white">
- **WS/WAS** : <img src = "https://img.shields.io/badge/Apachetomcat-F8DC75?&logo=apachetomcat&logoColor=white">
- **Meeting** : <img src = "https://img.shields.io/badge/Discord-5865F2?&logo=discord&logoColor=white">, <img src = "https://img.shields.io/badge/Notion (Link)-000000?&logo=Notion&logoColor=white">
- **Front-end** : <img src = "https://img.shields.io/badge/HTML-E34F26?&logo=html5&logoColor=white">, <img src = "https://img.shields.io/badge/CSS3-1572B6?&logo=css3&logoColor=white">, <img src = "https://img.shields.io/badge/Javascript-F7DF1E?&logo=javascript&logoColor=white">,  <img src = "https://img.shields.io/badge/Thymeleaf-005F0F?&logo=thymeleaf&logoColor=white">
- **CI/CD** :  <img src = "https://img.shields.io/badge/GitHub Actions-181717?&logo=github&logoColor=white">, <img src = "https://img.shields.io/badge/Amazon EC2-FF9900?&logo=amazonec2&logoColor=white">, <img src = "https://img.shields.io/badge/Amazon S3-569A31?&logo=Amazon S3&logoColor=white">


## 🔨 5. 프로젝트 구조
```
.├─main
│  ├─java
│  │  └─com
│  │      └─estsoft
│  │          └─paldotourism
│  │              ├─config
│  │              ├─controller
│  │              ├─dto
│  │              ├─entity
│  │              ├─exception
│  │              ├─repository
│  │              └─service
│  └─resources
│      ├─static
│      │  ├─css
│      │  ├─img
│      │  └─js
│      └─templates


```
## 6. UI 설계
[figma_link](https://www.figma.com/file/U5E9NIldaSkr28AP0Eebyp/8%EB%8F%84%EA%B4%80%EA%B4%91-%ED%99%94%EB%A9%B4%EC%84%A4%EA%B3%84?type=design&node-id=2-3&mode=design&t=JHOtFtCp44wTFtkn-0)

|||
|-|-|
|<img src="./assets/main-view.png" width="100%">메인 화면|<img src="./assets/ai-view.png" width="100%">AI 추천 화면|
|<img src="./assets/time-table-view.png" width="100%">버스 시간표 화면|<img src="./assets/seat-select-view.png" width="100%">좌석 선택 화면|
|<img src="./assets/payment-view.png" width="100%">결제 화면|<img src="./assets/payment-complete-view.png" width="100%">결제 완료 화면|
|<img src="./assets/qna-board-view.png" width="100%">QnA 메인 화면|<img src="./assets/qna-read-view.png" width="100%">Qna-read 화면|
|<img src="./assets/qna-write-view.png" width="100%">QnA-write 화면|<img src="./assets/login-view.png" width="100%">로그인 화면|
|<img src="./assets/signup-view.png" width="100%">회원가입 화면|<img src="./assets/mypage-reservation-view.png" width="100%">내 예매 정보 화면|
|<img src="./assets/mypage-likes-view.png" width="100%">내 노선 찜 화면|

## 7. ERD 설계
[ERD CLOUD](https://www.erdcloud.com/d/TYp6nBDBZw94StuBx)
<img src="./assets/db.png" width="100%">

## 8. API 명세


## 📌 9. 주요 기능
